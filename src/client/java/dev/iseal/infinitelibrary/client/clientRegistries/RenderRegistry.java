package dev.iseal.infinitelibrary.client.clientRegistries;

import dev.iseal.infinitelibrary.client.renderers.SpellBookRenderer;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class RenderRegistry {

    private static RenderRegistry INSTANCE;
    public static RenderRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RenderRegistry();
        }
        return INSTANCE;
    }

    public void initialize() {
        registerSpellBook();
    }

    private void registerSpellBook() {
        BuiltinItemRendererRegistry.INSTANCE.register(
                ItemRegistry.SPELL_BOOK,
                new SpellBookRenderer(SpellBookRenderer.getTexturedModelData().createModel())
        );
    }
}
