package com.example.discord_airportbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.discord_airportbot.listener.BotEventListener;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;

@Configuration
public class BotConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${Token}")
    private String Token;

    @Bean
    public JDA jda(BotEventListener listener) throws LoginException {
        JDA jda = JDABuilder.createDefault(Token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(listener)
                .setActivity(Activity.playing("✈️ 항공알림이, 항공이입니다"))
                .build();

        // 명령어 등록
        jda.updateCommands().addCommands(
                Commands.slash("도움말", "봇 사용법을 안내합니다."),
                Commands.slash("항공편", "항공편명을 조회합니다.")
                        .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "편명", "조회할 항공편명", true),
                Commands.slash("환율", "환율 정보를 조회합니다, 💜 도움말 참고")
                        .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "국가명", "조회할 환율의 국가명을 입력합니다.", true)
        ).queue();

        return jda;
    }
}
