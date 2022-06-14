package Utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackUtil {


    public static ItemStack newItemStack(Material mat, String name, List<String> description,int amount){
        ItemStack item = new ItemStack(mat,amount);
        ItemMeta meta = item.getItemMeta();
        if(name != null && !name.equals(""))
        meta.setDisplayName(name);
        if(description != null && !description.isEmpty())
            meta.setLore(description);

        item.setItemMeta(meta);
        return item;
    }


    public static ItemStack newItemStack(Material mat,String name,List<String> description){
        return newItemStack(mat,name,description,1);
    }

    public static ItemStack newItemStack(Material mat,String name) {
        return newItemStack(mat,name,null);
    }

    public static ItemStack newItemStack(Material mat,String name, int amount) {
    return newItemStack(mat,name,null,amount);
    }


    }
