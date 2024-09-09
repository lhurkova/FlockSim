/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.spi;

/**
 * Class representing a point or vector in three dimensional Euclidean space.
 * @author Lucie Hurkova
 */
public class Point {
    
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Creates a new {@link Point} with specified x, y and z coordinates.
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Gets x coordinate.
     * @return x coordinate
     */
    public double getX() {
        return x;
    }
    
    /**
     * Gets y coordinate.
     * @return y coordinate
     */
    public double  getY() {
        return y;
    }
    
    /**
     * Gets z coordinate.
     * @return z coordinate
     */
    public double  getZ() {
        return z;
    }
    
    /**
     * Adds the given vector to the current instance of the {@link Point}.
     * @param vector vector to be added
     * @return new {@link Point} with sum of coordinates
     */
    public Point add(Point vector) {
        return new Point(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }
    
    /**
     * Subtracts the given vector from the current instance of the {@link Point}.
     * @param vector vector to be subtracted
     * @return new {@link Point} with subtracted coordinates
     */
    public Point subtract(Point vector) {
        return new Point(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }
    
    /**
     * Gets a vector leading from current instance of the {@link Point} to given {@link Point}.
     * @param p end of vector
     * @return vector between the two points
     */
    public Point getDistanceVector(Point p) {
        return p.subtract(this);
    }
    
    /**
     * Gets squared size of this vector.
     * @return squared size of the vector
     */
    public double getSquaredSize() {
        return x*x + y*y + z*z;
    }
    
    /**
     * Gets size of this vector.
     * @return size of the vector
     */
    public double getSize() {
        return Math.sqrt(this.getSquaredSize());
    }
    
    /**
     * Changes size of this vector to given size.
     * @param newSize new size of the vector
     * @return new vector with changed size
     */
    public Point changeSizeTo(double newSize) {
        double oldSize = this.getSize();
        return new Point(newSize*(x/oldSize), newSize*(y/oldSize),
                newSize*(z/oldSize));
    }
    
    /**
     * Divides all coordinates of the {@link Point} with given number.
     * @param divider number to divide with
     * @return new {@link Point} with divided coordinates
     */
    public Point divide(double divider) {
        return new Point(x/divider, y/divider, z/divider);
    }
    
    /**
     * Gets a vector opposite to this vector.
     * @return opposite vector
     */
    public Point getOppositeVector() {
        return new Point(-x, -y, -z);
    }
    
    /**
     * Gets an orthogonal vector to this vector in 2 dimensional space.
     * @return orthogonal vector to this vector
     * @throws IllegalArgumentException if the vector does not have zero z coordinate
     */
    public Point getOrtogonalVector() throws IllegalArgumentException {
        if (Math.abs(z) > 1e-5) {
            throw new IllegalArgumentException("Only applicable on 2D vectors");
        }
        return new Point(y, -x, 0);
    }
    
    /**
     * Turnes this vector by specified oriented angle.
     * @param angle oriented angle in radians
     * @return new vector turned by given anle
     */
    public Point getVectorTurnedBy(double angle) {
        if (Math.abs(z) > 1e-5) {
            throw new IllegalArgumentException("Only applicable on 2D vectors");
        }
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double turnedX = cos * x - sin * y;
        double turnedY = sin * x + cos * y;
        return new Point(turnedX, turnedY, 0);
    }
    
    /**
     * Gets a oriented angle from this vector to given vector.
     * @param vector second vector
     * @return oriented angle between the vectors
     */
    public double getOrientedAngle(Point vector) {
        double angleCos = (x*vector.getX() + y*vector.getY() + z*vector.getZ())
                /(getSize() * vector.getSize());
        double angle = Math.acos(angleCos);
        double sign = vector.getY()*x - y*vector.getX();
        if (sign >= 0) return angle;
        return -angle;
    }
    
    /**
     * Rotates this point around the given center by given angle
     * @param center center of rotation
     * @param angle oriented angle
     * @return rotated point
     */
    public Point rotatePoint(Point center, double angle) {
        Point vector = center.getDistanceVector(this);
        Point turnedVector = vector.getVectorTurnedBy(angle);
        assert Math.abs(vector.getSize() - turnedVector.getSize()) < 1e-5;
        return center.add(turnedVector);
    }
    
    /**
     * Gets projection of the vector or point into a 2 dimensional space.
     * @return new {@link Point} in 2 dimensional space
     */
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
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
    
    
    
    
    
}
