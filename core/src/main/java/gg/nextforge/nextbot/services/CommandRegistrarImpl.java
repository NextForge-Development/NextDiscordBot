package gg.nextforge.nextbot.services;

import gg.nextforge.nextbot.api.CommandRegistrar;
import gg.nextforge.nextbot.api.DiscordCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CommandRegistrarImpl implements CommandRegistrar, EventListener {
    private final Map<String, Handler> handlers = new HashMap<>();
    private JDA jda;

    public void attach(JDA jda) {
        this.jda = jda;
        jda.addEventListener(this);
    }

    @Override
    public void register(Object handler) {
        for (Method m : handler.getClass().getDeclaredMethods()) {
            var ann = m.getAnnotation(DiscordCommand.class);
            if (ann != null) {
                m.setAccessible(true);
                handlers.put(ann.name(), new Handler(handler, m));
                log.info("Registered /{} ({})", ann.name(), handler.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void registerListener(EventListener listener) {
        if (jda != null) jda.addEventListener(listener);
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof SlashCommandInteractionEvent e) {
            var h = handlers.get(e.getName());
            if (h != null) {
                try { h.method().invoke(h.target(), e); }
                catch (Exception ex) {
                    log.error("Command error", ex);
                    e.reply("Error executing command.").setEphemeral(true).queue();
                }
            }
        }
    }

    private record Handler(Object target, Method method) {}
}
