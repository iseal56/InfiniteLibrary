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

        // blocks
        translationBuilder.add("block.infinitelibrary.quartz_bookshelf", "Quartz Bookshelf");
        translationBuilder.add("block.infinitelibrary.gleaming_chiseled_ivory", "Gleaming Chiseled Ivory");
        translationBuilder.add("block.infinitelibrary.dull_chiseled_ivory", "Dull Chiseled Ivory");
        translationBuilder.add("block.infinitelibrary.old_bookshelf", "Old Bookshelf");
        translationBuilder.add("block.infinitelibrary.old_empty_bookshelf", "Old Empty Bookshelf");

        // ivory stuff
        translationBuilder.add("block.infinitelibrary.chiseled_ivory", "Chiseled Ivory");
        translationBuilder.add("block.infinitelibrary.ivory_bricks", "Ivory Bricks");
        translationBuilder.add("block.infinitelibrary.ivory_pillar", "Ivory Pillar");
        translationBuilder.add("block.infinitelibrary.polished_ivory", "Polished Ivory");

        // gilded ivory stuff
        translationBuilder.add("block.infinitelibrary.gilded_chiseled_ivory", "Gilded Chiseled Ivory");
        translationBuilder.add("block.infinitelibrary.gilded_ivory_bricks", "Gilded Ivory Bricks");
        translationBuilder.add("block.infinitelibrary.gilded_ivory_pillar", "Gilded Ivory Pillar");
        translationBuilder.add("block.infinitelibrary.gilded_polished_ivory", "Gilded Polished Ivory");

        // death messages
        translationBuilder.add("death.attack.absorb_knowledge", "%1$s's knowledge was absorbed by the Library.");

        // items
        translationBuilder.add("item.infinitelibrary.ivory_brick", "Ivory Brick");
        translationBuilder.add("item.infinitelibrary.pale_sword", "Inactive Pale Sword");
        translationBuilder.add("item.infinitelibrary.pale_sword_full", "Active Pale Sword");
        translationBuilder.add("item.infinitelibrary.spell_book_item", "Spell Book");

        // effects
        translationBuilder.add("effect.infinitelibrary.hubris", "Hubris");
        translationBuilder.add("effect.infinitelibrary.knowledge", "Knowledge");
    }
}
