
public class Operator extends workingInAgency {
	private String name;
	private String type = "Operator";
	private Strategy s1;
	private double timeOnStrategy;
	private BoundedQueue<Operation> operationBQ;
	private Resources r1;
	private int howManyMotor;
	private int howManyCar;
	private InformationSystem infoSys;
	private static boolean endOfDay = false;
	private Strategy finish;

	// Operator constructor
	public Operator(String name, double timeOnStrategy, BoundedQueue<Operation> operationBQ, Resources r1,
			InformationSystem infoSys, Strategy finish) {
		this.name = name;
		this.timeOnStrategy = timeOnStrategy;
		this.operationBQ = operationBQ;
		this.r1 = r1;
		this.infoSys = infoSys;
		this.finish = finish;
	}

	// Operator order of actions
	public void run() {
		while (!endOfDay) {
			noStrategy();
			if (s1 == finish) {
				endOfDay = true;
				continue;
			} else {
				workOnStrategy();
				createOperation();
			}
		}
	}

	// operators waiting for information system to have strategies
	private synchronized void noStrategy() {
		synchronized (infoSys) {
			while (infoSys.extract() == null) {
				try {
					infoSys.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			s1 = infoSys.extract(); // operator trying to get strategy before other operators
			if (s1 == finish) {
				endOfDay = true;
				this.notifyAll();
				return;
			}
			infoSys.deleteFromVec(s1);
		}
	}

	// operator working on strategy
	private synchronized void workOnStrategy() {
		try {
			Thread.sleep((long) (this.timeOnStrategy * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// creating operation
	private void createOperation() {
		Operation o1 = new Operation(s1.getSerialNum(), s1.getCodeName(), s1.getLevel(), s1.getClient(),
				s1.getEstimatedTime());
		int investNum = s1.numOfInvestigators(), detectNum = s1.numOfDetectives();
		synchronized (r1) {
			while (r1.getNumOfDetect() < detectNum || r1.getNumOfInves() < investNum
					|| (r1.getNumOfMotor() + (r1.getNumOfCarSeats())) < (investNum + detectNum)) {
				try {
					r1.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			changeNumOfVehicles(investNum + detectNum, r1.getNumOfMotor(), r1.getNumOfCarSeats());
			o1.permission(this.type, this.howManyMotor, this.howManyCar, investNum, detectNum);
			r1.setResources(this.type, investNum, detectNum, this.howManyMotor, this.howManyCar);
		}
		this.howManyCar = 0;
		this.howManyMotor = 0;
		operationBQ.insert(o1);
	}

	// making changes in vehicles according to operations level
	private synchronized void changeNumOfVehicles(int seats, int motorcycles, int carSeats) {
		if (seats <= 0)
			return;
		else {
			if (motorcycles > 0) {
				this.howManyMotor++;
				changeNumOfVehicles(seats - 1, (motorcycles - 1), carSeats);
			} else {
				if (carSeats >= 5) {
					this.howManyCar++;
					changeNumOfVehicles(seats - 5, motorcycles, (carSeats - 5));
				}
			}
		}
	}

	// fresh day for re-run
	protected void restartEndOfTheDay() {
		endOfDay = false;
	}
}