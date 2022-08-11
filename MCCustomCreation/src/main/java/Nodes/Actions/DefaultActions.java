package Nodes.Actions;

import Nodes.IAction;
import Nodes.IDuplicableAction;
import Nodes.NodeEnum;
import Nodes.NodeItemStack;
import Utility.ItemStackUtil;
import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import Utility.PDCUtil;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DefaultActions implements IAction, NodeEnum, IDuplicableAction {

    ADD_ATTRIBUTE_TO_ITEM("", Material.TOTEM_OF_UNDYING, "ADD_ATTRIBUTE_TO_ITEM", new Class[]{ItemStack.class, Attribute.class, String.class, Double.class, AttributeModifier.Operation.class}, new String[]{"", "The attribute type", "The name", "The amount", "The operation to use on the attribute value"}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.addAttributeModifier((Attribute) params[1], new AttributeModifier((String) params[2], (Double) params[3], (AttributeModifier.Operation) params[4]));
            item.setItemMeta(meta);
            return true;
        }
    },
    ADD_ENCHANT_TO_ITEM("", Material.ENCHANTING_TABLE, "ADD_ENCHANT_TO_ITEM", new Class[]{ItemStack.class, Enchantment.class, Integer.class}, new String[]{"", "", "The level"}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant((Enchantment) params[1], (Integer) params[2], true);
            item.setItemMeta(meta);
            return true;
        }
    },
    ADD_ITEM_FLAG_TO_ITEM("Add an item flag to an item (Item flag is a setting. e.g HIDE_ENCHANTMENTS).", Material.WHITE_BANNER, "ADD_ITEM_FLAG_TO_ITEM", new Class[]{ItemStack.class, ItemFlag.class}, new String[]{"", "The item flag (item flag is a setting. e.g HIDE_ENCHANTMENT)"}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags((ItemFlag) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    ADD_PASSENGER_TO_ENTITY("", Material.SADDLE, "ADD_PASSENGER_TO_ENTITY", new Class[]{Entity.class, Entity.class}, new String[]{"The entity", "The passenger to add"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).addPassenger((Entity) params[1]);
            return true;
        }
    },
    ADD_POTION_EFFECT_TO_LIVING_ENTITY("", Material.POTION, "ADD_POTION_EFFECT_TO_LIVING_ENTITY", new Class[]{LivingEntity.class, PotionEffectType.class, Integer.class, Integer.class, Boolean.class, Boolean.class, Boolean.class}, new String[]{"", "The effect type", "Duration", "Amplifier", "If it is ambient", "If it has particles", "If it shows icon"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).addPotionEffect(new PotionEffect((PotionEffectType) params[1], (Integer) params[2], (Integer) params[3], (Boolean) params[4], (Boolean) params[5], (Boolean) params[6]));
            return true;
        }
    },
    ADD_SCOREBOARD_TAG_TO_ENTITY("", Material.NAME_TAG, "ADD_SCOREBOARD_TAG_TO_ENTITY", new Class[]{Entity.class, String.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).addPassenger((Entity) params[1]);
            return true;
        }
    },
    ADD_TO_ITEM_LORE("Adds text to the item's lore (body text).", Material.NAME_TAG, "ADD_TO_ITEM_LORE", new Class[]{ItemStack.class, String.class}, new String[]{"", "The lore, use '\\n' to go down a line."}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            String str = (String) params[1];
            String[] arr = str.split("\\\\n");
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.addAll(Arrays.asList(arr));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return true;
        }
    },
    APPLY_BONE_MEAL("Apply bone meal to a block.", Material.BONE_MEAL, "APPLY_BONE_MEAL", new Class[]{Block.class, BlockFace.class}, new String[]{"The block to apply to", "The direction to apply to"}) {
        @Override
        public boolean action(Object... params) {
            ((Block) params[0]).applyBoneMeal((BlockFace) params[1]);
            return true;
        }
    },
    BREAK_BLOCK_NATURALLY("", Material.CHIPPED_ANVIL, "BREAK_BLOCK_NATURALLY", new Class[]{Block.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Block) params[0]).breakNaturally();
            return true;
        }
    },
    BROADCAST_MESSAGE("Broadcast a message to everyone on the server.", Material.JUKEBOX, "BROADCAST_MESSAGE", new Class[]{String.class}, new String[]{"The message"}) {
        @Override
        public boolean action(Object... params) {
            Bukkit.broadcastMessage((String) params[0]);
            return true;
        }
    },
    CHANGE_SIGN_FOR_PLAYER("Make a sign changed only for a certain player.", Material.ACACIA_SIGN, "CHANGE_SIGN_FOR_PLAYER", new Class[]{Player.class, List.class, DyeColor.class, Boolean.class}, new String[]{"", "The lines", "The text color", "If has glowing text"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).sendSignChange((Location) params[1], (String[]) params[2], (DyeColor) params[3], (Boolean) params[4]);
            return true;
        }
    },
    CHAT_FOR_PLAYER("Send a message as the player.", Material.PLAYER_HEAD, "CHAT_FOR_PLAYER", new Class[]{Player.class, String.class}, new String[]{"", "The message"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).chat((String) params[1]);
            return true;
        }
    },
    CLOSE_INVENTORY_OF_PLAYER("Close the current opened inventory of a player.", Material.CRAFTING_TABLE, "CLOSE_INVENTORY_OF_PLAYER", new Class[]{Player.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).closeInventory();
            return true;
        }
    },
    CREATE_EXPLOSION_AT_LOCATION("", Material.TNT, "CREATE_EXPLOSION_AT_LOCATION", new Class[]{Location.class, Float.class, Boolean.class, Boolean.class}, new String[]{"The location of the explosion", "The explosion power", "If the explosion should set blocks on fire", "If the explosion should break blocks"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().createExplosion(loc, (Float) params[1], (Boolean) params[2], (Boolean) params[3]);
            return true;
        }
    },
    DAMAGE_BLOCK_AS_PLAYER("Make the player see as if he damages a block (even tho it doesn't effect the block)", Material.CRACKED_STONE_BRICKS, "DAMAGE_BLOCK_AS_PLAYER", new Class[]{Player.class, Location.class, Float.class}, new String[]{"", "The location of the block", "Damage amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).sendBlockDamage((Location) params[1], (Float) params[2]);
            return true;
        }
    },
    DAMAGE_ENTITY("Hit an entity with a certain amount of damage.", Material.NETHERITE_SWORD, "DAMAGE_ENTITY", new Class[]{Entity.class, Double.class}, new String[]{"", "The damage amount"}) {
        @Override
        public boolean action(Object... params) {
            Entity e = (Entity) params[0];
            if (e instanceof Damageable)
                ((Damageable) e).damage((Double) params[1]);
            return true;
        }
    },
    DROP_ITEM_IN_LOCATION("Spawn an item at a location.", Material.DROPPER, "DROP_ITEM_IN_LOCATION", new Class[]{Location.class, ItemStack.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().dropItem(loc, (ItemStack) params[1]);
            return true;
        }
    },
    DROP_ITEM_NATURALLY_IN_LOCATION("Spawn an item naturally at a location", Material.DROPPER, "DROP_ITEM_NATURALLY_IN_LOCATION", new Class[]{Location.class, ItemStack.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().dropItemNaturally(loc, (ItemStack) params[1]);
            return true;
        }
    },
    EJECT_PASSENGERS_FROM_ENTITY("Remove all passengers from an entity.", Material.SADDLE, "EJECT_PASSENGERS_FROM_ENTITY", new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).eject();
            return true;
        }
    },
    FORCE_ENTITY_TO_LEAVE_VEHICLE("", Material.ACTIVATOR_RAIL, "FORCE_ENTITY_TO_LEAVE_VEHICLE", new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).leaveVehicle();
            return true;
        }
    },
    GENERATE_TREE_AT_LOCATION("Generate a tree at a location (need to be one block above ground).", Material.OAK_SAPLING, "GENERATE_TREE_AT_LOCATION", new Class[]{Location.class, TreeType.class}, new String[]{"", "The type of the tree"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().generateTree(loc, (TreeType) params[1]);
            return true;
        }
    },
    GIVE_LEVELS_TO_PLAYER("Give xp levels to a player.", Material.EXPERIENCE_BOTTLE, "GIVE_LEVELS_TO_PLAYER", new Class[]{Player.class, Integer.class}, new String[]{"", "Xp levels amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).giveExpLevels((Integer) params[1]);
            return true;
        }
    },
    GIVE_XP_TO_PLAYER("Give xp orbs to a player.", Material.EXPERIENCE_BOTTLE, "GIVE_XP_TO_PLAYER", new Class[]{Player.class, Integer.class}, new String[]{"", "Xp amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).giveExp((Integer) params[1]);
            return true;
        }
    },
    KICK_PLAYER("Kick a player (force him to quit the server).", ItemStackUtil.newItemStack(Material.ENCHANTED_BOOK, "Kick Player", null, 1, meta -> {
        ((EnchantmentStorageMeta) meta).addStoredEnchant(Enchantment.KNOCKBACK, 1, true);
    }), "KICK_PLAYER", new Class[]{Player.class, String.class}, new String[]{"", "The message to show"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).kickPlayer((String) params[1]);
            return true;
        }
    },
    LAUNCH_PROJECTILE_FROM_LIVING_ENTITY("Shoot a projectile from an entity (e.g arrow,fireball).", Material.FIRE_CHARGE, "LAUNCH_PROJECTILE_FROM_LIVING_ENTITY", new Class[]{LivingEntity.class, Projectile.class, Vector.class}, new String[]{"", "The projectile type", "The velocity"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).launchProjectile(((Projectile) params[1]).getClass(), (Vector) params[2]);
            return true;
        }
    },
    LOAD_CHUNK_AT_LOCATION("", Material.SLIME_BLOCK, "LOAD_CHUNK_AT_LOCATION", new Class[]{Location.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Location) params[0]).getChunk().load();
            return true;
        }
    },
    MAKE_LIVING_ENTITY_ATTACK_ENTITY("Make an entity melee attack another ENTITY.", Material.GOLDEN_SWORD, "MAKE_LIVING_ENTITY_ATTACK_ENTITY", new Class[]{LivingEntity.class, Entity.class}, new String[]{"The attacker", "The entity to attack"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).attack((Entity) params[1]);
            return true;
        }
    },
    MAKE_PLAYER_DROP_ITEM_FROM_HAND("Force a player to drop the item he is currently holding.", ItemStackUtil.newItemStack(Material.SPLASH_POTION, "Make Player Drop Item From Hand", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.WATER));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "MAKE_PLAYER_DROP_ITEM_FROM_HAND", new Class[]{Player.class, Boolean.class}, new String[]{"", "If should drop the whole stack"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).dropItem((Boolean) params[1]);
            ((Player) params[0]).updateInventory();
            return true;
        }
    },
    MAKE_PLAYER_SLEEP("", Material.WHITE_BED, "MAKE_PLAYER_SLEEP", new Class[]{Player.class, Location.class, Boolean.class}, new String[]{"", "The location to sleep at", "If should force"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).sleep((Location) params[1], (Boolean) params[2]);
            return true;
        }
    },
    OPEN_BOOK_FOR_PLAYER("", Material.WRITTEN_BOOK, "OPEN_BOOK_FOR_PLAYER", new Class[]{Player.class, ItemStack.class}, new String[]{"", "The book (must be a book!)"}) {
        @Override
        public boolean action(Object... params) {
            if (!((ItemStack) params[1]).getType().equals(Material.WRITTEN_BOOK))
                return false;
            ((Player) params[0]).openBook((ItemStack) params[1]);
            return true;
        }
    },
    OPEN_CRAFTING_TABLE_FOR_PLAYER("Open a crafting table menu for a player.", Material.CRAFTING_TABLE, "OPEN_CRAFTING_TABLE_FOR_PLAYER", new Class[]{Player.class, Boolean.class}, new String[]{"", "If should force open it"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).openWorkbench(null, (Boolean) params[1]);
            return true;
        }
    },
    OPEN_ENCHANTING_TABLE_FOR_PLAYER("Open an enchanting table menu for a player.", Material.ENCHANTING_TABLE, "OPEN_ENCHANTING_TABLE_FOR_PLAYER", new Class[]{Player.class, Boolean.class}, new String[]{"", "If should force open it (usually true)"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).openEnchanting(null, (Boolean) params[1]);
            return true;
        }
    },
    OPEN_SIGN_FOR_PLAYER("", Material.OAK_SIGN, "OPEN_SIGN_FOR_PLAYER", new Class[]{Player.class, Sign.class}, new String[]{"", "The sign"}) {
        @Override
        public boolean action(Object... params) {
            if (!(params[1] instanceof Sign))
                return false;
            ((Player) params[0]).openSign((Sign) params[1]);
            return true;
        }
    },
    PERFORM_COMMAND_AS_PLAYER("Make a player execute a command.", Material.COMMAND_BLOCK, "EXECUTE_COMMAND_AS_PLAYER", new Class[]{Player.class, String.class}, new String[]{"", "The command"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).performCommand((String) params[1]);
            return true;
        }
    },
    PLAY_EFFECT_AT_LOCATION("", Material.JUKEBOX, "PLAY_EFFECT_AT_LOCATION", new Class[]{Location.class, Effect.class, Integer.class, Integer.class}, new String[]{"", "The effect", "The effect's data", "The radius"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().playEffect(loc, (Effect) params[1], (int) params[2], (int) params[3]);
            return true;
        }
    },
    PLAY_EFFECT_ON_ENTITY("", Material.BEACON, "PLAY_EFFECT_ON_ENTITY", new Class[]{Entity.class, EntityEffect.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).playEffect((EntityEffect) params[1]);
            return true;
        }
    },
    PLAY_SOUND_AT_LOCATION("", Material.JUKEBOX, "PLAY_SOUND_AT_LOCATION", new Class[]{Location.class, Sound.class, Float.class, Float.class}, new String[]{"", "The sound", "The volume", "The pitch"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().playSound(loc, (Sound) params[1], (Float) params[2], (Float) params[3]);
            return true;
        }
    },
    PLAY_SOUND_TO_PLAYER("Play sound only to a certain player.", Material.JUKEBOX, "PLAY_SOUND_TO_PLAYER", new Class[]{Player.class, Location.class, Sound.class, Float.class, Float.class}, new String[]{"", "The location of the sound", "The sound", "Volume", "Pitch"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).playSound((Location) params[1], (Sound) params[2], (Float) params[3], (Float) params[4]);
            return true;
        }
    },
    REMOVE_ATTRIBUTE_TO_ITEM("", Material.TOTEM_OF_UNDYING, "REMOVE_ATTRIBUTE_FROM_ITEM", new Class[]{ItemStack.class, Attribute.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.removeAttributeModifier((Attribute) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    REMOVE_ENCHANT_FROM_ITEM("", Material.GRINDSTONE, "REMOVE_ENCHANT_FROM_ITEM", new Class[]{ItemStack.class, Enchantment.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.removeEnchant((Enchantment) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    REMOVE_ENTITY("Removes an entity (THIS IS NOT KILLING AND WILL NOT WORK ON PLAYERS!)", Material.DIAMOND_SWORD, "REMOVE_ENTITY", new Class[]{Entity.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            Entity e = ((Entity) params[0]);
            if (e instanceof Player) {
                Logging.log("Remove Entity action was attempted to remove player. Was ignored.", LoggingOptions.INFO);
                return false;
            }
            e.remove();
            return true;
        }
    },
    REMOVE_ITEM_FLAG_FROM_ITEM("Remove an item flag from an item (Item flag is a setting. e.g HIDE_ENCHANTMENTS).", Material.RED_BANNER, "REMOVE_ITEM_FLAG_FROM_ITEM", new Class[]{ItemStack.class, ItemFlag.class}, new String[]{"", "The item flag to remove (item flag is a setting. e.g HIDE_ENCHANTMENT)"}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.removeItemFlags((ItemFlag) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    REMOVE_PASSENGER_FROM_ENTITY("", Material.SADDLE, "REMOVE_PASSENGER_FROM_ENTITY", new Class[]{Entity.class, Entity.class}, new String[]{"The entity", "The passenger to remove"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).addPassenger((Entity) params[1]);
            return true;
        }
    },
    REMOVE_PERSISTENT_DATA_FROM_ITEM("Removes data stored inside an item", Material.PAPER, "REMOVE_PERSISTENT_DATA_FROM_ITEM", new Class[]{ItemStack.class, String.class}, new String[]{"", "The key to remove the value from"}) {
        @Override
        public boolean action(Object... params) {
            PDCUtil.remove((ItemStack) params[0], (String) params[1]);
            return true;
        }
    },
    REMOVE_PERSISTENT_DATA_FROM_LIVING_ENTITY("Removes data stored inside an entity", Material.PAPER, "REMOVE_PERSISTENT_DATA_FROM_LIVING_ENTITY", new Class[]{LivingEntity.class, String.class}, new String[]{"", "The key to remove the value from"}) {
        @Override
        public boolean action(Object... params) {
            PDCUtil.remove((LivingEntity) params[0], (String) params[1]);
            return true;
        }
    },
    REMOVE_POTION_EFFECT_FROM_LIVING_ENTITY("", Material.POTION, "REMOVE_POTION_EFFECT_FROM_LIVING_ENTITY", new Class[]{LivingEntity.class, PotionEffectType.class}, new String[]{"", "The type"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).removePotionEffect((PotionEffectType) params[1]);
            return true;
        }
    },
    REMOVE_SCOREBOARD_TAG_FROM_ENTITY("", Material.NAME_TAG, "REMOVE_SCOREBOARD_TAG_FROM_ENTITY", new Class[]{Entity.class, String.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).removeScoreboardTag((String) params[1]);
            return true;
        }
    },
    RESET_PLAYER_TITLE("Reset the title currently shown to a player.", Material.NAME_TAG, "RESET_PLAYER_TITLE", new Class[]{Player.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).resetTitle();
            return true;
        }
    },
    RESET_WEATHER_FOR_PLAYER("Reset the weather of where the player is.", Material.SUNFLOWER, "RESET_WEATHER_FOR_PLAYER", new Class[]{Player.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).resetPlayerWeather();
            return true;
        }
    },
    SAVE_WORLD("", Material.PAPER, "SAVE_WORLD", new Class[]{World.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).save();
            return true;
        }
    },
    SEND_MESSAGE_TO_ENTITY("", Material.OAK_SIGN, "SEND_MESSAGE_TO_ENTITY", new Class[]{Entity.class, String.class}, new String[]{"", "The message"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).sendMessage((String) params[1]);
            return true;
        }
    },
    SEND_TITLE_TO_PLAYER("Send a title to a player (shows on the middle of the screen).", Material.NAME_TAG, "SEND_TITLE_TO_PLAYER", new Class[]{Player.class, String.class, String.class, Integer.class, Integer.class, Integer.class}, new String[]{"", "The title", "The subtitle", "Fade in ticks amount", "Stay ticks amount", "Fade out ticks amount"}) {
        @Override
        public boolean action(Object... params) {

            ((Player) params[0]).sendTitle((String) params[1], (String) params[2], (Integer) params[3], (Integer) params[4], (Integer) params[5]);
            return true;
        }
    },
    SET_BIOME("Set a biome to a certain location.", Material.GRASS_BLOCK, "SET_BIOME", new Class[]{Location.class, Biome.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            Location loc = (Location) params[0];
            Logging.log("biome: " + params[1], LoggingOptions.TEST);
            Logging.log("loc: " + loc.toVector(), LoggingOptions.TEST);
            loc.getWorld().setBiome(loc, (Biome) params[1]);
            return true;
        }
    },
    SET_BLOCK_MATERIAL("", Material.SMITHING_TABLE, "SET_BLOCK_MATERIAL", new Class[]{Block.class, Material.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Block) params[0]).setType((Material) params[1]);
            return true;
        }
    },
    SET_BOOLEAN_GAME_RULE_OF_WORLD("Set a game rule value which is a boolean.", Material.COMMAND_BLOCK, "SET_BOOLEAN_GAME_RULE_OF_WORLD", new Class[]{World.class, GameRule.class, Boolean.class}, new String[]{"", "The game rule (must be a boolean value game rule!)", "The value"}) {
        @Override
        public boolean action(Object... params) {
            GameRule rule = (GameRule) params[1];
            if (rule.getType().equals(Boolean.class))
                ((World) params[0]).setGameRule(rule, (Boolean) params[2]);
            else ((World) params[0]).setGameRule(rule, (Boolean) params[2] ? 1 : 0);

            return true;
        }
    },
    SET_CLEAR_WEATHER_DURATION_IN_WORLD("Set the duration of the clear weather in the world.", Material.WHITE_STAINED_GLASS, "SET_CLEAR_WEATHER_DURATION_IN_WORLD", new Class[]{World.class, Integer.class}, new String[]{"", "Duration in ticks"}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setClearWeatherDuration((Integer) params[1]);
            return true;
        }
    },
    SET_CURRENT_WEATHER_DURATION_IN_WORLD("Set the weather duration of the current weather in a world.", Material.BLUE_STAINED_GLASS_PANE, "SET_CURRENT_WEATHER_DURATION_IN_WORLD", new Class[]{World.class, Integer.class}, new String[]{"", "Duration in ticks"}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setWeatherDuration((Integer) params[1]);
            return true;
        }
    },
    SET_CUSTOM_MODEL_DATA_OF_ITEM("Set the custom model data value of an item (this is used by resource packs).", Material.PAINTING, "SET_CUSTOM_MODEL_DATA_OF_ITEM", new Class[]{ItemStack.class, Integer.class}, new String[]{"", "The custom model data"}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData((Integer) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },

    SET_ENTITY_ABSORPTION_AMOUNT("Set the amount of absorption an entity has.", Material.GOLDEN_APPLE, "SET_ENTITY_ABSORPTION_AMOUNT", new Class[]{LivingEntity.class, Double.class}, new String[]{"", "Absorption amount"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setAbsorptionAmount((Double) params[1]);
            return true;
        }
    },
    SET_ENTITY_CUSTOM_NAME("Set the custom name of an entity (like name tag).", Material.NAME_TAG, "SET_ENTITY_CUSTOM_NAME", new Class[]{Entity.class, String.class}, new String[]{"", "The name"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setCustomName((String) params[1]);
            ((Entity) params[0]).setCustomNameVisible(true);
            return true;
        }
    } // after setting it, surely need to display it
    ,
    SET_ENTITY_CUSTOM_NAME_VISIBLE("", Material.NAME_TAG, "SET_ENTITY_CUSTOM_NAME_VISIBLE", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setCustomNameVisible((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_FALL_DISTANCE("Set the distance an entity has already fell.", Material.FEATHER, "SET_ENTITY_FALL_DISTANCE", new Class[]{Entity.class, Float.class}, new String[]{"", "The distance"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setFallDistance((Float) params[1]);
            return true;
        }
    },
    SET_ENTITY_FIRE_TICKS("Set the amount of ticks an entity will be on fire.", Material.BLAZE_POWDER, "SET_ENTITY_FIRE_TICKS", new Class[]{Entity.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setFireTicks((Integer) params[1]);
            return true;
        }
    },
    SET_ENTITY_FREEZE_TICKS("Set the amount of ticks which an entity is frozen.", Material.PACKED_ICE, "SET_ENTITY_FREEZE_TICKS", new Class[]{Entity.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setFreezeTicks((Integer) params[1]);
            return true;
        }
    },
    SET_ENTITY_GLOWING("Set entity glowing (like spectral arrow effect).", Material.GLOWSTONE, "SET_ENTITY_GLOWING", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setGlowing((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_GRAVITY("Set the gravity of entity (NOTE: NO GRAVITY DOES NOT MEAN FLIGHT!).", Material.ANVIL, "SET_ENTITY_GRAVITY", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setGravity((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_INVULNERABLE("Set invulnerability of entity (if set to true, the entity does not take any damage).", Material.BEDROCK, "SET_ENTITY_INVULNERABLE", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setInvulnerable((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_OP("Set if entity is a server operator (used mostly for players).", Material.ENCHANTED_GOLDEN_APPLE, "SET_ENTITY_OP", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setOp((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_PORTAL_COOLDOWN("Set the cooldown which the entity can't enter a portal.", Material.END_PORTAL_FRAME, "SET_ENTITY_PORTAL_COOLDOWN", new Class[]{Entity.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setPortalCooldown((Integer) params[1]);
            return true;
        }
    },
    SET_ENTITY_ROTATION("", Material.AIR, "SET_ENTITY_ROTATION", new Class[]{Entity.class, Float.class, Float.class}, new String[]{"", "Yaw", "Pitch"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setRotation((Float) params[1], (Float) params[2]);
            return true;
        }
    },
    SET_ENTITY_SILENT("Set if an entity is silent (it wont make any sounds).", Material.AIR, "SET_ENTITY_SILENT", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setSilent((Boolean) params[1]);
            return true;
        }
    },
    SET_ENTITY_VELOCITY("", Material.FEATHER, "SET_ENTITY_VELOCITY", new Class[]{Entity.class, Vector.class}, new String[]{"", "Velocity vector"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setVelocity((Vector) params[1]);
            return true;
        }
    },
    SET_ENTITY_VISUAL_FIRE("Set if an entity should have visual fire (not acctual fire, just the visual effect).", Material.NAME_TAG, "SET_ENTITY_VISUAL_FIRE", new Class[]{Entity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).setVisualFire((Boolean) params[1]);
            return true;
        }
    },
    SET_IF_PLAYER_IS_HEALTH_SCALED("Set if the health displayed to the player should be scaled.", ItemStackUtil.newItemStack(Material.POTION, "Set IF Player IS Health Scaled", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_IF_PLAYER_IS_HEALTH_SCALED", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setHealthScaled((Boolean) params[1]);
            return true;
        }
    },
    SET_IF_PLAYER_IS_SLEEPING_IGNORING("Set if the player is ignoring sleep (phantoms start to attack).", Material.BLACK_BED, "SET_IF_PLAYER_IS_SLEEPING_IGNORING", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setSleepingIgnored((Boolean) params[1]);
            return true;
        }
    },
    SET_INTEGER_GAME_RULE_OF_WORLD("Set a game rule value which is an integer.", Material.COMMAND_BLOCK, "SET_INTEGER_GAME_RULE_OF_WORLD", new Class[]{World.class, GameRule.class, Integer.class}, new String[]{"", "The game rule (Must be an integer value game rule!)", "The value"}) {
        @Override
        public boolean action(Object... params) {
            GameRule rule = (GameRule) params[1];
            if (rule.getType().equals(Boolean.class))
                ((World) params[0]).setGameRule(rule, (Integer) params[2] > 0);
            else
                ((World) params[0]).setGameRule(rule, (Integer) params[2]);
            return true;
        }
    },
    SET_ITEM_DISPLAY_NAME("", Material.NAME_TAG, "SET_ITEM_DISPLAY_NAME", new Class[]{ItemStack.class, String.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName((String) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    SET_ITEM_LORE("Set the item's lore (body text).", Material.NAME_TAG, "SET_ITEM_LORE", new Class[]{ItemStack.class, String.class}, new String[]{"", "The lore, use '\\n' to go down a line."}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            String str = (String) params[1];
            String[] arr = str.split("\\\\n");
            meta.setLore(Arrays.asList(arr));
            item.setItemMeta(meta);
            return true;
        }
    },
    SET_ITEM_MATERIAL_TYPE("Set the material type of an item (e.g diamond pickaxe - DIAMOND_PICKAXE).", Material.STICK, "SET_ITEM_MATERIAL_TYPE", new Class[]{ItemStack.class, Material.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((ItemStack) params[0]).setType((Material) params[1]);
            return true;
        }
    },
    SET_ITEM_STACK_AMOUNT("Set the stack amount of an item (the quantity of an item).", Material.STICK, "SET_ITEM_STACK_AMOUNT", new Class[]{ItemStack.class, Integer.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((ItemStack) params[0]).setAmount((Integer) params[1]);
            return true;
        }
    },
    SET_ITEM_UNBREAKABLE("Set if an item is unbreakable (if the item can't lose durability).", Material.BEDROCK, "SET_ITEM_UNBREAKABLE", new Class[]{ItemStack.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ItemStack item = ((ItemStack) params[0]);
            ItemMeta meta = item.getItemMeta();
            meta.setUnbreakable((Boolean) params[1]);
            item.setItemMeta(meta);
            return true;
        }
    },
    SET_LAST_DAMAGE_LIVING_ENTITY_TOOK("Set the last damage amount an entity had took.", Material.WITHER_SKELETON_SKULL, "SET_LAST_DAMAGE_LIVING_ENTITY_TOOK", new Class[]{LivingEntity.class, Double.class}, new String[]{"", "The damage amount"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setLastDamage((Double) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_ARROWS_AMOUNT_IN_BODY("Set the amount of arrows stuck in an entity.", Material.ARROW, "SET_LIVING_ENTITY_ARROWS_AMOUNT_IN_BODY", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setArrowsInBody((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_ARROW_COOLDOWN("Set the cooldown of when an arrow is going to be removed from an entity's body (when an entity is hit by arrow, how much time until it will be removed).", Material.ARROW, "SET_LIVING_ENTITY_ARROW_COOLDOWN", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", "Cooldown in ticks"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setArrowCooldown((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_CAN_PICKUP_ITEMS("Set if an entity is able to pickup items.", Material.HOPPER, "SET_LIVING_ENTITY_CAN_PICKUP_ITEMS", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setCanPickupItems((Boolean) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_COLLIDABLE("Set if an entity is collidable (if other entities can go through it).", Material.GRASS, "SET_LIVING_ENTITY_COLLIDABLE", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setCollidable((Boolean) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_CURRENT_NO_DAMAGE_TICKS("Set the current duration which an entity did not take any damage.", Material.CACTUS, "SET_LIVING_ENTITY_CURRENT_NO_DAMAGE_TICKS", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setNoDamageTicks((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_GLIDING("Set an entity gliding (like with elytra).", Material.ELYTRA, "SET_LIVING_ENTITY_GLIDING", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setGliding((Boolean) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_HEALTH("Set the health amount of an entity", ItemStackUtil.newItemStack(Material.POTION, "Set Living Entity Health", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_LIVING_ENTITY_HEALTH", new Class[]{LivingEntity.class, Double.class}, new String[]{"", "Health amount"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setHealth((Double) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_INVISIBLE("", ItemStackUtil.newItemStack(Material.POTION, "Set Entity Invisible", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_LIVING_ENTITY_INVISIBLE", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setInvisible((Boolean) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_LEASH_HOLDER("Set the entity which holds an entity with a lead.", Material.LEAD, "SET_LIVING_ENTITY_LEASH_HOLDER", new Class[]{LivingEntity.class, Entity.class}, new String[]{"The entity", "The leash holder"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setLeashHolder((Entity) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_MAX_AIR("Set the maximum amount of air bubbles an entity has.", Material.PUFFERFISH, "SET_LIVING_ENTITY_MAX_AIR", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setMaximumAir((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_MAX_NO_DAMAGE_TICKS("Set the highest duration which an entity did not take any damage.", Material.CACTUS, "SET_LIVING_ENTITY_MAX_NO_DAMAGE_TICKS", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setMaximumNoDamageTicks((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_REMAINING_AIR("Set the current air bar level (air bubbles).", Material.PUFFERFISH, "SET_LIVING_ENTITY_REMAINING_AIR", new Class[]{LivingEntity.class, Integer.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setRemainingAir((Integer) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_REMOVED_WHEN_FAR_AWAY("Set if an entity should despawn when out of range.", Material.SPYGLASS, "SET_LIVING_ENTITY_REMOVED_WHEN_FAR_AWAY", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setRemoveWhenFarAway((Boolean) params[1]);
            return true;
        }
    },
    SET_LIVING_ENTITY_SWIMMING("", Material.COD, "SET_LIVING_ENTITY_SWIMMING", new Class[]{LivingEntity.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).setSwimming((Boolean) params[1]);
            return true;
        }
    },
    SET_PERSISTENT_DATA_TO_ITEM("Sets data inside an item, this data will be saved after restart.", Material.PAPER, "SET_PERSISTENT_DATA_TO_ITEM", new Class[]{ItemStack.class, String.class, Object.class}, new String[]{"", "The key to set the value to", "The value"}) {
        @Override
        public boolean action(Object... params) {
            PDCUtil.set((ItemStack) params[0], (String) params[1], params[2]);
            return true;
        }
    },
    SET_PERSISTENT_DATA_TO_LIVING_ENTITY("Sets data inside an entity, this data will be saved after restart.", Material.PAPER, "SET_PERSISTENT_DATA_TO_LIVING_ENTITY", new Class[]{LivingEntity.class, String.class, Object.class}, new String[]{"", "The key to set the value to", "The value"}) {
        @Override
        public boolean action(Object... params) {
            PDCUtil.set((LivingEntity) params[0], (String) params[1], params[2]);
            return true;
        }
    },
    SET_PLAYER_ALLOWED_TO_FLY("Set if a player is allowed to fly.", Material.ELYTRA, "SET_PLAYER_ALLOWED_TO_FLY", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setAllowFlight((Boolean) params[1]);
            return true;
        }
    },
    SET_PLAYER_BED_SPAWN_LOCATION("", Material.RED_BED, "SET_PLAYER_BED_SPAWN_LOCATION", new Class[]{Player.class, Location.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setBedSpawnLocation((Location) params[1], true);
            return true;
        }
    },
    SET_PLAYER_COMPASS_TARGET("Set the target which a player's compass is targeting.", Material.COMPASS, "SET_PLAYER_COMPASS_TARGET", new Class[]{Player.class, Location.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setCompassTarget((Location) params[1]);
            return true;
        }
    },
    SET_PLAYER_COOLDOWN_OF_SPECIFIC_MATERIAL("Set the cooldown of a certain material of a player. (The cooldown which a player can't use the specific material).", Material.CLOCK, "SET_PLAYER_COOLDOWN_OF_SPECIFIC_MATERIAL", new Class[]{Player.class, Material.class, Integer.class}, new String[]{"", "The material", "Cooldown ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setCooldown((Material) params[1], (Integer) params[2]);
            return true;
        }
    },
    SET_PLAYER_CURRENT_SATURATION("Set the current saturation (the 'hidden food bar').", Material.GLISTERING_MELON_SLICE, "SET_PLAYER_CURRENT_SATURATION", new Class[]{Player.class, Float.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setSaturation((Float) params[1]);
            return true;
        }
    },
    SET_PLAYER_EXHAUSTION("Set the amount of exhaustion a player has (How fast will the player lose saturation level).", Material.CAKE, "SET_PLAYER_EXHAUSTION", new Class[]{Player.class, Float.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setExhaustion((Float) params[1]);
            return true;
        }
    },
    SET_PLAYER_FLYING("Set if the player is currently flying. (PLAYER MUST BE ALLOWED FLIGHT FIRST)", Material.ELYTRA, "SET_PLAYER_FLYING", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            if (((Player) params[0]).getAllowFlight())
                ((Player) params[0]).setFlying((Boolean) params[1]);
            return true;
        }
    },
    SET_PLAYER_FLY_SPEED("", Material.ELYTRA, "SET_PLAYER_FLY_SPEED", new Class[]{Player.class, Float.class}, new String[]{"", "The speed"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setFlySpeed((Float) params[1]);
            return true;
        }
    },
    SET_PLAYER_FOOD_LEVEL("Set the food bar level.", Material.COOKED_BEEF, "SET_PLAYER_FOOD_LEVEL", new Class[]{Player.class, Integer.class}, new String[]{"", "The level"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setFoodLevel((Integer) params[1]);
            return true;
        }
    },
    SET_PLAYER_GAME_MODE("Set the game mode of a player (e.g CREATIVE).", Material.ENCHANTED_GOLDEN_APPLE, "SET_PLAYER_GAME_MODE", new Class[]{Player.class, GameMode.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setGameMode((GameMode) params[1]);
            return true;
        }
    },
    SET_PLAYER_HEALTH_SCALE("Set the health scale which is displayed to the player with this formula: Health / Max Health * Health scale.", ItemStackUtil.newItemStack(Material.POTION, "Set Player Health Scale", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_PLAYER_HEALTH_SCALE", new Class[]{Player.class, Double.class}, new String[]{"", "The scale"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setHealthScale((Double) params[1]);
            return true;
        }
    },
    SET_PLAYER_HIDDEN_FOR_PLAYER("Hide a player from another player.", ItemStackUtil.newItemStack(Material.POTION, "Set Player Hidden For Player", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_PLAYER_HIDDEN_FROM_PLAYER", new Class[]{Player.class, Player.class}, new String[]{"The player to hide", "The player"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[1]).hidePlayer(Main.getInstance(), (Player) params[0]);
            return true;
        }
    },
    SET_PLAYER_LAST_DEATH_LOCATION("", Material.SKELETON_SKULL, "SET_PLAYER_LAST_DEATH_LOCATION", new Class[]{Player.class, Location.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setLastDeathLocation((Location) params[1]);
            return true;
        }
    },
    SET_PLAYER_SATURATION_REGEN_RATE("Set the health regen rate of a player when he is saturated (when food bar is full).", Material.GOLDEN_CARROT, "SET_PLAYER_SATURATION_REGEN_RATE", new Class[]{Player.class, Integer.class}, new String[]{"", "Regen rate in ticks (1 point per x ticks)"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setSaturatedRegenRate((Integer) params[1]);
            return true;
        }
    },
    SET_PLAYER_SNEAKING("", Material.SMOOTH_STONE_SLAB, "SET_PLAYER_SNEAKING", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setSneaking((Boolean) params[1]);
            return true;
        }
    },
    SET_PLAYER_SPRINTING("", ItemStackUtil.newItemStack(Material.POTION, "Set Player Sprinting", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.SPEED));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_PLAYER_SPRINTING", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setSprinting((Boolean) params[1]);
            return true;
        }
    },
    SET_PLAYER_STARVATION_RATE("The rate which a player loses hearts when he is starving (his food bar level is 0).", Material.CHICKEN, "SET_PLAYER_STARVATION_RATE", new Class[]{Player.class, Integer.class}, new String[]{"", "Ticks amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setStarvationRate((Integer) params[1]);
            return true;
        }
    },
    SET_PLAYER_TOTAL_XP("", Material.EXPERIENCE_BOTTLE, "SET_PLAYER_TOTAL_XP", new Class[]{Player.class, Integer.class}, new String[]{"", "Xp amount"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setTotalExperience((Integer) params[1]);
            return true;
        }
    },
    SET_PLAYER_UNHIDDEN_FOR_PLAYER("Make a player unhidden for a certain player (see 'Set Player Hidden For Player').", Material.SPECTRAL_ARROW, "SET_PLAYER_UNHIDDEN_FOR_PLAYER", new Class[]{Player.class, Player.class}, new String[]{"The player to unhide", "The player"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[1]).showPlayer(Main.getInstance(), (Player) params[0]);
            return true;
        }
    },
    SET_PLAYER_UNSATURATED_REGEN_RATE("Set the health regen rate which a player receives when his food bar is not full.", Material.CARROT, "SET_PLAYER_UNSATURATED_REGEN_RATE", new Class[]{Player.class, Integer.class}, new String[]{"", "Regen rate ticks amount (1 point per x ticks)"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setUnsaturatedRegenRate((Integer) params[1]);
            return true;
        }
    },
    SET_PLAYER_WALK_SPEED("", ItemStackUtil.newItemStack(Material.POTION, "Set Player Walk Speed", null, 1, meta -> {
        ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.SPEED));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }), "SET_PLAYER_WALK_SPEED", new Class[]{Player.class, Float.class}, new String[]{"", "The speed"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setWalkSpeed((Float) params[1]);
            return true;
        }
    },
    SET_PLAYER_WEATHER("Set the weather of where the player is.", Material.SUNFLOWER, "SET_PLAYER_WEATHER", new Class[]{Player.class, WeatherType.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setPlayerWeather((WeatherType) params[1]);
            return true;
        }
    },
    SET_PLAYER_WHITELISTED("Set if a player is whitelisted (if the player isn't in the white list, and the server whitelisted option is true, he won't be able to join).", Material.WHITE_DYE, "SET_PLAYER_WHITE_LISTED", new Class[]{Player.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setWhitelisted((Boolean) params[1]);
            return true;
        }
    },
    SET_RESOURCE_PACK_TO_PLAYER("", Material.PAINTING, "SET_RESOURCE_PACK_TO_PLAYER", new Class[]{Player.class, String.class}, new String[]{"", "The resource pack URL"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setResourcePack((String) params[1]);
            return true;
        }
    },
    SET_STATISTIC_TO_PLAYER("Change a statistic of a player.", Material.WRITABLE_BOOK, "SET_STATISTIC_TO_PLAYER", new Class[]{Player.class, Statistic.class, Integer.class}, new String[]{"", "The statistic", "The statistic's value"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).setStatistic((Statistic) params[1], (Integer) params[2]);
            return true;
        }
    },
    SET_STORM_IN_WORLD("Set the world storming.", Material.BLUE_STAINED_GLASS, "SET_STORM_IN_WORLD", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setStorm((Boolean) params[1]);
            return true;
        }
    },
    SET_THUNDERSTORM_DURATION_IN_WORLD("Set the thunderstorm durtaion in a world.", Material.CYAN_STAINED_GLASS, "SET_THUNDERSTORM_DURATION_IN_WORLD", new Class[]{World.class, Integer.class}, new String[]{"", "Duration in ticks"}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setThunderDuration((Integer) params[1]);
            return true;
        }
    },
    SET_THUNDERSTORM_IN_WORLD("Set the world weather thundering.", Material.CYAN_STAINED_GLASS, "SET_THUNDERSTORM_IN_WORLD", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setThundering((Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_ALLOWING_PVP("Set if a world allows player versus player interaction.", Material.NETHERITE_SWORD, "SET_WORLD_ALLOWING_PVP", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setPVP((Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_ALLOW_ANIMALS("Set if a world allows animals to spawn.", Material.SHEEP_SPAWN_EGG, "SET_WORLD_ALLOW_ANIMALS", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            World world = ((World) params[0]);
            world.setSpawnFlags(world.getAllowMonsters(), (Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_ALLOW_MONSTERS("Set if a world allow monsters to spawn.", Material.ZOMBIE_SPAWN_EGG, "SET_WORLD_ALLOW_MONSTERS", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            World world = ((World) params[0]);
            world.setSpawnFlags((Boolean) params[1], world.getAllowAnimals());
            return true;
        }
    },
    SET_WORLD_AUTO_SAVE("Set if a world should be auto saved.", Material.PAPER, "SET_WORLD_AUTO_SAVE", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setAutoSave((Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_DIFFICULTY("Set the difficulty of the world (e.g NORMAL,HARD).", Material.BEDROCK, "SET_WORLD_DIFFICULTY", new Class[]{World.class, Difficulty.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setDifficulty((Difficulty) params[1]);
            return true;
        }
    },

    SET_WORLD_HARDCORE("Set if the world hardcore mode activated.", Material.NETHERITE_CHESTPLATE, "SET_WORLD_HARDCORE", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setHardcore((Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_SPAWN_KEPT_LOADED("Set if the world spawn location is kept loaded in memory.", Material.RED_BED, "SET_WORLD_SPAWN_KEPT_LOADED", new Class[]{World.class, Boolean.class}, new String[]{"", ""}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setKeepSpawnInMemory((Boolean) params[1]);
            return true;
        }
    },
    SET_WORLD_SPAWN_LIMIT_OF_CATEGORY("Set the spawn limit of a spawn category.", Material.SPAWNER, "SET_WORLD_SPAWN_LIMIT_OF_CATEGORY", new Class[]{World.class, SpawnCategory.class, Integer.class}, new String[]{"", "", "The limit"}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setSpawnLimit((SpawnCategory) params[1], (Integer) params[2]);
            return true;
        }
    },
    SET_WORLD_SPAWN_LOCATION("", Material.RESPAWN_ANCHOR, "SET_WORLD_SPAWN_LOCATION", new Class[]{Location.class}, new String[]{"The location to set, it will set to the world of this location"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().setSpawnLocation(loc);
            return true;
        }
    },
    SET_WORLD_TICKS_PER_SPAWNS_OF_CATEGORY("Set the cooldown per spawn of a spawn category (this means that if the value is 10, then it will spawn from the spawn category each 10 ticks).", Material.SPAWNER, "SET_WORLD_TICKS_PER_SPAWNS_OF_CATEGORY", new Class[]{World.class, SpawnCategory.class, Integer.class}, new String[]{"", "", "Cooldown in ticks"}) {
        @Override
        public boolean action(Object... params) {
            ((World) params[0]).setTicksPerSpawns((SpawnCategory) params[1], (Integer) params[2]);
            return true;
        }
    },
    SHOOT_ARROW_AT_LOCATION("", Material.ARROW, "SPAWN_ARROW_IN_WORLD", new Class[]{Location.class, Vector.class, Float.class, Float.class}, new String[]{"", "The direction", "The speed", "The spread"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().spawnArrow(loc, (Vector) params[1], (Float) params[2], (Float) params[3]);
            return true;
        }
    },
    SHOW_DEMO_SCREEN_TO_PLAYER("", Material.PAINTING, "SHOW_DEMO_SCREEN_TO_PLAYER", new Class[]{Player.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).showDemoScreen();
            return true;
        }
    },
    SPAWN_ENTITY_AT_LOCATION("", Material.SPAWNER, "SPAWN_ENTITY_AT_LOCATION", new Class[]{Location.class, EntityType.class, Boolean.class}, new String[]{"", "The type of entity", "If should randomize the data of the entity (gear for example)"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().spawnEntity(loc, (EntityType) params[1], (Boolean) params[2]);
            return true;
        }
    },
    SPAWN_FALLING_BLOCK_AT_LOCATION("", Material.GRAVEL, "SPAWN_FALLING_BLOCK_AT_LOCATION", new Class[]{Location.class, Material.class}, new String[]{"", "The block's material"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().spawnFallingBlock(loc, ((Material) params[1]).createBlockData());
            return true;
        }
    },
    SPAWN_PARTICLE_AT_LOCATION("", Material.FIREWORK_ROCKET, "SPAWN_PARTICLE_AT_LOCATION", new Class[]{Location.class, Particle.class, Integer.class, Double.class, Double.class, Double.class, Double.class}, new String[]{"", "The particle", "The particles amount", "x offset (range)", "y offset (range)", "z offset (range)", "Extra data (speed or color for example. depends on the particle)"}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().spawnParticle(
                    (Particle) params[1],
                    loc,
                    (Integer) params[2],
                    (Double) params[3],
                    (Double) params[4],
                    (Double) params[5],
                    ((Double) params[6]),
                    null);
            return true;
        }
    },
    SPAWN_PARTICLE_FOR_PLAYER("Spawn a particle effect shown only for a certain player.", Material.FIREWORK_ROCKET, "SPAWN_PARTICLE_FOR_PLAYER", new Class[]{Player.class, Particle.class, Location.class, Integer.class, Double.class, Double.class, Double.class, Double.class}, new String[]{"The player to show to", "The particle", "The location of the particle", "The particles amount", "X offset (range)", "Y offset (range)", "Z offset (range)", "Extra data (speed or color usually, depends on particle)"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).spawnParticle(
                    (Particle) params[1],
                    (Location) params[2],
                    (Integer) params[3],
                    (Double) params[4],
                    (Double) params[5],
                    (Double) params[6],
                    ((Double) params[7]),
                    null);
            return true;
        }
    },
    STOP_ALL_SOUNDS_FOR_PLAYER("Stop all currently played sounds for a player.", Material.JUKEBOX, "STOP_ALL_SOUNDS_FOR_PLAYER", new Class[]{Player.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).stopAllSounds();
            return true;
        }
    },
    STOP_SOUND_FOR_PLAYER("Stop a certain sound currently played for a player.", Material.JUKEBOX, "STOP_SOUND_FOR_PLAYER", new Class[]{Player.class, Sound.class}, new String[]{"", "The sound to stop"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).stopSound((Sound) params[1]);
            return true;
        }
    },
    STRIKE_LIGHTNING_AT_LOCATION("", Material.LIGHTNING_ROD, "STRIKE_LIGHTNING_AT_LOCATION", new Class[]{Location.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            Location loc = ((Location) params[0]);
            loc.getWorld().strikeLightning(loc);
            return true;
        }
    },
    SWING_LIVING_ENTITY_MAIN_HAND("Swing an entity's main hand (like left click).", Material.IRON_SWORD, "SWING_LIVING_ENTITY_MAIN_HAND", new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).swingMainHand();
            return true;
        }
    },

    SWING_LIVING_ENTITY_OFF_HAND("Swing an entity's off hand.", Material.IRON_PICKAXE, "SWING_LIVING_ENTITY_OFF_HAND", new Class[]{LivingEntity.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((LivingEntity) params[0]).swingOffHand();
            return true;
        }
    },
    TELEPORT_ENTITY("", Material.ENDER_PEARL, "TELEPORT_ENTITY", new Class[]{Entity.class, Location.class}, new String[]{"", "Location to teleport to"}) {
        @Override
        public boolean action(Object... params) {
            ((Entity) params[0]).teleport((Location) params[1]);
            return true;
        }
    },

    UNLOAD_CHUNK("", Material.SLIME_BLOCK, "UNLOAD_CHUNK", new Class[]{Location.class}, new String[]{""}) {
        @Override
        public boolean action(Object... params) {
            ((Location) params[0]).getChunk().unload();
            return true;
        }
    },
    WAKEUP_PLAYER("", Material.BLUE_BED, "WAKEUP_PLAYER", new Class[]{Player.class, Boolean.class}, new String[]{"", "If should force"}) {
        @Override
        public boolean action(Object... params) {
            ((Player) params[0]).wakeup((Boolean) params[1]);
            return true;
        }
    },
    ;


    /**
     *
     */
    private static final Material DEFAULT_ACTION_MATERIAL = Material.PURPLE_STAINED_GLASS_PANE;
    private static final ChatColor DEFAULT_NAME_COLOR = ChatColor.LIGHT_PURPLE;
    /**
     * The node's description
     */
    private String description;
    /**
     * The node's item reference material
     */
    private Material mat;
    /**
     * The node's key
     */
    private String key;
    /**
     * The node's received types
     */
    private Class[] receivedTypes;
    /**
     * The node's item reference
     */
    private ItemStack item;
    /**
     * The node's received types descriptions
     */
    private String[] receivedTypesDescriptions;

    /**
     *
     * @param description the node's description
     * @param mat the node's item reference material
     * @param key the node's key
     * @param receivedTypes the node's received types
     * @param receivedTypesDescriptions the node's received types descriptions
     */
    DefaultActions(String description, Material mat, String key, Class[] receivedTypes, String[] receivedTypesDescriptions) {
        this.description = description;
        this.mat = mat;
        this.key = key;
        this.receivedTypes = receivedTypes;
        this.item = null;
        this.receivedTypesDescriptions = receivedTypesDescriptions;
    }

    /**
     *
     * @param description the node's description
     * @param item the node's item reference
     * @param key the node's key
     * @param receivedTypes the node's received types
     * @param receivedTypesDescriptions the node's received types descriptions
     */
    DefaultActions(String description, ItemStack item, String key, Class[] receivedTypes, String[] receivedTypesDescriptions) {
        this.description = description;
        this.mat = item.getType();
        this.item = item;
        this.key = key;
        this.receivedTypes = receivedTypes;
        this.receivedTypesDescriptions = receivedTypesDescriptions;
    }


    @Override
    public NodeItemStack getItemReference() {
        if (this.item == null)
            return new NodeItemStack(mat == null || mat.equals(Material.AIR) ? DEFAULT_ACTION_MATERIAL : mat, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);
        else {
            //making sure default things are set
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(DEFAULT_NAME_COLOR + meta.getDisplayName());
            item.setItemMeta(meta);
            return new NodeItemStack(item, this);
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getDescription() {
        return this.description;
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
        return new NodeItemStack(DEFAULT_ACTION_MATERIAL, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);

    }


}
