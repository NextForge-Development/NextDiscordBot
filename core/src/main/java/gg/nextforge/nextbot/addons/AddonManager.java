package gg.nextforge.nextbot.addons;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import gg.nextforge.nextbot.api.Addon;
import gg.nextforge.nextbot.core.CoreAddonContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

@Slf4j
@Component
public class AddonManager {
    private final Map<String, LoadedAddon> addons = new LinkedHashMap<>();
    private final CoreAddonContext ctx;
    private final YAMLMapper yaml = new YAMLMapper();

    public AddonManager(CoreAddonContext ctx) { this.ctx = ctx; }

    public void loadAll() throws Exception {
        Path dir = Path.of(ctx.properties().getAddonsDir());
        Files.createDirectories(dir);
        try (var stream = Files.list(dir)) {
            for (Path jar : stream.filter(p -> p.toString().endsWith(".jar")).toList()) {
                load(jar);
            }
        }
    }

    public void load(Path jarPath) throws Exception {
        URL url = jarPath.toUri().toURL();
        var cl = new ChildFirstClassLoader(new URL[]{url}, getClass().getClassLoader());
        var desc = readDescriptor(cl);
        if (!"1.0".equals(desc.apiVersion())) {
            throw new IllegalStateException("API mismatch: " + desc.apiVersion());
        }
        Class<?> main = Class.forName(desc.main(), true, cl);
        if (!Addon.class.isAssignableFrom(main))
            throw new IllegalArgumentException("Main is not Addon: " + desc.main());

        Addon addon = (Addon) main.getDeclaredConstructor().newInstance();

        var dataFolder = jarPath.getParent().resolve(desc.name());
        Files.createDirectories(dataFolder);

        var handle = new LoadedAddon(desc.name(), desc, addon, cl, dataFolder);
        addon.onLoad(ctx.withDataFolder(dataFolder, desc));
        addon.onEnable();
        addons.put(desc.name(), handle);
        log.info("Enabled addon {}", desc.name());
    }

    public void unloadAll() {
        addons.values().forEach(this::unloadSafe);
        addons.clear();
    }

    private void unloadSafe(LoadedAddon a) {
        try { a.instance().onDisable(); } catch (Exception e) { log.warn("onDisable fail", e); }
    }

    private AddonDescriptor readDescriptor(ClassLoader cl) throws Exception {
        try (InputStream is = cl.getResourceAsStream("addon.yml")) {
            if (is == null) throw new IllegalArgumentException("addon.yml missing");
            return yaml.readValue(is, AddonDescriptor.class);
        }
    }
}
