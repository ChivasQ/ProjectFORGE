package com.chivasss.pocket_dimestions.util;

import net.minecraft.world.phys.Vec3;

public class SnakePoint {
    public Vec3 Position;
    public double deltaDistance;
    public SnakePoint(Vec3 position){
        this.Position = position;
    }
}
