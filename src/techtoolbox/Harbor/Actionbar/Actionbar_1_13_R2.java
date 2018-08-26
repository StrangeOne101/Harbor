package techtoolbox.Harbor.Actionbar;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_13_R2.PlayerConnection;

public class Actionbar_1_13_R2 implements Actionbar {
	
	// Creates actionbar
	public void sendActionbar(Player player, String title) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent titleComponent = ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, titleComponent);
		connection.sendPacket(titlePacket);
	}
}
