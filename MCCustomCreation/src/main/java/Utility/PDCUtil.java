package Utility;

import me.ODINN.MCCustomCreation.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class PDCUtil {

    public static boolean has(PersistentDataContainer container,String key,Class clazz){
        return container.has(new NamespacedKey(Main.getInstance(),key),new StoredData<>(clazz));
    }
    public static boolean has(ItemStack container, String key,Class clazz){
        return has(container.getItemMeta().getPersistentDataContainer(), key,clazz);
    }
    public static boolean has(LivingEntity container, String key, Class clazz){
        return has(container.getPersistentDataContainer(),key,clazz);
    }

    public static <T> T get(PersistentDataContainer container,String key,Class<T> clazz){
        if(has(container,key,clazz))
            return container.get(new NamespacedKey(Main.getInstance(),key),new StoredData<>(clazz));
        return null;
    }

    public static <T> T get(ItemStack container, String key,Class<T> clazz){
        return get(container.getItemMeta().getPersistentDataContainer(), key,clazz);
    }

    public static <T> T get(LivingEntity container, String key, Class<T> clazz){
        return get(container.getPersistentDataContainer(),key,clazz);
    }


    public static <T> void set(PersistentDataContainer container,String key,Class<T> clazz,T value){
        container.set(new NamespacedKey(Main.getInstance(),key),new StoredData<>(clazz),value);
    }

    public static <T> void set(ItemStack container,String key,Class<T> clazz,T value){
        container.getItemMeta().getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(),key),new StoredData<>(clazz),value);
    }

    public static <T> void set(LivingEntity container,String key,Class<T> clazz,T value){
        container.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(),key),new StoredData<>(clazz),value);
    }

}
