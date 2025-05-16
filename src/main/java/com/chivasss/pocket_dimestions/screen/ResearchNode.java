package com.chivasss.pocket_dimestions.screen;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class ResearchNode {
    public int xPos, yPos;
    public List<ResearchNode> connectedTo = new ArrayList<>();
    public String description;
    public ItemStack itemToDisplay;
    public boolean isUnlocked;

    public ResearchNode(int xPos, int yPos, String description, ItemStack itemToDisplay, boolean isUnlocked) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.description = description;
        this.itemToDisplay = itemToDisplay;
        this.isUnlocked = isUnlocked;
    }

    public ResearchNode connectTo(ResearchNode node) {
        this.connectedTo.add(node);
        return this;
    }
}
