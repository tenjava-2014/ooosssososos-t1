package com.hotmail.ooosssososos.Managers;

import com.hotmail.ooosssososos.StatusWeapons;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RuneManager {

    private static HashMap<UUID, Short> IDs = new HashMap<UUID, Short>();
    private static HashMap<String, FrameTimeEntry> runesActive = new HashMap<String, FrameTimeEntry>();
    private static HashMap<UUID, BufferedImage> runeImages = new HashMap<UUID, BufferedImage>();

    public static void addActive(String a,ItemFrame b){
        runesActive.put(a,new FrameTimeEntry(b, System.currentTimeMillis()));
    }

    public static void remEntry(String a){
        runesActive.remove(a);
    }

    public static short getID(UUID d){
        Short z = IDs.get(d);
        if(z == null){
            z = assignID(d);
        }
        return z;
    }

    public static void setID(UUID a, short b){
        System.out.println("Loaded from FlatFile: " + a + ": " + b);
        IDs.put(a,b);
    }

    public static short assignID(UUID d){
        System.out.println("Player: " + d + " Assigned: " + (IDs.size() + 10000));
        return (short)(IDs.size() + 10000);
    }

    public static void checkOvertime(){
        long d = System.currentTimeMillis();
        for(Map.Entry<String, FrameTimeEntry> t : runesActive.entrySet()){
            if((d-t.getValue().time) > StatusWeapons.getInstance().settings.millis_before_confirm){
                finalize(t);
            }
        }
    }

    public static void finalize(Map.Entry<String, FrameTimeEntry> ent){
        if(ent.getValue().frame.getItem().getType() == Material.MAP){
            MapView v = (MapView)ent.getValue().frame.getItem();

        }else{
            System.out.println("Soemthing went wrong");
        }
    }

    private static class FrameTimeEntry{
        public ItemFrame frame;
        public long time;
        public FrameTimeEntry(ItemFrame f, long t){
            time = t;
            frame = f;
        }
    }

}
