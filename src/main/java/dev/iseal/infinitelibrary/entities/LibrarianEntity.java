package dev.iseal.infinitelibrary.entities;

import dev.iseal.infinitelibrary.entities.goals.TeleportAwayGoal;
import dev.iseal.infinitelibrary.entities.projectiles.ExperienceOrbProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class LibrarianEntity extends HostileEntity implements Angerable {

    @Nullable
    private UUID angryAt;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    private static final UniformIntProvider ANGER_PASSING_COOLDOWN_RANGE = TimeHelper.betweenSeconds(4, 6);
    private int angerPassingCooldown;
    private TeleportAwayGoal teleportAwayGoal;

    public LibrarianEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initGoals() {
        super.initGoals();
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
        this.goalSelector.add(1, new ShootBulletGoal());
        // teleport always
        teleportAwayGoal = new TeleportAwayGoal(this);
        this.goalSelector.add(2, teleportAwayGoal);
        // flee only if angry
        /*this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, 8, 1.0f, 1.2f) {
            @Override
            public boolean canStart() {
                LivingEntity livingEntity = LibrarianEntity.this.getTarget();
                return super.canStart()
                        && livingEntity != null
                        && livingEntity.isAlive()
                        && LibrarianEntity.this.getWorld().getDifficulty() != Difficulty.PEACEFUL;
            }
        });*/
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tickAngerLogic(world, true);
        if (this.getTarget() != null) {
            tickAngerPassing();
        }

        if (hasAngerTime()) {
            playerHitTimer = age;
        }

        super.mobTick(world);
    }

    @Override
    public boolean shouldAngerAt(LivingEntity target, ServerWorld world) {
        // only accept sneaking players, and check if you already have a target
        if (!(target instanceof PlayerEntity player))
            return false;
        if (player.isCreative() || player.isSpectator())
            return false;
        if (this.getTarget() != null || this.getTarget() == target)
            return false;
        if (!this.getVisibilityCache().canSee(target))
            return false;
        if (player.isSneaking())
            return false;
        return true;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target instanceof PlayerEntity player)
            this.setAttacking(player);

        super.setTarget(target);
    }

    private void angerNearbyLibraryEntities() {
        double d = this.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
        Box box = Box.from(this.getPos()).expand(d, 10.0, d);
        this.getWorld()
                .getEntitiesByClass(LibrarianEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR)
                .stream()
                .filter(librarianEntity -> librarianEntity != this)
                .filter(librarianEntity -> librarianEntity.getTarget() == null)
                .filter(librarianEntity -> !librarianEntity.isTeammate(this.getTarget()))
                .forEach(librarianEntity -> librarianEntity.setTarget(this.getTarget()));

        // TODO: Add more library entities and make them angry
    }

    private void tickAngerPassing() {
        if (this.angerPassingCooldown > 0) {
            this.angerPassingCooldown--;
        } else {
            if (this.getVisibilityCache().canSee(this.getTarget())) {
                this.angerNearbyLibraryEntities();
            }

            this.angerPassingCooldown = ANGER_PASSING_COOLDOWN_RANGE.get(this.random);
        }
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        DefaultAttributeContainer.Builder builder = createHostileAttributes();

        // totally random values xD
        builder.add(EntityAttributes.ARMOR, 3f)
                .add(EntityAttributes.SPAWN_REINFORCEMENTS, 0.0)
                .add(EntityAttributes.ARMOR_TOUGHNESS, 2f)
                .add(EntityAttributes.MAX_HEALTH, 20f)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 5f)
                .add(EntityAttributes.FOLLOW_RANGE, 16f)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(EntityAttributes.ATTACK_KNOCKBACK, 0.5f)
                .add(EntityAttributes.ATTACK_SPEED, 1f);

        return builder;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return stack.isOf(Items.BOOK) || stack.isOf(Items.ENCHANTED_BOOK) || stack.isOf(Items.WRITABLE_BOOK);
    }

    @Override
    public boolean canGather(ServerWorld world, ItemStack stack) {
        return this.canPickupItem(stack);
    }

    @Override
    public boolean shouldDropLoot() {
        return false;
    }

    @Override
    public ItemStack getProjectileType(ItemStack stack) {
        return Items.ENCHANTED_BOOK.getDefaultStack();
    }

    @Override
    public int getAngerTime() {
        return angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Override
    public @Nullable UUID getAngryAt() {
        return angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.angerTime = ANGER_TIME_RANGE.get(this.random);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.getWorld(), nbt);
    }

    class ShootBulletGoal extends Goal {
        private int counter;
        private long barrageCooldown;
        private int amountLeft;

        public ShootBulletGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = LibrarianEntity.this.getTarget();
            return livingEntity != null
                    && livingEntity.isAlive()
                    && livingEntity.getPos().distanceTo(LibrarianEntity.this.getPos()) >= 5
                    && LibrarianEntity.this.getWorld().getDifficulty() != Difficulty.PEACEFUL
                    && barrageCooldown - System.currentTimeMillis() <= 0;
        }

        @Override
        public void start() {
            this.counter = 20;
            this.barrageCooldown = 0;
            this.amountLeft = 5;
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity target = LibrarianEntity.this.getTarget();
            boolean isAlive = target != null && target.isAlive();
            boolean isClose = isAlive && target.getPos().distanceTo(LibrarianEntity.this.getPos()) <= 5;

            if (isClose) {
                // teleport away and return false
                teleportAwayGoal.teleportToSafety();
                return false;
            }
            return isAlive;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (LibrarianEntity.this.getWorld().getDifficulty() != Difficulty.PEACEFUL) {
                this.counter--;
                LivingEntity livingEntity = LibrarianEntity.this.getTarget();
                if (livingEntity != null) {
                    LibrarianEntity.this.getLookControl().lookAt(livingEntity, 180.0F, 180.0F);
                    double d = LibrarianEntity.this.squaredDistanceTo(livingEntity);
                    if (d < 400.0) {
                        if (this.counter <= 0) {
                            this.counter = 5;
                            amountLeft--;
                            LibrarianEntity.this.getWorld()
                                    .spawnEntity(new ExperienceOrbProjectileEntity(LibrarianEntity.this.getWorld(), LibrarianEntity.this, livingEntity));
                            LibrarianEntity.this.playSound(
                                    SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 2.0F, (LibrarianEntity.this.random.nextFloat() - LibrarianEntity.this.random.nextFloat()) * 0.2F + 1.0F
                            );
                        }
                        if (amountLeft <= 0) {
                            barrageCooldown = System.currentTimeMillis()+3000;
                            amountLeft = 5;
                            stop();
                        }
                    } else {
                        LibrarianEntity.this.setTarget(null);
                    }

                    super.tick();
                }
            }
        }
    }
    
}
