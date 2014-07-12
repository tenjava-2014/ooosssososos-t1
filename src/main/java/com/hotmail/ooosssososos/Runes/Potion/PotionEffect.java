package com.hotmail.ooosssososos.Runes.Potion;

import com.hotmail.ooosssososos.Runes.BasicEffect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oscar
 * Date: 7/11/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PotionEffect implements BasicEffect {
    org.bukkit.potion.PotionEffect eff;

    public PotionEffect(Map<String, Object> in){

        PotionEffectType ptype = PotionEffectType.getByName((String)in.get("name"));
        eff = ptype.createEffect((int)in.get("duration")*20, (int)in.get("amplifier") );


    }


    @Override
    public void execute(Player p) {
        p.addPotionEffect(eff);
    }
}
