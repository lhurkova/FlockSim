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
    
    public Point get2DPoint() {
        return new Point(x, y, 0);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    
    
    
}
