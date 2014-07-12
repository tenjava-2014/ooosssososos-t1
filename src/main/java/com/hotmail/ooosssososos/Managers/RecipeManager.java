package com.hotmail.ooosssososos.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeManager {
    public static ItemStack rune;

    public RecipeManager(){
        initRecipes();
    }

    //creates recipes
    public void initRecipes(){
        rune = new ItemStack(Material.ITEM_FRAME);
        rune.getItemMeta().setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + "Rune");
        Bukkit.addRecipe(new ShapedRecipe(rune).shape("AAA","ABA","ACA").setIngredient('A',Material.DIAMOND).setIngredient('B', Material.PAPER).setIngredient('C', Material.REDSTONE));
    }

}
