package Nodes.Primitives.TruePrimitives;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.Packets.PacketUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * this class represents a true primitive which its value is received through sign gui
 */
public abstract class SignInputtedPrimitive extends TruePrimitive {

    /**
     * the primitive's chooser gui
     */
    protected transient GUI_ChooseGUI gui;

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        this.gui = gui;
        gui.next(null,true);
        try {
            PacketUtil.INSTANCE.openSignEditor(gui.getOwner(), lines -> onInput(lines));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an input of a sign change
     * @param lines given lines of the sign
     */
    protected abstract void onInput(String[] lines);
}
