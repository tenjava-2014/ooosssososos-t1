package com.hotmail.ooosssososos;

import com.hotmail.ooosssososos.IO.IDFileManager;
import com.hotmail.ooosssososos.IO.RuneLoader;
import com.hotmail.ooosssososos.Listener.PlayerInteractListener;
import com.hotmail.ooosssososos.Listener.PlayerPVPListener;
import com.hotmail.ooosssososos.Managers.RecipeManager;
import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Managers.Settings;
import com.hotmail.ooosssososos.Managers.StatManager;
import com.hotmail.ooosssososos.Tasks.DrawTask;
import com.hotmail.ooosssososos.Tasks.RuneTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class StatusWeapons extends JavaPlugin {

    private static StatusWeapons pl;
    public Settings settings;
    public StatManager statMan;
    public IDFileManager IDFMan;

    public void onEnable() {
        pl = this;

        //loadData
        copyFileFromJar("haste.jpg");
        copyFileFromJar("haste.yml");
        copyFileFromJar("speed.jpg");
        copyFileFromJar("speed.yml");
        copyFileFromJar("strength.jpg");
        copyFileFromJar("strength.yml");
        saveDefaultConfig();
        settings = new Settings(getConfig());

        statMan = new StatManager();

        try{
            IDFMan = new IDFileManager();
            RuneLoader.loadRunes();
        }catch(IOException e){
            e.printStackTrace();
        }

        //Recipes

        new RecipeManager();


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

    public void copyFileFromJar(String fileName) {

        File file = new File(this.getDataFolder()+File.separator + "runes" + File.separator + fileName);
        InputStream fis = this.getResource(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) { // Catching NPEs too
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
