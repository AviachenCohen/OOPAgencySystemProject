
public class Executive extends workingInAgency {
	private String name;
	protected String type = "Executive";
	private BoundedQueue<Operation> operationBQ;
	private Queue<Operation> operDetails;
	private Operation o1;
	private Resources r1;
	private int howLong;
	private static boolean endOfDay = false;

	// Executive constructor
	public Executive(String name, BoundedQueue<Operation> operationBQ, Resources r1, Queue<Operation> operDetails) {
		this.name = name;
		this.operationBQ = operationBQ;
		this.r1 = r1;
		this.operDetails = operDetails;
	}

	// executive order of actions
	public void run() {
		while (!endOfDay) {
			o1 = operationBQ.extract(); // executive trying to get operation before other executives
			if (o1 == null) {
				operationBQ.insert(o1);
				endOfDay = true;
				continue;
			}
			workOnOperation();
		}
	}

	// executive working on operation
	private void workOnOperation() {
		int random = (int) (Math.abs(9 - (Math.random() * 10)));
		if (random < 2)
			random = 2;
		int time = (o1.getNumOfAgents() + o1.getNumOfVehicles() + random);
		this.howLong = time;
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		returnResources();
	}

	// returning agents and vehicles
	private void returnResources() {
		synchronized (r1) {
			r1.setResources(type, o1.getNumOfInves(), o1.getNumOfDetect(), o1.getNumOfMotor(), (o1.getNumOfCar() * 5));
		}
		notifyAgencyManager();
	}

	// notifying agency manager about operations details & how long it took
	private void notifyAgencyManager() {
		o1.practicalTime(type, this.howLong);
		operDetails.insert(o1);
	}

	// fresh day for re-run
	protected void restartEndOfTheDay() {
		endOfDay = false;
	}

}