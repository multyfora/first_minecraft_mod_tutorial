package net.multyfora.first_tutor.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.multyfora.first_tutor.FirstTutor;

public class CoolItem extends Item {
    public CoolItem(Settings settings) {
        super(settings);
    }


    /**
     * makes the entity who is right-clicked fly up by 1f and away from player by vector of length 1
     */
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient) {

            entity.addVelocity(normalize(entity.getX() - user.getX(), entity.getZ() - user.getZ())[0], 1f,
                                normalize(entity.getX() - user.getX(), entity.getZ() - user.getZ())[1]);
            FirstTutor.LOGGER.info("target entity velocity after hit: " + entity.getVelocity());
        }
        user.getStackInHand(hand).damage(1, user,
                playerEntity -> playerEntity.sendToolBreakStatus(hand));

        return ActionResult.SUCCESS;
    }

    /**
     * takes 2 vector coordinates and returns the normalized vector coordinates (length exactly 1)
     */
    public static double[] normalize(double x, double y) {

        double L = vecLength(x,y);
        double[] newVec = {x/L,y/L};

        return newVec;
    }

    /**
     * returns vectors length
     */
    public static double vecLength(double x, double y){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }
}
