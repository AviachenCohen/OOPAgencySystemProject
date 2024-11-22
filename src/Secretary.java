
public class Secretary extends workingInAgency {
	private String name;
	private String type = "Secretary";
	private Queue<Call> callQueue;
	private Queue<Task> taskQueue;
	private static int callCounter;
	private static int callAmount;
	private Call c1;
	private static boolean endOfDay = false;

	// Secretary constructor
	public Secretary(String name, Queue<Call> callQueue, Queue<Task> taskQueue, int callAmount) {
		this.name = name;
		this.callQueue = callQueue;
		this.taskQueue = taskQueue;
		Secretary.callAmount = callAmount;
		callCounter = 1;
	}

	// secretary order of actions
	public void run() {
		while (!endOfDay) {
			if (callAmount <= 0)
				callQueue.insert(null);
			c1 = callQueue.extract(); // secretary trying to get call before other secretaries
			if (c1 == null) {
				callQueue.insert(c1);
				endOfDay = true;
				continue;
			}
			workOnCall();
			c1.setIsDone();
			callAmount--;
		}
	}

	// secretary working on call
	private void workOnCall() {
		int howLong = clientNum(this.c1.client);
		try {
			Thread.sleep(howLong + (this.c1.duration * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Task task = new Task(callCounter++, c1.suspect, getServiceNum(c1), getClientNum(c1), c1.arrival);
		this.taskQueue.insert(task); // creating task
	}

	// getting a random number to send to workOnCall function
	private int clientNum(String clientNum) {
		if (clientNum.equals("private"))
			return (int) (500 + (Math.random() * 500));
		if (clientNum.equals("business"))
			return (int) (1000 + (Math.random() * 1000));
		return (int) (2000 + (Math.random() * 1000));
	}

	// converting client type to number for task
	private int getClientNum(Call c1) {
		if (c1.client.equals("private"))
			return 1;
		if (c1.client.equals("business"))
			return 2;
		return 3;
	}

	private int getServiceNum(Call c1) {
		if (c1.service.equals("inquiry"))
			return 1;
		if (c1.service.equals("Background check"))
			return 2;
		if (c1.service.equals("surveillance"))
			return 3;
		if (c1.service.equals("fraud and illegal activity"))
			return 4;
		return 5;
	}

	// returns the object type
	protected String getType() {
		return type;
	}

	// fresh day for re-run
	protected void restartEndOfTheDay() {
		endOfDay = false;
	}
}
