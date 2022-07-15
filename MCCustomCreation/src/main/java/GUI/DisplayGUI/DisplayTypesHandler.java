package GUI.DisplayGUI;

import Nodes.FunctionTree;
import Nodes.INode;
import Nodes.IPrimitive;
import Nodes.TruePrimitive;
import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * A handler for display types
 */
public class DisplayTypesHandler {

    /**
     * The registered display types
     */
    private static Set<DisplayType> DISPLAYS = new HashSet<>();
    
    /**
     * Singleton implementation
     */
    public static final DisplayTypesHandler INSTANCE = new DisplayTypesHandler();
    private DisplayTypesHandler(){
        // default registry
        register(DefaultDisplayTypes.values());
    }

    /**
     * returns a default DisplayType for custom type which is not registered in this plugin
     * @param type a given type
     * @return a default "custom" DisplayType
     */
    private static DisplayType getCustom(Class type){
        return new DisplayType() {
            @Override
            public ItemStack getDisplayItem(FunctionTree tree) {
                return DisplayTypesHandler.INSTANCE.createDefaultDisplayType(Material.PURPLE_STAINED_GLASS_PANE,  getType().getSimpleName(),ChatColor.DARK_PURPLE,tree);
            }

            @Override
            public Class getType() {
                return type;
            }
        };
    }



    /**
     * Registers display types
     * @param types given types to register
     */
    public void register(DisplayType... types){
        DISPLAYS.addAll(Arrays.asList(types));
    }

    /**
     *
     * @param type a given type
     * @return the display type which displays the given class type. custom if doesn't exist.
     */
    public DisplayType getByType(Class type){
        for (DisplayType display : DISPLAYS) {
            if(display.getType().equals(type))
                return display;
        }

        return getCustom(type);
    }

    public List<String> getFunctionTreeDisplayOnNode(FunctionTree node, ChatColor nameColor, ChatColor otherColor){
        if(node == null)
            return new ArrayList<>();
        List<String> lore = new ArrayList<>();
        lore.add(otherColor+"Current - "+nameColor+ ((INode) node.getCurrent()).getKey()+otherColor);
        lore.add("");
        String next = otherColor+"  - Next: ";
        if(node.getNext() == null) {
            next += nameColor;
            if(node.getCurrent() instanceof TruePrimitive)
                next += ((TruePrimitive) node.getCurrent()).getValue();
            else next += "null";

            lore.add(next);
            return lore;
        }

        lore.add(next);
        for (FunctionTree tree : node.getNext()) {
            String nextNode = otherColor + "           - ";
            if(tree == null || tree.getCurrent() == null)
                nextNode += nameColor + "null";
            else nextNode += nameColor + ((INode) tree.getCurrent()).getKey();
            lore.add(nextNode+= ChatColor.RESET);
        }

        return lore;
    }

    public List<String> getFunctionTreeDisplayOnNode(FunctionTree node){
        final ChatColor NAME_COLOR = ChatColor.GOLD;
        final ChatColor OTHER_COLOR = ChatColor.GRAY;

        return getFunctionTreeDisplayOnNode(node,NAME_COLOR,OTHER_COLOR);
    }

    public ItemStack createDefaultDisplayType(Material mat,String name , ChatColor nameColor,FunctionTree tree){
        if(mat == null)
            return null;
        return ItemStackUtil.newItemStack(mat,nameColor+ name,getFunctionTreeDisplayOnNode(tree));
    }


}
