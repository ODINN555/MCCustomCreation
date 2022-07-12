package me.ODINN.MCCustomCreation;

import Utility.PDCUtil;
import org.bukkit.inventory.ItemStack;

public class CreationsUtil {
    public static final String CREATIONS_PDC_KEY = "Creation";
    private CreationsUtil(){}

    public static boolean isCreation(ItemStack item){
        return PDCUtil.has(item,CREATIONS_PDC_KEY,String.class);
    }

    public static String getCreationFromItem(ItemStack item){
        return PDCUtil.get(item,CREATIONS_PDC_KEY,String.class);
    }

    public static void setCreationIntoItem(ItemStack item,String creation){
        PDCUtil.set(item,CREATIONS_PDC_KEY,String.class,creation);
    }

    public static void removeCreationFromItem(ItemStack item){
        PDCUtil.remove(item,CREATIONS_PDC_KEY);
    }

}
