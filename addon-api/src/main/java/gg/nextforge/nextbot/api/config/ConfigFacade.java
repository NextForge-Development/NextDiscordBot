package gg.nextforge.nextbot.api.config;

import java.util.Map;

public interface ConfigFacade {
    Map<String, Object> getAll();
    Object get(String path, Object def);
    String getString(String path, String def);
    Integer getInt(String path, Integer def);
    Boolean getBool(String path, Boolean def);
    void set(String path, Object value);
    void save();
}
