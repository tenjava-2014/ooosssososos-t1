package com.hotmail.ooosssososos.IO;

import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.StatusWeapons;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class IDFileManager {

    private static File f = new File(StatusWeapons.getInstance().getDataFolder().getAbsolutePath()+"/IDs.txt");
    public IDFileManager() throws IOException {
        if(!f.exists())f.createNewFile();
        else{
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line = r.readLine();
            while(line != null){
                String[] d = line.split(":");
                RuneManager.setID(UUID.fromString(d[0]),Short.parseShort(d[1]));
                line = r.readLine();
            }
        }
    }

    public void save() throws IOException {
        BufferedWriter w = new BufferedWriter(new FileWriter(f));
        for(Map.Entry<UUID, Short> s : RuneManager.getids().entrySet()){
            w.write(s.getKey() + ":" + s.getValue());
        }
        w.close();
    }

}
