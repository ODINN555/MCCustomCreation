package Nodes.Primitives;

import Nodes.IPrimitive;
import Nodes.NodeEnum;
import Nodes.NodeItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public enum DefaultPrimitives implements IPrimitive, NodeEnum {

    EVENT_ITEM(null, "EVENT_ITEM", ItemStack.class, "The item which was used in the event.") {
        @Override
        public Object getValue(LivingEntity executor, ItemStack item) {
            return item == null || item.getType().equals(Material.AIR) ? null : item;
        }
    },
    EVENT_LOCATION(null, "EVENT_LOCATION", Location.class, "The event's location.") {
        @Override
        public Object getValue(LivingEntity executor, ItemStack item) {
            if (executor == null)
                return null;
            Location loc = executor.getLocation();
            loc.setY(Math.round(loc.getY()));

            return loc;
        }
    },
    EVENT_OWNER(null, "EVENT_OWNER", LivingEntity.class, "The event executor or responsible entity.") {
        @Override
        public Object getValue(LivingEntity executor, ItemStack item) {
            return executor == null ? null : executor;
        }
    },
    EVENT_WORLD(null, "EVENT_WORLD", World.class, "The world which the event occurred at.") {
        @Override
        public Object getValue(LivingEntity executor, ItemStack item) {
            return executor == null ? null : executor.getLocation().getWorld();
        }
    },

    ;

    /**
     * The default primitive material
     */
    private static final Material DEFAULT_PRIMITIVE_MATERIAL = Material.YELLOW_STAINED_GLASS_PANE;

    /**
     * The default display name color
     */
    private static final ChatColor DEFAULT_NAME_COLOR = ChatColor.YELLOW;

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
     * the node's description
     */
    private String description;


    /**
     *
     * @param mat the node's material
     * @param key the node's key
     * @param returnType the node's return type
     * @param description the node's description
     */
    DefaultPrimitives(Material mat, String key, Class returnType, String description) {
        this.mat = mat;
        this.key = key;
        this.returnType = returnType;
        this.description = description;
    }

    @Override
    public NodeItemStack getItemReference() {
        return new NodeItemStack(mat == null ? DEFAULT_PRIMITIVE_MATERIAL : mat, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);
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
    public NodeItemStack getDefaultNodeItem() {
        return new NodeItemStack(DEFAULT_PRIMITIVE_MATERIAL, DEFAULT_NAME_COLOR + getKeyAsDisplay(), null, 1, this);
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
