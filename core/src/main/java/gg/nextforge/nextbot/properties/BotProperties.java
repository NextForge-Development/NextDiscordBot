package gg.nextforge.nextbot.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "bot")
@Getter
public class BotProperties {
    private String token;
    private String addonsDir;
    private List<String> intents;

    public Set<GatewayIntent> intentsResolved() {
        return intents == null ? Set.of() :
                intents.stream().map(GatewayIntent::valueOf).collect(Collectors.toSet());
    }
}
