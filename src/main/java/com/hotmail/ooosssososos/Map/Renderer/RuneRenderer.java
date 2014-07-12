package com.hotmail.ooosssososos.Map.Renderer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.UUID;

public class RuneRenderer extends MapRenderer{

    public BufferedImage img;
    public ArrayList<UUID> rendered = new ArrayList<UUID>();

    public RuneRenderer(){
        super(false);
        System.out.println("new");
        img = new java.awt.image.BufferedImage(128,128, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 128, 128);
        g.setColor(Color.black);
    }

    public boolean redrawNeeded = true;

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {

            if (redrawNeeded) {
                mapCanvas.drawImage(0, 0, img);
                redrawNeeded = false;
                rendered.clear();
            }
        if(!rendered.contains(player.getUniqueId())){
            rendered.add(player.getUniqueId());
            player.sendMap(mapView);
        }
    }
}
