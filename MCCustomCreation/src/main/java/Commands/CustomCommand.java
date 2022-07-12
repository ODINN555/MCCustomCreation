package Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomCommand {



    private String name;
    private List<String> description;
    private String permission;
    private int minimumArgsAmount;
    private int maximumArgsAmount;
    private List<String> aliases; // must contain the name

    public CustomCommand(String name, List<String> description, String permission, int minimumArgsAmount,int maximumArgsAmount,List<String> aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.minimumArgsAmount = minimumArgsAmount;
        this.maximumArgsAmount = maximumArgsAmount;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getDescription() {
        return new ArrayList<>(description);
    }

    public int getMinimumArgsAmount() {
        return minimumArgsAmount;
    }

    public int getMaximumArgsAmount() {
        return maximumArgsAmount;
    }

    public List<String> getAliases(){
        return new ArrayList<>(this.aliases);
    }



    abstract boolean onCommand(Player sender, List<String> args);

    abstract List<String> getCompletions(int argumentIndex);



}
