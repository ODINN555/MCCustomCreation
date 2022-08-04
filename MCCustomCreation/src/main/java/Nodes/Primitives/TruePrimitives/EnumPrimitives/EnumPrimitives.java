package Nodes.Primitives.TruePrimitives.EnumPrimitives;

import Nodes.NodesHandler;
import Utility.ItemStackUtil;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class holds all the enum primitives
 */
public class EnumPrimitives {
    public static final EnumPrimitive EAttribute = new EnumPrimitive(Attribute.class);
    public static final EnumPrimitive EBlockFace = new EnumPrimitive(BlockFace.class);
    public static final EnumPrimitive ESpawnCategory = new EnumPrimitive(SpawnCategory.class);
    public static final EnumPrimitive EEntityType = new EnumPrimitive(EntityType.class);
    public static final EnumPrimitive EEntityEffect = new EnumPrimitive(EntityEffect.class);
    public static final EnumPrimitive ESound = new EnumPrimitive(Sound.class);
    public static final EnumPrimitive EWeatherType = new EnumPrimitive(WeatherType.class);
    public static final EnumPrimitive EWorldType = new EnumPrimitive(WorldType.class);
    public static final EnumPrimitive EMaterial = new EnumPrimitive(Material.class);
    public static final EnumPrimitive EStatistic = new EnumPrimitive(Statistic.class);
    public static final EnumPrimitive EDyeColor = new EnumPrimitive(DyeColor.class);
    public static final EnumPrimitive EGameMode = new EnumPrimitive(GameMode.class);
    public static final EnumPrimitive EPotionEffectType = new EnumPrimitive(PotionEffectType.values(),PotionEffectType.class){
        @Override
        public String getConstantToName(Object constant) {
            String str = "";
            String[] split = ((PotionEffectType) constant).getKey().getKey().toLowerCase(Locale.ROOT).split("_");
            for (String s : split)
                str += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

            return str;
        }
    };
    public static final EnumPrimitive EProjectile = new EnumPrimitive(new Projectile[]{},Projectile.class){
        @Override
        public ItemStack getDefaultDisplayItem() {
            // TEMPORARY, TODO disabled/unimplemented display?
            ItemStack item = super.getDefaultDisplayItem();
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.RED+"[NOT IMPLEMENTED]"));
            item.setItemMeta(meta);
            return item;
        }
    };//TODO projectiles
    public static final EnumPrimitive ETreeType = new EnumPrimitive(TreeType.class);
    public static final EnumPrimitive EDifficulty = new EnumPrimitive(Difficulty.class);
    public static final EnumPrimitive EGameRule= new EnumPrimitive(GameRule.values(),GameRule.class){
        @Override
        public ItemStack getDisplayOfConstant(int index){
            Object constant = getConstants()[index];
            GameRule rule = (GameRule) constant;
            ItemStack item = ItemStackUtil.newItemStack(ENUM_PRIMITIVE_MATERIAL, rule.getName(),Arrays.asList("type: "+rule.getType().getSimpleName()));
            return item;
        }
    };
    public static final EnumPrimitive EEnchantment= new EnumPrimitive(Enchantment.values(),Enchantment.class){
        @Override
        public String getConstantToName(Object constant) {
            String str = "";
            String[] split = ((Enchantment) constant).getKey().getKey().toLowerCase(Locale.ROOT).split("_");
            for (String s : split)
                str += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

            return str;
        }
    };
    public static final EnumPrimitive EItemFlag= new EnumPrimitive(ItemFlag.class);
    public static final EnumPrimitive EAttributeModifierOperation= new EnumPrimitive(AttributeModifier.Operation.class);
    public static final EnumPrimitive EFluidCollisionMode= new EnumPrimitive(FluidCollisionMode.class);
    public static final EnumPrimitive EParticle = new EnumPrimitive(Particle.class);
    public static final EnumPrimitive EEquipmentSlot = new EnumPrimitive(EquipmentSlot.class);
    public static final EnumPrimitive EBiome = new EnumPrimitive(Biome.class);
    public static final EnumPrimitive EEffect = new EnumPrimitive(Effect.class);
    public static final EnumPrimitive EStructureType= new EnumPrimitive(getStructureTypes(), StructureType.class){
        @Override
        public ItemStack getDisplayOfConstant(int index){
            Object constant = getConstants()[index];
            StructureType struct = (StructureType) constant;
            ItemStack item = ItemStackUtil.newItemStack(ENUM_PRIMITIVE_MATERIAL, struct.getKey().getKey());
            return item;
        }
    };

    /**
     * this method is used by the structure type enum
     * @return all structure types
     */
    private static StructureType[] getStructureTypes(){
        List<StructureType> list = new ArrayList<>();
        Registry.STRUCTURE_TYPE.forEach(s -> list.add(s));
        return list.toArray(new StructureType[0]);
    }

    /**
     *
     * @return all the enum primitives
     */
    public static EnumPrimitive[] values(){
        return new EnumPrimitive[]{
                EAttribute,EBlockFace,ESpawnCategory,EEntityEffect,EEntityType,ESound,EWeatherType,
                EWorldType,EMaterial,EStatistic,EDyeColor,EGameMode,EPotionEffectType,EProjectile,
                ETreeType,EDifficulty,EGameRule,EEnchantment,EItemFlag,EAttributeModifierOperation,
                EFluidCollisionMode,EStructureType,EParticle,EEquipmentSlot,EBiome,EEffect
        };
    }

    /**
     * registers the default EnumPrimitives
     */
    public static void registerDefaults(){
        NodesHandler.INSTANCE.register(values());
    }


}
