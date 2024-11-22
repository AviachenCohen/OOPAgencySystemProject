
public class Operation {
	private int serialNum;
	private String codeName;
	private int level;
	private int client;
	private long estimatedTime;
	private int investigatorsInOp;
	private int detectivesInOp;
	private int motorcyclesInOp;
	private int carsInOp;
	private int practicalTime;

	// operation constructor
	public Operation(int serialNum, String codeName, int level, int client, long estimatedTime) {
		this.serialNum = serialNum;
		this.codeName = codeName;
		this.level = level;
		this.client = client;
		this.estimatedTime = estimatedTime;
	}

	// number of investigators in operation
	protected int numOfInvestigators() {
		switch (this.level) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 1;
		case 4:
			return 4;
		case 5:
			return 7;
		default:
			return 0;
		}
	}

	// number of detectives in operation
	protected int numOfDetectives() {
		switch (this.level) {
		case 1:
			return 0;
		case 2:
			return 2;
		case 3:
			return 5;
		case 4:
			return 6;
		case 5:
			return 8;
		default:
			return 0;
		}
	}

	// getting number of agents in operation
	protected int getNumOfAgents() {
		return (getNumOfInves() + getNumOfDetect());
	}

	// getting number of vehicles in operation
	protected int getNumOfVehicles() {
		return (getNumOfMotor() + getNumOfCar());
	}

	// get number of investigators in operation
	protected int getNumOfInves() {
		return this.investigatorsInOp;
	}

	// get number of detectives in operation
	protected int getNumOfDetect() {
		return this.detectivesInOp;
	}

	// get number of cars in operation
	protected int getNumOfCar() {
		return this.carsInOp;
	}

	// get number of motorcycles in operation
	protected int getNumOfMotor() {
		return this.motorcyclesInOp;
	}

	// defining or getting practical time it took for operation
	protected int practicalTime(String type, int time) {
		setPracticalTime(type, time);
		return getPracticalTime(type);
	}

	// setting the "real time" the operation took
	private void setPracticalTime(String type, int time) {
		if (type.equals("Executive"))
			this.practicalTime = time;
	}

	// getting the "real time" the operation took
	private int getPracticalTime(String type) {
		if (type.equals("AgencyManager"))
			return this.practicalTime;
		return 0; // fictitious value
	}

	// making sure only operators can add agents and vehicles to operation and place
	// them
	public synchronized void permission(String type, int motorcycles, int cars, int investNum, int detectNum) {
		if (type.equals("Operator")) {
			this.investigatorsInOp = investNum;
			this.detectivesInOp = detectNum;
			this.motorcyclesInOp = motorcycles;
			this.carsInOp = cars;
		}
	}

}