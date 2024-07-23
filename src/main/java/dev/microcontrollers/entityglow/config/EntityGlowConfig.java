package dev.microcontrollers.entityglow.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Info;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import dev.microcontrollers.entityglow.EntityGlow;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class EntityGlowConfig extends Config {
    @Switch(
            name = "Entity Glow",
            description = "Port the 1.9+ entity outline system to 1.8, rendering a glow effect around entities.",
            subcategory = "Toggle"
    )
    public static boolean entityGlow = true;

    @Info(
            text = "May cause performance issues.",
            type = InfoType.WARNING,
            subcategory = "Toggle"
    )
    Runnable info = () -> {
    };

    @Button(
            name = "Join My Discord",
            text = "Click",
            subcategory = "Socials"
    )
    Runnable mixmeticaDiscord = () -> {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/rejfv9kFJj"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Button(
            name = "Check Out My Other Mods",
            text = "Click",
            subcategory = "Socials"
    )
    Runnable modrinthProfile = () -> {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://modrinth.com/user/Microcontrollers"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public EntityGlowConfig() {
        super(new Mod(EntityGlow.NAME, ModType.UTIL_QOL), EntityGlow.MODID + ".json");
        initialize();
    }
}

