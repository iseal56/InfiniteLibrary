package dev.iseal.infinitelibrary.utils;

import dev.iseal.infinitelibrary.enchantment.effect.AddChargesEnchantmentEffect;
import net.minecraft.block.Block;
import net.minecraft.component.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.TargetedEnchantmentEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

public class Utils {
    private static final int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    /*
        * Executes blockConsumer for every block in the 4 directions of the initialPos
        * @param initialPos The initial position to start from
        * @param blockConsumer The consumer to execute. If the consumer returns false, the loop will break
     */
    public static void executeForEveryConnectedBlock(BlockPos initialPos, Function<BlockPos, Boolean> blockConsumer) {
        for (int[] direction : directions) {
            BlockPos neighbourPos = initialPos.add(direction[0], 0, direction[1]);
            if (!blockConsumer.apply(neighbourPos)) {
                break;
            }
        }
    }

    public static int[] findBlockPosCoordinates(BlockPos[][] array, BlockPos item) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == null) {
                    continue;
                }
                if (array[i][j].getX() == item.getX() && array[i][j].getY() == item.getY() && array[i][j].getZ() == item.getZ()) {
                    return new int[]{i, j}; // Return the coordinates as an array [x, y]
                }
            }
        }
        return null; // Return null if the item is not found
    }

    public static ItemStack addTextToBook(ItemStack bookStack, String[] text, String title, String author) {
        // 1. Collect all lines from portalLocs (replace with your actual data)
        List<String> lines = Arrays.asList(text);

        // 2. Split lines into valid pages
        List<Text> pages = new ArrayList<>();
        List<String> currentPageLines = new ArrayList<>();

        for (String line : lines) {
            // Check if adding this line would exceed the page limit
            List<String> testLines = new ArrayList<>(currentPageLines);
            testLines.add(line);
            Text testPage = Text.literal(String.join("\n", testLines));

            if (testPage.getString().length() > 256) {
                // Finalize the current page
                if (!currentPageLines.isEmpty()) {
                    pages.add(Text.literal(String.join("\n", currentPageLines)));
                    currentPageLines.clear();
                }
            }
            currentPageLines.add(line);
        }

        // Add remaining lines as the last page
        if (!currentPageLines.isEmpty()) {
            pages.add(Text.literal(String.join("\n", currentPageLines)));
        }

        // 3. Write NBT data to the book
        List<RawFilteredPair<Text>> pageList = new ArrayList<>();

        for (Text page : pages) {
            pageList.add(RawFilteredPair.of(page));
        }

        bookStack.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, new WrittenBookContentComponent(RawFilteredPair.of(title), author, 0, pageList, true));

        return bookStack;
    }

    public static boolean hasEnchantmentEffect(ItemStack stack, Class<? extends EnchantmentEntityEffect> effectToCheck, DynamicRegistryManager manager) {
        AtomicBoolean hasEffect = new AtomicBoolean(false);
        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
            for (Component<?> effect : manager.getOrThrow(RegistryKeys.ENCHANTMENT).get(enchantment.getKey().get()).effects()) {
                System.out.println("Effect: " + effect.value().toString());
                System.out.println("Effect class: " + effect.value().getClass());
                for (Object value : (ArrayList<?>) effect.value()) {
                    if (value instanceof TargetedEnchantmentEffect<?> tef) {
                        System.out.println("Effect value: " + tef.effect().toString());
                        if (effectToCheck.isAssignableFrom(tef.effect().getClass())) {
                            hasEffect.set(true);
                            break;
                        }
                    }
                }
            }
        });
        return hasEffect.get();
    }

    /**
     * Gets locations on the perimeter of a cylinder and executes a consumer for each point.
     * @param center The center of the cylinder.
     * @param height The height of the cylinder.
     * @param numberOfPoints The number of points to generate.
     * @param radius The radius of the cylinder.
     * @param rand The random object to use for randomization.
     *
     * @param consumer The consumer to execute for each generated point.
     */
    public static void getCylinderLocations(Vec3d center, int height, int numberOfPoints, double radius, Optional<Random> rand, Consumer<Vec3d> consumer) {
        double angleStep = 2 * Math.PI / numberOfPoints;
        Random random = rand.orElseGet(Random::create);

        for (int i = 0; i < numberOfPoints; i++) {
            double angle = i * angleStep;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            double yOffset = random.nextDouble() * height;

            Vec3d point = center.add(xOffset, yOffset, zOffset);
            consumer.accept(point);
        }
    }

    /**
    * Gets locations on the perimeter of a cylinder and return them as a list
     * @param center The center of the cylinder.
     * @param height The height of the cylinder.
     * @param numberOfPoints The number of points to generate.
     * @param radius The radius of the cylinder.
     * @param rand The random object to use for randomization.
     *
     * @return A list of points on the perimeter of the cylinder.
     */
    public static ArrayList<Vec3d> getCylinderLocations(Vec3d center, int height, int numberOfPoints, double radius, Optional<Random> rand) {
        ArrayList<Vec3d> points = new ArrayList<>();
        getCylinderLocations(center, height, numberOfPoints, radius, rand, points::add);
        return points;
    }

}
