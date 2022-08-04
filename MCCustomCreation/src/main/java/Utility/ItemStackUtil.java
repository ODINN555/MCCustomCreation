package Utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * An utility class for ItemStack objects
 */
public class ItemStackUtil {

    /**
     * a function on ItemMeta
     */
    public interface MetaFunction{
        void changeMeta(ItemMeta meta);
    }

    /**
     * creates a new ItemStack instance with the given settings
     * @param mat a given material
     * @param name a given name
     * @param description a given lore
     * @param amount a given stack amount
     * @return the new ItemStack instance
     */
    public static ItemStack newItemStack(Material mat, String name, List<String> description,int amount,MetaFunction metaFunction){
        ItemStack item = new ItemStack(mat,amount);
        ItemMeta meta = item.getItemMeta();
        if(name != null && !name.equals(""))
        meta.setDisplayName(name);
        if(description != null && !description.isEmpty())
            meta.setLore(description);

        if(metaFunction != null )
            metaFunction.changeMeta(meta);

        item.setItemMeta(meta);
        return item;
    }

    /**
     *
     * creates a new ItemStack instance with the given settings
     * @param mat a given material
     * @param name a given name
     * @param description a given lore
     * @return the new ItemStack instance
     */
    public static ItemStack newItemStack(Material mat,String name,List<String> description,int amount){
        return newItemStack(mat,name,description,amount,null);
    }

    /**
     *
     * creates a new ItemStack instance with the given settings
     * @param mat a given material
     * @param name a given name
     * @param description a given lore
     * @return the new ItemStack instance
     */
    public static ItemStack newItemStack(Material mat,String name,List<String> description){
        return newItemStack(mat,name,description,1,null);
    }

    /**
     *
     * creates a new ItemStack instance with the given settings
     * @param mat a given material
     * @param name a given name
     * @return the new ItemStack instance
     */
    public static ItemStack newItemStack(Material mat,String name) {
        return newItemStack(mat,name,null);
    }

    /**
     *
     * creates a new ItemStack instance with the given settings
     * @param mat a given material
     * @param name a given name
     * @param amount a given stack amount
     * @return the new ItemStack instance
     */
    public static ItemStack newItemStack(Material mat,String name, int amount) {
    return newItemStack(mat,name,null,amount);
    }


}
