package Nodes;

public interface NodeEnum{

    /**
     *
     * @return the default node item
     */
    NodeItemStack getDefaultNodeItem();

    /**
     * registers the given node enum class
     * @param enumm a given enum class
     * @param <T> the enum's type
     */
    static <T extends Enum<T> & INode> void registerDefaults(Class<T> enumm){
        NodesHandler.INSTANCE.register(enumm.getEnumConstants());
    }

}
