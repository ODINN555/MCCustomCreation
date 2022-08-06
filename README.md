# MCCustomCreation
 A minecraft plugin for creating custom items abilities without touching even 1 line of code!

 Have you ever thought about an item, an ability which does not exist yet. Which should be created and will be so fun to use, yet, holded back because you couldnt create it? 
 If that is the case, this plugin is just for you! 
 It will let you create any ability you have ever thought of without touching a single line of code!

The plugin uses GUIs and components named "Nodes" that the player can combine together to make his own custom abilities to sepcific events. The sum of all abilities in a single item are called a "Creation".
 
# Setup
  Download the plugin and put it in the server's plugins folder.
  This plugin requires ProtocolLib, so download it and put it in the server's plugins folder as well.

# Commands
 * /MCCustomCreation Help - shows the list of commands and their detailed description.
 * /MCCustomCreation Create - creates a new Creation.
 * /MCCustomCreation Remove - deletes a Creation.
 * /MCCustomCreation Duplicate - creates a new creation with the abilities of the duplicated creation.
 * /MCCustomCreation test - for tests purpuses (BETA), not in use in general.

# What is a Creation?
 A Creation represents a set of abilities that can be attached to an item. 
 A Node is a part of the chain which together make a whole Ability. 
 The chain of nodes goes like this: Event -> Action -> Parameter/Primitive
 * **Event** - Represents an event, like Right Click Air. It can be cancelled. Each event node holds a list of Actions.
 * **Action** - This is a function which get executed when the event occurs. An example of that is 'Damage Entity' Action, which as it's name says, damages an entity when the event occurs. An Action receives specific types of parameters or primitives.
 * **Parameters/Primitives** - Those are the values which the Action receives, for example the 'Damage Entity' Action receives an Entity and a Double (which is a type of number).The Entity that the action receives is the entity to damage and the Double is the damage amount. If you don't know what Double is, see data types below.
 
The difference between Parameter and Primitive is that parameter receives values but also gives a value, primitives only gives a value. An example for parameter: Get Player Walk Speed - receives a Player and gives an Integer. An example of Primitive: Get Event Location - gives the location which the event occured at.
 * **Raw Values** - Some Primitives represents raw data type values, values which are inputed by the user (like numbers for example).

 ## Raw Data Types
  In order to give values to Actions, data types are needed to specify what the Action should receive.

  Number data types:
  * **Double** - Double is a large precision decimal number (decimal number examples: 0.5, 3.14), it is almost the same as float but one thing- it can have more numbers after the decimal point)
  * **Float** - Float is a small precision decimal number (decimal number examples: 0.5, 3.14..), it is almost the same as double but one thing- it can have less numbers after the decimal point)
  * **Integer** - integer is a non decimal number (only 1,5,10000... not 0.1,1.5 or 0.147389 for example)
  * **Byte** - (Rarely used) Byte is a very small Integer, its range is from -128 to 127 (meaning inputing 128 will error)

  Other data types:
  * **String** - a String is anything that is placed under two quotes. a message, like 'hello' for example. it could be number,sentence,letter. anything.
  * **Boolean** - a simple true or false value (for example if you would say "does 1 = 1 ?", then you would say it is true, The boolean value of that equation is true) 

Note that you don't need to understand fully what those data types mean, The use of primitives and parameters makes it possible to not even worry about it. 

## Converting Data types
  It is also possible to convert data types, for example 1.5 which is a Double can be converted to 1 which is an Integer. The convertion at this example removes the decimal number (0.5) and gives the non decimal number (1).
  Just note that if you will try to convert unmatched numbers like 500 to Byte (which caps at 127) it will error.

## Data Types Limits
  It is known that data types has a limit, for example the Integer data type limit is 2,147,483,647 which is 2 ^ 31. Meaning any higher number received will error.
* **Double** - 2 ^ 1023
* **Float** - 2 ^ 127
* **Byte** - from -128 to 127
* **String** - 2 ^ 31 characters

It is rarely happens that a number exceeds the limit, but just to make sure, remember not to pass them.

 # WalkThrough - "Aspect Of The Creation"
 Lets walk through the process of making a new Creation!\
 In this tutorial we will be making an "Aspect Of The Creation" (AOTC).\
 This sword has the ability to teleport you 4 blocks ahead at the direction you are looking when you right click the air. So lets get started!

* Hold the sword which you desire to transform into the AOTC and run the Create command.
You can name the creation however you want, in this tutorial we will be calling it 'AOTC'.
 When running the command it will automatticaly apply the named creation to the item.
* After the command was executed a GUI will pop up. This is the Create Events GUI. In this GUI you can modify events for the creation. For now, search and click the "Right Click Air Event" to modify the right click air event. 
* Now a new GUI has popped up. This is the Display Event GUI. This GUI displays all the Actions of the Event. For now, it is empty since we havent chose any Action. Let's add a new Action by clicking the "Add Action +" button.
* After clicking the add action button, the Choose GUI will open up. The GUI is for choosing a node, for now it displays all the Actions which you can choose from. Search and click the "Teleport Entity" Action.
* After clicking it the Display Node GUI will open. This GUI displays the needed data types for the Action, for "Teleport Entity" those are 'Entity' and 'Location'. At the moment we haven't set any value so the Action does not know which entity to teleport and to where, so let's give it some values. Click on the 'Entity' data type. 
* A Choose GUI will open but now it will display all Parameters and Primitives which give an 'Entity' type. Search and click on the "Event Owner" Primitive. 
* You are now back to the Display Node GUI which displays the "Teleport Entity" Action. But now, when you look at the 'Entity' data type, it will show you that "Event Owner" is set as current value.
* Next, click on the 'Location' data type.
* Another Choose GUI will open, choose the "Set Location Direction" Parameter. 
* Now, a Display Node GUI displays the "Set Location Direction". Click on the 'Vector' data type. 
* Now choose the "Get Direction Of Location" Parameter, And then "Get Entity Location" And at last choose the "Event Owner" Primitive.
* Now, you at the bottom of the Node Chain, go back up to the display of the "Set Location Direction" and now click on the 'Location' data type.
* Choose the "Get Block Location" Parameter and then the "Get Living Entity Targeted Block". 
* Click the 'LivingEntity' data type and choose the "Event Owner" primitive. 
* Click on the "Integer" data type and choose "Raw Integer" and input '4' to the sign.
**And were done!**

 You have just created your own Aspect Of The Creation!\
 **So, what have we acctually done here?**\
 We created a chain of nodes that together created the desired ability. Let's explain every bit of it.\
 The 'Entity' which we gave to the Teleport Action was the event owner, meaning on Right Click Air, the action will teleport the Event's executor, which is the Player.\
 At the 'Location' is where things get a bit complicated.\
 A location is a data type which contains several things: x cords, y cords, z cords, World, vertical rotation (pitch) and horizontal rotation (yaw).\
 so setting the direction of the Location is like setting it's pitch and yaw.\
 So we first chose to set a location direction. The direction vector we gave to the Set Location Direction parameter was the player's location direction, which is, the direction which the player is looking.\
 The location we set the direction to was the location of the targeted block 4 blocks ahead of the player.
 
 **You may ask, why setting the direction?**\
 Since we are using the location of the targeted block ahead to the player, it does not consider the direction of the player eyes.\
 Then why using the targeted block? if we were to just teleport the player 4 blocks ahead, he could go through walls.
 
 Now, whenever you hold an item and you use the Create command for AOTC, it will apply the Creation weve made to the item! You can now right click the air and teleport 4 blocks ahead!
 
 ***So, what are you waiting for? Go create you own Minecraft Custom Creations!***


# For Developers
  It is also possible to code your own Nodes!\
  To do that you will need to add the plugin to your plugin's build path or add the following dependency of maven/gradle.\
  (Replace VERSION with the latest version of the plugin)

  Maven:
  ```xml
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

    <dependencies>
        <dependency>
	        <groupId>com.github.ODINN555</groupId>
	        <artifactId>MCCustomCreation</artifactId>
	        <version>VERSION</version>
	    </dependency>
    </dependencies>
  ```
  Gradle:
  ```groovy
  repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    compileOnly "com.github.ODINN555:MCCustomCreation:VERSION"
}
  ```

  ## Implementation
  See the [Javadoc](https://javadoc.jitpack.io/com/github/ODINN555/MCCustomCreation/BETA-v1.0.0/javadoc/) as well.

  ### Adding Your Own Nodes
  ```java
package me.example.plugin;

import GUI.ChooseGUIs.GUI_ChooseGUI;
import Nodes.*;
import Nodes.Events.IEvent;
import Nodes.Primitives.TruePrimitives.EnumPrimitives.EnumPrimitive;
import Nodes.Primitives.TruePrimitives.SignInputtedPrimitive;
import Utility.ItemStackUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
There are different types of nodes which you can add:
IEvent - represents an event. The IEvent is by default registered as listener. To actually function, it must override an event (@EventHandler).
IAction - represents an action. An action only receives values.
IParameter - represents a parameter. A parameter receives values and return a value.
IPrimitive - represents a primitive. A primitive only returns a value, but receives the default event's parameters (executor and item)
TruePrimitive - represents a raw data type primitive, which the value returned is a constant value which the user inputs.
EnumPrimitive - a TruePrimitive, but for an enum.

The hierarchy of the nodes:
                                    INode
                                      |
                                      |----- IEvent
                            __________|__________
                           |                     |
                    IReceivableNode        IReturningNode
                           |                     |
                           |                     |
        ___________________|_________   _________|___________________
       |                             \ /                             |
    IAction                       IParameter                     IPrimitive
                                                                     |
                                                               TruePrimitive
                                                           __________|__________
                                                          |                     |
                                                     EnumPrimitive    SignInputtedPrimitive

A few notes:
The receivable values are checked. They cannot be null and the amount of parameters required is the amount given.
Returning nodes can return null.
Receivable nodes don't have to validate the received types, but in some cases it is necessary. With that note, errors in console are acceptable since they mostly happen because of users not using the plugin correctly.
Any received abstract type will accept any of it's child types (for example Entity will accept Player and LivingEntity). If some types need to be excluded (like 'Remove Entity', which cannot remove players), specify it in the description of the node.
 */
public class AddNodesExample extends JavaPlugin {

    @Override
    public void onEnable(){
        // register by creating a new instance of the node, note that only true primitive is cloned
        NodesHandler.INSTANCE.register(
                new EventNodeExample(),
                new ActionNodeExample(),
                new ParameterExample(),
                new PrimitiveExample(),
                new TPri_Example(),
                new SignInputTPriExample()
        );
    }

    // example event
    class EventNodeExample implements IEvent{
        @Override
        public NodeItemStack getItemReference() {
            return new NodeItemStack(Material.AIR,getKeyAsDisplay(),null,1,this);
        }

        @Override
        public String getKey() {
            return "EXAMPLE_EVENT";
        }

        @Override
        public String getDescription() {
            return "An example event";
        }

        @EventHandler
        public void exampleEvent(PlayerDropItemEvent e){
            // event nodes already registered as Listeners for the plugin, only need to implement an event.
            // Remember to execute the node!
            executeEvent(e.getItemDrop().getItemStack(),e.getPlayer(),e);
        }
    }

    // example action
    class ActionNodeExample implements IAction{

        @Override
        public boolean action(Object... objects) {
            // example action function
            System.out.println("Hello World");
            return false;
        }

        @Override
        public Class[] getReceivedTypes() {
            return new Class[]{};
        }

        @Override
        public String[] getReceivedTypesDescriptions() {
            return new String[0];
        }

        @Override
        public NodeItemStack getItemReference() {
            return new NodeItemStack(ItemStackUtil.newItemStack(Material.AIR,getKeyAsDisplay()),this);
        }

        @Override
        public String getKey() {
            return "EXAMPLE_ACTION";
        }

        @Override
        public String getDescription() {
            return "An example action which prints Hello World";
        }
    }


    // example parameter
    class ParameterExample implements IParameter{

        @Override
        public Object getParameter(Object... objects) {
            return ((Player) objects[0]).getName();
        }

        @Override
        public Class getReturnType() {
            return String.class;
        }

        @Override
        public Class[] getReceivedTypes() {
            return new Class[]{Player.class};
        }

        @Override
        public String[] getReceivedTypesDescriptions() {
            return new String[]{"The string to pass"};
        }

        @Override
        public NodeItemStack getItemReference() {
            return new NodeItemStack(ItemStackUtil.newItemStack(Material.AIR,getKeyAsDisplay()),this);
        }

        @Override
        public String getKey() {
            return "EXAMPLE_PARAMETER";
        }

        @Override
        public String getDescription() {
            return "An example parameter which gives it's given String";
        }
    }

    // example primitive
    class PrimitiveExample implements IPrimitive{

        @Override
        public Object getValue(LivingEntity livingEntity, ItemStack itemStack) {
            return null;
        }

        @Override
        public Class getReturnType() {
            return String.class;
        }

        @Override
        public NodeItemStack getItemReference() {
            return new NodeItemStack(ItemStackUtil.newItemStack(Material.AIR,getKeyAsDisplay()),this);
        }

        @Override
        public String getKey() {
            return "EXAMPLE_PRIMITIVE";
        }

        @Override
        public String getDescription() {
            return "An example primitive which gives the event's owner name";
        }
    }

    // example true primitive
    // (true primitive is a primitive which holds a data type)
    class TPri_Example extends TruePrimitive{

        @Override
        public ItemStack getDefaultDisplayItem() {
            return ItemStackUtil.newItemStack(Material.AIR,"EXAMPLE_TRUE_PRIMITIVE");
        }

        @Override
        public void onChosen(GUI_ChooseGUI gui) {
            setValue(null);
            gui.prev();

            /*If the input requires a GUI, you can do the following:

            AGUI YOUR_GUI; Which must implement IChainable and extend AGUI (see the javadocs for AGUI methods)
            gui.next(YOUR_GUI,false);

            and after you receive the input:
            YOUR_GUI.prev();

            You can see a better example on the github for the TPri_Boolean True Primitive
            */


        }

        @Override
        public TruePrimitive clone() {
            return new TPri_Example();
        }

        @Override
        public Class getReturnType() {
            return null;
        }

        @Override
        public String getKey() {
            return "TRUE_PRIMITiVE_EXAMPLE";
        }
    }


    // an example for a true primitive which it's input is a sign GUI
    class SignInputTPriExample extends SignInputtedPrimitive{

        @Override
        protected void onInput(String[] strings) {
            // insert code
        }

        @Override
        public ItemStack getDefaultDisplayItem() {
            return ItemStackUtil.newItemStack(Material.AIR,getKeyAsDisplay());
        }

        @Override
        public TruePrimitive clone() {
            return new SignInputTPriExample();
        }

        @Override
        public Class getReturnType() {
            return null;
        }

        @Override
        public String getKey() {
            return "EXAMPLE_SIGN_INPUT_TRUE_PRIMITIVE";
        }
    }

    // an example for an enum primitive
    public static EnumPrimitive EEnumPrimitiveExample = new EnumPrimitive(ChatColor.class);

}
```
 ### Adding A Display Type

 ```java
 package me.example.plugin;

import GUI.DisplayGUI.DisplayType;
import GUI.DisplayGUI.DisplayTypesHandler;
import Nodes.FunctionTree;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
Display types are for the Display Node GUI, they tell the GUI how each type should look like.
 By default, they have a custom look, so you don't really need to add any display type but it's still possible if you want some customization.
 */
public class AddDisplayTypeExample extends JavaPlugin {


    @Override
    public void onEnable(){
        DisplayTypesHandler.INSTANCE.register(
                new ExampleDisplayType()
        );
    }

    // An example for display type.
    // Any time a Display Node GUI will need to display this type, it will display the display item given by the overridden getDisplayItem function.
    // make sure to display the FunctionTree in the lore,
    // if you want a custom lore use the getFunctionTreeDisplayOnNode function to receive the display of a function,
    // and then apply it to the lore.
    class ExampleDisplayType implements DisplayType{

        @Override
        public ItemStack getDisplayItem(FunctionTree functionTree) {
            return DisplayTypesHandler.INSTANCE.createDefaultDisplayType(Material.AIR,"Example Display Type", ChatColor.AQUA,functionTree);
        }

        @Override
        public Class getType() {
            return ExampleDisplayType.class;
        }
    }


}

 ```

### Adding A Serializer

```java
package me.example.plugin;

import Utility.ConfigUtil.Serialization.Serializations;
import Utility.ConfigUtil.Serialization.Serializer;
import com.destroystokyo.paper.Namespaced;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/*
 Serializers are used to serialize the objects into the persistent data container and the saving node's file
 For that, serializers of each non-serializable object is needed.
 The serializers convert the object to a map which then that map is converted to a byte[] array.
 NOTE: THE OBJECTS IN THE MAP MUST BE SERIALIZED AS WELL SO IF THEY ARE NOT SERIALIZABLE CREATE A SERIALIZER FOR THEM TOO
 */
public class AddSerializerExample extends JavaPlugin {


    @Override
    public void onEnable(){
        Serializations.registerSerializer(AddSerializerExample.class,new SerializerExample());
        // NOTE: serializers don't have to be in a class, they can be created inside the method like this:
        // Another note: registering an existent serializer will override the existing serializer
        Serializations.registerSerializer(AddSerializerExample.class, new Serializer<AddSerializerExample>() {
            @Override
            protected Map<String, Object> compressValue(AddSerializerExample addSerializerExample) {
                // DO NOT USE THIS:
                // return new HashMap<>(){ {put(KEY,VALUE);}};
                // IT WILL ERROR ClassDefFound!
                // USE THIS:
                Map<String,Object> map = new HashMap<>();
                map.put("example","example");

                //An example for an object which needed to put non-serializable objects inside the map:
                // NameSpacedKey is not serializable for example, but the plugin has a Serializer for it.
                map.put("key",Serializations.serialize(new NamespacedKey(Bukkit.getPluginManager().getPlugin("plugin"),"key")));
                return null;
            }

            @Override
            protected AddSerializerExample decompressValue(Map<String, Object> map) {
                String value = (String) map.get("example");

                //An example for an object which needed to put non-serializable objects inside the map:
                // NameSpacedKey is not serializable for example, but the plugin has a Serializer for it.
                NamespacedKey key = (NamespacedKey) Serializations.deserialize((byte[]) map.get("key"),NamespacedKey.class);

                // do something with values from the map to create a new instance of the object..
                return new AddSerializerExample();
            }
        });
    }


    class SerializerExample extends Serializer<AddSerializerExample>{

        @Override
        protected Map<String, Object> compressValue(AddSerializerExample addSerializerExample) {
            // DO NOT USE THIS:
            // return new HashMap<>(){ {put(KEY,VALUE);}};
            // IT WILL ERROR ClassDefFound!
            // USE THIS:
            Map<String,Object> map = new HashMap<>();
            map.put("example","example");

            //An example for an object which needed to put non-serializable objects inside the map:
            // NameSpacedKey is not serializable for example, but the plugin has a Serializer for it.
            map.put("key",Serializations.serialize(new NamespacedKey(Bukkit.getPluginManager().getPlugin("plugin"),"key")));
            return null;
        }

        @Override
        protected AddSerializerExample decompressValue(Map<String, Object> map) {
            String value = (String) map.get("example");

            //An example for an object which needed to put non-serializable objects inside the map:
            // NameSpacedKey is not serializable for example, but the plugin has a Serializer for it.
            NamespacedKey key = (NamespacedKey) Serializations.deserialize((byte[]) map.get("key"),NamespacedKey.class);

            // do something with values from the map to create a new instance of the object..
            return new AddSerializerExample();
        }
    }

}

```
