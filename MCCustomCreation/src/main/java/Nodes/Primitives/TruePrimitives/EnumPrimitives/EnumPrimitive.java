package Nodes.Primitives.TruePrimitives.EnumPrimitives;

import Exceptions.InappropriateInheritanceException;
import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.TruePrimitive;
import Utility.ConfigUtil.Serialization.Serializations;
import Utility.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class converts enums and enum like classes to become primitives
 * @param <T> the primitive's value
 */
public class EnumPrimitive<T> extends TruePrimitive {

    /**
     * The enum primitive display item material
     */
    public static final Material ENUM_PRIMITIVE_MATERIAL = Material.LIME_STAINED_GLASS_PANE;

    /**
     * The primitive's enum class
     */
    private Class<T> enumClass;

    /**
     * the primitive's constants to choose from
     */
    private transient T[] constants;

    /**
     *
     * @param enums the primitive's constants
     * @param clazz the primitive's enum class
     */
    public EnumPrimitive(T[] enums,Class<T> clazz){
    super();
    enumClass = clazz;
    this.constants = enums;
    }

    /**
     *
     * @param enumClass the primitive's enum class
     */
    public EnumPrimitive(Class<T> enumClass){
            this.enumClass = enumClass;
            this.constants = enumClass.getEnumConstants();
            if(constants == null) throw new InappropriateInheritanceException(EnumPrimitive.class,"An enum primitive was attempted to be constructed with the enum class constructor but was provided with a non enum class. use the other constructor!");
    }

    @Override
    public String getKey() {
        return (enumClass).getSimpleName();
    }

    @Override
    public Class getReturnType() {
        return enumClass;
    }

    @Override
    public ItemStack getDefaultDisplayItem() {
        return ItemStackUtil.newItemStack(ENUM_PRIMITIVE_MATERIAL,getKeyAsDisplay());
    }

    /**
     *
     * @param index a given index
     * @return the display item of the constant of the given index
     */
    public ItemStack getDisplayOfConstant(int index){
        T constant = constants[index];
        ItemStack item = ItemStackUtil.newItemStack(ENUM_PRIMITIVE_MATERIAL, getConstantToName(constant));

        return item;

    }

    /**
     *
     * @param constant a given constant
     * @return The display name of the constant
     */
    public String getConstantToName(Object constant){
        String str = "";
        String[] split = constant.toString().toLowerCase(Locale.ROOT).split("_");
        for (String s : split)
            str += s.substring(0,1).toUpperCase() + s.substring(1) +" ";

        return str;
    }

    @Override
    public void onChosen(GUI_ChooseGUI gui) {
        gui.next(new EnumChooseGUI(this),true);
    }

    @Override
    public TruePrimitive clone() {
        return new EnumPrimitive(constants,enumClass);
    }

    public Class<T> getEnumClass() {
        return enumClass;
    }

    public T[] getConstants() {
        return constants;
    }

    /**
     * override for serializable
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        List<byte[]> list = Arrays.stream(constants).map(c -> Serializations.serialize(c)).collect(Collectors.toList());
        out.writeObject(list);
    }

    /**
     * override for serializable
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        List<byte[]> list = (List<byte[]>) in.readObject();
        this.constants = (T[]) list.stream().map(x -> Serializations.deserialize(x,enumClass)).toArray();
    }
}
