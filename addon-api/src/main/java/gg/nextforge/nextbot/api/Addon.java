package gg.nextforge.nextbot.api;

public interface Addon {
    void onLoad(AddonContext ctx) throws Exception;
    void onEnable() throws Exception;
    void onDisable() throws Exception;
    AddonInfo info();
}
