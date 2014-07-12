package com.hotmail.ooosssososos.IO;

import com.hotmail.ooosssososos.StatusWeapons;

import java.io.File;

public class RuneLoader {
    public static void loadRunes(){
        File baseFolder = new File(StatusWeapons.getInstance().getDataFolder().getAbsolutePath() + "/runes/");
        if(baseFolder.exists()){
            for(File f : baseFolder.listFiles()){
                if(!f.isDirectory()){
                    if(f.getName().endsWith(".txt"));
                }
            }
        }
    }
}
