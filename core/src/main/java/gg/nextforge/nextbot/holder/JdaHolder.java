package gg.nextforge.nextbot.holder;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JdaHolder {
    private JDA jda;
}
