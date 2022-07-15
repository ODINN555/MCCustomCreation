package me.ODINN.MCCustomCreation;

import Utility.PDCUtil;
import org.bukkit.inventory.ItemStack;

/**
 * utility for creations
 */
public class CreationsUtil {

    /**
     * Singleton implementation
     */
    public static final String CREATIONS_PDC_KEY = "Creation";
    private CreationsUtil(){}

    /**
     *
     * @param item a given item
     * @return if the item has a creation in it
     */
    public static boolean isCreation(ItemStack item){
        return PDCUtil.has(item,CREATIONS_PDC_KEY,String.class);
    }

    /**
     *
     * @param item a given item
     * @return the item's creation
     */
    public static String getCreationFromItem(ItemStack item){
        return PDCUtil.get(item,CREATIONS_PDC_KEY,String.class);
    }

    /**
     * sets the creation in the item
     * @param item a given item
     * @param creation a given creation name
     */
    public static void setCreationIntoItem(ItemStack item,String creation){
        PDCUtil.set(item,CREATIONS_PDC_KEY,String.class,creation);
    }

    /**
     * removes a creation from the given item
     * @param item a given item
     */
    public static void removeCreationFromItem(ItemStack item){
        PDCUtil.remove(item,CREATIONS_PDC_KEY);
    }

}
