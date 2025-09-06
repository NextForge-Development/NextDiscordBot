package gg.nextforge.nextbot.api.placeholder;

@FunctionalInterface
public interface PlaceholderProvider {
    String resolve(String key, PlaceholderContext ctx);
}
