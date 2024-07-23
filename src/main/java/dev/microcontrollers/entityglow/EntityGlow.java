package dev.microcontrollers.entityglow;

import dev.microcontrollers.entityglow.config.EntityGlowConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * The entrypoint of the Example Mod that initializes it.
 *
 * @see Mod
 * @see InitializationEvent
 */
@Mod(modid = EntityGlow.MODID, name = EntityGlow.NAME, version = EntityGlow.VERSION)
public class EntityGlow {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static EntityGlow INSTANCE; // Adds the instance of the mod, so we can access other variables.
    public static EntityGlowConfig config;

    // Register the config and commands.
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new EntityGlowConfig();
    }
}
