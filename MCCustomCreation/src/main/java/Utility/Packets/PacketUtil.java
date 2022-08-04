package Utility.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import me.ODINN.MCCustomCreation.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for packets
 */
public class PacketUtil {

    /**
     * a map containing all opened sign GUIs
     */
    private static Map<Player,SignCallback> openedSigns = new HashMap<>();

    /**
     * the default fake sign y location
     */
    private static int signYLoc = 250;

    /**
     * singleton implementation
     */
    public static PacketUtil INSTANCE = new PacketUtil();
    private PacketUtil(){

        // signs
        Main.getProtocolsManager().addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL,PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(openedSigns.containsKey(event.getPlayer())){
                    PacketContainer packet = event.getPacket();
                    String[] result = packet.getStringArrays().read(0);
                    event.setCancelled(true);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location signLoc = event.getPlayer().getLocation();
                            signLoc.setY(signYLoc);
                            event.getPlayer().sendBlockChange(signLoc, signLoc.getBlock().getBlockData());
                            openedSigns.get(event.getPlayer()).onCallback(result);
                        }
                    }.runTask(Main.getInstance());
                }
            }
        });
    }

    /**
     * a call back for sign update
     */
    public interface SignCallback{
        void onCallback(String[] args);
    }

    /**
     * opens a sign GUI for the given player and the input is sent to the callback function
     * @param player a given player
     * @param callback a given sign call back function
     * @throws InvocationTargetException
     */
    public void openSignEditor(Player player,SignCallback callback) throws InvocationTargetException {
        if(player == null)
            return;
        Location loc = player.getLocation();
        PacketContainer blockChange = Main.getProtocolsManager().createPacket(PacketType.Play.Server.BLOCK_CHANGE);
        blockChange.getBlockPositionModifier().write(0,new BlockPosition(loc.getBlockX(),signYLoc,loc.getBlockZ()));
        blockChange.getBlockData().write(0, WrappedBlockData.createData(Material.OAK_SIGN));
        PacketContainer openSign = Main.getProtocolsManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        openSign.getBlockPositionModifier().write(0, new BlockPosition(loc.getBlockX(),signYLoc,loc.getBlockZ()));

        Main.getProtocolsManager().sendServerPacket(player,blockChange);
        Main.getProtocolsManager().sendServerPacket(player,openSign);
        openedSigns.put(player,callback);
    }
}
