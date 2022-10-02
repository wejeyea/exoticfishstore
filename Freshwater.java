package model;

public class Freshwater extends Fish {
	
	protected int algae;

	public Freshwater(String id ,String name, int speed, double size, int algae) {
		super(id, name, speed, size);
		this.algae = algae;
		// TODO Auto-generated constructor stub
	}
	
	

	public int getAlgae() {
		return algae;
	}



	public void setAlgae(int algae) {
		this.algae = algae;
	}

	public int calculatePrice() {
		// TODO Auto-generated method stub
		return (int) (this.getSize() * this.getSpeed() * this.getAlgae());
	}


}