package Nodes.Parameters;

import Nodes.IParameter;
import Nodes.NodeEnum;
import Nodes.NodeItemStack;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import com.google.common.collect.Multimap;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import Utility.PDCUtil;
import java.util.List;
import java.util.UUID;

public enum DefaultParameters implements IParameter, NodeEnum {


    ADD_VECTOR_TO_LOCATION("", Material.CYAN_STAINED_GLASS_PANE, "ADD_VECTOR_TO_LOCATION", Location.class, new Class[]{Location.class, Vector.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).add((Vector) objects[1]);
        }
    },
    CAN_WORLD_GENERATE_STRUCTURES("If a world has the 'generate structures' setting enabled.", null, "CAN_WORLD_GENERATE_STRUCTURES", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).canGenerateStructures();
        }
    },
    CONVERT_LOCATION_TO_VECTOR("Convert a location to a vector value (only cords).", null, "CONVERT_LOCATION_TO_VECTOR", Vector.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Location) objects[0]).toVector();
        }
    },
    GET_ALL_PLAYERS_IN_WORLD("The world's player list.", null, "GET_ALL_PLAYERS_IN_WORLD", List.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getPlayers();
        }
    },
    GET_ATTRIBUTE_VALUE_OF_ITEM("The attribute value of an item. (e.g walk speed, armor toughness..).", null, "GET_ATTRIBUTE_VALUE_OF_ITEM", Double.class, new Class[]{ItemStack.class, Attribute.class, EquipmentSlot.class}, new String[]{"", "The attribute type", "The attribute's equipment slot use"}) {
        @Override
        public Object getParameter(Object... objects) {
            ItemMeta meta = ((ItemStack) objects[0]).getItemMeta();
            double amount = 0;
            Multimap<Attribute, AttributeModifier> map = meta.getAttributeModifiers((EquipmentSlot) objects[2]);
            if (map.get((Attribute) objects[1]) != null)
                for (AttributeModifier attributeModifier : map.get((Attribute) objects[1])) {
                    amount += attributeModifier.getAmount();
                }

            return amount;
        }
    },
    GET_BIOME("", Material.GRASS_BLOCK, "GET_BIOME", Biome.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getBlock().getBiome();
        }
    },
    GET_BLOCK_AT_LOCATION("The block at a location.", null, "GET_BLOCK_AT_LOCATION", Block.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getBlock();
        }
    },
    GET_BLOCK_HUMIDITY("", null, "GET_BLOCK_HUMIDITY", Double.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Block) objects[0]).getHumidity();
        }
    },
    GET_BLOCK_LIGHT_FROM_BLOCKS("The light which the block receives from other blocks near it.", Material.REDSTONE_LAMP, "GET_BLOCK_LIGHT_LEVEL_FROM_OTHER_BLOCKS", Byte.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getLightFromBlocks();
        }
    },
    GET_BLOCK_LIGHT_LEVEL("Total block light level.", Material.GLOWSTONE, "GET_BLOCK_LIGHT_LEVEL", Byte.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getLightLevel();
        }
    },
    GET_BLOCK_Location("", null, "GET_BLOCK_LOCATION", Location.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getLocation();
        }
    },
    GET_BLOCK_MATERIAL_TYPE("The material of the block (e.g dirt block's material is DIRT)", null, "GET_BLOCK_MATERIAL_TYPE", Material.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getType();
        }
    },
    GET_BLOCK_POWER("Receive the redstone power value of the block.", Material.REDSTONE_BLOCK, "GET_BLOCK_POWER", Integer.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getBlockPower();
        }
    },
    GET_BLOCK_SKYLIGHT_LEVEL("The block's direct skylight level (hint: use f3 to see that light level).", Material.SUNFLOWER, "GET_BLOCK_SKYLIGHT_LEVEL", Byte.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getLightFromSky();
        }
    },
    GET_BLOCK_TEMPERATURE("", Material.ICE, "GET_BLOCK_TEMPERATURE", Double.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getTemperature();
        }
    },
    GET_BLOCK_X("The x cords of the block.", null, "GET_BLOCK_X_CORDS", Integer.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getX();
        }
    },
    GET_BLOCK_Y("The y cords of the block.", null, "GET_BLOCK_Y_CORDS", Integer.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getY();
        }
    },
    GET_BLOCK_Z("The z cords of the block.", null, "GET_BLOCK_Y_CORDS", Integer.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getZ();
        }
    },
    GET_CHUNK("", null, "GET_CHUNK", Chunk.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getChunk();
        }
    },
    GET_DIRECTION_OF_LOCATION("", null, "GET_DIRECTION_OF_LOCATION", Vector.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getDirection();
        }
    },
    GET_DISTANCE_BETWEEN_LOCATIONS("", null, "GET_DISTANCE_BETWEEN_LOCATIONS", Double.class, new Class[]{Location.class, Location.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).distance((Location) objects[1]);
        }
    },
    GET_ENCHANTMENT_LEVEL_IN_ITEM("The level of a certain enchantment on an item.", null, "GET_ENCHANTMENT_LEVEL_IN_ITEM", Integer.class, new Class[]{ItemStack.class, Enchantment.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getEnchantmentLevel((Enchantment) objects[1]);
        }
    },
    GET_ENTITY_CUSTOM_NAME("The custom name of an entity (custom name is given through name tags or code).", null, "GET_ENTITY_CUSTOM_NAME", String.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getCustomName();
        }
    },
    GET_ENTITY_FACING("The direction which an entity is facing.", null, "GET_ENTITY_FACING", BlockFace.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getFacing();
        }
    },
    GET_ENTITY_FALL_DISTANCE("How many blocks an entity already fell.", null, "GET_ENTITY_FALL_DISTANCE", Float.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getFallDistance();
        }
    },
    GET_ENTITY_FIRE_TICKS("How many ticks an entity was on fire.", null, "GET_ENTITY_FIRE_TICKS", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getFireTicks();
        }
    },
    GET_ENTITY_FREEZE_TICKS("The ticks amount which an entity has been frozen.", null, "GET_ENTITY_FREEZE_TICKS", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getFreezeTicks();
        }
    },
    GET_ENTITY_HEIGHT("The height of an entity (most entity are 2 blocks high).", null, "GET_ENTITY_HEIGHT", Double.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getHeight();
        }
    },
    GET_ENTITY_LOCATION("The entity location.", null, "GET_ENTITY_LOCATION", Location.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getLocation();
        }
    },
    GET_ENTITY_MAX_FIRE_TICKS("The maximum amount of ticks an entity can be on fire.", null, "GET_ENTITY_MAX_FIRE_TICKS", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getMaxFireTicks();
        }
    },
    GET_ENTITY_MAX_FREEZE_TICKS("The maximum ticks amount which an entity can stay frozen.", null, "GET_ENTITY_MAX_FREEZE_TICKS", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getMaxFreezeTicks();
        }
    },
    GET_ENTITY_NEARBY_ENTITIES("A list of all entity nearby entities within a certain range.", null, "GET_ENTITY_NEARBY_ENTITIES", List.class, new Class[]{Entity.class, Double.class, Double.class, Double.class}, new String[]{"", "x offset", "y offset", "z offset"}) {
        @Override
        public Object getParameter(Object... objects) {
            List<Entity> entities = ((Entity) objects[0]).getNearbyEntities((Double) objects[1], (Double) objects[2], (Double) objects[3]);
            entities.remove(0);
            return entities;
        }
    },
    GET_ENTITY_NEAREST_ENTITY("The nearest entity to an entity", null, "GET_ENTITY_NEAREST_ENTITY", List.class, new Class[]{Entity.class, Double.class, Double.class, Double.class}, new String[]{"", "x offset", "y offset", "z offset"}) {
        @Override
        public Object getParameter(Object... objects) {
            List<Entity> entities = ((Entity) objects[0]).getNearbyEntities((Double) objects[1], (Double) objects[2], (Double) objects[3]);
            entities.remove(0);
            if (entities.isEmpty())
                return null;
            return entities.get(0);
        }
    },
    GET_ENTITY_PASSENGERS("The entity's passengers.", null, "GET_ENTITY_PASSENGERS", List.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getPassengers();
        }
    },
    GET_ENTITY_PORTAL_COOLDOWN("The cooldown which an entity can't enter a portal.", null, "GET_ENTITY_PORTAL_COOLDOWN", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getPortalCooldown();
        }
    },
    GET_ENTITY_SCOREBOARD_TAGS("", null, "GET_ENTITY_SCOREBOARD_TAGS", List.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getScoreboardTags();
        }
    },
    GET_ENTITY_SPAWN_CATEGORY("The spawn category of an entity (e.g sheep - ANIMAL).", null, "GET_ENTITY_SPAWN_CATEGORY", SpawnCategory.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getSpawnCategory();
        }
    },
    GET_ENTITY_TYPE("The entity type. (e.g zombie - ZOMBIE).", null, "GET_ENTITY_TYPE", EntityType.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getType();
        }
    },
    GET_ENTITY_UNIQUE_ID("The UUID of an entity, each entity has a different UUID.", null, "GET_ENTITY_UNIQUE_ID", UUID.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getUniqueId();
        }
    },
    GET_ENTITY_VEHICLE("The vehicle which the entity is in (e.g minecart,boat).", null, "GET_ENTITY_VEHICLE", Entity.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getVehicle();
        }
    },
    GET_ENTITY_VELOCITY("", null, "GET_ENTITY_VELOCITY", Vector.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getVelocity();
        }
    },
    GET_ENTITY_WIDTH("", null, "GET_ENTITY_WIDTH", Double.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getWidth();
        }
    },
    GET_FILTERED_LIST_OF_NEARBY_ENTITIES_OF_LOCATION("A list of nearby entities from a location but filtered by EntiyType.", null, "GET_FILTERED_LIST_OF_NEARBY_ENTITIES_OF_LOCATION", List.class, new Class[]{Location.class, Double.class, Double.class, Double.class, EntityType.class}, new String[]{"", "x offset", "y offset", "z offset", "The entity type filter"}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);

            return loc.getWorld().getNearbyEntities(loc, (Double) objects[1], (Double) objects[2], (Double) objects[3], entity -> entity.getType().equals(objects[4]));
        }
    },
    GET_FIRST_LINE_OF_SIGHT_ENTITY("The entity which this entity looks at.", null, "GET_FIRST_LINE_OF_SIGHT_ENTITY", Entity.class, new Class[]{LivingEntity.class, Double.class}, new String[]{"The entity which is looking", "The range"}) {
        @Override
        public Object getParameter(Object... objects) {
            LivingEntity entity = ((LivingEntity) objects[0]);
            double range = ((Double) objects[1]);
            Location loc = entity.getEyeLocation();
            RayTraceResult result = loc.getWorld().rayTraceEntities(loc, loc.getDirection(), range, x -> !x.equals(entity));

            return result == null ? null : result.getHitEntity();

        }
    },
    GET_FIRST_TIME_PLAYER_PLAYED_IN_SERVER("The first time a player has played in the server (in miliseconds, since january 1, 1970 UTC).", null, "GET_FIRST_TIME_PLAYER_PLAYED_IN_SERVER", Long.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getFirstPlayed();
        }
    },
    GET_HIGHEST_BLOCK_AT_LOCATION("The highest non air block at a location.", null, "GET_HIGHEST_BLOCK_AT_LOCATION", Block.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = (Location) objects[0];
            return loc.getWorld().getHighestBlockAt(loc);
        }
    },
    GET_IF_BED_WORKS_IN_WORLD("If a world allows beds (unlike the nether).", null, "GET_IF_BED_WORKS_IN_WORLD", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isBedWorks();
        }
    },
    GET_IF_BLOCK_INDIRECTLY_POWERED("If the block is redstone powered through a certain direction.", null, "GET_IF_BLOCK_INDIRECTLY_POWERED", Boolean.class, new Class[]{Block.class, BlockFace.class}, new String[]{"", "The direction to check"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isBlockFaceIndirectlyPowered((BlockFace) objects[1]);
        }
    },
    GET_IF_BLOCK_IS_EMPTY("If the block is AIR.", null, "GET_IF_BLOCK_IS_EMPTY", Boolean.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isEmpty();
        }
    },
    GET_IF_BLOCK_IS_LIQUID("If the block's material is a liquid (e.g water).", null, "GET_IF_BLOCK_IS_LIQUID", Boolean.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isLiquid();
        }
    },
    GET_IF_BLOCK_IS_PASSABLE("If the block is passable (e.g flowers,tall grass).", null, "GET_IF_BLOCK_IS_PASSABLE", Boolean.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isPassable();
        }
    },
    GET_IF_BLOCK_IS_POWERED("If the block is redstone powered.", null, "GET_IF_BLOCK_IS_POWERED", Boolean.class, new Class[]{Block.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isBlockPowered();
        }
    },
    GET_IF_CHUNK_IS_LOADED_AT_LOCATION("", null, "GET_IF_CHUNK_IS_LOADED_AT_LOCATION", Boolean.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Location) objects[0]).getChunk().isLoaded();
        }
    },
    GET_IF_ENTITY_CUSTOM_NAME_IS_VISIBLE("If the custom name given to an entity is visible. (custom name is given through name tags or code)", null, "GET_IF_ENTITY_CUSTOM_NAME_IS_VISIBLE", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isCustomNameVisible();
        }
    },
    GET_IF_ENTITY_HAS_GRAVITY("If entity has gravity, NOTE: no gravity does NOT mean flight! (but it does make the entity unable to jump).", null, "GET_IF_ENTITY_HAS_GRAVITY", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).hasGravity();
        }
    },
    GET_IF_ENTITY_HAS_PERMISSION("If an entity has a certain permission (this is used for players mostly).", null, "GET_IF_ENTITY_HAS_PERMISSION", Boolean.class, new Class[]{Entity.class, String.class}, new String[]{"", "The permission's name"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).hasPermission((String) objects[1]);
        }
    },
    GET_IF_ENTITY_HAS_VISUAL_FIRE("If an entity has visual fire (not real fire, just the visual effect).", null, "GET_IF_ENTITY_HAS_VISUAL_FIRE", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isVisualFire();
        }
    },
    GET_IF_ENTITY_IS_DEAD("If an entity is dead (I dont see how you can use it, but here you go).", null, "GET_IF_ENTITY_IS_DEAD", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isDead();
        }
    },
    GET_IF_ENTITY_IS_FROZEN("", null, "GET_IF_ENTITY_IS_FROZEN", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isFrozen();
        }
    },
    GET_IF_ENTITY_IS_GLOWING("If entity is glowing by the glow effect (given by spectral arrow, or code).", null, "GET_IF_ENTITY_IS_GLOWING", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isGlowing();
        }
    },
    GET_IF_ENTITY_IS_INSIDE_VEHICLE("If an entity is inside a vehicle (e.g minecart,boat).", null, "GET_IF_ENTITY_IS_INSIDE_VEHICLE", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isInsideVehicle();
        }
    },
    GET_IF_ENTITY_IS_INVULNERABLE("If an entity is invulnerable (e.g cannot be damaged).", null, "GET_IF_ENTITY_IS_INVULNERABLE", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isInvulnerable();
        }
    },
    GET_IF_ENTITY_IS_IN_WATER("", null, "GET_IF_ENTITY_IS_IN_WATER", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isInWater();
        }
    },
    GET_IF_ENTITY_IS_ON_GROUND("If the entity is standing on ground (e.g not jumping,falling or in a liquid).", null, "GET_IF_ENTITY_IS_ON_GROUND", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isOnGround();
        }
    },
    GET_IF_ENTITY_IS_OP("If an entity is a server operator (e.g admin).", null, "GET_IF_ENTITY_IS_OP", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isOp();
        }
    },
    GET_IF_ENTITY_IS_SILENT("If an entity is silent (silent is set using code).", null, "GET_IF_ENTITY_IS_SILENT", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isSilent();
        }
    },
    GET_IF_ENTITY_IS_VALID("If an entity is valid, means it is not dead or despawned.", null, "GET_IF_ENTITY_IS_VALID", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isValid();
        }
    },
    GET_IF_IS_PREFERRED_TOOL("If the item is preffered tool to mine a certain block (e.g shovel - dirt).", null, "GET_IF_ITEM_IS_PREFERRED_TOOL", Boolean.class, new Class[]{Block.class, ItemStack.class}, new String[]{"The block to check on", "The item to check"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).isPreferredTool((ItemStack) objects[1]);
        }
    },
    GET_IF_ITEM_CONTAINS_ENCHANTMENT("If an item contains a certain enchantment.", null, "GET_IF_ITEM_CONTAINS_ENCHANTMENT", Boolean.class, new Class[]{ItemStack.class, Enchantment.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().hasEnchant((Enchantment) objects[1]);
        }
    },
    GET_IF_ITEM_HAS_ATTRIBUTES("If an item has any attributes.", null, "GET_IF_ITEM_HAS_ATTRIBUTES", Boolean.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().hasAttributeModifiers();
        }
    },
    GET_IF_ITEM_HAS_CUSTOM_MODEL_DATA("If an item has custom model data (custom model data is used by resource packs).", null, "GET_IF_ITEM_HAS_CUSTOM_MODEL_DATA", Boolean.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().hasCustomModelData();
        }
    },
    GET_IF_ITEM_HAS_ITEM_FLAG("If an item has an item flag (e.g HIDE_ENCHANTMENTS).", null, "GET_IF_ITEM_HAS_ITEM_FLAG", Boolean.class, new Class[]{ItemStack.class, ItemFlag.class}, new String[]{"", "The item flag (e.g HIDE_ENCHANTS)"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().hasItemFlag((ItemFlag) objects[1]);
        }
    },
    GET_IF_ITEM_HAS_LORE("If an item has a lore (the body text of the item).", null, "GET_IF_ITEM_HAS_LORE", Boolean.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().hasLore();
        }
    },
    GET_IF_ITEM_IS_SIMILAR_TO_ANOTHER_ITEM("If an item is similar to another (ignores stack amount. for fully identical use equals).", null, "GET_IF_ITEM_IS_SIMILAR_TO_ANOTHER_ITEM", Boolean.class, new Class[]{ItemStack.class, ItemStack.class}, new String[]{"The first item", "The second item"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).isSimilar((ItemStack) objects[1]);
        }
    },
    GET_IF_ITEM_IS_UNBREAKABLE("", null, "GET_IF_ITEM_IS_UNBREAKABLE", Boolean.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().isUnbreakable();
        }
    },
    GET_IF_LIVING_ENTITY_CAN_PICKUP_ITEMS("If an entity can pickup items.", null, "GET_IF_LIVING_ENTITY_CAN_PICKUP_ITEMS", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getCanPickupItems();
        }
    },
    GET_IF_LIVING_ENTITY_HAS_A_POTION_EFFECT("If an entity has a certain potion effect (e.g regeneration,fire resistence..).", null, "GET_IF_LIVING_ENTITY_HAS_A_POTION_EFFECT", Boolean.class, new Class[]{LivingEntity.class, PotionEffectType.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).hasPotionEffect((PotionEffectType) objects[1]);
        }
    },
    GET_IF_LIVING_ENTITY_HAS_LINE_OF_SIGHT_WITH_ANOTHER_ENTITY("If an entity has a line of sight with anoter, means it can reach it (like when zombies try to find a player).", null, "GET_IF_LIVING_ENTITY_HAS_LINE_OF_SIGHT_WITH_ANOTHER_ENTITY", Boolean.class, new Class[]{LivingEntity.class, Entity.class}, new String[]{"The entity", "The other entity"}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).hasLineOfSight((Entity) objects[1]);
        }
    },
    GET_IF_LIVING_ENTITY_IS_CLIMBING("If the entity is climbing (a ladder for example).", null, "GET_IF_LIVING_ENTITY_IS_CLIMBING", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isClimbing();
        }
    },
    GET_IF_LIVING_ENTITY_IS_COLLIDABLE("If an entity is collidable, means it cannot get passed through.", null, "GET_IF_LIVING_ENTITY_IS_COLLIDABLE", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isCollidable();
        }
    },
    GET_IF_LIVING_ENTITY_IS_GLIDING("If the entity is gliding with elytra.", null, "GET_IF_LIVING_ENTITY_IS_GLIDING", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isGliding();
        }
    },
    GET_IF_LIVING_ENTITY_IS_INVISIBLE("", null, "GET_IF_LIVING_ENTITY_IS_INVISIBLE", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isInvisible();
        }
    },
    GET_IF_LIVING_ENTITY_IS_LEASHED("If the entity is leashed with a lead by another entity.", null, "GET_IF_LIVING_ENTITY_IS_LEASHED", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isLeashed();
        }
    },
    GET_IF_LIVING_ENTITY_IS_REMOVED_WHEN_FAR_AWAY("If an entity should despawn when out of range.", null, "GET_IF_LIVING_ENTITY_IS_REMOVED_WHEN_FAR_AWAY", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getRemoveWhenFarAway();
        }
    },
    GET_IF_LIVING_ENTITY_IS_RIPTIDING("If an entity is riptiding (riptide enchant).", null, "GET_IF_LIVING_ENTITY_IS_RIPTIDING", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isRiptiding();
        }
    },
    GET_IF_LIVING_ENTITY_IS_SLEEPING("", null, "GET_IF_LIVING_ENTITY_IS_SLEEPING", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isSleeping();
        }
    },
    GET_IF_LIVING_ENTITY_IS_SWIMMING("", null, "GET_IF_LIVING_ENTITY_IS_SWIMMING", Boolean.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).isSwimming();
        }
    },
    GET_IF_PLAYER_CAN_SEE_OTHER_PLAYER("If a player can see another player (if he is unhidden from him).", null, "GET_IF_PLAYER_CAN_SEE_OTHER_PLAYER", Boolean.class, new Class[]{Player.class, Player.class}, new String[]{"The player", "The other player"}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).canSee((Player) objects[1]);
        }
    },
    GET_IF_PLAYER_HAS_COOLDOWN_FOR_SPECIFIC_MATERIAL("The player's cooldown of a specific item material.", null, "GET_IF_PLAYER_HAS_COOLDOWN_FOR_SPECIFIC_MATERIAL", Boolean.class, new Class[]{Player.class, Material.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).hasCooldown((Material) objects[1]);
        }
    },
    GET_IF_PLAYER_HAS_PLAYED_BEFORE("If a player has played before in the server.", null, "GET_IF_PLAYER_HAS_PLAYED_BEFORE", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).hasPlayedBefore();
        }
    },
    GET_IF_PLAYER_IS_ABOUT_TO_BLOCK("If a player is about to block again (when the player has blocked there is a delay, but he is still trying to block).", null, "GET_IF_PLAYER_IS_ABOUT_TO_BLOCK", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isHandRaised();
        }
    },
    GET_IF_PLAYER_IS_ALLOWED_FLYING("If the player is allowed to fly (available through code, or CREATIVE game mode).", null, "GET_IF_PLAYER_IS_ALLOWED_FLYING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getAllowFlight();
        }
    },
    GET_IF_PLAYER_IS_ALLOWING_SERVER_LISTINGS("If the player is allowing server listings ('Allow Server Listings' setting).", null, "GET_IF_PLAYER_IS_ALLOWING_SERVER_LISTINGS", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isAllowingServerListings();
        }
    },
    GET_IF_PLAYER_IS_BANNED("If the player is banned.", null, "GET_IF_PLAYER_IS_BANNED", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isBanned();
        }
    },
    GET_IF_PLAYER_IS_CURRENTLY_BLOCKING("If a player is blocking with a shield.", null, "GET_IF_PLAYER_IS_CURRENTLY_BLOCKING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isBlocking();
        }
    },
    GET_IF_PLAYER_IS_FLYING("", null, "GET_IF_PLAYER_IS_FLYING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isFlying();
        }
    },
    GET_IF_PLAYER_IS_HEALTH_SCALED("if the client health display is scaled.", null, "GET_IF_PLAYER_IS_HEALTH_SCALED", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isHealthScaled();
        }
    },
    GET_IF_PLAYER_IS_IGNORING_SLEEPING("if the player is ignoring sleep (phantoms start to attack).", null, "GET_IF_PLAYER_IS_IGNORING_SLEEPING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isSleepingIgnored();
        }
    },
    GET_IF_PLAYER_IS_ONLINE("If the player is online.", null, "GET_IF_PLAYER_IS_ONLINE", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isOnline();
        }
    },
    GET_IF_PLAYER_IS_SNEAKING("", null, "GET_IF_PLAYER_IS_SNEAKING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isSneaking();
        }
    },
    GET_IF_PLAYER_IS_SPRINTING("", null, "GET_IF_PLAYER_IS_SPRINTING", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isSprinting();
        }
    },
    GET_IF_PLAYER_IS_WHITELISTED("If the player is whitelisted (if he is not listed in the whitelist, and the server is whitelisted, he wont be able to join).", null, "GET_IF_PLAYER_IS_WHITELISTED", Boolean.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).isWhitelisted();
        }
    },
    GET_IF_VEHICLE_ENTITY_IS_EMPTY("If the current entity is a vehicle, and it is empty.", null, "GET_IF_VEHICLE_ENTITY_IS_EMPTY", Boolean.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).isEmpty();
        }
    },
    GET_IF_WORLD_ALLOWS_RESPAWN_ANCHORS("", null, "GET_IF_WORLD_ALLOWS_RESPAWN_ANCHORS", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isRespawnAnchorWorks();
        }
    },
    GET_IF_WORLD_HAS_ANY_RAIDS("If a world has an ongoing pillager raids.", null, "GET_IF_WORLD_HAS_ANY_RAIDS", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).hasRaids();
        }
    },
    GET_IF_WORLD_HAS_CEILING("If a world has a ceiling (like nether).", null, "GET_IF_WORLD_HAS_CEILING", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).hasCeiling();
        }
    },
    GET_IF_WORLD_HAS_SKYLIGHT("If world has skylight (unlike the end).", null, "GET_IF_WORLD_HAS_SKYLIGHT", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).hasSkyLight();
        }
    },
    GET_IF_WORLD_HAS_STORM("If the world is storming", null, "GET_IF_WORLD_HAS_STORM", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).hasStorm();
        }
    },
    GET_IF_WORLD_IS_ALLOWING_ANIMALS("If a world allows animals to spawn.", null, "GET_IF_WORLD_IS_ALLOWING_ANIMALS", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getAllowAnimals();
        }
    },
    GET_IF_WORLD_IS_ALLOWING_MONSTERS("If a world allows monsters to spawn.", null, "GET_IF_WORLD_IS_ALLOWING_MONSTERS", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getAllowMonsters();
        }
    },
    GET_IF_WORLD_IS_ALLOWING_PVP("If a world allows players hit other players.", null, "GET_IF_WORLD_IS_ALLOWING_PVP", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getPVP();
        }
    },
    GET_IF_WORLD_IS_AUTO_SAVED("", null, "GET_IF_WORLD_IS_AUTO_SAVED", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isAutoSave();
        }
    },
    GET_IF_WORLD_IS_HARDCORE("If hardcore mode is activated in the world.", null, "GET_IF_WORLD_IS_HARDCORE", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isHardcore();
        }
    },
    GET_IF_WORLD_IS_LOADED_AT_LOCATION("", null, "GET_IF_WORLD_IS_LOADED_AT_LOCATION", Boolean.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).isWorldLoaded();
        }
    },
    GET_IF_WORLD_IS_NATURAL("If a world is natural, this means for example that compasses spin randomly and setting respawn point with beds is disabled.", null, "GET_IF_WORLD_IS_NATURAL", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isNatural();
        }
    },
    GET_IF_WORLD_IS_PIGLIN_SAFE("If the world allows piglins to survive without transforming to zombified piglins.", null, "GET_IF_WORLD_IS_PIGLIN_SAFE", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isPiglinSafe();
        }
    },
    GET_IF_WORLD_IS_ULTRA_WARM("If a world is ultra warm, this means some lava/water mechanics are triggered: water evaporates, sponges dry and lavav spreads faster.", null, "GET_IF_WORLD_IS_ULTRA_WARM", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isUltraWarm();
        }
    },
    GET_IF_WORLD_SHOULD_KEEP_SPAWN_LOADED("", null, "GET_IF_WORLD_SHOULD_KEEP_SPAWN_LOADED", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getKeepSpawnInMemory();
        }
    },
    GET_IF_WORLD_WEATHER_IS_CLEAR("", null, "GET_IF_WORLD_WEATHER_IS_CLEAR", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isClearWeather();
        }
    },
    GET_IF_WORLD_WEATHER_IS_THUNDERSTORM("", null, "GET_IF_WORLD_WEATHER_IS_THUNDERSTORM", Boolean.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).isThundering();
        }
    },
    GET_ITEM_CUSTOM_MODEL_DATA("The item's custom model data (the custom model data is for resource packs).", null, "GET_ITEM_CUSTOM_MODEL_DATA", Integer.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().getCustomModelData();
        }
    },
    GET_ITEM_DISPLAY_NAME("The item's display name.", null, "GET_ITEM_DISPLAY_NAME", String.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getItemMeta().getDisplayName();
        }
    },
    GET_ITEM_LORE("The items lore (the body text of the item).", null, "GET_ITEM_LORE", String.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            String str = "";
            for (String s : ((ItemStack) objects[0]).getItemMeta().getLore()) {
                str += s + "\n";
            }
            return str;
        }
    },
    GET_ITEM_MATERIAL_TYPE("The item's material type (e.g, for diamond pickaxe it will be DIAMOND_PICKAXE, diamond - DIAMOND).", null, "GET_ITEM_MATERIAL_TYPE", Integer.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getType();
        }
    },
    GET_ITEM_MAX_STACK_AMOUNT("The maximum stack amount of an item (this is almost the same as material stack size)", null, "GET_ITEM_MAX_STACK_AMOUNT", Integer.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getMaxStackSize();
        }
    },
    GET_ITEM_STACK_AMOUNT("The current stack amount of an item.", null, "GET_ITEM_STACK_AMOUNT", Integer.class, new Class[]{ItemStack.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((ItemStack) objects[0]).getAmount();
        }
    },
    GET_LAST_TIME_PLAYER_PLAYED_IN_SERVER("The last time a player has played in the server (in miliseconds, since january 1, 1970 UTC).", null, "GET_LAST_TIME_PLAYER_PLAYED_IN_SERVER", Long.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getLastPlayed();
        }
    },
    GET_LIST_OF_NEARBY_ENTITIES_OF_LOCATION("A list of nearby entities from a location.", null, "GET_LIST_OF_NEARBY_ENTITIES_OF_LOCATION", List.class, new Class[]{Location.class, Double.class, Double.class, Double.class}, new String[]{"", "x offset", "y offset", "z offset"}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            return loc.getWorld().getNearbyEntities(loc, (Double) objects[1], (Double) objects[2], (Double) objects[3]);
        }
    },
    GET_LIVING_ENTITY_ABSORPTION_AMOUNT("The entity's absorption hearts (This effect given by golden apple, or code).", null, "GET_LIVING_ENTITY_ABSORPTION_AMOUNT", Double.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getAbsorptionAmount();
        }
    },
    GET_LIVING_ENTITY_ARROWS_AMOUNT_IN_BODY("The amount of arrows stuck in an entity,", null, "GET_LIVING_ENTITY_ARROWS_AMOUNT_IN_BODY", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getArrowsInBody();
        }
    },
    GET_LIVING_ENTITY_ARROW_COOLDOWN("The amount of ticks until an arrow is removed from the entities body (e.g when an arrow hits an entity, how much time will take until it disappears).", null, "GET_LIVING_ENTITY_ARROW_COOLDOWN", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getArrowCooldown();
        }
    },
    GET_LIVING_ENTITY_ATTRIBUTE_VALUE("An entity's total attribute value.", null, "GET_LIVING_ENTITY_ATTRIBUTE_VALUE", Double.class, new Class[]{LivingEntity.class, Attribute.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getAttribute((Attribute) objects[1]).getValue();
        }
    },
    GET_LIVING_ENTITY_CURRENT_NO_DAMAGE_TICKS("The current ticks amount which an entity didnt take any damage.", null, "GET_LIVING_ENTITY_CURRENT_NO_DAMAGE_TICKS", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getNoDamageTicks();
        }
    },
    GET_LIVING_ENTITY_EYE_HEIGHT("The entity's eye height.", null, "GET_LIVING_ENTITY_EYE_HEIGHT", Double.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getEyeHeight();
        }
    },
    GET_LIVING_ENTITY_EYE_LOCATION("The entity's eye location.", null, "GET_LIVING_ENTITY_EYE_LOCATION", Location.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getEyeLocation();
        }
    },
    GET_LIVING_ENTITY_HEALTH("The entity's health points.", null, "GET_LIVING_ENTITY_HEALTH", Double.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getHealth();
        }
    },
    GET_LIVING_ENTITY_KILLER("The killer of an entity (if it is alive then no killer)", null, "GET_LIVING_ENTITY_KILLER", Player.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getKiller();
        }
    },
    GET_LIVING_ENTITY_LAST_DAMAGE_TOOK("The last damage an entity took.", null, "GET_LIVING_ENTITY_LAST_DAMAGE_TOOK", Double.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getLastDamage();
        }
    },
    GET_LIVING_ENTITY_LEASH_HOLDER("The entity which controls the entity with a lead.", null, "GET_LIVING_ENTITY_LEASH_HOLDER", Entity.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getLeashHolder();
        }
    },
    GET_LIVING_ENTITY_MAX_AIR_AMOUNT("The maximum amount of air boubles an entity can have.", null, "GET_LIVING_ENTITY_MAX_AIR_AMOUNT", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getMaximumAir();
        }
    },
    GET_LIVING_ENTITY_MAX_NO_DAMAGE_TICKS("The heighest amount of ticks which an entity didnt took any damage.", null, "GET_LIVING_ENTITY_MAX_NO_DAMAGE_TICKS", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getMaximumNoDamageTicks();
        }
    },
    GET_LIVING_ENTITY_REMAINING_AIR("The amount of air boubles the entity has left.", null, "GET_LIVING_ENTITY_REMAINING_AIR", Integer.class, new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getRemainingAir();
        }
    },
    GET_LIVING_ENTITY_TARGETED_BLOCK("The targeted block the entity looks up to the given range (could be air).", null, "GET_LIVING_ENTITY_TARGETED_BLOCK", Block.class, new Class[]{LivingEntity.class, Integer.class}, new String[]{"", "The range"}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((LivingEntity) objects[0]).getTargetBlock(null, (Integer) objects[1]);
        }
    },
    GET_LOCATION_PITCH("The location's pitch, pitch is the vertical rotation value.", null, "GET_LOCATION_PITCH", Float.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getPitch();
        }
    },
    GET_LOCATION_X_CORD("", null, "GET_LOCATION_X_CORD", Double.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getX();
        }
    },
    GET_LOCATION_YAW("The location's yaw, yaw is the horizontal rotation value.", null, "GET_LOCATION_YAW", Float.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getYaw();
        }
    },
    GET_LOCATION_Y_CORD("", null, "GET_LOCATION_Y_CORD", Double.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getY();
        }
    },
    GET_LOCATION_Z_CORD("", null, "GET_LOCATION_Z_CORD", Double.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getZ();
        }
    },
    GET_MATERIAL_MAX_STACK_SIZE("The maximum stack size of a material (e.g ender pearls - 16, dirt - 64).", null, "GET_MATERIAL_MAX_STACK_SIZE", Integer.class, new Class[]{Material.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Material) objects[0]).getMaxStackSize();
        }
    },
    GET_NEAREST_LOCATION_OF_STRUCTURE("The nearest structure location from a location.", null, "GET_NEAREST_LOCATION_OF_STRUCTURE", Location.class, new Class[]{Location.class, StructureType.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            Location result = loc.getWorld().locateNearestStructure(loc, (StructureType) objects[1], Integer.MAX_VALUE, true).getLocation();
            return result.getWorld().getHighestBlockAt(result).getLocation();
        }
    },
    GET_NEAREST_RAID_LOCATION("The nearest raid location from a location.", null, "GET_NEAREST_RAID_LOCATION", Location.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            return loc.getWorld().locateNearestRaid(loc, Integer.MAX_VALUE);
        }
    },
    GET_OBJECT_DISPLAY_STRING("The toString of an object, this will convert any object to a string.", null, "GET_OBJECT_DISPLAY_STRING", String.class, new Class[]{Object.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return objects[0].toString();
        }
    },
    GET_PERSISTENT_DATA_FROM_LIVING_ENTITY("The data stored inside an entity.", Material.PURPLE_STAINED_GLASS_PANE, "GET_PERSISTENT_DATA_FROM_LIVING_ENTITY", Object.class, new Class[]{LivingEntity.class,String.class}, new String[]{"The entity to get the data from","The key to get the data from"}) {
        @Override
        public Object getParameter(Object... objects) {

            return PDCUtil.get((LivingEntity) objects[0],(String) objects[1]);
        }
    },
    GET_PERSISTENT_DATA_FROM_ITEM("The data stored inside an item.", Material.PURPLE_STAINED_GLASS_PANE, "GET_PERSISTENT_DATA_FROM_ITEM", Object.class, new Class[]{ItemStack.class,String.class}, new String[]{"The item to get the data from","The key to get the data from"}) {
        @Override
        public Object getParameter(Object... objects) {
            String key = (String) objects[1];
            ChatColor.stripColor(key);
            return PDCUtil.get((ItemStack) objects[0],key);
        }
    },
    GET_PLAYER_ATTACK_COOLDOWN("The attack cooldown for a crit attack.", null, "GET_PLAYER_ATTACK_COOLDOWN", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getAttackCooldown();
        }
    },
    GET_PLAYER_BED_SPAWN_LOCATION("The bed spawn location of a player.", null, "GET_PLAYER_BED_SPAWN_LOCATION", Location.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getBedSpawnLocation();
        }
    },
    GET_PLAYER_BLOCK_BREAKING_SPEED("The breaking speed which a player can break a block with his current stats (haste,attributes,item on main hand).", null, "GET_PLAYER_BLOCK_BREAKING_SPEED", Float.class, new Class[]{Block.class, Player.class}, new String[]{"The block to check on", "The player which is checked"}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Block) objects[0]).getBreakSpeed((Player) objects[1]);
        }
    },
    GET_PLAYER_COMPASS_TARGET("The compass target location of a certain player.", null, "GET_PLAYER_COMPASS_TARGET", Location.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getCompassTarget();
        }
    },
    GET_PLAYER_CURRENT_LEVEL_XP("The player's current xp in the current level.", null, "GET_PLAYER_CURRENT_LEVEL_XP", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getExp();
        }
    },
    GET_PLAYER_CURRENT_SATURATION("The player's 'hidden food' bar. (This is a bar which won't let your food level drop until it is emptied).", null, "GET_PLAYER_CURRENT_SATURATION", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getSaturation();
        }
    },
    GET_PLAYER_CURRENT_WEATHER("The current weather the player is experiencing.", null, "GET_PLAYER_CURRENT_WEATHER", WeatherType.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getPlayerWeather();
        }
    },
    GET_PLAYER_EXHAUSTION_LEVEL("The player's exhaustion level (how fast will the player lose a saturation level).", null, "GET_PLAYER_EXHAUSTION_LEVEL", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getExhaustion();
        }
    },
    GET_PLAYER_FLY_SPEED("The player's current flight speed.", null, "GET_PLAYER_FLY_SPEED", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getFlySpeed();
        }
    },
    GET_PLAYER_FOOD_LEVEL("The player's food level (food bar points amount).", null, "GET_PLAYER_FOOD_LEVEL", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getFoodLevel();
        }
    },
    GET_PLAYER_GAME_MODE("The player's game mode (e.g SURVIVAL).", null, "GET_PLAYER_GAME_MODE", GameMode.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getGameMode();
        }
    },
    GET_PLAYER_HEALTH_SCALE("The player's health scale if it is scaled.", null, "GET_PLAYER_HEALTH_SCALE", Double.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getHealthScale();
        }
    },
    GET_PLAYER_ITEM_IN_MAIN_HAND("The item which the player holds in the main hand.", null, "GET_PLAYER_ITEM_IN_MAIN_HAND", ItemStack.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            ItemStack item = ((Player) objects[0]).getInventory().getItemInMainHand();

            return item == null || item.getType().equals(Material.AIR) ? null : item;
        }
    },
    GET_PLAYER_ITEM_IN_OFF_HAND("The item which the player holds in the off hand.", null, "GET_PLAYER_ITEM_IN_OFF_HAND", ItemStack.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            ItemStack item = ((Player) objects[0]).getInventory().getItemInOffHand();

            return item == null || item.getType().equals(Material.AIR) ? null : item;
        }
    },
    GET_PLAYER_LAST_DEATH_LOCATION("The player's last death location.", null, "GET_PLAYER_LAST_DEATH_LOCATION", Location.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getLastDeathLocation();
        }
    },
    GET_PLAYER_LEVEL("The player's current level", null, "GET_PLAYER_LEVEL", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getLevel();
        }
    },
    GET_PLAYER_NAME("", null, "GET_PLAYER_NAME", String.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getName();
        }
    },
    GET_PLAYER_NEEDED_XP_TO_LEVEL_UP("How much xp needed for a player to level up.", null, "GET_PLAYER_NEEDED_XP_TO_LEVEL_UP", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getExpToLevel();
        }
    },
    GET_PLAYER_PING("The current ping of the player to the server.", null, "GET_PLAYER_PING", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getPing();
        }
    },
    GET_PLAYER_REMAINING_COOLDOWN_FOR_SPECIFIC_MATERIAL("The remaining cooldown of a certain material of a player", null, "GET_PLAYER_REMAINING_COOLDOWN_FOR_SPECIFIC_MATERIAL", Integer.class, new Class[]{Player.class, Material.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getCooldown((Material) objects[1]);
        }
    },
    GET_PLAYER_SATURATED_REGEN_RATE("The health regen rate of a player when his food bar is full.", null, "GET_PLAYER_SATURATED_REGEN_RATE", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getSaturatedRegenRate();
        }
    },
    GET_PLAYER_SLEEP_TICKS("The ticks amount which a player is currently sleeping (not total sleep time).", null, "GET_PLAYER_SLEEP_TICKS", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getSleepTicks();
        }
    },
    GET_PLAYER_STARVATION_RATE("The rate which a player loses health when his food bar level is 0.", null, "GET_PLAYER_STARVATION_RATE", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getStarvationRate();
        }
    },
    GET_PLAYER_STATISTIC("The statistic value of a player.", null, "GET_PLAYER_STATISTIC", Integer.class, new Class[]{Player.class, Statistic.class}, new String[]{"", "The statistic to get"}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getStatistic((Statistic) objects[1]);
        }
    },
    GET_PLAYER_TOTAL_XP("The player's total xp, includes the current level xp.", null, "GET_PLAYER_TOTAL_XP", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getTotalExperience();
        }
    },
    GET_PLAYER_UNSATURATED_REGEN_RATE("The health regen rate of a player when his food bar isn't full.", null, "GET_PLAYER_UNSATURATED_REGEN_RATE", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getUnsaturatedRegenRate();
        }
    },
    GET_PLAYER_VIEW_DISTANCE("The player's view distance (how far he can see).", null, "GET_PLAYER_VIEW_DISTANCE", Integer.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getClientViewDistance();
        }
    },
    GET_PLAYER_WALK_SPEED("", null, "GET_PLAYER_WALK_SPEED", Float.class, new Class[]{Player.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((Player) objects[0]).getWalkSpeed();
        }
    },
    GET_TICKS_ENTITY_HAS_LIVED("How many ticks an entity has lived.", null, "GET_TICKS_ENTITY_HAS_LIVED", Integer.class, new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Entity) objects[0]).getTicksLived();
        }
    },
    GET_TIME_UNTIL_WEATHER_IS_CLEAR_IN_WORLD("The ticks amount until the weather in the world is cleared.", null, "GET_TIME_UNTIL_WEATHER_IS_CLEAR_IN_WORLD", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getClearWeatherDuration();
        }
    },
    GET_WORLD_DIFFICULTY("The difficulty gamerule (e.g EASY,HARD,NORMAL).", null, "GET_WORLD_DIFFICULTY", Difficulty.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getDifficulty();
        }
    },
    GET_WORLD_FROM_LOCATION("The location's world.", Material.GRASS_BLOCK, "GET_WORLD_FROM_LOCATION", World.class, new Class[]{Location.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return ((Location) objects[0]).getWorld();
        }
    },
    GET_WORLD_FULL_TIME("The world full in-game time.", null, "GET_WORLD_FULL_TIME", Long.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getFullTime();
        }
    },
    GET_WORLD_GAME_RULE_BOOLEAN_VALUE("A world's game rule which his value is a boolean.", null, "GET_WORLD_GAME_RULE_BOOLEAN_VALUE", Object.class, new Class[]{World.class, GameRule.class}, new String[]{"", "A boolean game rule"}) {
        @Override
        public Object getParameter(Object... objects) {

            return (Boolean) ((World) objects[0]).getGameRuleValue((GameRule) objects[1]);
        }
    },
    GET_WORLD_GAME_RULE_INTEGER_VALUE("A world's game rule which his value is an integer.", null, "GET_WORLD_GAME_RULE_INTEGER_VALUE", Object.class, new Class[]{World.class, GameRule.class}, new String[]{"", "An integer game rule"}) {
        @Override
        public Object getParameter(Object... objects) {

            return (Integer) ((World) objects[0]).getGameRuleValue((GameRule) objects[1]);
        }
    },
    GET_WORLD_GAME_TIME("The current world time since world generation.", null, "GET_WORLD_GAME_TIME", Long.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getGameTime();
        }
    },
    GET_WORLD_LOGICAL_HEIGHT("The world's logical height, the highest height in which chorus fruits and nether portal can teleport a player within the world.", null, "GET_WORLD_LOGICAL_HEIGHT", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getLogicalHeight();
        }
    },
    GET_WORLD_SEA_LEVEL("The world's sea height.", null, "GET_WORLD_SEA_LEVEL", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getSeaLevel();
        }
    },
    GET_WORLD_SIMULATION_DISTANCE("", null, "GET_WORLD_SIMULATION_DISTANCE", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getSimulationDistance();
        }
    },

    GET_WORLD_SPAWN_LIMIT_OF_CATEGORY("The world's spawn limit of a certain category.", null, "GET_WORLD_SPAWN_LIMIT_OF_CATEGORY", Integer.class, new Class[]{World.class, SpawnCategory.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getSpawnLimit((SpawnCategory) objects[1]);
        }
    },
    GET_WORLD_SPAWN_LOCATION("The world spawn location.", null, "GET_WORLD_SPAWN_LOCATION", Location.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getSpawnLocation();
        }
    },
    GET_WORLD_THUNDERSTORM_DURATION("The world's current thunderstorm duration", null, "GET_WORLD_THUNDERSTORM_DURATION", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getThunderDuration();
        }
    },
    GET_WORLD_TICKS_PER_SPAWNS_OF_CATEGORY("The world's ticks per spawns of a spawn category (e.g a value of 1 means the serverwill attempt to spawn the spawn category every tick).", null, "GET_WORLD_TICKS_PER_SPAWNS_OF_CATEGORY", Integer.class, new Class[]{World.class, SpawnCategory.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getTicksPerSpawns((SpawnCategory) objects[1]);
        }
    },
    GET_WORLD_VIEW_DISTANCE("The world's view distance setting.", null, "GET_WORLD_VIEW_DISTANCE", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getViewDistance();
        }
    },
    GET_WORLD_WEATHER_DURATION("The world's current weather duration.", null, "GET_WORLD_WEATHER_DURATION", Integer.class, new Class[]{World.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {

            return ((World) objects[0]).getWeatherDuration();
        }
    },
    NORMALIZE_PITCH("Normalizes the pitch (if pitch is 170, then normalize would give the modulus of 90 of that number. which is 80).", Material.CYAN_STAINED_GLASS_PANE, "NORMALIZE_PITCH", Float.class, new Class[]{Float.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return Location.normalizePitch((Float) objects[0]);
        }
    },
    NORMALIZE_YAW("Normalizes the yaw (if yaw is 210, then normalize would give the modulus of 180 of that number. which is 30).", Material.CYAN_STAINED_GLASS_PANE, "NORMALIZE_YAW", Float.class, new Class[]{Float.class}, new String[]{""}) {
        @Override
        public Object getParameter(Object... objects) {
            return Location.normalizeYaw((Float) objects[0]);
        }
    },
    SET_LOCATION_DIRECTION("Set the location's direction (pitch and yaw).", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_DIRECTION", Location.class, new Class[]{Location.class, Vector.class}, new String[]{"", "The direction"}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setDirection((Vector) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_PITCH("Set the location's pitch, pitch is the vertical rotation.", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_PITCH", Location.class, new Class[]{Location.class, Float.class}, new String[]{"", "The pitch"}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setPitch((Float) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_WORLD("Set the world of the location.", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_WORLD", Location.class, new Class[]{Location.class, World.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setWorld((World) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_X_CORD("", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_X_CORD", Location.class, new Class[]{Location.class, Double.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setX((Double) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_YAW("Set the location's yaw, yaw is the horizontal rotation.", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_YAW", Location.class, new Class[]{Location.class, Float.class}, new String[]{"", "The yaw"}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setYaw((Float) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_Y_CORD("", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_Y_CORD", Location.class, new Class[]{Location.class, Double.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setY((Double) objects[1]);
            return loc;
        }
    },
    SET_LOCATION_Z_CORD("", Material.CYAN_STAINED_GLASS_PANE, "SET_LOCATION_Z_CORD", Location.class, new Class[]{Location.class, Double.class}, new String[]{"", ""}) {
        @Override
        public Object getParameter(Object... objects) {
            Location loc = ((Location) objects[0]);
            loc.setZ((Double) objects[1]);
            return loc;
        }
    },


    //Primitive Parameters
    RAW_VECTOR("Raw Vector value.\nRaw values are user input values.",Material.LIME_STAINED_GLASS_PANE,"RAW_VECTOR",Vector.class,new Class[]{Double.class,Double.class,Double.class},new String[]{"x","y","z"}){
        @Override
        public Object getParameter(Object... objects){
            return new Vector((Double)objects[0],(Double)objects[1],(Double)objects[2]);
        }

    },
    RAW_LOCATION("Raw Location value.\nRaw values are user input values.",Material.RED_STAINED_GLASS_PANE,"RAW_LOCATION",Location.class,new Class[]{World.class,Vector.class,Float.class,Float.class},new String[]{"The world","Cords","yaw","pitch"}){
        @Override
        public Object getParameter(Object... objects){
            return new Location((World)objects[0],(Double)objects[1],(Double)objects[2],(Double)objects[3],(Float)objects[4],(Float)objects[5]);
        }

    },
    //UTIL PARAMS
    CAST_ENTITY_TO_PLAYER("Converts an entity data type, to a player (only if the said entity is an actual player!)",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_ENTITY_TO_PLAYER",Player.class,new Class[]{Entity.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            if(!(objects[0] instanceof Player))
                return null;
            Player p = ((Player) objects[0]);
            return p;
        }
    },
    CAST_ENTITY_TO_LIVING_ENTITY("Converts an entity data type, to a living entity (only if the said entity is an actual living entity!)",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_ENTITY_TO_LIVING_ENTITY",LivingEntity.class,new Class[]{Entity.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            if(!(objects[0] instanceof LivingEntity))
                return null;
            return ((LivingEntity) objects[0]);
        }
    },
    CAST_DOUBLE_TO_INTEGER("Converts a Double data type, to an Integer (will round the double if needed).",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_DOUBLE_TO_INTEGER",Integer.class,new Class[]{Double.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]).intValue();
        }
    },
    CAST_DOUBLE_TO_FLOAT("Converts a Double data type to a Float.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_DOUBLE_TO_FLOAT",Float.class,new Class[]{Double.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]).floatValue();
        }
    },
    CAST_FLOAT_TO_DOUBLE("Converts a Float data type to a Double.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_FLOAT_TO_DOUBLE",Double.class,new Class[]{Float.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Float) objects[0]).doubleValue();
        }
    },
    CAST_INTEGER_TO_DOUBLE("Converts an Integer data type to a Double",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"CONVERT_NODE_INTEGER_TO_DOUBLE",Double.class,new Class[]{Integer.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]).doubleValue();
        }
    },
    //TODO add missing casts if found
    ADD_TO_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"ADD_VECTOR_TO_VECTOR",Vector.class,new Class[]{Vector.class,Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).add((Vector) objects[1]);
        }
    },
    SUBTRACT_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"SUBTRACT_VECTOR_FROM_VECTOR",Vector.class,new Class[]{Vector.class,Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).subtract((Vector) objects[1]);
        }
    },
    MULTIPLY_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MULTIPLY_VECTOR_BY_VECTOR",Vector.class,new Class[]{Vector.class,Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).multiply((Vector) objects[1]);
        }
    },
    DIVIDE_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"DIVIDE_VECTOR_BY_VECTOR",Vector.class,new Class[]{Vector.class,Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).divide((Vector) objects[1]);
        }
    },
    NORMALIZE_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"DIVIDE_VECTOR_BY_VECTOR",Vector.class,new Class[]{Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).normalize();
        }
    },
    MIDPOINT_VECTOR("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"GET_VECTOR_MIDPOINT_WITH_OTHER_VECTOR",Vector.class,new Class[]{Vector.class,Vector.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Vector) objects[0]).getMidpoint((Vector) objects[1]);
        }
    },
    //TODO add list functions (lists not supported atm)

    //primitives actions
    // Boolean
    BOOLEAN_NOT("A boolean NOT function (e.g if a boolean value is true, a NOT of that value is false).",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"GET_NOT_OF_BOOLEAN",Boolean.class,new Class[]{Boolean.class},new String[]{""}){
        @Override
        public Object getParameter(Object... objects){
            return !((Boolean) objects[0]);
        }
    },
    BOOLEAN_EQUALS("If two objects are equal.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"GET_IF_EQUALS",Boolean.class,new Class[]{Object.class,Object.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return objects[0].equals(objects[1]);
        }
    },
    // Integer
    ADD_INTEGER("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"ADD_TO_INTEGER",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]) + ((Integer) objects[1]);
        }
    },
    SUBTRACT_INTEGER("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"SUBTRACT_FROM_INTEGER",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]) - ((Integer) objects[1]);
        }
    },
    MULTIPLY_INTEGER("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MULTIPLY_INTEGER",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]) * ((Integer) objects[1]);
        }
    },
    DIVIDE_INTEGER("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"DIVIDE_INTEGER",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]) / ((Integer) objects[1]);
        }
    },
    MODULUS_INTEGER("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MODULUS_INTEGER",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Integer) objects[0]) % ((Integer) objects[1]);
        }
    },
    MAX_OF_INTEGERS("Use the greater integer between two integers.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MAX_OF_INTEGERS",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Integer.max((Integer) objects[0], (Integer) objects[1]);
        }
    },
    MIN_OF_INTEGERS("Use the smaller integer between two integers.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MIN_OF_INTEGERS",Integer.class,new Class[]{Integer.class,Integer.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Integer.min((Integer) objects[0], (Integer) objects[1]);
        }
    },
    // Double
    ADD_DOUBLE("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"ADD_TO_DOUBLE",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]) + ((Double) objects[1]);
        }
    },
    SUBTRACT_DOUBLE("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"SUBTRACT_FROM_DOUBLE",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]) - ((Double) objects[1]);
        }
    },
    MULTIPLY_DOUBLE("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MULTIPLY_DOUBLE",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]) * ((Double) objects[1]);
        }
    },
    DIVIDE_DOUBLE("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"DIVIDE_DOUBLE",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Double) objects[0]) / ((Double) objects[1]);
        }
    },
    MAX_OF_DOUBLES("Use the greater double between two doubles.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MAX_OF_DOUBLES",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Double.max((Double) objects[0], (Double) objects[1]);
        }
    },
    MIN_OF_DOUBLES("Use the smaller integer between two doubles.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MIN_OF_DOUBLES",Double.class,new Class[]{Double.class,Double.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Double.min((Double) objects[0], (Double) objects[1]);
        }
    },
    // Float
    ADD_FLOAT("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"ADD_TO_FLOAT",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Float) objects[0]) + ((Float) objects[1]);
        }
    },
    SUBTRACT_FLOAT("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"SUBTRACT_FROM_FLOAT",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Float) objects[0]) - ((Float) objects[1]);
        }
    },
    MULTIPLY_FLOAT("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MULTIPLY_FLOAT",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Float) objects[0]) * ((Float) objects[1]);
        }
    },
    DIVIDE_FLOAT("",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"DIVIDE_FLOAT",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return ((Float) objects[0]) / ((Float) objects[1]);
        }
    },
    MAX_OF_FLOATS("Use the greater Float between two Floats.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MAX_OF_FLOATS",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Float.max((Float) objects[0], (Float) objects[1]);
        }
    },
    MIN_OF_FLOATS("Use the smaller integer between two Floats.",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"MIN_OF_FloatS",Float.class,new Class[]{Float.class,Float.class},new String[]{"",""}){
        @Override
        public Object getParameter(Object... objects){
            return Float.min((Float) objects[0], (Float) objects[1]);
        }
    },

    RANDOM("Generate a random number between the minimum and maximum numbers",Material.LIGHT_BLUE_STAINED_GLASS_PANE,"RANDOM",Double.class,new Class[]{Double.class,Double.class},new String[]{"Minimum, the number generated wont be smaller than this","maximum, the number generated wont be greater than this"}){
        @Override
        public Object getParameter(Object... objects){

            Double min = ((Double) objects[0]);
            Double max = ((Double) objects[1]);
            return (Math.random() * (max-min))  + min;
        }
    },
    //TODO add primitives actions (like NOT, and equals)
    ;

    /**
     * The default parameter material
     */
    private static final Material DEFAULT_PARAMETER_MATERIAL = Material.BLUE_STAINED_GLASS_PANE;

    /**
     * the default display name material
     */
    private static final ChatColor DEFAULT_NAME_COLOR = ChatColor.AQUA;

    /**
     * the node's description
     */
    private String description;

    /**
     * the node's material
     */
    private Material mat;

    /**
     * the node's key
     */
    private String key;

    /**
     * the node's return type
     */
    private Class returnType;

    /**
     * the node's received types
     */
    private Class[] receivedTypes;

    /**
     * the node's received types descriptions
     */
    private String[] receivedTypesDescriptions;

    /**
     *
     * @param description the node's description
     * @param mat the node's material
     * @param key the node's key
     * @param returnType the node's return type
     * @param receivedTypes the node's received return types
     * @param receivedTypesDescriptions the node's received return types descriptions
     */
    DefaultParameters(String description, Material mat, String key, Class returnType, Class[] receivedTypes, String[] receivedTypesDescriptions) {
        this.description = description;
        this.mat = mat;
        this.key = key;
        this.returnType = returnType;
        this.receivedTypes = receivedTypes;
        this.receivedTypesDescriptions = receivedTypesDescriptions;
    }

    @Override
    public NodeItemStack getItemReference() {
        return new NodeItemStack(mat == null ? DEFAULT_PARAMETER_MATERIAL : mat, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public Class getReturnType() {
        return this.returnType;
    }

    @Override
    public Class[] getReceivedTypes() {
        return this.receivedTypes;
    }

    @Override
    public String[] getReceivedTypesDescriptions() {
        return this.receivedTypesDescriptions;
    }

    @Override
    public NodeItemStack getDefaultNodeItem() {
        return new NodeItemStack(DEFAULT_PARAMETER_MATERIAL, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);

    }

    @Override
    public String getDescription() {
        return this.description;
    }


}
