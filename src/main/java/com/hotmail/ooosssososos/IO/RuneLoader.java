package com.hotmail.ooosssososos.IO;

import com.hotmail.ooosssososos.Managers.RuneManager;
import com.hotmail.ooosssososos.Runes.BasicEffect;
import com.hotmail.ooosssososos.Runes.Potion.PotionEffect;
import com.hotmail.ooosssososos.StatusWeapons;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class RuneLoader{

    //Loads runes from file
    public static void loadRunes(){
        File baseFolder = new File(StatusWeapons.getInstance().getDataFolder().getAbsolutePath() + "/runes/");
        DumperOptions op = new DumperOptions();
        op.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yml = new Yaml(op);

        if(baseFolder.exists()){
            for(File f : baseFolder.listFiles()){
                if(!f.isDirectory()){
                    if(f.getName().endsWith(".yml")){
                        try{
                            Map<String, Object> runeRoot = (Map<String, Object>) yml.load(new FileInputStream(f));
                            String type = (String)runeRoot.get("type");
                            BasicEffect ec = null;
                            switch(type){
                                case "potion":
                                    ec = new PotionEffect(runeRoot);
                                    break;
                                case "enchantment":
                                    break;
                                case "custom":
                                    break;
                                default:
                                    break;
                            }

                            BufferedImage img = ImageIO.read(new File(f.getAbsoluteFile().toString().substring(0,f.getAbsoluteFile().toString().length()-4)+".jpg"));

                            if(ec != null && img != null){
                                RuneManager.loadRune(img,ec);
                            }
                        }catch(Exception e){
                            System.out.println("Error, Unable to load rune: " + f.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
