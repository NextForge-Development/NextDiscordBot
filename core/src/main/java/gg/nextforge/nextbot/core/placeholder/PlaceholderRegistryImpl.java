package gg.nextforge.nextbot.core.placeholder;

import gg.nextforge.nextbot.api.placeholder.PlaceholderContext;
import gg.nextforge.nextbot.api.placeholder.PlaceholderProvider;
import gg.nextforge.nextbot.api.placeholder.PlaceholderRegistry;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlaceholderRegistryImpl implements PlaceholderRegistry {
    private final Map<String, PlaceholderProvider> providers = new ConcurrentHashMap<>();

    @Override
    public void register(String namespace, PlaceholderProvider provider) {
        providers.put(namespace.toLowerCase(), provider);
    }

    @Override
    public String resolve(String placeholder, PlaceholderContext ctx) {
        int dot = placeholder.indexOf('.');
        if (dot < 0) return placeholder;
        String ns = placeholder.substring(0, dot).toLowerCase();
        String key = placeholder.substring(dot + 1);
        var prov = providers.get(ns);
        if (prov == null) return placeholder;
        try { return String.valueOf(prov.resolve(key, ctx)); }
        catch (Exception e) { return placeholder; }
    }
}
