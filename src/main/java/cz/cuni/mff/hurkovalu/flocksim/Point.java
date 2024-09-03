/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class Point {
    
    private final double x;
    private final double y;
    private final double z;
    
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return x;
    }
    
    public double  getY() {
        return y;
    }
    
    public double  getZ() {
        return z;
    }
    
    public Point add(Point vector) {
        return new Point(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }
    
    public Point subtract(Point vector) {
        return new Point(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }
    
    public Point getDistanceVector(Point p) {
        return p.subtract(this);
    }
    
    public double getSquaredSize() {
        return x*x + y*y + z*z;
    }
    
    public double getSize() {
        return Math.sqrt(this.getSquaredSize());
    }
    
    public Point changeSizeTo(double newSize) {
        double oldSize = this.getSize();
        return new Point(newSize*(x/oldSize), newSize*(y/oldSize),
                newSize*(z/oldSize));
    }
    
    public Point divide(double divider) {
        return new Point(x/divider, y/divider, z/divider);
    }
    
    public Point getOppositeVector() {
        return new Point(-x, -y, -z);
    }
    
    public Point getOrtogonalVector() {
        if (z != 0) {
            throw new IllegalArgumentException("Only applicable on 2D vectors");
        }
        return new Point(y, -x, 0);
    }
    
    public Point getVectorTurnedBy(double angle) {
        if (z != 0) {
            throw new IllegalArgumentException("Only applicable on 2D vectors");
        }
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double turnedX = cos * x - sin * y;
        double turnedY = sin * x + cos * y;
        return new Point(turnedX, turnedY, 0);
    }
    
    public double getOrientedAngle(Point vector) {
        double angleCos = (x*vector.getX() + y*vector.getY() + z*vector.getZ())
                /(getSize() * vector.getSize());
        double angle = Math.acos(angleCos);
        double sign = vector.getY()*x - y*vector.getX();
        if (sign >= 0) return angle;
        return -angle;
    }
    
    public Point get2DPoint() {
        return new Point(x, y, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point p) {
            return Math.abs(x - p.x) < 1e-5 &&
                    Math.abs(y - p.y) < 1e-5 &&
                    Math.abs(z - p.z) < 1e-5;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    
    
}
