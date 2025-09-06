package gg.nextforge.nextbot.core;

import gg.nextforge.nextbot.api.*;
import gg.nextforge.nextbot.addons.AddonDescriptor;
import gg.nextforge.nextbot.api.config.ConfigFacade;
import gg.nextforge.nextbot.api.placeholder.PlaceholderRegistry;
import gg.nextforge.nextbot.core.placeholder.PlaceholderRegistryImpl;
import gg.nextforge.nextbot.holder.JdaHolder;
import gg.nextforge.nextbot.properties.BotProperties;
import gg.nextforge.nextbot.services.CommandRegistrarImpl;
import gg.nextforge.nextbot.services.ConfigFacadeImpl;
import gg.nextforge.nextbot.services.CoreLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class CoreAddonContext implements AddonContext {
    private final JdaHolder jdaHolder;
    private final CommandRegistrarImpl commands;
    private final PlaceholderRegistryImpl placeholders;
    private final ConfigFacadeImpl config;
    private final CoreLogger logger;
    private final BotProperties properties;
    private Path dataFolder;
    private AddonInfo info;

    public AddonContext withDataFolder(Path folder, AddonDescriptor desc) {
        var c = new CoreAddonContext(
                jdaHolder, commands, placeholders,
                config.subspace(desc.name()),
                logger.sub(desc.name()),
                properties
        );
        c.dataFolder = folder;
        c.info = new AddonInfo(desc.name(), desc.version(), desc.apiVersion());
        return c;
    }

    public BotProperties properties() { return properties; }

    @Override public net.dv8tion.jda.api.JDA jda() { return jdaHolder.getJda(); }
    @Override public Path dataFolder() { return dataFolder; }
    @Override public CommandRegistrar commands() { return commands; }
    @Override public PlaceholderRegistry placeholders() { return placeholders; }
    @Override public ConfigFacade config() { return config; }
    @Override public Logger logger() { return logger; }
}
