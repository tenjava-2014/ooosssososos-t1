package com.hotmail.ooosssososos.Managers;

import com.hotmail.ooosssososos.Managers.Data.FrameTimeEntry;
import com.hotmail.ooosssososos.Map.Renderer.RuneRenderer;
import com.hotmail.ooosssososos.StatusWeapons;
import org.bukkit.Bukkit;
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
    private static HashMap<UUID, FrameTimeEntry> runesActive = new HashMap<UUID, FrameTimeEntry>();
    private static HashMap<UUID, BufferedImage> runeImages = new HashMap<UUID, BufferedImage>();

    public static void addActive(UUID a,ItemFrame b){
        runesActive.put(a,new FrameTimeEntry(b, System.currentTimeMillis()));
    }

    public static FrameTimeEntry getRuneFrame(UUID c){
        return runesActive.get(c);
    }

    public static HashMap<UUID, Short> getids(){
        return IDs;
    }

    public static void remEntry(String a){
        runesActive.remove(a);
    }

    public static boolean isRune(ItemFrame f){
        for(FrameTimeEntry e : runesActive.values()){
            if(e.frame == f)return true;
        }
        return false;
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
        MapView v = Bukkit.createMap(Bukkit.getPlayer(d).getWorld());
        System.out.println("Player: " + d + " Assigned: " + v.getId());
        IDs.put(d,  v.getId());
        return v.getId();
    }

    public static void checkOvertime(){
        long d = System.currentTimeMillis();
        for(Map.Entry<UUID, FrameTimeEntry> t : runesActive.entrySet()){
            if((d-t.getValue().time) > StatusWeapons.getInstance().settings.millis_before_confirm){
                finalize(t);
            }
        }
    }

    public static void finalize(Map.Entry<UUID, FrameTimeEntry> ent){
        if(ent.getValue().frame.getItem().getType() == Material.MAP){
            MapView v = Bukkit.getMap(ent.getValue().frame.getItem().getDurability());
            //((RuneRenderer)v.getRenderers().get(0)).img


            System.out.println("removing Rune");
            RuneManager.runesActive.remove(ent.getKey());
            ent.getValue().frame.remove();
        }else{
            System.out.println("Soemthing went wrong");
        }
    }



}
