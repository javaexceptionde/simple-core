/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.corev.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta meta;
    private List<String> lore;

    public ItemBuilder(Material mat){
        itemStack = new ItemStack(mat);
        meta = itemStack.getItemMeta();
        lore = new ArrayList<>();
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        meta = this.itemStack.getItemMeta();
        lore = new ArrayList<>();
    }

    public ItemBuilder setDurability(int durability){
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setName(String name){
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setNoName(){
        meta.setDisplayName(" ");
        return this;
    }

    public ItemBuilder setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }


    public ItemBuilder addEnchant(Enchantment enchantment, int level){
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment){
        meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder setSkullOwner(String name){
        ((SkullMeta)this.meta).setOwner(name);
        return this;
    }

    public ItemBuilder setCustomSkull(String texture){
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + texture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try
        {
            profileField = ((SkullMeta)this.meta).getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(((SkullMeta)this.meta), profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    public ItemBuilder addLoreLine(String lore){
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore){
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder setGlow(){
        this.meta.addEnchant(Enchantment.DURABILITY, 1, true);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack getItemStack(){
        this.meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
