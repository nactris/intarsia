package dot.funky.intarsia.common.entities;

import dot.funky.intarsia.Intarsia;
import dot.funky.intarsia.IntarsiaConfig;
import dot.funky.intarsia.common.goals.AmethystGolemGoals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

//todo - sing when allays or parrots are nearby triggering dance
public class AmethystGolem extends AbstractGolem {
    public static final TagKey<Item> AMETHYST_FRAGMENT = ItemTags.create(new ResourceLocation(Intarsia.MODID, "amethyst_fragment"));
    public static final TagKey<Block> GROWN_TAG = BlockTags.create(new ResourceLocation(Intarsia.MODID, "grown_crystal"));
    public static final TagKey<Block> GROWING_TAG = BlockTags.create(new ResourceLocation(Intarsia.MODID, "growing_crystal"));
    public static final EntityDataAccessor<Integer> BUD = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<BlockPos> SONG_TARGET = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.BLOCK_POS);
    public static final EntityDataAccessor<Boolean> SONG_SIGNAL = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SINGING = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(AmethystGolem.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState singAnimationState = new AnimationState();
    public AnimationState sitAnimationState = new AnimationState();
    public AnimationState standAnimationState = new AnimationState();
    public AnimationState danceAnimationState = new AnimationState();
    private BlockPos jukebox;
    private int shedTime;
    private int nextGrow;


    public AmethystGolem(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.00).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BUD, 0);
        this.entityData.define(SINGING, false);
        this.entityData.define(SONG_TARGET, BlockPos.ZERO);
        this.entityData.define(DANCING, false);
        this.entityData.define(SITTING, false);
        this.entityData.define(SONG_SIGNAL, false);

    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new AmethystGolemGoals.SitGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0F, Ingredient.of(ItemTags.create(new ResourceLocation(Intarsia.MODID, "amethyst_fragment"))), false));
        this.goalSelector.addGoal(3, new AmethystGolemGoals.ResonateGoal(this));
        this.goalSelector.addGoal(4, new AmethystGolemGoals.InspectCrystalGoal(this));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5F));
        this.goalSelector.addGoal(1,new FloatGoal(this));


        super.registerGoals();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);


        if (itemstack.is(AMETHYST_FRAGMENT) && (this.getHealth() < this.getMaxHealth() || getBud() < 4)) {

            if (this.level.isClientSide) {
                for (int i = 0; i < 7; ++i) {
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    double d2 = this.random.nextGaussian() * 0.02D;
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.AMETHYST_SHARD) ), this.getRandomX(0.2D), this.getRandomY() + 0.1D, this.getRandomZ(0.2D), d0, d1, d2);

                }
            } else {

                this.heal(25.0F);
                float pitch = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, pitch);
                this.gameEvent(GameEvent.EAT, this);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (getRandom().nextInt(10) == 0 && getBud() < 4) {
                    this.shedTime = this.random.nextInt(200) + 200;
                    setBud(getBud() + 1);
                }
            }
            return InteractionResult.SUCCESS;

        }
        /*
       //  I changed my mind, no petting rocks 4 yall

        else if (!itemstack.is(AMETHYST_FRAGMENT) && this.isOnGround()) {

            if (this.level.isClientSide) {
                NacsWorkshop.LOGGER.info("hello form client sit: {}",!itemstack.is(AMETHYST_FRAGMENT));



            } else {
                NacsWorkshop.LOGGER.info("hello form server sit: {}",!itemstack.is(AMETHYST_FRAGMENT));

            }

            this.setSitting(!this.isSitting());
            return InteractionResult.SUCCESS;


        }*/

        return InteractionResult.PASS;


    }


    @Override
    public void aiStep() {
        if (getSongSignal()) {
            BlockPos target = getSongTarget();
            if (isSinging() && target != null) {
                for (int i = 0; i < 5; i++) {

                    double d = Math.max(9.5, Math.min(11.5, 2D * this.getRandom().nextDouble() + 9.5D));
                    Vec3 targetpos = new Vec3(target.getX(), target.getY(), target.getZ());
                    Position selfpos = this.getEyePosition();
                    Position offset = new Vec3(random.nextDouble(), random.nextDouble(), random.nextDouble());
                    this.level.addParticle(ParticleTypes.NOTE, targetpos.x() + offset.x(), targetpos.y() + offset.y(), targetpos.z() + offset.z(), d / 24.0D, 10.0D, 1.0D);
                    this.level.addParticle(ParticleTypes.NOTE, selfpos.x() + offset.x(), selfpos.y() + offset.y(), selfpos.z() + offset.z(), d / 24.0D, 10.0D, 1.0D);
                }
            }
            setSongSignal(false);
        }

      /*  if (isSinging()){
            double rx = random.nextGaussian()/4;
            double ry = random.nextGaussian()/4;
            double rz = random.nextGaussian()/4;
            BlockPos target = new BlockPos(getSongTarget().getX()+ rx,getSongTarget().getY()+ ry,getSongTarget().getZ()+ rz);
            this.level.addParticle(new VibrationParticleOption(new BlockPositionSource(target), (int) target.distToCenterSqr(this.position()) ), this.getRandomX(1.0D)/4, this.getRandomY()/4 + 0.5D, this.getRandomZ(1.0D)/4, 0, 0, 0);

        } */

        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 4D) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.setDancing(false);
            this.jukebox = null;
        }

        if (this.getBud() < 4 && this.nextGrow-- < 0) {
            nextGrow = random.nextInt(IntarsiaConfig.max_growth_time.get()) + IntarsiaConfig.min_growth_time.get();
            setBud(getBud() + 1);
        } else if (this.getBud() == 4) {
            this.shedTime--;
        }


        if (!this.level.isClientSide && this.shedTime <= 0 && this.getBud() == 4) {
            this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            if (IntarsiaConfig.does_golem_charge.get()){
                this.spawnAtLocation(at.petrak.hexcasting.common.lib.HexItems.CHARGED_AMETHYST);
            }else {
                this.spawnAtLocation(Items.AMETHYST_CLUSTER);
            }

            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.setBud(0);
        }


        super.aiStep();
    }

    @Override
    public float getEyeHeight(Pose p) {
        return super.getEyeHeight(p);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMETHYST_BLOCK_CHIME;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.AMETHYST_CLUSTER_HIT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.AMETHYST_BLOCK_BREAK;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.AMETHYST_CLUSTER_STEP;
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {

        if (data.equals(DANCING)) {
            if (!isSitting() && isDancing()) {
                this.danceAnimationState.start(this.tickCount);
            } else {
                this.danceAnimationState.stop();
            }

        }
        if (data.equals(SINGING)) {
            if (!isDancing() && !isSitting()) {
                if (isSinging()) {
                    this.singAnimationState.start(this.tickCount);
                } else {
                    this.singAnimationState.stop();

                }
            }
        }

        if (data.equals(SITTING)) {

            if (isSitting()) {

                if (isDancing()) {
                    this.danceAnimationState.stop();
                }
                if (isSinging()) {
                    this.singAnimationState.stop();
                }
                this.standAnimationState.stop();
                this.sitAnimationState.start(this.tickCount);
            } else {
                if (isDancing()) {
                    this.danceAnimationState.start(this.tickCount);
                }
                this.sitAnimationState.stop();
                this.standAnimationState.start(this.tickCount);
            }

        }

        super.onSyncedDataUpdated(data);

    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.55f;
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Bud", this.getBud());
        tag.putBoolean("Sitting", this.isSitting());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setBud(tag.getInt("Bud"));
        this.setSitting(tag.getBoolean("Sitting"));
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {

        this.entityData.set(SITTING, sitting);
    }

    public int getBud() {
        return this.entityData.get(BUD);
    }

    public void setBud(int state) {
        this.entityData.set(BUD, Math.max(0, Math.min(4, state)));
    }

    public boolean isSinging() {
        return this.entityData.get(SINGING);
    }

    public void setSinging(boolean sing) {
        this.entityData.set(SINGING, sing);
    }

    public boolean isDancing() {
        return this.entityData.get(DANCING);
    }

    public void setDancing(boolean dance) {
        this.entityData.set(DANCING, dance);
    }

    public BlockPos getSongTarget() {
        return this.entityData.get(SONG_TARGET);
    }

    public void setSongTarget(BlockPos pos) {
        this.entityData.set(SONG_TARGET, pos);
    }

    public boolean getSongSignal() {
        return this.entityData.get(SONG_SIGNAL);
    }

    public void setSongSignal() {
        this.entityData.set(SONG_SIGNAL, true);
    }
        public void setSongSignal(boolean signal) {
        this.entityData.set(SONG_SIGNAL, signal);
    }

    public void setRecordPlayingNearby(BlockPos jukeboxPos, boolean shouldDance) {

        if (!isDancing()) {
            this.jukebox = jukeboxPos;
            this.setDancing(shouldDance);
        } else if (this.jukebox.equals(jukeboxPos) && !shouldDance) {
            this.jukebox = jukeboxPos;
            this.setDancing(false);

        }

    }


}

