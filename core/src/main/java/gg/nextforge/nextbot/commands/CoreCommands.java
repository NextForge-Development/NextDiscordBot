package gg.nextforge.nextbot.commands;

import gg.nextforge.nextbot.api.DiscordCommand;
import gg.nextforge.nextbot.addons.AddonManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoreCommands {
    private final AddonManager addonManager;

    @DiscordCommand(name = "core", description = "Core Management")
    public void core(SlashCommandInteractionEvent e) {
        var sub = e.getSubcommandName();
        if ("reload".equals(sub)) {
            try {
                addonManager.unloadAll();
                addonManager.loadAll();
                e.reply("Core & Addons reloaded.").setEphemeral(true).queue();
            } catch (Exception ex) {
                e.reply("Reload failed: " + ex.getMessage()).setEphemeral(true).queue();
            }
        } else {
            e.reply("Subcommands: reload").setEphemeral(true).queue();
        }
    }
}
