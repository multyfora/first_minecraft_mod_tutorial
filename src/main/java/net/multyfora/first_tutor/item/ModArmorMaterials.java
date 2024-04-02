package net.multyfora.first_tutor.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.multyfora.first_tutor.FirstTutor;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    RUBY("ruby", 25, new int[] {3,8,6,3},19,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2f,0.5f, () -> Ingredient.ofItems(ModItems.RUBY));



    private final String name;
    private final int durabilityMultiplier;
    private final int[] protactionAmounts;
    private final int enchantablility;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11,16,15,13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protactionAmounts, int enchantablility, SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.toughness = toughness;
        this.protactionAmounts = protactionAmounts;
        this.enchantablility = enchantablility;
        this.equipSound = equipSound;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protactionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantablility;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return FirstTutor.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
