package com.hotmail.ooosssososos.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeManager {
    public static ItemStack rune;

    public RecipeManager(){
        initRecipes();
    }

    //creates recipes
    public void initRecipes(){
        rune = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = rune.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + "Rune");
        rune.setItemMeta(meta);
        Bukkit.addRecipe(new ShapedRecipe(rune).shape("AAA","ABA","ACA").setIngredient('A',Material.DIAMOND).setIngredient('B', Material.PAPER).setIngredient('C', Material.REDSTONE));
    }

}
