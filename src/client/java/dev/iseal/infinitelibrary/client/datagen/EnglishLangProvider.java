package dev.iseal.infinitelibrary.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.registry.RegistryWrapper;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class EnglishLangProvider extends FabricLanguageProvider {

    protected EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        super(dataOutput, future);
    }

    private final HashMap<String, Boolean> generateSetNames = new HashMap<>();

    {

        generateSetNames.put("polished_ivory", true);
        generateSetNames.put("ivory_brick", false);
        generateSetNames.put("gilded_ivory_brick", false);
        generateSetNames.put("gilded_polished_ivory", true);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {

        for (String setName : generateSetNames.keySet()) {
            // decide whether to generate default block name
            String formattedName = formatSetName(setName);
            if (generateSetNames.get(setName))
                translationBuilder.add("item.infinitelibrary." + setName, formattedName);
            translationBuilder.add("item.infinitelibrary." + setName + "_wall", formattedName + " Wall");
            translationBuilder.add("item.infinitelibrary." + setName + "_stairs", formattedName + " Stairs");
            translationBuilder.add("item.infinitelibrary." + setName + "_slab", formattedName + " Slab");
        }

        translationBuilder.add("itemGroup.infinitelibrary.blocks_group", "Infinite Library Blocks");
        translationBuilder.add("itemGroup.infinitelibrary.items_group", "Infinite Library Items");

        // blocks
        translationBuilder.add("item.infinitelibrary.ivory_bookshelf", "Ivory Bookshelf");
        translationBuilder.add("item.infinitelibrary.gleaming_chiseled_ivory", "Gleaming Chiseled Ivory");
        translationBuilder.add("item.infinitelibrary.dull_chiseled_ivory", "Dull Chiseled Ivory");
        translationBuilder.add("item.infinitelibrary.old_bookshelf", "Old Bookshelf");
        translationBuilder.add("item.infinitelibrary.old_empty_bookshelf", "Old Empty Bookshelf");

        // ivory stuff
        translationBuilder.add("item.infinitelibrary.chiseled_ivory", "Chiseled Ivory");
        translationBuilder.add("item.infinitelibrary.ivory_bricks", "Ivory Bricks");
        translationBuilder.add("item.infinitelibrary.ivory_pillar", "Ivory Pillar");

        // gilded ivory stuff
        translationBuilder.add("item.infinitelibrary.gilded_chiseled_ivory", "Gilded Chiseled Ivory");
        translationBuilder.add("item.infinitelibrary.gilded_ivory_bricks", "Gilded Ivory Bricks");
        translationBuilder.add("item.infinitelibrary.gilded_ivory_pillar", "Gilded Ivory Pillar");

        // death messages
        translationBuilder.add("death.attack.absorb_knowledge", "%1$s's knowledge was absorbed by the Library.");

        // items
        translationBuilder.add("item.infinitelibrary.ivory_brick", "Ivory Brick");
        translationBuilder.add("item.infinitelibrary.pale_sword", "Pale Sword");
        translationBuilder.add("item.infinitelibrary.spell_book", "Spell Book");

        // effects
        translationBuilder.add("effect.infinitelibrary.hubris", "Hubris");
        translationBuilder.add("effect.infinitelibrary.knowledge", "Knowledge");
    }

    private String formatSetName(String setName) {
        StringBuilder formattedName = new StringBuilder();
        boolean toUpperCase = true;
        boolean first = true;
        for (char c : setName.toCharArray()) {
            if (c == '_') {
                toUpperCase = true;
            } else {
                if (toUpperCase) {
                    if (!first)
                        formattedName.append(" ");
                    formattedName.append(Character.toUpperCase(c));
                    toUpperCase = false;
                    first = false;
                } else {
                    formattedName.append(c);
                }
            }
        }
        return formattedName.toString();
    }
}
