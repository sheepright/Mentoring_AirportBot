package com.example.discord_airportbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url_1}")
    private String apiUrl;

    @Value("${api.key_1}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<JsonNode> getExchangeRate(String currencyName) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("searchdata", "")
                .queryParam("data", "AP01")
                .encode()
                .build()
                .toUri();

        HttpEntity<?> request = new HttpEntity<>(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                String.class
        );

        try {
            if (responseEntity.getBody() == null || responseEntity.getBody().isEmpty()) {
                return Optional.empty();
            }

            JsonNode root = objectMapper.readTree(responseEntity.getBody());
            for (JsonNode item : root) {
                if (item.path("cur_nm").asText().contains(currencyName)) {
                    return Optional.of(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public String formatExchangeRateResponse(Optional<JsonNode> exchangeRate) {
        if (!exchangeRate.isPresent()) {
            return ":no_entry: **해당 날짜 기준 은행영업일이 아닙니다.**";
        }

        JsonNode item = exchangeRate.get();
        String curUnit = item.path("cur_unit").asText();
        String ttb = item.path("ttb").asText();
        String curNm = item.path("cur_nm").asText();

        return String.format(
                ":moneybag: **환율 정보**\n\n" +
                        ":flag_%s: **통화명:  ** %s\n" +
                        ":dollar: **매매 기준율:  ** %s",
                curUnit.substring(0, 2).toLowerCase(), curNm, ttb
        );
    }
}