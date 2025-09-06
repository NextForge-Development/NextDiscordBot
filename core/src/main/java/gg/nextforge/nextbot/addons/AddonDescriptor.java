package gg.nextforge.nextbot.addons;

/**
 * Represents the metadata of an addon.
 *
 * @param name       The name of the addon.
 * @param version    The version of the addon.
 * @param main       The main class of the addon.
 * @param apiVersion The API version the addon is compatible with.
 */
public record AddonDescriptor(String name, String version, String main, String apiVersion) {}