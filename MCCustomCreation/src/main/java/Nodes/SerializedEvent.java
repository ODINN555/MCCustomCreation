package Nodes;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

public class SerializedEvent implements Serializable {
    List<FunctionTree> functions;

    public void execute(LivingEntity entity, ItemStack item){
        functions.forEach(functionTree ->{
            FunctionTree.executeFunction(functionTree,entity,item);
        });
    }
}
