package gg.nextforge.nextbot.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DiscordCommand {
    String name();
    String description() default "";
    boolean guildOnly() default true;
}
