package gg.nextforge.nextbot;

import gg.nextforge.nextbot.addons.AddonManager;
import gg.nextforge.nextbot.services.CommandRegistrarImpl;
import gg.nextforge.nextbot.services.JdaHolder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotProperties.class)
@RequiredArgsConstructor
public class NextBotApplication {
    private final BotProperties props;
    private final JdaHolder jdaHolder;
    private final AddonManager addonManager;
    private final CommandRegistrarImpl commands;

    public static void main(String[] args) {
        SpringApplication.run(NextBotApplication.class, args);
    }

    @PostConstruct
    void start() throws Exception {
        var builder = JDABuilder.createDefault(props.token())
                .enableIntents(props.intentsResolved());
        var jda = builder.build().awaitReady();
        jdaHolder.set(jda);

        // attach command bus to JDA
        commands.attach(jda);

        // load addons
        addonManager.loadAll();
    }
}
