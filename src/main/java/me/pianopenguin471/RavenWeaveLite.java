package me.pianopenguin471;

import keystrokesmod.client.main.Raven;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.StartGameEvent;

public class RavenWeaveLite implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("If we don't make it here, we're screwed.");
        EventBus.subscribe(StartGameEvent.Post.class, (startGameEvent) -> Raven.init());
    }
}