package dot.funky.intarsia.common.goals;


import dot.funky.intarsia.common.entities.AmethystGolem;
import kotlin.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static dot.funky.intarsia.common.api.Util.RayTraceBlocks;
import static java.lang.Math.min;


public class AmethystGolemGoals {

    public static class InspectCrystalGoal extends Goal {
        private final AmethystGolem mob;
        protected int stareTime;
        protected int totalStareTime;
        protected BlockPos selectedCrystal;
        private boolean locked_eyes = false;
        private int tryTicks;

        public InspectCrystalGoal(AmethystGolem mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canContinueToUse() {
            return stareTime <= totalStareTime && tryTicks <= 1200 && !mob.isDancing() && selectedCrystal.closerToCenterThan(mob.position(), 10.0D) && mob.level.getBlockState(selectedCrystal).is(AmethystGolem.GROWING_TAG);
        }

        public boolean canUse() {
            return (this.mob.getSongTarget() != null && this.mob.level.getBlockState(this.mob.getSongTarget()).is(AmethystGolem.GROWING_TAG) && mob.isOnGround() && !mob.isDancing());


        }

        public void start() {
            tryTicks = 0;
            stareTime = 0;
            selectedCrystal = this.mob.getSongTarget();
            setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
            totalStareTime = mob.getRandom().nextIntBetweenInclusive(60, 120);
            if (!isReachedTarget()) {
                mob.getNavigation().moveTo(selectedCrystal.getX() + 0.5D, selectedCrystal.getY(), selectedCrystal.getZ() + 0.5D, 1D);
            }
        }

        @Override
        public void tick() {
            super.tick();
            if (isReachedTarget()) {
                locked_eyes = true;
                mob.getNavigation().stop();
                this.mob.getLookControl().setLookAt(this.selectedCrystal.getX() + 0.5, this.selectedCrystal.getY() + 0.5, this.selectedCrystal.getZ() + 0.5);
                this.stareTime++;

            } else if (locked_eyes) {
                this.start();
                locked_eyes = false;

            } else {
                tryTicks++;
            }
        }


        private boolean isReachedTarget() {
            Vec3 hor = new Vec3(selectedCrystal.getX(), mob.position().y, selectedCrystal.getZ());
            Vec3 ver = new Vec3(mob.position().x, selectedCrystal.getY(), mob.position().z);
            return this.selectedCrystal.distToCenterSqr(ver) <= 16D && selectedCrystal.distToCenterSqr(hor) <= 100D && this.mob.canSee(selectedCrystal);
        }
    }

    public static class ResonateGoal extends Goal {

        private final AmethystGolem mob;
        protected int breakTime;
        protected int totalBreakTime;
        protected int lastBreakProgress = -1;
        protected BlockPos selectedCrystal;
        protected int waitTicks;
        private boolean locked_eyes = false;
        private int tryTicks;
        private boolean lastNotes;

        public ResonateGoal(AmethystGolem mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canContinueToUse() {


            return (this.mob.level.getBlockState(this.selectedCrystal).is(AmethystGolem.GROWN_TAG) || lastNotes) && !this.mob.isDancing() && this.breakTime <= this.totalBreakTime + 4 && this.selectedCrystal.closerToCenterThan(this.mob.position(), 30.0D);
        }

        public boolean canUse() {
            return this.mob.getSongTarget() != null && this.mob.level.getBlockState(this.mob.getSongTarget()).is(AmethystGolem.GROWN_TAG) && !this.mob.isDancing() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.mob.level, this.mob);
        }

        public void start() {
            lastNotes = false;
            this.selectedCrystal = this.mob.getSongTarget();
            this.waitTicks = mob.getRandom().nextIntBetweenInclusive(10, 25);
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.totalBreakTime = 20 + 5 * mob.getRandom().nextIntBetweenInclusive(0, 2);
            this.breakTime = 0;
        }

        public void stop() {
            this.mob.level.destroyBlockProgress(this.mob.getId(), this.selectedCrystal, -1);
            this.mob.setSinging(false);
        }

        public void tick() {

            if (this.isReachedTarget() || lastNotes) {
                locked_eyes = true;
                this.tryTicks--;
                mob.getNavigation().stop();

                this.mob.getLookControl().setLookAt(this.selectedCrystal.getX() + 0.5, this.selectedCrystal.getY() + 1, this.selectedCrystal.getZ() + 0.5);
                if (this.waitTicks-- <= 0) {

                    if (!this.mob.isSinging()) {
                        this.mob.setSinging(true);
                    }
                    if (this.breakTime % 5 == 0 && this.breakTime != 0) {
                        this.mob.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1, mob.getRandom().nextFloat());
                        this.mob.setSongSignal();
                    }

                    ++this.breakTime;
                    int i = (int) ((float) this.breakTime / (float) this.totalBreakTime * 10.0F);
                    if (i != this.lastBreakProgress) {
                        this.mob.level.destroyBlockProgress(this.mob.getId(), this.selectedCrystal, i);
                        this.lastBreakProgress = i;
                    }

                    if (this.breakTime == this.totalBreakTime) {
                        this.mob.level.destroyBlock(this.selectedCrystal, true);
                        this.mob.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1, min(0.5F, mob.getRandom().nextFloat()));
                        this.mob.setSongSignal();
                        lastNotes = true;

                    }


                }
            } else if (locked_eyes) {
                this.stop();
                locked_eyes = false;
            } else {

                tryTicks++;
                if (tryTicks % 160 == 0) {
                    RandomSource rs = mob.getRandom();

                    this.mob.getNavigation().moveTo(mob.position().x + rs.nextIntBetweenInclusive(-5, 5), mob.position().y, mob.position().z + rs.nextIntBetweenInclusive(-5, 5), 1);

                }
                if (tryTicks % 40 == 0 && tryTicks % 160 != 0) {

                    this.mob.getNavigation().moveTo((double) ((float) selectedCrystal.getX()) + 0.5D, selectedCrystal.getY() + 1, (double) ((float) selectedCrystal.getZ()) + 0.5D, 1);

                }
            }


        }


        public boolean isReachedTarget() {

            if (selectedCrystal == null) return false;
            Vec3 hor = new Vec3(selectedCrystal.getX(), this.mob.position().y, selectedCrystal.getZ());
            Vec3 ver = new Vec3(this.mob.position().x, selectedCrystal.getY(), this.mob.position().z);
            boolean los = canSee(selectedCrystal);
            return selectedCrystal.distToCenterSqr(ver) <= 9D && selectedCrystal.distToCenterSqr(hor) <= 100D && los;

        }

        public boolean canSee(BlockPos pos) {
            Vec3 posmid = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            Pair<Boolean, BlockPos> hit = RayTraceBlocks(mob.level, mob.getEyePosition(), posmid, (int) (mob.getEyePosition().distanceTo(posmid) * 10));
            return pos.equals(hit.getSecond());
        }


    }


    public static class SitGoal extends Goal {
        private final AmethystGolem mob;
        private int waitTicks;
        private boolean sitState;

        public SitGoal(AmethystGolem golem) {
            this.mob = golem;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Flag.LOOK));
        }

        public boolean canContinueToUse() {
            return (this.mob.isSitting() || waitTicks < 20);
        }

        public boolean canUse() {
            return this.mob.isSitting() && this.mob.isOnGround();
        }

        public void start() {
            this.mob.getNavigation().stop();
        }

        public void tick() {
            if (sitState && !mob.isSitting()) {
                waitTicks = 0;
            } else if (!sitState && !mob.isSitting()) {
                waitTicks++;
            }


            sitState = mob.isSitting();

        }

        public void stop() {
        }
    }
}


