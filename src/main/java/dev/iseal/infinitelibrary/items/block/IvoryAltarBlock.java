package dev.iseal.infinitelibrary.items.block;

import dev.iseal.infinitelibrary.registry.ItemRegistry;
import dev.iseal.infinitelibrary.registry.StatusEffectRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static dev.iseal.infinitelibrary.registry.BlockRegistry.blockKey;

public class IvoryAltarBlock extends Block {

    private static final int MAX_SCRAPS = 4;

    // level 15
    private static final int EXPERIENCE_COST_TO_LEAVE = 255;

    public static final IntProperty SCRAPS_INSERTED = IntProperty.of("scraps_of_wisdom_inserted", 0, MAX_SCRAPS);

    public IvoryAltarBlock() {
        super(
                AbstractBlock.Settings.create()
                        .registryKey(blockKey("ivory_altar"))
                        .emissiveLighting((state, world, pos) -> true)
                        .luminance((blockState -> 15))
                        .mapColor(MapColor.BLACK)
                        .instrument(NoteBlockInstrument.BASEDRUM)
                        .strength(50.0F, 1200.0F)
        );
        setDefaultState(getDefaultState().with(SCRAPS_INSERTED, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SCRAPS_INSERTED);
    }

    @Override
    protected ActionResult onUseWithItem(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        if (world.isClient)
            return ActionResult.PASS;

        if (stack.getItem() == ItemRegistry.SCRAPS_OF_WISDOM) {
            if (state.get(SCRAPS_INSERTED) == MAX_SCRAPS) {
                denyUsage(world, pos, stack);
            } else {
                // remove the item from the player's hand
                stack.decrement(1);
                // charge the altar
                world.setBlockState(pos, state.with(SCRAPS_INSERTED, state.get(SCRAPS_INSERTED) + 1));

                // play the sound of charging
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1, 1);
            }
        } else if (stack.getItem() == Items.APPLE) {
            if (state.get(SCRAPS_INSERTED) == MAX_SCRAPS) {
                // remove the item from the player's hand
                stack.decrement(1);

                // reset inserted scraps counter
                world.setBlockState(pos, state.with(SCRAPS_INSERTED, 0));
                // spawn the archivist apple
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, new ItemStack(ItemRegistry.ARCHIVIST_APPLE));
                world.spawnEntity(itemEntity);

                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1, 1);
            } else {
                denyUsage(world, pos, stack);
            }
        } else if (stack.isOf(ItemRegistry.TOME_OF_RETURN)) {

            // remove the item
            player.setStackInHand(hand, Items.AIR.getDefaultStack());

            // add the status effect that will teleport the player later - 5 seconds
            player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffectRegistry.RECALL.value()), 5*20));

        } else if (stack.isEmpty()) {
            if (player.totalExperience < EXPERIENCE_COST_TO_LEAVE) {
                denyUsage(world, pos, stack);
                return ActionResult.SUCCESS;
            }
            player.addExperience(-EXPERIENCE_COST_TO_LEAVE);
            // try leaving the dimension
            player.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(StatusEffectRegistry.RECALL.value()), 5 * 20));
        }

        return ActionResult.SUCCESS;
    }

    private void denyUsage(World world, BlockPos pos, ItemStack stack) {

        // play the sound of denial
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_SAND_STEP, SoundCategory.BLOCKS, 1, 0.3f);

        if (stack.isEmpty()) {
            return;
        }
        // remove the item from the player's hand
        stack.decrement(1);
        // spawn it on top of the altar
        ItemStack newStack = stack.copy();
        newStack.setCount(1);
        world.spawnEntity(new ItemEntity(world, pos.getX()+0.5f, pos.getY()+1, pos.getZ()+0.5f, newStack));
    }
}
