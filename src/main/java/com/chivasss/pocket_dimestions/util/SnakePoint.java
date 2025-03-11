package com.chivasss.pocket_dimestions.util;

public class SnakePoint {
    public final double x, y, z;

    public SnakePoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(SnakePoint other) {
        return Math.sqrt(
                (this.x - other.x) * (this.x - other.x) +
                        (this.y - other.y) * (this.y - other.y) +
                        (this.z - other.z) * (this.z - other.z)
        );
    }
}
