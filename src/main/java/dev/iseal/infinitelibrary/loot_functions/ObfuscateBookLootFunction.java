package dev.iseal.infinitelibrary.loot_functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.iseal.infinitelibrary.registry.loot.LootFunctionTypeRegistry;
import dev.iseal.infinitelibrary.utils.DistributedRandomNumberGenerator;
import dev.iseal.infinitelibrary.utils.PortalHelper;
import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ObfuscateBookLootFunction extends ConditionalLootFunction {

    private static ObfuscateBookLootFunction INSTANCE;
    public static ObfuscateBookLootFunction getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObfuscateBookLootFunction(new ArrayList<>(0));
        }
        return INSTANCE;
    }

    public static final MapCodec<ObfuscateBookLootFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addConditionsField(instance).apply(instance, ObfuscateBookLootFunction::new)
    );

    private final HashMap<String, boolean[]> portalBookLocs = new HashMap<>();
    private final DistributedRandomNumberGenerator random = new DistributedRandomNumberGenerator();

    public ObfuscateBookLootFunction(List<LootCondition> conditions) {
        super(conditions);
        random.addNumber(1, 20);
        random.addNumber(2, 80);
    }

    @Override
    public LootFunctionType<ObfuscateBookLootFunction> getType() {
        return LootFunctionTypeRegistry.OBFUSCATE_BOOK;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        if (!stack.isOf(Items.WRITTEN_BOOK))
            return stack;
        int height = 5;
        int width = 4;

        if (portalBookLocs.isEmpty()) {
            PortalHelper portalHelper = new PortalHelper(context.getWorld());
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (x > 0 && x < width-1 && y > 0 && y < height-1) {
                        portalBookLocs.put(x + ", " + y, null);
                        continue;
                    }
                    portalBookLocs.put(x + ", " + y, portalHelper.getRequiredSlots(x, y));
                }
            }
        }

        for (String s : portalBookLocs.keySet()) {
            System.out.println(s);
        }

        String[] portalLocs = new String[3 * height - 1];
        Arrays.fill(portalLocs, "");
        int hiddenParts = 0;
        for (int y = height-1; y >= 0; y--) {
            int actualIndex = (height - 1 - y) * 3; // Start index for current y's lines
            for (int x = 0; x < width; x++) {
                boolean[] locsForXY = portalBookLocs.get(x + ", " + y);
                if (portalBookLocs.get(x + ", " + y) == null) {
                    portalLocs[actualIndex] += x % 2 == 0 ? "     " : "      ";
                    portalLocs[actualIndex+1] += x % 2 == 0 ? "     " : "      ";
                    continue;
                }
                /*
                portalLocs[actualIndex] += (locsForXY[0] ? "B" : "N") + (locsForXY[1] ? "B" : "N") + (locsForXY[2] ? "B" : "N")+" ";
                portalLocs[actualIndex+1] += (locsForXY[3] ? "B" : "N") + (locsForXY[4] ? "B" : "N") + (locsForXY[5] ? "B" : "N")+" ";
                 */
                for (int i = 0; i < 6; i++) {
                    if (random.getDistributedRandomNumber() == 1 && hiddenParts <= 30) {
                        portalLocs[actualIndex + (i / 3)] += "?";
                        hiddenParts++;
                    } else {
                        portalLocs[actualIndex + (i / 3)] += (locsForXY[i] ? "B" : "N");
                    }
                    if (i % 3 == 2) {
                        portalLocs[actualIndex + (i / 3)] += " ";
                    }
                }
            }
        }

        System.out.println("Portal Locations");
        for (String s : portalLocs) {
            System.out.println(s);
        }

        Utils.addTextToBook(stack, portalLocs, "Portal Locations", "Infinite Library");
        return stack;
    }
}
