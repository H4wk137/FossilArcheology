package mod.fossil.common.entity.mob;

import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Random;

import mod.fossil.common.Fossil;
import mod.fossil.common.blocks.BlockBreakingRule;
import mod.fossil.common.fossilAI.DinoAIAvoidEntityWhenYoung;
import mod.fossil.common.fossilAI.DinoAIFollowOwner;
import mod.fossil.common.fossilAI.DinoAIGrowup;
import mod.fossil.common.fossilAI.DinoAIPickItem;
import mod.fossil.common.fossilAI.DinoAIStarvation;
import mod.fossil.common.fossilAI.DinoAITargetNonTamedExceptSelfClass;
import mod.fossil.common.fossilAI.DinoAIUseFeeder;
import mod.fossil.common.fossilAI.DinoAIWander;
import mod.fossil.common.fossilEnums.EnumDinoEating;
import mod.fossil.common.fossilEnums.EnumDinoType;
import mod.fossil.common.fossilEnums.EnumOrderType;
import mod.fossil.common.fossilEnums.EnumSituation;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTRex extends EntityDinosaurce
{
    public final int Areas = 15;
    public final float HuntLimit = (float)this.getHungerLimit() * 0.8F;
    private boolean looksWithInterest;
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25052_g;
    public boolean Screaming = false;
    public int SkillTick = 0;
    public int WeakToDeath = 0;
    public int TooNearMessageTick = 0;
    public boolean SneakScream = false;
    private final BlockBreakingRule blockBreakingBehavior;

    public EntityTRex(World var1)
    {
        super(var1);
        this.blockBreakingBehavior = new BlockBreakingRule(this.worldObj, this, 3, 5.0F);
        this.SelfType = EnumDinoType.TRex;
        this.looksWithInterest = false;
        this.texture = "/mod/fossil/common/textures/TRex.png";
        this.setSize(2.5F, 2.5F);
        this.moveSpeed = 0.3F;
        this.health = 10;
        this.attackStrength = 4 + this.getDinoAge();
        this.tasks.addTask(0, new DinoAIGrowup(this, 8, 23));
        this.tasks.addTask(0, new DinoAIStarvation(this));
        this.tasks.addTask(1, new DinoAIAvoidEntityWhenYoung(this, EntityPlayer.class, 8.0F, 0.3F, 0.35F));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(4, new DinoAIFollowOwner(this, this.moveSpeed, 5.0F, 2.0F));
        this.tasks.addTask(5, new DinoAIUseFeeder(this, this.moveSpeed, 24, this.HuntLimit, EnumDinoEating.Carnivorous));
        this.tasks.addTask(6, new DinoAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.porkRaw, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.beefRaw, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.chickenRaw, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.porkCooked, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.beefCooked, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Item.chickenCooked, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Fossil.rawDinoMeat, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(8, new DinoAIPickItem(this, Fossil.cookedDinoMeat, this.moveSpeed, 24, this.HuntLimit));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new DinoAITargetNonTamedExceptSelfClass(this, EntityLiving.class, 16.0F, 50, false));
    }

    public int getHungerLimit()
    {
        return 500;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return !this.isModelized() && this.riddenByEntity == null && !this.isWeak();
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean isYoung()
    {
        return this.getDinoAge() <= 3;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setBoolean("Angry", this.isSelfAngry());
        var1.setBoolean("Sitting", this.isSelfSitting());
        var1.setInteger("WeakToDeath", this.WeakToDeath);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.setSelfAngry(var1.getBoolean("Angry"));
        this.setSelfSitting(var1.getBoolean("Sitting"));
        this.InitSize();
        this.WeakToDeath = var1.getInteger("WeakToDeath");
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.worldObj.getClosestPlayerToEntity(this, 8.0D) != null ? "Trex_Living" : null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "TRex_hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "Trex_Death";
    }

    protected void updateEntityActionState() {}

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return this.onGround ? false : super.isInWater();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.blockBreakingBehavior.execute();

        if (this.health > 0)
        {
            this.field_25054_c = this.field_25048_b;

            if (this.looksWithInterest)
            {
                this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
            }
            else
            {
                this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
            }

            if (this.looksWithInterest)
            {
                this.numTicksToChaseTarget = 10;
            }
        }
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void applyEntityCollision(Entity var1)
    {
        if (var1 instanceof EntityLiving && !(var1 instanceof EntityPlayer) && this.getHunger() < this.getHungerLimit() / 2 && this.onGround && this.getDinoAge() > 3)
        {
            ((EntityLiving)var1).attackEntityFrom(DamageSource.causeMobDamage(this), 10);
        }
    }

    public boolean getSelfShaking()
    {
        return false;
    }

    public float getInterestedAngle(float var1)
    {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * var1) * 0.15F * (float)Math.PI;
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSelfSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    private void handleScream()
    {
        EntityLiving var1 = this.getAttackTarget();

        if (var1 == null)
        {
            this.Screaming = false;
        }
        else
        {
            double var2 = this.getDistanceSqToEntity(var1);

            if (var2 <= (double)(this.width * 4.0F * this.width * 4.0F))
            {
                this.Screaming = true;
            }
            else
            {
                this.Screaming = false;
            }
        }
    }

    /**
     * Disables a mob's ability to move on its own while true.
     */
    protected boolean isMovementCeased()
    {
        return this.isSelfSitting() || this.field_25052_g;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (var1.getEntity() == this)
        {
            return false;
        }
        else
        {
            Entity var3 = var1.getEntity();

            if (var1.damageType.equals("arrow") && this.getDinoAge() >= 3)
            {
                return false;
            }
            else if (var2 < 6 && var3 != null && this.getDinoAge() >= 3)
            {
                return false;
            }
            else if (var2 == 20 && var3 == null)
            {
                return super.attackEntityFrom(var1, 200);
            }
            else
            {
                this.setTarget((EntityLiving)var3);
                return super.attackEntityFrom(var1, var2);
            }
        }
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource var1, int var2)
    {
        var2 = this.applyArmorCalculations(var1, var2);
        var2 = this.applyPotionDamageCalculations(var1, var2);
        this.health -= var2;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return this.isSelfAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0D) : null;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity var1, float var2)
    {
        this.faceEntity(var1, 30.0F, 30.0F);

        if (!this.hasPath())
        {
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, this.getEntityToAttack(), var2, true, false, true, false));
        }

        if ((double)var2 > (double)this.width * 1.6D)
        {
            if (this.onGround)
            {
                double var3 = var1.posX - this.posX;
                double var5 = var1.posZ - this.posZ;
                float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
                this.motionX = var3 / (double)var7 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                this.motionZ = var5 / (double)var7 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;

                if (this.getDinoAge() <= 3)
                {
                    this.motionY = 0.4000000059604645D;
                }

                if (var2 < 5.0F && !this.Screaming)
                {
                    if (this.getDinoAge() >= 3)
                    {
                        this.worldObj.playSoundAtEntity(this, "TRex_scream", this.getSoundVolume() * 2.0F, 1.0F);
                    }

                    this.Screaming = true;
                }
            }
        }
        else
        {
            var1.attackEntityFrom(DamageSource.causeMobDamage(this), this.attackStrength);
        }
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving var1)
    {
        super.onKillEntity(var1);

        if (this.getDinoAge() >= 3)
        {
            this.worldObj.playSoundAtEntity(this, "TRex_scream", this.getSoundVolume() * 2.0F, 1.0F);
        }

        if (!this.isWeak())
        {
            if (var1 instanceof EntityPig)
            {
                this.HandleEating(30);
            }

            if (var1 instanceof EntitySheep)
            {
                this.HandleEating(35);
            }

            if (var1 instanceof EntityCow)
            {
                this.HandleEating(50);
            }

            if (var1 instanceof EntityChicken)
            {
                this.HandleEating(20);
            }

            if (var1 instanceof EntityMob)
            {
                this.HandleEating(20);
            }

            this.heal(5);
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();

        if (FMLCommonHandler.instance().getSide().isClient() && var2 != null && var2.itemID == Fossil.dinoPedia.itemID)
        {
            EntityDinosaurce.pediaingDino = this;
            var1.openGui(var1, 4, this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
            return true;
        }
        else if (this.EOCInteract(var2, var1))
        {
            return true;
        }
        else if (!this.isTamed())
        {
            if (var2 != null)
            {
                if (var2.itemID == Fossil.gen.itemID)
                {
                    if (this.isWeak() && !this.isTamed() && this.getDinoAge() > 3)
                    {
                        this.heal(200);
                        this.HandleEating(500);
                        this.WeakToDeath = 0;
                        this.setTamed(true);
                        this.setOwner(var1.username);
                        --var2.stackSize;
                        return true;
                    }
                    else
                    {
                        if (!this.isWeak())
                        {
                            this.SendStatusMessage(EnumSituation.GemErrorHealth, this.SelfType);
                            --var2.stackSize;
                        }

                        if (this.getDinoAge() <= 3)
                        {
                            this.SendStatusMessage(EnumSituation.GemErrorYoung, this.SelfType);
                        }

                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == var1))
        {
            var1.rotationYaw = this.rotationYaw;
            var1.mountEntity(this);
            this.setPathToEntity((PathEntity)null);
            this.renderYawOffset = this.rotationYaw;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 200;
    }

    public boolean isSelfAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public boolean isSelfSitting()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setSelfAngry(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1 != this.isSelfAngry())
        {
            if (var1)
            {
                this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
            }
            else
            {
                this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
            }
        }
    }

    public void setSelfSitting(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    public void setTamed(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (var1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
            this.texture = "/mod/fossil/common/textures/TRex.png";
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }

    private void InitSize()
    {
        this.setSize((float)(0.5D + 0.5125D * (double)((float)this.getDinoAge())), (float)(0.5D + 0.5125D * (double)((float)this.getDinoAge())));
        this.setPosition(this.posX, this.posY, this.posZ);

        if (this.getDinoAge() > 3 && !this.isTamed())
        {
            this.texture = "/mod/fossil/common/textures/TRex_Adult.png";
        }
        else
        {
            this.texture = "/mod/fossil/common/textures/TRex.png";
        }

        this.attackStrength = 4 + this.getDinoAge();
    }

    public boolean CheckSpace()
    {
        return !this.isEntityInsideOpaqueBlock();
    }

    public boolean HandleEating(int var1)
    {
        if (this.getHunger() >= this.getHungerLimit())
        {
            return false;
        }
        else
        {
            this.increaseHunger(var1);
            this.showHeartsOrSmokeFX(false);

            if (this.getHunger() >= this.getHungerLimit())
            {
                this.setHunger(this.getHungerLimit());
            }

            return true;
        }
    }

    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            if (this.riddenByEntity != null)
            {
                this.riddenByEntity.setPosition(this.posX, this.posY + (double)this.getGLY() * 1.5D, this.posZ);
            }
        }
    }

    private void Flee(Entity var1, int var2)
    {
        int var3 = (new Random()).nextInt(var2) + 1;
        int var4 = (int)Math.round(Math.sqrt(Math.pow((double)var2, 2.0D) - Math.pow((double)var3, 2.0D)));
        boolean var5 = false;
        int var6 = 0;
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = true;
        boolean var10 = true;
        boolean var11 = true;
        float var12 = -99999.0F;
        int var14;

        if (var1.posX <= this.posX)
        {
            var14 = (int)Math.round(this.posX) + var3;
        }
        else
        {
            var14 = (int)Math.round(this.posX) - var3;
        }

        int var15;

        if (var1.posZ <= this.posZ)
        {
            var15 = (int)Math.round(this.posZ) + var4;
        }
        else
        {
            var15 = (int)Math.round(this.posZ) - var4;
        }

        for (int var13 = 128; var13 > 0; --var13)
        {
            if (!this.worldObj.isAirBlock(var14, var13, var15))
            {
                var6 = var13;
                break;
            }
        }

        this.setTamed(false);
        this.setSelfSitting(false);
        this.setPathToEntity(this.worldObj.getEntityPathToXYZ(this, var14, var6, var15, (float)var2, true, false, true, false));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.isWeak())
        {
            this.handleScream();
        }
        else
        {
            this.HandleWeak();
        }

        super.onLivingUpdate();
    }

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        return this.isModelized() ? super.getTexture() : (!this.isBaby() && !this.isTamed() ? "/mod/fossil/common/textures/TRex_Adult.png" : "/mod/fossil/common/textures/TRex.png");
    }

    private boolean isBaby()
    {
        return this.getDinoAge() < 3;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
        if (!this.isInWater())
        {
            if (this.riddenByEntity != null)
            {
                this.motionY += 0.6299999803304672D;
            }
            else
            {
                super.jump();
            }
        }
        else if (!this.onGround)
        {
            this.motionY -= 0.1D;
        }
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(int var1)
    {
        if (this.health > 0)
        {
            this.health += var1;

            if (this.health > 200)
            {
                this.health = 200;
            }
        }
    }

    public boolean isWeak()
    {
        return this.getHunger() < 5 && this.getHealth() <= 20 && this.getDinoAge() > 3 && !this.isTamed();
    }

    private void HandleWeak()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.texture != "/mod/fossil/common/textures/TRexWeak.png")
            {
                this.texture = "/mod/fossil/common/textures/TRexWeak.png";
            }

            ++this.WeakToDeath;

            if (this.WeakToDeath >= 200)
            {
                this.attackEntityFrom(DamageSource.generic, 20);
            }
            else
            {
                this.setTarget((Entity)null);
                this.setPathToEntity((PathEntity)null);
                this.setSelfAngry(false);
            }
        }
    }

    public void ShowPedia(EntityPlayer var1)
    {
        this.PediaTextCorrection(this.SelfType, var1);

        if (this.isTamed())
        {
            Fossil.ShowMessage(OwnerText + this.getOwnerName(), var1);
            Fossil.ShowMessage(AgeText + this.getDinoAge(), var1);
            Fossil.ShowMessage(HelthText + this.health + "/" + 20, var1);
            Fossil.ShowMessage(HungerText + this.getHunger() + "/" + this.getHungerLimit(), var1);

            if (this.getDinoAge() >= 4 && this.isTamed())
            {
                Fossil.ShowMessage(RidiableText, var1);
            }
        }
        else if (!this.isWeak())
        {
            Fossil.ShowMessage(CautionText, var1);
        }
        else
        {
            Fossil.ShowMessage(WeakText, var1);
        }
    }

    public String[] additionalPediaMessage()
    {
        String[] var1 = null;

        if (!this.isTamed())
        {
            var1 = new String[] {UntamedText};
        }
        else
        {
            ArrayList var2 = new ArrayList();

            if (this.getDinoAge() >= 4 && this.isTamed())
            {
                var2.add(RidiableText);
            }

            if (!this.isWeak() && !this.isTamed())
            {
                var2.add(CautionText);
            }

            if (!var2.isEmpty())
            {
                var1 = new String[1];
                var1 = (String[])var2.toArray(var1);
            }
        }

        return var1;
    }

    public void SetOrder(EnumOrderType var1)
    {
        this.OrderStatus = var1;
    }

    public boolean HandleEating(int var1, boolean var2)
    {
        return this.HandleEating(var1);
    }

    public boolean CheckEatable(int var1)
    {
        return false;
    }

    public EntityTRex spawnBabyAnimal(EntityAgeable var1)
    {
        return new EntityTRex(this.worldObj);
    }

    public int getMaxHealth()
    {
        return 200;
    }

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float getSpeedModifier()
    {
        float var1 = 1.0F;

        if (this.getDinoAge() < 3)
        {
            var1 = super.getSpeedModifier();

            if (this.fleeingTick > 0)
            {
                var1 *= 3.0F;
            }
        }
        else if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayerSP)
        {
            EntityPlayerSP var2 = (EntityPlayerSP)this.riddenByEntity;

            if (var2.movementInput.sneak)
            {
                var1 = 5.0F;
            }
        }

        return var1;
    }

    public void updateSize(boolean var1) {}

    public EnumOrderType getOrderType()
    {
        return this.OrderStatus;
    }

    protected int foodValue(Item var1)
    {
        return 0;
    }

    public void HoldItem(Item var1) {}

    public float getGLX()
    {
        return (float)(0.5D + 0.5125D * (double)this.getDinoAge());
    }

    public float getGLY()
    {
        return (float)(0.5D + 0.5125D * (double)this.getDinoAge());
    }

    public EntityAgeable func_90011_a(EntityAgeable var1)
    {
        return this.spawnBabyAnimal(var1);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}
}