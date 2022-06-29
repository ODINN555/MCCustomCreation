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
    private ItemStack itemRef;
    public TruePrimitive(T value,ItemStack itemRef){
        this.value = value;
        this.itemRef = itemRef;
    }

    public TruePrimitive(ItemStack itemRef){
        this(null,itemRef);
    }

    @Override
    public NodeItemStack getItemReference() {
        return new NodeItemStack(itemRef,this);
    }

    @Override
    public T getValue(LivingEntity executor, ItemStack item) {
        return this.value;
    }

    @Override
    public Class getReturnType() {
        return this.value.getClass();
    }

    public abstract void onChosen(GUI_ChooseGUI gui);
}
