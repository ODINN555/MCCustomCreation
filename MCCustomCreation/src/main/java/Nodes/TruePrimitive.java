package Nodes;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * An interface representing a true primitive*
 * @param <T> The primitive's type
 *
 * *true primitive - a function which returns a default value, the default value is a constant which is chosen before runtime.
 */
public abstract class TruePrimitive<T> implements IPrimitive,Cloneable{

    /**
     * The primitive's value
     */
    private transient T value;

    /**
     *
     * @param value the primitive's value
     */
    public TruePrimitive(T value){
        this.value = value;
    }

    public TruePrimitive(){
        this.value = null;
    }

    /**
     *
     * @return the default display item
     */
    public abstract ItemStack getDefaultDisplayItem();

    @Override
    public NodeItemStack getItemReference() {
        return new NodeItemStack(getDefaultDisplayItem(),this);
    }

    @Override
    public T getValue(LivingEntity executor, ItemStack item) {
        return this.getValue();
    }

    public T getValue(){
        return this.value;
    }

    public void setValue(T value){
        this.value = value;
    }

    /**
     * handles a choose of this node on the given gui
     * @param gui a given choosing gui
     */
    public abstract void onChosen(GUI_ChooseGUI gui);

    /**
     *
     * @return a new instance of the true primitive
     */
    public abstract TruePrimitive clone();

    @Override
    public String getDescription(){
        return "Raw "+getKeyAsDisplay()+" value.\nRaw values are user input values.";
    }
}
