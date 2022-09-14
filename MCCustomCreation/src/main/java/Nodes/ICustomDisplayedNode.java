package Nodes;

import GUI.AGUI;
import GUI.DisplayGUI.GUI_DisplayGUI;

public interface ICustomDisplayedNode {
    /**
     * triggers when this node is about the be displayed
     * @param displayTree the tree of nodes up to the displayed node.
     * @param displayGUI the previous display GUI which is attempting to open a display GUI of the node
     * @return if the custom display was executed, if false, the default display by the previous gui will be executed.
     */
    boolean onDisplay(FunctionTree displayTree,GUI_DisplayGUI displayGUI);

}
