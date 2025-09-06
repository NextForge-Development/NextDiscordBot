package gg.nextforge.nextbot.addons;

import gg.nextforge.nextbot.api.Addon;
import java.nio.file.Path;

public record LoadedAddon(
        String id,
        AddonDescriptor descriptor,
        Addon instance,
        ClassLoader classLoader,
        Path dataFolder
) {}
