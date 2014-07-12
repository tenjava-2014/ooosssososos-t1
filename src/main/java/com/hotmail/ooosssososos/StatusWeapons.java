package com.hotmail.ooosssososos;

import com.hotmail.ooosssososos.IO.IDFileManager;
import com.hotmail.ooosssososos.Listener.PlayerInteractListener;
import com.hotmail.ooosssososos.Listener.PlayerPVPListener;
import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Managers.Settings;
import com.hotmail.ooosssososos.Managers.StatManager;
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
        }catch(IOException e){
            e.printStackTrace();
        }


        //register Events
        this.getServer().getPluginManager().registerEvents(new PlayerPVPListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    public void onDisable(){

        pl = null;

    }

    public static StatusWeapons getInstance(){
        return pl;
    }

}
