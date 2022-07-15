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
public abstract class TruePrimitive<T> implements IPrimitive{

    private T value;
    public TruePrimitive(T value){
        this.value = value;
    }

    public TruePrimitive(){
        this.value = null;
    }

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

    public abstract void onChosen(GUI_ChooseGUI gui);

}
