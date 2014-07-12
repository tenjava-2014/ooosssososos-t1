package com.hotmail.ooosssososos.Map.Renderer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RuneRenderer extends MapRenderer{

    public BufferedImage img = new java.awt.image.BufferedImage(128,128, BufferedImage.TYPE_INT_RGB);

    public RuneRenderer(){
        img.getGraphics().setColor(Color.WHITE);
        img.getGraphics().fillRect(0,0,128,128);
    }

    boolean redrawNeeded = true;

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (redrawNeeded) {
            mapCanvas.drawImage(0,0,img);
            redrawNeeded = false;
            player.sendMap(mapView);
        }
    }
}
