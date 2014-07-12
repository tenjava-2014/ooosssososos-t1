package com.hotmail.ooosssososos.Tasks;

import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Map.Renderer.RuneRenderer;
import com.hotmail.ooosssososos.Tasks.Actions.Action;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.map.MapRenderer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class DrawTask extends BukkitRunnable {

    public static ArrayList<Action> actions = new ArrayList<Action>();
    HashMap<UUID, Action> lasts = new HashMap<UUID,Action>();


    // Iterates through and executes all queued actions
    @Override
    public void run() {
        Iterator<Action> itr = actions.iterator();

        while(itr.hasNext()){

            Action ac = itr.next();

            for(MapRenderer b : Bukkit.getMap(RuneManager.getID(ac.p)).getRenderers()){
                if(b instanceof RuneRenderer){
                    //drawing
                    RuneRenderer r = (RuneRenderer)b;
                    Graphics2D g = (Graphics2D)r.img.getGraphics();
                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(15,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
                    if(lasts.get(ac.p) == null){
                        g.fillOval(ac.x, ac.y, 15, 15);
                    }else{
                        Action last = lasts.get(ac.p);
                        if((ac.time - last.time)>500){
                            g.fillOval(ac.x, ac.y, 15, 15);
                        }else{
                            g.drawLine(last.x,last.y,ac.x,ac.y);
                        }
                    }
                    r.redrawNeeded = true;
                }
            }
            lasts.put(ac.p,ac);
            itr.remove();
        }

    }
}
