package Nodes;

import Utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class NodeItemStack implements Serializable {

    private Material material;
    private String display;
    private String description;
    private Class classRef;
    private int stackAmount;
    private ItemStack itemStack;

    public NodeItemStack(Material material, String display, String description, int stackAmount, Class classRef) {
        this.material = material;
        this.display = display;
        this.description = description;
        this.stackAmount = stackAmount;
        this.classRef = classRef;

        this.itemStack = new ItemStack(material,stackAmount);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getClassRef() {
        return classRef;
    }

    public void setClassRef(Class classRef) {
        this.classRef = classRef;
    }

    public ItemStack getItemStack(){
        return this.itemStack;
    }


    public static boolean isNodeItemStack(ItemStack item){
        return PDCUtil.has(item,"NodeItemStack",NodeItemStack.class);
    }

    public static NodeItemStack getNodeFromItem(ItemStack item){
        if(!isNodeItemStack(item))
            return null;
        return PDCUtil.get(item,"NodeItemStack",NodeItemStack.class);
    }
}
