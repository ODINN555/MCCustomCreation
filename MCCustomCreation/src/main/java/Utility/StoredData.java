package Utility;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * A PersistentDataType class which represents a byte[] data type since any object is first serialized to a byte[] through serializations
 */
public class StoredData implements PersistentDataType<byte[] ,byte[]> {


    public StoredData(){}

    @Override
    public Class<byte[]> getComplexType() {
        return byte[].class;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public byte[] toPrimitive(byte[] arg0, PersistentDataAdapterContext arg1) {
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
    public byte[] fromPrimitive(byte[] arg0, PersistentDataAdapterContext arg1) {
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(arg0);
            BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(byteIn);

            return (byte[]) bukkitIn.readObject();
        }catch(Exception e) {
            return null;
        }

    }


}
