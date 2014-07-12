package com.hotmail.ooosssososos.Managers;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    public long millis_before_confirm;

    public Settings(FileConfiguration conf){
        millis_before_confirm = conf.getLong("millis_before_confirm");
    }

}
