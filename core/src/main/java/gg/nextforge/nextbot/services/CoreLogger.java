package gg.nextforge.nextbot.services;

import gg.nextforge.nextbot.api.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CoreLogger implements Logger {
    private final org.slf4j.Logger log = LoggerFactory.getLogger("NextBot");
    private final String prefix;

    public CoreLogger() { this.prefix = "NDB"; }
    private CoreLogger(String prefix) { this.prefix = prefix; }
    public CoreLogger sub(String sub) { return new CoreLogger(sub); }

    @Override public void info(String msg) { log.info("[{}] {}", prefix, msg); }
    @Override public void warn(String msg) { log.warn("[{}] {}", prefix, msg); }
    @Override public void error(String msg) { log.error("[{}] {}", prefix, msg); }
    @Override public void error(String msg, Throwable t) { log.error("[{}] {}", prefix, msg, t); }
}
