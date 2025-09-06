package gg.nextforge.nextbot.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gg.nextforge.nextbot.api.config.ConfigFacade;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigFacadeImpl implements ConfigFacade {
    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
    private final Path file;
    private Map<String, Object> data;

    public ConfigFacadeImpl() {
        this.file = Path.of("config.yml");
        this.data = new HashMap<>();
        loadIfExists();
    }

    private ConfigFacadeImpl(Path file) {
        this.file = file;
        this.data = new HashMap<>();
        loadIfExists();
    }

    public ConfigFacadeImpl subspace(String addonName) {
        var folder = Path.of("addons-data");
        try { Files.createDirectories(folder); } catch (IOException ignored) {}
        return new ConfigFacadeImpl(folder.resolve(addonName + ".yml"));
    }

    private void loadIfExists() {
        if (Files.exists(file)) {
            try {
                data = yaml.readValue(Files.readString(file), new TypeReference<>() {});
            } catch (Exception ignored) {
                data = new HashMap<>();
            }
        }
    }

    @Override public Map<String, Object> getAll() { return new HashMap<>(data); }
    @Override public Object get(String path, Object def) { return data.getOrDefault(path, def); }
    @Override public String getString(String path, String def) { return String.valueOf(data.getOrDefault(path, def)); }

    @Override
    public Integer getInt(String path, Integer def) {
        var v = data.get(path);
        if (v instanceof Number n) return n.intValue();
        try { return v == null ? def : Integer.parseInt(v.toString()); }
        catch (Exception e) { return def; }
    }

    @Override
    public Boolean getBool(String path, Boolean def) {
        var v = data.get(path);
        if (v instanceof Boolean b) return b;
        return v == null ? def : Boolean.parseBoolean(v.toString());
    }

    @Override public void set(String path, Object value) { data.put(path, value); }

    @Override
    public void save() {
        try {
            if (file.getParent() != null) Files.createDirectories(file.getParent());
            yaml.writeValue(file.toFile(), data);
        } catch (IOException ignored) {}
    }
}
