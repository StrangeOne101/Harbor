package techtoolbox.Harbor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
public class ActionBarMessage implements Runnable {
	@Override
	public void run() {
		if (Main.amountSleeping > 0 && Main.amountSleeping < Bukkit.getServer().getOnlinePlayers().size()) {
			Main.sleepingMessage = Main.sleepingMessage.replaceAll("%sleeping%", String.valueOf(Main.amountSleeping));
			Main.sleepingMessage = Main.sleepingMessage.replaceAll("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
			for(Player p : Bukkit.getOnlinePlayers()){
				Main.sendTitle(p, Main.sleepingMessage);
			}
		}
		else if (Main.amountSleeping == Bukkit.getServer().getOnlinePlayers().size()) {
			Main.allSleepingMessage = Main.allSleepingMessage.replaceAll("%sleeping%", String.valueOf(Main.amountSleeping));
			Main.allSleepingMessage = Main.allSleepingMessage.replaceAll("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
			for(Player p : Bukkit.getOnlinePlayers()){
				Main.sendTitle(p, Main.allSleepingMessage);
			}
		}
	}
}
