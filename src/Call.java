
public class Call extends Thread {
	protected String suspect;
	protected String service;
	protected String client;
	protected int arrival;
	protected int duration;
	private Queue<Call> callQueue;
	private boolean isDone;

	// Call constructor
	public Call(String suspect, String service, String client, int arrival, int duration, Queue<Call> callQueue)
			throws InterruptedException {
		this.suspect = suspect;
		this.service = service;
		this.client = client;
		this.arrival = arrival;
		this.duration = duration;
		this.callQueue = callQueue;
		this.isDone = false;
	}

	// call order of actions
	public void run() {
		waitForStartCall();
		while (!isDone) {
			checkCanFinish();
		}
	}

	// waiting for call's arrival to insert call to queue
	private void waitForStartCall() {
		try {
			Thread.sleep(this.arrival * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callQueue.insert(this);
	}

	// checking if secretary finished "taking care" of this call
	private synchronized void checkCanFinish() {
		while (!isDone) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// secretary finishes call
	protected synchronized void setIsDone() {
		this.isDone = true;
		this.notify();
	}

	public String toString() { // delete this later
		return this.suspect;
	}

}