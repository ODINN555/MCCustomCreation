package Nodes.Events;

import Nodes.NodeEnum;
import Nodes.NodeItemStack;
import com.sun.istack.internal.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * An enum for default events
 */
public enum DefaultEvents implements Serializable, IEvent, NodeEnum {
    CHANGED_HELD_ITEM_FROM_ITEM("When a player changes his held creation item to an item.", "CHANGED_HELD_ITEM_FROM_ITEM_EVENT", null) {
        @EventHandler
        public void onHeldItemChanged(PlayerItemHeldEvent event) {
            ItemStack from = event.getPlayer().getInventory().getItem(event.getPreviousSlot());
            if (from != null && !from.getType().equals(Material.AIR))
                executeEvent(from, event.getPlayer(), event);
        }
    },
    CHANGED_HELD_ITEM_TO_ITEM("When a player changes his held item to this creation item.", "CHANGED_HELD_ITEM_TO_ITEM_EVENT", null) {
        @EventHandler
        public void onHeldItemChanged(PlayerItemHeldEvent event) {
            ItemStack to = event.getPlayer().getInventory().getItem(event.getNewSlot());
            if (to != null && !to.getType().equals(Material.AIR))
                executeEvent(to, event.getPlayer(), event);
        }
    },
    DAMAGE_ENTITY("When an entity is damaging another entity.", "DAMAGE_ENTITY_EVENT", null) {
        @EventHandler
        public void onDamage(EntityDamageByEntityEvent event) {
            if (event.getDamager() instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) event.getDamager();
                ItemStack item = damager.getEquipment().getItemInMainHand();
                if (item == null || item.getType().equals(Material.AIR))
                    return;

                executeEvent(item, damager, event);
            }
        }
    },
    ENCHANT_ITEM("When the item is being enchanted.", "ENCHANT_ITEM_EVENT", null) {
        @EventHandler
        public void onEnchant(EnchantItemEvent event) {
            ItemStack item = event.getItem();
            if (item == null || item.getType().equals(Material.AIR))
                return;

            executeEvent(item, event.getEnchanter(), event);
        }
    },
    ENTITY_DROP_ITEM("When an entity drops an item.", "ENTITY_DROP_ITEM_EVENT", null) {
        @EventHandler
        public void onDropItem(EntityDropItemEvent event) {
            if (event.getEntity() instanceof LivingEntity) {

                ItemStack item = event.getItemDrop().getItemStack();
                if (item == null || item.getType().equals(Material.AIR))
                    return;

                executeEvent(item, (LivingEntity) event.getEntity(), event);
            }
        }
    },
    ENTITY_PICKUP_ITEM("When an entity is about to pick up an item.", "ENTITY_PICKUP_ITEM_EVENT", null) {
        @EventHandler
        public void onEntityPickupItem(EntityPickupItemEvent event) {
            executeEvent(event.getItem().getItemStack(), event.getEntity(), event);
        }
    },
    ITEM_BREAK("When an item is breaking.", "ITEM_BREAK_EVENT", null) {
        @EventHandler
        public void onItemBreak(PlayerItemBreakEvent event) {
            executeEvent(event.getBrokenItem(), event.getPlayer(), event);
        }
    },
    ITEM_CONSUME("When an item is consumed (e.g food)", "ITEM_CONSUME_EVENT", null) {
        @EventHandler
        public void onItemConsume(PlayerItemConsumeEvent event) {
            executeEvent(event.getItem(), event.getPlayer(), event);
        }
    },
    ITEM_DAMAGE("When an item durability is damaged.", "ITEM_DAMAGE_EVENT", null) {
        @EventHandler
        public void onItemDamage(PlayerItemDamageEvent event) {
            executeEvent(event.getItem(), event.getPlayer(), event);
        }
    },
    ITEM_DESPAWN("When an item is being despawned.", "ITEM_DESPAWN_EVENT", null) {
        @EventHandler
        public void onDespawn(ItemDespawnEvent event) {
            executeEvent(event.getEntity().getItemStack(), null, event);
        }
    },
    ITEM_MEND("When the item is being mended (mending enchant)", "ITEM_MEND_EVENT", null) {
        @EventHandler
        public void onItemMend(PlayerItemMendEvent event) {
            executeEvent(event.getItem(), event.getPlayer(), event);
        }
    },
    ITEM_SPAWN("When an item is being spawned.", "ITEM_SPAWN_EVENT", null) {
        @EventHandler
        public void onDespawn(ItemSpawnEvent event) {
            executeEvent(event.getEntity().getItemStack(), null, event);
        }
    },
    LEFT_CLICK_AIR("When a player left clicks the air.", "LEFT_CLICK_AIR_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();

            if (event.getAction().equals(Action.LEFT_CLICK_AIR))
                executeEvent(item, event.getPlayer(), event);
        }
    },
    LEFT_CLICK_BLOCK("When a player left clicks a block.", "LEFT_CLICK_BLOCK_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();

            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
                executeEvent(item, event.getPlayer(), event);
        }
    },
    PLAYER_DROP_ITEM("When a player drops an item.", "PLAYER_DROP_ITEM_EVENT", null) {
        @EventHandler
        public void onDropItem(PlayerDropItemEvent event) {


            ItemStack item = event.getItemDrop().getItemStack();
            if (item == null || item.getType().equals(Material.AIR))
                return;

            executeEvent(item, event.getPlayer(), event);

        }
    },
    RIGHT_CLICK_AIR("When a player right clicks the air.", "RIGHT_CLICK_AIR_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR))
                executeEvent(item, event.getPlayer(), event);
        }
    },
    RIGHT_CLICK_BLOCK("When a player right clicks a block.", "RIGHT_CLICK_BLOCK_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();

            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                executeEvent(item, event.getPlayer(), event);
        }
    },
    RIGHT_CLICK_ENTITY("When a player right clicks an entity.", "RIGHT_CLICK_ENTITY_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractAtEntityEvent event) {
            ItemStack item = event.getHand().equals(EquipmentSlot.HAND) ? event.getPlayer().getInventory().getItemInMainHand() : event.getPlayer().getInventory().getItemInOffHand();
            if (item == null || item.getType().equals(Material.AIR))
                return;

            executeEvent(item, event.getPlayer(), event);
        }
    },
    SHIFT_LEFT_CLICK_AIR("When a player left clicks the air while sneaking.", "SHIFT_LEFT_CLICK_AIR_EVENT", null) {
        @EventHandler
        public void onShiftRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();
            if (event.getPlayer().isSneaking())
                if (event.getAction().equals(Action.LEFT_CLICK_AIR))
                    executeEvent(item, event.getPlayer(), event);
        }
    },
    SHIFT_LEFT_CLICK_BLOCK("When a player left clicks a block while sneaking.", "SHIFT_LEFT_CLICK_BLOCK_EVENT", null) {
        @EventHandler
        public void onShiftRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();
            if (event.getPlayer().isSneaking())
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
                    executeEvent(item, event.getPlayer(), event);
        }
    },
    SHIFT_RIGHT_CLICK_AIR("When a player is right clicking the air while sneaking.", "SHIFT_RIGHT_CLICK_AIR_EVENT", null) {
        @EventHandler
        public void onShiftRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();
            if (event.getPlayer().isSneaking())
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR))
                    executeEvent(item, event.getPlayer(), event);
        }
    },
    SHIFT_RIGHT_CLICK_BLOCK("When a player clicks a block while sneaking.", "SHIFT_RIGHT_CLICK_BLOCK_EVENT", null) {
        @EventHandler
        public void onShiftRightClick(PlayerInteractEvent event) {
            ItemStack item = null;

            if (event.getHand() != null)
                if (event.getHand().equals(EquipmentSlot.HAND))
                    item = event.getPlayer().getInventory().getItemInMainHand();
                else if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                    item = event.getPlayer().getInventory().getItemInOffHand();
            if (event.getPlayer().isSneaking())
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                    executeEvent(item, event.getPlayer(), event);
        }
    },
    SHIFT_RIGHT_CLICK_ENTITY("When a player right clicks an entity while sneaking.", "SHIFT_RIGHT_CLICK_ENTITY_EVENT", null) {
        @EventHandler
        public void onRightClick(PlayerInteractAtEntityEvent event) {
            ItemStack item = event.getHand().equals(EquipmentSlot.HAND) ? event.getPlayer().getInventory().getItemInMainHand() : event.getPlayer().getInventory().getItemInOffHand();
            if (item == null || item.getType().equals(Material.AIR))
                return;
            if (event.getPlayer().isSneaking())
                executeEvent(item, event.getPlayer(), event);
        }
    },
    SWAP_ITEM_TO_MAIN_HAND("When a player swaps this creation item to the main hand.", "SWAP_ITEM_TO_MAIN_HAND_EVENT", null) {
        @EventHandler
        public void onSwapItem(PlayerSwapHandItemsEvent event) {
            ItemStack item = event.getOffHandItem();
            if (item != null && !item.getType().equals(Material.AIR))
                executeEvent(item, event.getPlayer(), event);
        }
    },
    SWAP_ITEM_TO_OFF_HAND("When a player swaps this creation item to the off hand.", "SWAP_ITEM_TO_OFF_HAND_EVENT", null) {
        @EventHandler
        public void onSwapItem(PlayerSwapHandItemsEvent event) {
            ItemStack item = event.getMainHandItem();
            if (item != null && !item.getType().equals(Material.AIR))
                executeEvent(item, event.getPlayer(), event);
        }
    };

    /**
     * The event's key
     */
    private String key;

    /**
     * The event's nodeItemStack
     */
    private NodeItemStack nodeItemStack;

    /**
     * the node's description
     */
    private String description;


    /**
     *
     * @param description the node's description
     * @param key the node's key
     * @param nodeItemStack the node's NodeItemStack
     */
    DefaultEvents(String description, String key, @Nullable NodeItemStack nodeItemStack) {
        this.description = description;
        this.key = key;
        this.nodeItemStack = nodeItemStack;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public NodeItemStack getItemReference() {
        return this.nodeItemStack == null ? getDefaultNodeItem() : this.nodeItemStack;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public NodeItemStack getDefaultNodeItem() {
        return IEvent.super.getDefaultNodeItem();
    }
}
