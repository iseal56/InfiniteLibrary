package dev.iseal.infinitelibrary.client.clientRegistries;

import dev.iseal.infinitelibrary.client.renderers.SpellBookRenderer;

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


    private void registerSpellBook() {
        new SpellBookRenderer(SpellBookRenderer.getTexturedModelData().createModel());
    }
}
