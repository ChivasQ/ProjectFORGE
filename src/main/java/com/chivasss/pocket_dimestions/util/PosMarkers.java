package com.chivasss.pocket_dimestions.util;

import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.ArrayList;

public class PosMarkers {
    public Vec3 position;
    public Quaternionf rotation;

    public PosMarkers(Vec3 position, Quaternionf rotation){
        this.position = position;
        this.rotation = rotation;
    }

    public ArrayList<PosMarkers> markerList = new ArrayList<PosMarkers>();

    public void UpdateMarkerList() {
        //markerList.add(new PosMarkers())
    }
}
