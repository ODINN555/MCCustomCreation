package Utility;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * A PersistentDataType generic class, allows any Serializable object to be serialized inside an PDC
 * @param <T> The stored data type
 */
public class StoredData<T> implements PersistentDataType<byte[] ,T> {

    /**
     * The stored data class type
     */
    private Class<T> dataClass;

    public StoredData(Class<T> dataClass){
        this.dataClass = dataClass;
    }

    @Override
    public Class<T> getComplexType() {
        return dataClass;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public byte[] toPrimitive(T arg0, PersistentDataAdapterContext arg1) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(byteOut);
            bukkitOut.writeObject(arg0);
            bukkitOut.flush();

            return byteOut.toByteArray();

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public T fromPrimitive(byte[] arg0, PersistentDataAdapterContext arg1) {
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(arg0);
            BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(byteIn);

            return (T) bukkitIn.readObject();
        }catch(Exception e) {
            return null;
        }

    }


}
