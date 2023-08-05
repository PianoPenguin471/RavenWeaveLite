package com.example.mod;

import keystrokesmod.client.main.Raven;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.StartGameEvent;

public class ExampleMod implements ModInitializer {
    @Override
    public void preInit() {
        EventBus.subscribe(StartGameEvent.class, (startGameEvent) -> Raven.init());
    }
}