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

    /**
     *
     * @param node a given node
     * @param nameColor the color of the display name
     * @param otherColor the color for other things
     * @return The display of the function tree of a node
     */
    public List<String> getFunctionTreeDisplayOnNode(FunctionTree node, ChatColor nameColor, ChatColor otherColor){
        if(node == null)
            return new ArrayList<>();
        List<String> lore = new ArrayList<>();
        lore.add(otherColor+"Current - "+nameColor+ (node.getCurrent() != null ? ((INode) node.getCurrent()).getKeyAsDisplay() : "none") +otherColor);
        lore.add("");
        String next = otherColor+"  - Next: ";
        if(node.getNext() == null) {
            next += nameColor;
            if(node.getCurrent() instanceof IPrimitive) {
                if(node.getCurrent() instanceof TruePrimitive) {
                    TruePrimitive tp = ((TruePrimitive) node.getCurrent());
                    next += tp.getValue() == null ? "none" : tp.getValue();
                }else next =""; // if primitive don't add anything
            }
            else next += "none";

            lore.add(next);
            return lore;
        }

        lore.add(next);
        for (FunctionTree tree : node.getNext()) {
            String nextNode = otherColor + "           - ";
            if(tree == null || tree.getCurrent() == null)
                nextNode += nameColor + "none";
            else nextNode += nameColor + ((INode) tree.getCurrent()).getKeyAsDisplay();
            lore.add(nextNode+= ChatColor.RESET);
        }

        return lore;
    }

    /**
     *
     * @param node a given node
     * @return the display of the given function tree node, with the default colors
     */
    public List<String> getFunctionTreeDisplayOnNode(FunctionTree node){
        final ChatColor NAME_COLOR = ChatColor.GOLD;
        final ChatColor OTHER_COLOR = ChatColor.GRAY;

        return getFunctionTreeDisplayOnNode(node,NAME_COLOR,OTHER_COLOR);
    }

    /**
     *
     * @param mat a given material
     * @param name a given name
     * @param nameColor a given name color
     * @param tree a given function tree
     * @return a new default display item with the given parameters
     */
    public ItemStack createDefaultDisplayType(Material mat,String name , ChatColor nameColor,FunctionTree tree){
        if(mat == null)
            return null;
        return ItemStackUtil.newItemStack(mat,nameColor+ name,getFunctionTreeDisplayOnNode(tree));
    }


}
