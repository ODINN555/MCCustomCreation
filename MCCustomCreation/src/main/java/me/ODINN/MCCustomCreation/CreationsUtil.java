package me.ODINN.MCCustomCreation;

import PluginEvents.ApplyCreationEvent;
import PluginEvents.RemoveCreationEvent;
import Utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * utility for creations
 */
public class CreationsUtil {

    /**
     * Singleton implementation
     */
    public static final String CREATIONS_PDC_KEY = "Creation";
    public static final CreationsUtil INSTANCE = new CreationsUtil();
    private CreationsUtil(){}

    /**
     *
     * @param item a given item
     * @return if the item has a creation in it
     */
    public static boolean isCreation(ItemStack item){
        return PDCUtil.has(item,CREATIONS_PDC_KEY);
    }

    /**
     *
     * @param item a given item
     * @return the item's creation
     */
    public static String getCreationFromItem(ItemStack item){
        return PDCUtil.get(item,CREATIONS_PDC_KEY);
    }

    /**
     * sets the creation in the item
     * @param item a given item
     * @param creation a given creation name
     */
    public static void setCreationIntoItem(ItemStack item,String creation){
        if(item == null || item.getType().equals(Material.AIR))
            return;

        ApplyCreationEvent event = new ApplyCreationEvent(creation,item);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled())
            return;

        ItemStack endItem = event.getItem();
        String endCreation = event.getCreation();
        PDCUtil.set(endItem,CREATIONS_PDC_KEY,endCreation);
    }

    /**
     * removes a creation from the given item
     * @param item a given item
     */
    public static void removeCreationFromItem(ItemStack item){
        if(!PDCUtil.has(item,CREATIONS_PDC_KEY))
            return;

        RemoveCreationEvent event = new RemoveCreationEvent(PDCUtil.get(item,CREATIONS_PDC_KEY),item);

        if(event.isCancelled())
            return;

        ItemStack endItem = event.getItem();
        PDCUtil.remove(endItem,CREATIONS_PDC_KEY);
    }

}
