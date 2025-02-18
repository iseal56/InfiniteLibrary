package dev.iseal.infinitelibrary.client.clientRegistries;

import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.screen.PlayerScreenHandler;

public class RenderRegistry {

    private static RenderRegistry INSTANCE;
    public static RenderRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RenderRegistry();
        }
        return INSTANCE;
    }

    public void initialize() {

    }

    /*
    private void registerSpellBook() {
        BuiltinItemRendererRegistry.INSTANCE.register(
                ItemRegistry.SPELL_BOOK,
                new SpellBookRenderer(SpellBookRenderer.getTexturedModelData().createModel())
        );
    }

     */
}
