package model;

public class Saltwater extends Fish {
	
	protected String depth;

	public Saltwater(String id, String name, int speed, double size, String depth) {
		super(id, name, speed, size);
		this.depth = depth;
		// TODO Auto-generated constructor stub
	}
	
	
	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	@Override
	public int calculatePrice() {
		// TODO Auto-generated method stub
		return (int) (this.getSize() * this.getSpeed() * 2.5);
	}

}