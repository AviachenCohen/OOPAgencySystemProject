
public class Resources {
	private int investigators;
	private int detectives;
	private int motorcycles;
	private int carSeats;

	// Resources constructor
	public Resources(int investigators, int detectives, int motorcycles, int carSeats) {
		this.investigators = investigators;
		this.detectives = detectives;
		this.motorcycles = motorcycles;
		this.carSeats = carSeats;
	}

	// get number of investigators
	protected int getNumOfInves() {
		return investigators;
	}

	// get number of detectives
	protected int getNumOfDetect() {
		return detectives;
	}

	// get number of cars seats
	protected int getNumOfCarSeats() {
		return carSeats;
	}

	// get number of motorcycles
	protected int getNumOfMotor() {
		return motorcycles;
	}

	// letting operator/executive change resources
	protected synchronized void setResources(String type, int investNum, int detectNum, int howManyMotor,
			int howManyCar) {
		if (type.equals("Operator") || type.equals("Executive")) {
			changeNumOfAgents(type, investNum, detectNum);
			changeNumOfVehicles(type, (investNum + detectNum), howManyMotor, howManyCar);
			this.notifyAll();
		}
	}

	// making changes in agents according to operations level
	private synchronized void changeNumOfAgents(String type, int investNum, int detectNum) {
		if (type.equals("Operator")) {
			detectives = detectives - detectNum;
			investigators = investigators - investNum;
		} else {
			detectives = detectives + detectNum;
			investigators = investigators + investNum;
		}
	}

	// making changes in vehicles according to operations level
	private synchronized void changeNumOfVehicles(String type, int seats, int howManyMotor, int howManyCar) {
		if (type.equals("Operator")) {
			motorcycles = motorcycles - howManyMotor;
			carSeats = carSeats - (howManyCar * 5);
		} else {
			motorcycles = motorcycles + howManyMotor;
			carSeats = carSeats + howManyCar;
		}
	}
}