package dev.iseal.infinitelibrary.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.NetherPortalBlock;

public class EnglishLangProvider extends FabricLanguageProvider {

    protected EnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup.infinitelibrary", "Infinite Library");
        translationBuilder.add("block.infinitelibrary.quartz_bookshelf", "Quartz Bookshelf");
        translationBuilder.add("block.infinitelibrary.activated_chiseled_quartz", "Activated Chiseled Quartz");
        translationBuilder.add("block.infinitelibrary.inactive_chiseled_quartz", "Inactive Chiseled Quartz");
        translationBuilder.add("block.infinitelibrary.old_bookshelf", "Old Bookshelf");
        translationBuilder.add("block.infinitelibrary.old_empty_bookshelf", "Old Empty Bookshelf");
        translationBuilder.add("death.attack.absorb_knowledge", "%1$s's knowledge was absorbed by the Library.");
    }
}
