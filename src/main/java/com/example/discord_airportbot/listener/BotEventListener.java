package com.example.discord_airportbot.listener;

import com.example.discord_airportbot.service.ExchangeRateService;
import com.example.discord_airportbot.service.FlightService;
import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BotEventListener extends ListenerAdapter {

    private final FlightService flightService;
    private final ExchangeRateService exchangeRateService;

    public BotEventListener(FlightService flightService, ExchangeRateService exchangeRateService) {
        this.flightService = flightService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("도움말")) {
            String helpMessage = ":bulb: **항공이 명령어 사용법**\n\n" +
                    ":ledger: **/도움말 - 명령어 사용법을 안내합니다.**\n" +
                    ":airplane: **/항공편 조회 [항공편명] - 항공편명을 조회합니다.**\n" +
                    ":money_with_wings: **/환율 조회 [도움말 참고 국가명] - 오늘의 환율 정보를 조회합니다.**\n" +
                    ":loudspeaker: **환율 조회시 국가입력칸 참고:\n사우디아라비아 :arrow_right: 사우디, 말레이시아 :arrow_right: 말레이지아, 유럽연합국가 :arrow_right: 유로, 덴마크 :arrow_right: 덴마이크, 중국 :arrow_right: 위안화**\n" +
                    ":pushpin: **위 참고사항 준수시 정확한 값이 나옵니다.**";
            event.reply(helpMessage).queue();
        } else if (event.getName().equals("항공편")) {
            String flightId = event.getOption("편명").getAsString();
            ResponseEntity<String> responseEntity = flightService.airLine(flightId);
            String responseData = responseEntity.getBody();

            if (responseData != null && !responseData.isEmpty()) {
                String formattedResponse = flightService.parseFlightDetails(responseData);
                event.reply(formattedResponse).queue();
            } else {
                event.reply("응답데이터 없음").queue();
            }
        } else if (event.getName().equals("환율")) {
            String currencyName = event.getOption("국가명").getAsString();
            Optional<JsonNode> exchangeRate = exchangeRateService.getExchangeRate(currencyName);

            String response = exchangeRateService.formatExchangeRateResponse(exchangeRate);
            event.reply(response).queue();
        }
    }
}