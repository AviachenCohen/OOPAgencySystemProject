
public class Task {
	private int serialNum;
	private String suspect;
	private int service;
	private int client;
	private int arrival;

	// Task constructor
	public Task(int serialNum, String suspect, int service, int client, int arrival) {
		this.serialNum = serialNum;
		this.suspect = suspect;
		this.service = service;
		this.client = client;
		this.arrival = arrival;
	}

	// get serial number
	protected synchronized int getSerialNum() {
		return this.serialNum;
	}

	// get suspects name
	protected synchronized String getSuspect() {
		return this.suspect;
	}

	// get clients type
	protected synchronized int getClient() {
		return this.client;
	}

	// get operation type according to numbers in operator table
	protected synchronized int getLevelNum() {
		return this.service;
	}

}
