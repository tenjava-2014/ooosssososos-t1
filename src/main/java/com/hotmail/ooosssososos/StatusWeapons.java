package com.hotmail.ooosssososos;

import com.hotmail.ooosssososos.IO.IDFileManager;
import com.hotmail.ooosssososos.IO.RuneLoader;
import com.hotmail.ooosssososos.Listener.PlayerInteractListener;
import com.hotmail.ooosssososos.Listener.PlayerPVPListener;
import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Managers.Settings;
import com.hotmail.ooosssososos.Managers.StatManager;
import com.hotmail.ooosssososos.Tasks.DrawTask;
import com.hotmail.ooosssososos.Tasks.RuneTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class StatusWeapons extends JavaPlugin {

    private static StatusWeapons pl;
    public Settings settings;
    public StatManager statMan;
    public IDFileManager IDFMan;

    public void onEnable() {
        pl = this;

        //loadData
        saveDefaultConfig();
        settings = new Settings(getConfig());

        statMan = new StatManager();

        try{
            IDFMan = new IDFileManager();
            RuneLoader.loadRunes();
        }catch(IOException e){
            e.printStackTrace();
        }


        //register Events
        this.getServer().getPluginManager().registerEvents(new PlayerPVPListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);


        new RuneTask().runTaskTimer(this, 20,10);
        new DrawTask().runTaskTimer(this, 20,1);
    }

    public void onDisable(){

        try{
            IDFMan.save();
        }catch(Exception e){
            e.printStackTrace();
        }

        pl = null;

    }

    public static StatusWeapons getInstance(){
        return pl;
    }

}
