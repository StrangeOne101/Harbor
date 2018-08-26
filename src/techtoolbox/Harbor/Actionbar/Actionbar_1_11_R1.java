package techtoolbox.Harbor.Actionbar;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PlayerConnection;
public class Actionbar_1_11_R1 implements Actionbar {
	
	// Creates actionbar
	public void sendActionbar(Player player, String title) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent titleComponent = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
		PacketPlayOutChat titlePacket = new PacketPlayOutChat(titleComponent, (byte) 2);
		connection.sendPacket(titlePacket);
	}
}
