package com.hotmail.ooosssososos.Listener;

import com.hotmail.ooosssososos.Managers.RecipeManager;
import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Map.Renderer.RuneRenderer;
import com.hotmail.ooosssososos.Tasks.DrawTask;
import com.hotmail.ooosssososos.Util.InteractTools;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractListener implements Listener {

    List<String> places = new ArrayList<String>();

    @EventHandler(ignoreCancelled = true)
    public void onFrameuse(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK)return;
        if(e.getBlockFace() == BlockFace.UP || e.getBlockFace() == BlockFace.DOWN) return;
        if(e.getItem() != null && e.getItem().getType() == Material.ITEM_FRAME){
            try{

                if(e.getItem().equals(RecipeManager.rune)){
                    places.add(e.getPlayer().getName());
                }

            }catch(NullPointerException err){}

        }
        if(RuneManager.isRuneActive(e.getPlayer().getUniqueId()) && e.getClickedBlock().getLocation().distance(RuneManager.getRuneFrame(e.getPlayer().getUniqueId()).frame.getLocation()) < 4){

            InteractTools.BlockAndPosition p = InteractTools.getTargetPoint(e.getPlayer(), null, 100);

            InteractTools.FaceCoordinate d = null;
            if (p.face == BlockFace.DOWN || p.face == BlockFace.UP) {
                d = new InteractTools.FaceCoordinate(p.point.getX() - p.block.getX(), p.point.getZ() - p.block.getZ());
            } else {
                d = new InteractTools.FaceCoordinate(0,1-(p.point.getY()-p.block.getY()));
                if (p.face == BlockFace.NORTH) {
                    d.x = 1-(p.point.getX()-p.block.getX());
                } else if (p.face == BlockFace.EAST) {
                    d.x = 1-(p.point.getZ()-p.block.getZ());

                } else if (p.face == BlockFace.SOUTH) {
                    d.x = (p.point.getX()-p.block.getX());

                } else if (p.face == BlockFace.WEST) {
                    d.x = (p.point.getZ()-p.block.getZ());

                }
            }
            d.x = d.x*1.28;
            d.y = d.y*1.28;



            DrawTask.actions.add(new com.hotmail.ooosssososos.Tasks.Actions.Action(e.getPlayer().getUniqueId(),(int)(d.x*100),(int)(d.y*100),System.currentTimeMillis()));

            e.setCancelled(true);

        }
    }

    @EventHandler
    public void itemFrameItemRemoval(EntityDamageEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            if(RuneManager.isRune((ItemFrame)e.getEntity()))
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void frameInteract(PlayerInteractEntityEvent e){
        if(e.getRightClicked() instanceof ItemFrame){
            if(RuneManager.isRune((ItemFrame)e.getRightClicked())){

                InteractTools.BlockAndPosition p = InteractTools.getTargetPoint(e.getPlayer(), null, 100);

                InteractTools.FaceCoordinate d = null;
                if (p.face == BlockFace.DOWN || p.face == BlockFace.UP) {
                    d = new InteractTools.FaceCoordinate(p.point.getX() - p.block.getX(), p.point.getZ() - p.block.getZ());
                } else {
                    d = new InteractTools.FaceCoordinate(0,1-(p.point.getY()-p.block.getY()));
                    if (p.face == BlockFace.NORTH) {
                        d.x = 1-(p.point.getX()-p.block.getX());
                    } else if (p.face == BlockFace.EAST) {
                        d.x = 1-(p.point.getZ()-p.block.getZ());

                    } else if (p.face == BlockFace.SOUTH) {
                        d.x = (p.point.getX()-p.block.getX());

                    } else if (p.face == BlockFace.WEST) {
                        d.x = (p.point.getZ()-p.block.getZ());

                    }
                }
                d.x = d.x*1.28;
                d.y = d.y*1.28;



                DrawTask.actions.add(new com.hotmail.ooosssososos.Tasks.Actions.Action(e.getPlayer().getUniqueId(),(int)(d.x*100),(int)(d.y*100),System.currentTimeMillis()));

                e.setCancelled(true);

            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFramePlace(HangingPlaceEvent e){

        if(places.contains(e.getPlayer().getName())){
            places.remove(e.getPlayer().getName());
            ItemFrame frame = (ItemFrame)e.getEntity();
            RuneManager.addActive(e.getPlayer().getUniqueId(), frame);
            MapView v = Bukkit.getServer().getMap(RuneManager.getID(e.getPlayer().getUniqueId()));
            Iterator<MapRenderer> a = v.getRenderers().iterator();
            while(a.hasNext()){
                v.removeRenderer(a.next());
            }
            System.out.println("done");
            v.addRenderer(new RuneRenderer());
            frame.setItem(new ItemStack(Material.MAP, 1, v.getId()));
        }

    }

}
