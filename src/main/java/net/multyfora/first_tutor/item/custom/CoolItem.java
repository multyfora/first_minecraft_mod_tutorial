package net.multyfora.first_tutor.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class CoolItem extends Item {
    public CoolItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient) {

            entity.addVelocity(normalize(entity.getX() - user.getX()), 1f, normalize(entity.getZ() - user.getZ()));




            user.sendMessage(Text.literal("yaw: " + user.headYaw + " vector: " + entity.getVelocity()));
        }
        return ActionResult.SUCCESS;
    }

    public static double normalize(double value) {
//        // Minimum and maximum values expected (replace with your actual min and max if known)
//        float min = -1.0f;
//        float max = 1.0f;
//
//        // Handle case where min == max (avoid division by zero)
//        if (min == max) {
//            return value; // Any value will result in 0 or 1 after normalization
//        }
//
//        // Normalize the value
//        return (value - min) / (max - min);
        return value;
    }

}
