package simplepets.brainsynder.nms.v1_15_R1.entities.list;

import net.minecraft.server.v1_15_R1.*;
import simple.brainsynder.nbt.StorageTagCompound;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.passive.IEntityRabbitPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_15_R1.entities.AgeableEntityPet;
import simplepets.brainsynder.nms.v1_15_R1.utils.DataWatcherWrapper;
import simplepets.brainsynder.wrapper.RabbitType;

/**
 * NMS: {@link net.minecraft.server.v1_15_R1.EntityRabbit}
 */
@Size(width = 0.6F, length = 0.7F)
public class EntityRabbitPet extends AgeableEntityPet implements IEntityRabbitPet {
    private static final DataWatcherObject<Integer> RABBIT_TYPE;
    private int by;
    private int bz;

    static {
        RABBIT_TYPE = DataWatcher.a(EntityRabbitPet.class, DataWatcherWrapper.INT);
    }

    private boolean onGroundLastTick = false;
    private int delay = 0;

    public EntityRabbitPet(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
    }
    public EntityRabbitPet(EntityTypes<? extends EntityCreature> type, World world, IPet pet) {
        super(type, world, pet);
        bq = new ControllerJumpRabbit(this);
        moveController = new ControllerMoveRabbit(this);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(RABBIT_TYPE, 0);
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("type"))
            setRabbitType(RabbitType.getByName(object.getString("type")));
        super.applyCompound(object);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setString("type", getRabbitType().name());
        return object;
    }

    public RabbitType getRabbitType() {
        return RabbitType.getByID(this.datawatcher.get(RABBIT_TYPE));
    }

    public void setRabbitType(RabbitType type) {
        this.datawatcher.set(RABBIT_TYPE, type.getId());
    }

/*
    protected void cH() {
        super.cH();
        double d0 = this.moveController.c();
        if (d0 > 0.0D) {
            double d1 = this.motX * this.motX + this.motZ * this.motZ;
            if (d1 < 0.010000000000000002D) {
                this.a(0.0F, 0.0F, 1.0F, 0.1F);
            }
        }

        if (!this.world.isClientSide) {
            this.world.broadcastEntityEffect(this, (byte)1);
        }
    }
*/

    private void reset() {
        this.resetDelay();
        ((ControllerJumpRabbit) this.bq).a(false);
    }

    private void resetDelay() {
        if (this.moveController.c() < 2.2D) {
            this.delay = 5;
        } else {
            this.delay = 1;
        }
    }

    @Override
    public void repeatTask() {
        super.repeatTask();
        this.onGroundLastTick = this.onGround;
    }

    public void mobTick() {
        super.mobTick();
        if (this.delay > 0) {
            --this.delay;
        }

        if (this.onGround) {
            if (!this.onGroundLastTick) {
                this.setJumping(false);
                stop();
            }

            EntityRabbitPet.ControllerJumpRabbit controller = (EntityRabbitPet.ControllerJumpRabbit)this.bq;
            if (!controller.c()) {
                if (this.moveController.b() && this.delay == 0) {
                    PathEntity pathentity = this.navigation.k();
                    Vec3D vec3d = new Vec3D(this.moveController.d(), this.moveController.e(), this.moveController.f());
                    if (pathentity != null && pathentity.f() < pathentity.e()) {
                        vec3d = pathentity.a(this);
                    }

                    this.b(vec3d.x, vec3d.z);
                    this.reseter();
                }
            } else if (!controller.d()) {
                ((ControllerJumpRabbit) bq).a(true);
            }
        }

        this.onGroundLastTick = this.onGround;
    }

    private void stop() {
        if (this.moveController.c() < 2.2D) {
            this.delay = 10;
        } else {
            this.delay = 1;
        }
        ((ControllerJumpRabbit)this.bq).a(false);
    }

    private void b(double d0, double d1) {
        this.yaw = (float)(MathHelper.d(d1 - this.locZ(), d0 - this.locX()) * 57.2957763671875D) - 90.0F;
    }

    public void movementTick() {
        super.movementTick();
        if (by != bz) {
            ++this.by;
        } else if (bz != 0) {
            by = 0;
            bz = 0;
            setJumping(false);
        }
    }

    protected void jump() {
        super.jump();
        double d0 = this.moveController.c();
        if (d0 > 0.0D) {
            double d1 = b(this.getMot());
            if (d1 < 0.01D) {
                this.a(0.1F, new Vec3D(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isClientSide) {
            this.world.broadcastEntityEffect(this, (byte)1);
        }

    }

    public void reseter() {
        this.setJumping(true);
        this.bz = 10;
        this.by = 0;
    }

    public void i(double d0) {
        this.getNavigation().a(d0);
        this.moveController.a(this.moveController.d(), this.moveController.e(), this.moveController.f(), d0);
    }

    private static class ControllerJumpRabbit extends ControllerJump {
        private EntityRabbitPet rabbitPet;
        private boolean d = false;

        public ControllerJumpRabbit(EntityRabbitPet entity) {
            super(entity);
            this.rabbitPet = entity;
        }

        public boolean c() {
            return this.a;
        }

        public boolean d() {
            return this.d;
        }

        public void a(boolean flag) {
            this.d = flag;
        }

        public void b() {
            if (this.a) {
                this.rabbitPet.reseter();
                this.a = false;
            }

        }
    }

    private static class ControllerMoveRabbit extends ControllerMove {
        private final EntityRabbitPet i;
        private double j;

        public ControllerMoveRabbit(EntityRabbitPet entityrabbit) {
            super(entityrabbit);
            this.i = entityrabbit;
        }

        public void a() {
            if (this.i.onGround && !this.i.jumping && !((EntityRabbitPet.ControllerJumpRabbit)this.i.bq).c()) {
                this.i.i(0.0);
            } else if (this.b()) {
                this.i.i(this.j);
            }

            super.a();
        }

        public void a(double d0, double d1, double d2, double d3) {
            if (this.i.isInWater()) {
                d3 = 1.5D;
            }
            super.a(d0, d1, d2, d3);
            if (d3 > 0.0D) {
                this.j = d3;
            }

        }
    }
}
