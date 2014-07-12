package com.hotmail.ooosssososos.Tasks;

import com.hotmail.ooosssososos.Managers.RuneManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RuneTask extends BukkitRunnable {

    @Override
    public void run() {
        RuneManager.checkOvertime();
    }
}
