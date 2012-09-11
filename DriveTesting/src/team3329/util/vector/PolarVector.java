package team3329.util.vector;

public class PolarVector implements CoordinateVector{
	private double magnitude;
	private double angle;

	public PolarVector(){
	        this.magnitude = 0;
        	this.angle = 0;
	}

	public PolarVector(double magnitude, double angle){
		this.magnitude = magnitude;
		this.angle = angle;
	}

	public PolarVector(CoordinateVector v){
		this.magnitude = v.getDistance();
        this.angle = v.getDirection();
	}


	public double getDistance(){
		return this.magnitude;
	}

	public double getDirection(){
		return this.angle;
	}

	public void setDistance(double dist){
		this.magnitude = dist;
	}

	public void setDirection(double direction){
		this.angle = direction;
	}

	public void add(CoordinateVector v){
		this.magnitude += v.getDistance();
		this.angle += v.getDirection();
	}

	public double dotProduct(CoordinateVector v){
		return (this.magnitude * v.getDistance() * Math.cos(this.angle - v.getDirection()));
	}
}