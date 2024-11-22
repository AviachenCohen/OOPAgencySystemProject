
public abstract class workingInAgency extends Thread {
	protected String type = "workingInAgency";
	protected static boolean endOfDay = false;

	// workingInAgency constructor
	public workingInAgency() {
	}

	// returns the object type
	protected String getType() {
		return type;
	}

	// override run
	public void run() {

	}

	protected void restartEndOfTheDay() {
		endOfDay = false;
	}

}
