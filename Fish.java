package model;

public abstract class Fish {
	protected String id, name;
	protected int speed;
	protected double size;
	
	public abstract int calculatePrice();

	public Fish(String id, String name, int speed, double size) {
		super();
		this.id = id;
		this.name = name;
		this.speed = speed;
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
}