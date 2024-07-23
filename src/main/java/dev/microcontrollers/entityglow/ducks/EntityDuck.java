package dev.microcontrollers.entityglow.ducks;

public interface EntityDuck {
    default boolean entityglow$isGlowing() {
        return false;
    }
}
