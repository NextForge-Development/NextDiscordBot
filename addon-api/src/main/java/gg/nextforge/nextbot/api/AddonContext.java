package gg.nextforge.nextbot.api;

import gg.nextforge.nextbot.api.config.ConfigFacade;
import gg.nextforge.nextbot.api.placeholder.PlaceholderRegistry;
import net.dv8tion.jda.api.JDA;
import java.nio.file.Path;

public interface AddonContext {
    JDA jda();
    Path dataFolder();
    CommandRegistrar commands();
    PlaceholderRegistry placeholders();
    ConfigFacade config();
    Logger logger();
}
