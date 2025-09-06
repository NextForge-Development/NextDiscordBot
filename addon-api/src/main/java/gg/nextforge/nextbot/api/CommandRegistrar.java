package gg.nextforge.nextbot.api;

import net.dv8tion.jda.api.hooks.EventListener;

public interface CommandRegistrar {
    void register(Object commandHandler);
    void registerListener(EventListener listener);
}
