package Utility;

import Utility.ConfigUtil.Serialization.Serializations;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * An utility class for Persistent Data Container
 */
public class PDCUtil {

    /**
     *
     * @param container a given container
     * @param key a given key
     * @return if the given container has the given key with the given value
     */
    public static boolean has(PersistentDataContainer container,String key){
        if(container == null)
            return false;
        key =validateKey(key);
        return container.has(new NamespacedKey(Main.getInstance(),key),new StoredData());
    }

    /**
     *
     * @param container a given container
     * @param key a given key
     * @return if the given container has the given key with the given value
     */
    public static boolean has(ItemStack container, String key){
        if(container == null || container.getType().equals(Material.AIR))
            return false;
        return has(container.getItemMeta().getPersistentDataContainer(), key);
    }

    /**
     *
     * @param container a given container
     * @param key a given key
     * @return if the given container has the given key with the given value
     */
    public static boolean has(LivingEntity container, String key){
        if(container == null)
            return false;
        return has(container.getPersistentDataContainer(),key);
    }

    /**
     *
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @return the value of the given container with the given key with the given class of return type
     */
    public static <T> T get(PersistentDataContainer container,String key ){
        key =validateKey(key);
        if(has(container,key))
            return (T) Serializations.deserialize(container.get(new NamespacedKey(Main.getInstance(),key),new StoredData()));
        return null;
    }

    /**
     *
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @return the value of the given container with the given key with the given class of return type
     */
    public static <T> T get(ItemStack container, String key ){
        if(container == null || container.getType().equals(Material.AIR))
            return null;
        return get(container.getItemMeta().getPersistentDataContainer(), key);
    }

    /**
     *
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @return the value of the given container with the given key with the given class of return type
     */
    public static <T> T get(LivingEntity container, String key ){
        if(container == null)
            return null;
        return get(container.getPersistentDataContainer(),key);
    }

    /**
     * sets the given value in the given container with the given key with the given class of value type
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @param value a given value
     */
    public static <T> void set(PersistentDataContainer container,String key ,T value){
        if(container == null)
            return;
        key =validateKey(key);
        container.set(new NamespacedKey(Main.getInstance(),key),new StoredData(),Serializations.serialize(value));
    }

    /**
     * sets the given value in the given container with the given key with the given class of value type
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @param value a given value
     */
    public static <T> void set(ItemStack container,String key ,T value){
        if(container == null || container.getType().equals(Material.AIR))
            return;
        ItemMeta meta =container.getItemMeta();
        set(meta.getPersistentDataContainer(),key,value);
        container.setItemMeta(meta);
    }

    /**
     * sets the given value in the given container with the given key with the given class of value type
     * @param container a given container
     * @param key a given key
     * @param <T> the container's value type
     * @param value a given value
     */
    public static <T> void set(LivingEntity container,String key ,T value){
        if(container == null)
            return;
        set(container.getPersistentDataContainer(),key,value);
    }

    /**
     * removes the given key from the container
     * @param container a given container
     * @param key a given key
     */
    public static void remove(PersistentDataContainer container ,String key){
        if(container == null)
            return;
        key =validateKey(key);
        container.remove(new NamespacedKey(Main.getInstance(),key));
    }

    /**
     * removes the given key from the container
     * @param container a given container
     * @param key a given key
     */
    public static void remove(LivingEntity container ,String key){
        if(container == null)
            return;
        remove(container.getPersistentDataContainer(),key);
    }

    /**
     * removes the given key from the container
     * @param container a given container
     * @param key a given key
     */
    public static void remove(ItemStack container ,String key){
        if(container == null || container.getType().equals(Material.AIR))
            return;
        ItemMeta meta = container.getItemMeta();
        remove(meta.getPersistentDataContainer(),key);
        container.setItemMeta(meta);
    }

    public static String validateKey(String key){
        return key.replaceAll(" ","_");
    }
}
