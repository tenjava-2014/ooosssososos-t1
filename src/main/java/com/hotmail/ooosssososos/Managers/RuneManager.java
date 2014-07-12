package com.hotmail.ooosssososos.Managers;

import com.hotmail.ooosssososos.Managers.Data.FrameTimeEntry;
import com.hotmail.ooosssososos.Map.Renderer.RuneRenderer;
import com.hotmail.ooosssososos.Runes.BasicEffect;
import com.hotmail.ooosssososos.StatusWeapons;
import com.hotmail.ooosssososos.Util.ImageCompare;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class RuneManager {

    private static HashMap<UUID, Short> IDs = new HashMap<UUID, Short>();
    private static HashMap<UUID, FrameTimeEntry> runesActive = new HashMap<UUID, FrameTimeEntry>();
    private static HashMap<BufferedImage, BasicEffect> runeImages = new HashMap<BufferedImage, BasicEffect>();
    //Loads rune
    public static void loadRune(BufferedImage a, BasicEffect e){
        runeImages.put(a,e);
    }
    //checks if rune is currently active
    public static boolean isRuneActive(UUID a){
        return runesActive.get(a) == null ? false : true;
    }
    //adds active rune to track
    public static void addActive(UUID a,ItemFrame b){
        runesActive.put(a,new FrameTimeEntry(b, System.currentTimeMillis()));
    }
    //get itemframe assigned to player
    public static FrameTimeEntry getRuneFrame(UUID c){
        return runesActive.get(c);
    }
    //get All ID's
    public static HashMap<UUID, Short> getids(){
        return IDs;
    }
    //remove entry
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
            BufferedImage img =  null;
            System.out.println(v.getRenderers().size());
            for(MapRenderer b : v.getRenderers()){
                if(b instanceof RuneRenderer){
                    img = ((RuneRenderer)b).img;
                    try {
                        // retrieve image
                        File outputfile = new File("saved.png");
                        ImageIO.write(img, "png", outputfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            HashMap<Double, BasicEffect> values = new HashMap<Double, BasicEffect>();
            for(Map.Entry<BufferedImage, BasicEffect> i : runeImages.entrySet()){
                ImageCompare ic = new ImageCompare(img, i.getKey());
                ic.setParameters(10,10,4,10);
                ic.compare();
                System.out.print("match" + ic.match());
                if(ic.match())values.put(ic.diff, i.getValue());
                ic = null;
            }

            double lowest = 9999;

            for(Map.Entry<Double, BasicEffect> i : values.entrySet()){
                if(i.getKey() < lowest)lowest = i.getKey();
            }

            values.get(lowest).execute(Bukkit.getPlayer(ent.getKey()));


            RuneManager.runesActive.remove(ent.getKey());
            ent.getValue().frame.remove();
        }else{
            Bukkit.getServer().getLogger().info("Soemthing went wrong");
        }
    }



}
