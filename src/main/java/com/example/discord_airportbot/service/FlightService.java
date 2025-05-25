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

@Service
public class FlightService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity<String> airLine(String flightId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("flight_id", flightId)
                .queryParam("type", "json")
                .encode()
                .build()
                .toUri();

        HttpEntity<?> request = new HttpEntity<>(null);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    public String parseFlightDetails(String responseData) {
        try {
            JsonNode root = objectMapper.readTree(responseData);
            JsonNode items = root.path("response").path("body").path("items");

            if (items.isArray() && items.size() > 0) {
                JsonNode item = items.get(0);

                String airline = item.path("airline").asText();
                String flightId = item.path("flightId").asText();
                String airport = item.path("airport").asText();
                String scheduleDateTime = item.path("scheduleDateTime").asText();
                String estimatedDateTime = item.path("estimatedDateTime").asText();
                String chkinrange = item.path("chkinrange").asText();
                String gatenumber = item.path("gatenumber").asText();
                String remark = item.path("remark").asText();

                return String.format(
                        ":page_facing_up:**해당 편명 검색결과,** \n\n" +
                                ":airplane:  **항공사:**  `%s`\n" +
                                ":ticket:  **편명:**  `%s`\n" +
                                ":triangular_flag_on_post:  **도착지:**  `%s`\n" +
                                ":clock3:  **예정 출발시간:**  `%s`\n" +
                                ":clock9:  **변경 시간:**  `%s`\n" +
                                ":ballot_box_with_check:  **체크인 카운터:**  `%s`\n" +
                                ":8ball:  **게이트 번호:**  `%s`\n" +
                                ":speech_balloon:  **비행기 현황:**  `%s`\n",
                        airline, flightId, airport, scheduleDateTime, estimatedDateTime, chkinrange, gatenumber, remark
                );
            } else {
                return ":no_entry:  **일치하는 비행기 편명이 없습니다, 다시 입력해주세요.**";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}