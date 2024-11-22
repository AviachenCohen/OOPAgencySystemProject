
public class Strategy implements Comparable<Object> {
	private int serialNum;
	private String codeName;
	private int level;
	private int client;
	private int estimatedTime;

	// Strategy constructor
	public Strategy(int serialNum, String codeName, int level, int client, int estimatedTime) {
		this.serialNum = serialNum;
		this.codeName = codeName;
		this.level = level;
		this.client = client;
		this.estimatedTime = estimatedTime;
	}

	// get serialNum
	protected int getSerialNum() {
		return this.serialNum;
	}

	// get code name
	protected String getCodeName() {
		return this.codeName;
	}

	// get level
	protected int getLevel() {
		return this.level;
	}

	// get client
	protected int getClient() {
		return this.client;
	}

	// get estimatedTime
	protected int getEstimatedTime() {
		return this.estimatedTime;
	}

	// comparing Strategy estimatedTime
	public int compareTo(Object o) {
		if (estimatedTime > ((Strategy) o).estimatedTime)
			return 1;
		if (estimatedTime < ((Strategy) o).estimatedTime)
			return -1;
		return 0;
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
}