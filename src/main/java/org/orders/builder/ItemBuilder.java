package org.orders.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.orders.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(Utils.colorize(displayName));
        return this;
    }

    public ItemBuilder addLore(String lore) {
        List<String> lores = new ArrayList<>();
        if (itemMeta.getLore() != null && itemMeta.getLore().size() > 0) {
            lores = itemMeta.getLore();

        }
        lores.add(Utils.colorize(lore));
        itemMeta.setLore(lores);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        itemMeta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder isGlow(boolean glow) {
        itemMeta.addEnchant(Enchantment.CHANNELING, 1, glow);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setCustomModelData(int id) {
        itemMeta.setCustomModelData(id);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder hideEnchants() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder hideAttributes() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemBuilder hidePotionEffects(boolean hide) {
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    public ItemBuilder hideUnbreakable(boolean hide) {
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
