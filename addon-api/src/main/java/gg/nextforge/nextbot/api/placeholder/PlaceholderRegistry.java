package gg.nextforge.nextbot.api.placeholder;

public interface PlaceholderRegistry {
    void register(String namespace, PlaceholderProvider provider);
    String resolve(String placeholder, PlaceholderContext ctx);
}
