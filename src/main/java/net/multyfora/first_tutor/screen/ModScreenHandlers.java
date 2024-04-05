package net.multyfora.first_tutor.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.multyfora.first_tutor.FirstTutor;

public class ModScreenHandlers {
    public static final ScreenHandlerType<GemPolishingScreenHandler> GEM_POLISHING_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(FirstTutor.MOD_ID, "gem_polishing"),
                    new ExtendedScreenHandlerType<>(GemPolishingScreenHandler::new));

    public static final ScreenHandlerType<ParticleAcceleratorControllerScreenHandler> PARTICLE_ACCELERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(FirstTutor.MOD_ID, "particle_accelerator"),
                    new ExtendedScreenHandlerType<>(ParticleAcceleratorControllerScreenHandler::new));

    public static void registerScreenHandlers(){
        FirstTutor.LOGGER.info("Registering screen handlers for " + FirstTutor.MOD_ID);
    }
}
