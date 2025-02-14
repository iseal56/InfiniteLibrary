package dev.iseal.infinitelibrary.client;

import dev.iseal.infinitelibrary.client.clientRegistries.RenderRegistry;
import net.fabricmc.api.ClientModInitializer;

public class ILClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderRegistry.getInstance().initialize();
    }
}
