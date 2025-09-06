package gg.nextforge.nextbot.api.placeholder;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public record PlaceholderContext(User user, Member member, Guild guild) {}
