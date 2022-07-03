package Nodes;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * An interface representing a primitive*
 * @param <T> The primitive's type
 *
 * *primitive - a function which receives default values or no values at all and returns a certain value
 */
public interface IPrimitive<T> extends IReturningNode{

    /**
     *
     * @param executor a given executor
     * @param item a given item
     * @return primitive's value depending on the given values
     */
    T getValue(LivingEntity executor,ItemStack item);


}
