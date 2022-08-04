package Utility.ConfigUtil.Serialization;

import Utility.Logging.Logging;
import Utility.Logging.LoggingOptions;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Serializations {

    /**
     * a map containing all serializers
     */
    public static final Map<Class,Serializer> serializers = new HashMap<Class,Serializer>(){
        //put values
        {
            put(GameRule.class,new Serializer<GameRule>(){

                @Override
                protected Map<String, Object> compressValue(GameRule object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",object.getName());
                    return map;

                }

                @Override
                protected GameRule decompressValue(Map<String, Object> map) {
                    return GameRule.getByName((String) map.get("name"));
                }
            });

            put(Enchantment.class, new Serializer<Enchantment>() {
                @Override
                protected Map<String, Object> compressValue(Enchantment object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",Serializations.serialize(object.getKey()));
                    return map;
                }

                @Override
                protected Enchantment decompressValue(Map<String, Object> map) {
                    return Enchantment.getByKey((NamespacedKey) Serializations.deserialize((byte[]) map.get("key"),NamespacedKey.class));
                }
            });
            put(PotionEffectType.class, new Serializer<PotionEffectType>() {
                @Override
                protected Map<String, Object> compressValue(PotionEffectType object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",Serializations.serialize(object.getKey()));
                    return map;
                }

                @Override
                protected PotionEffectType decompressValue(Map<String, Object> map) {
                    return PotionEffectType.getByKey((NamespacedKey) Serializations.deserialize((byte[]) map.get("key"),NamespacedKey.class));
                }
            });

            put(StructureType.class, new Serializer<StructureType>() {
                @Override
                protected Map<String, Object> compressValue(StructureType object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",object.getKey().getKey());
                    return map;

                }

                @Override
                protected StructureType decompressValue(Map<String, Object> map) {
                    return Registry.STRUCTURE_TYPE.get(NamespacedKey.fromString((String) map.get("key")));
                }
            });
            put(StructureType.class, new Serializer<StructureType>() {
                @Override
                protected Map<String, Object> compressValue(StructureType object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",object.getKey().getKey());
                    return map;

                }

                @Override
                protected StructureType decompressValue(Map<String, Object> map) {
                    return Registry.STRUCTURE_TYPE.get(NamespacedKey.fromString((String) map.get("key")));
                }
            });
            put(NamespacedKey.class, new Serializer<NamespacedKey>() {
                @Override
                protected Map<String, Object> compressValue(NamespacedKey object) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",object.getKey());
                    return map;
                }

                @Override
                protected NamespacedKey decompressValue(Map<String, Object> map) {
                    return NamespacedKey.fromString((String) map.get("key"));
                }
            });
        }
    };

    private Serializations(){}
    /**
     * registers a serializer
     * @param type a given type class
     * @param serializer a given serializer
     * @param <T> the type
     */
    public static <T> void registerSerializer(Class<T> type, Serializer<T> serializer){
        serializers.put(type,serializer);
    }

    /**
     *
     * @param obj a given object
     * @return the object serialized to a byte[]
     */
    public static byte[] serialize(Object obj){
        if(obj == null)
            return null;
        Class c = obj.getClass();
        while(c != null)
        if(serializers.containsKey(c)) {
            Serializer serial = serializers.get(c);
            byte[] arr = serial.serializeValue(obj);
            return arr;
        }else c = c.getSuperclass();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(byteOut);
            bukkitOut.writeObject(obj);
            bukkitOut.flush();

            return byteOut.toByteArray();
        } catch (IOException e) {
            Logging.log("There was no Serializer for "+obj.getClass()+" and it couldn't be serialized. make sure there are no errors or you need to make a serializer!", LoggingOptions.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param arr a given byte[]
     * @return the array deserialized
     */
    public static Object deserialize(byte[] arr){
        return deserialize(arr,null);
    }

    /**
     *
     * @param arr a given byte[]
     * @param deserializeTo a given class to try to deserialize to
     * @return the array deserialized, preferably to the deserializeTo class
     */
    public static Object deserialize(byte[] arr,Class deserializeTo) {
        if (arr == null)
            return null;
        if (deserializeTo != null && serializers.containsKey(deserializeTo))
            return serializers.get(deserializeTo).deserializeValue(arr);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(arr);
        try {
            BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(byteIn);

            return bukkitIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logging.log("Could not deserialize object, could be not serializable.", LoggingOptions.ERROR);

            e.printStackTrace();
            return null;
        }
    }

    }
