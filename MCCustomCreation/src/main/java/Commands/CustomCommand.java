package Commands;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomCommand {


    /**
     * the command's name
     */
    private String name;

    /**
     * the command's description
     */
    private List<String> description;

    /**
     * the command's permission
     */
    private String permission;

    /**
     * the command's minimum argument amount
     */
    private int minimumArgsAmount;

    /**
     * the command's maximum argument amount
     */
    private int maximumArgsAmount;

    /**
     * the command's aliases list
     */
    private List<String> aliases; // must contain the name

    /**
     * the command's usages
     */
    private List<String> usages;

    /**
     *
     * @param name the command's name
     * @param description the command's description
     * @param permission the command's permission
     * @param minimumArgsAmount the command's minimum argument amount
     * @param maximumArgsAmount the command's maximum argument amount
     * @param aliases the command's aliases list
     */
    public CustomCommand(String name, List<String> description, String permission, int minimumArgsAmount,int maximumArgsAmount,List<String> aliases,List<String> usages) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.minimumArgsAmount = minimumArgsAmount;
        this.maximumArgsAmount = maximumArgsAmount;
        this.aliases = aliases;
        this.usages = usages;
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

    public List<String> getUsages() {
        return new ArrayList<>(usages);
    }

    /**
     *
     * @param sender the command's sender
     * @param args the given arguments
     * @return if the command was successful (note: only on exceptions wont be success)
     */
    abstract boolean onCommand(Player sender, List<String> args);

    /**
     *
     * @param argumentIndex a given argument index
     * @return the completions word list for the given argument index
     */
    abstract List<String> getCompletions(int argumentIndex,Player player);



}
