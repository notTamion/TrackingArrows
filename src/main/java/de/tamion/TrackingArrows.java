package de.tamion;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public final class TrackingArrows extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (World world : Bukkit.getWorlds()) {
                if(world.getPlayers().size() < 2) {
                    return;
                }
                for (Arrow arrow : world.getEntitiesByClass(Arrow.class)) {
                    if(!(arrow.getShooter() instanceof Player)) {
                        return;
                    }
                    Player shooter = (Player) arrow.getShooter();
                    List<Player> otherPlayers = world.getPlayers();
                    otherPlayers.remove(shooter);
                    Player target = otherPlayers.get(0);
                    for (Player player : otherPlayers) {
                        System.out.println(player.name());
                        if(player.getEyeLocation().distance(arrow.getLocation())<target.getEyeLocation().distance(arrow.getLocation())) {
                            target = player;
                        }
                    }
                    arrow.setVelocity(arrow.getVelocity().getMidpoint(target.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize()));
                }
            }
        }, 20L, 2L);
    }
}
