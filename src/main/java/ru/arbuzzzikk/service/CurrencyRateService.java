package ru.arbuzzzikk.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.arbuzzzikk.dto.CbrCurrencyResponse;
import ru.arbuzzzikk.model.Currency;
import ru.arbuzzzikk.repository.CurrencyRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private static final String CBR_API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void checkCurrencyRates() {
        CbrCurrencyResponse response = restTemplate.getForObject(CBR_API_URL, CbrCurrencyResponse.class);
        List<Currency> trackedCurrencies = currencyRepository.findAll();

        if (response != null && response.getValute() != null) {
            for (Currency currency : trackedCurrencies) {
                CbrCurrencyResponse.CbrCurrency cbrCurrency = response.getValute().get(currency.getBaseCurrency());
                if (cbrCurrency != null) {
                    checkRateChange(currency, cbrCurrency);
                }
            }
        }
    }

    private void checkRateChange(Currency currency, CbrCurrencyResponse.CbrCurrency cbrCurrency) {
        double changePercent = ((cbrCurrency.getValue() - cbrCurrency.getPrevious()) / cbrCurrency.getPrevious()) * 100;
        String[] rangeParts = currency.getPriceChangeRange().split("/");

        double lowerThreshold = parseThreshold(rangeParts[0]);
        double upperThreshold = parseThreshold(rangeParts[1]);

        if (changePercent <= lowerThreshold || changePercent >= upperThreshold) {
            String direction = changePercent < 0 ? "упал" : "вырос";
            log.warn("{} {} на {:.2f}% (текущий курс: {}, предыдущий: {})",
                    currency.getName(),
                    direction,
                    Math.abs(changePercent),
                    cbrCurrency.getValue(),
                    cbrCurrency.getPrevious());
        }
    }

    private double parseThreshold(String threshold) {
        return Double.parseDouble(threshold.replace("%", "").replace("+", ""));
    }
}
