
public class TaskManager extends workingInAgency {
	private String name;
	private String type = "TaskManager";
	private Queue<Task> taskQueue;
	private Task t1;
	private InformationSystem infoSys;
	private static boolean endOfDay = false;

	// TaskManager constructor
	public TaskManager(String name, Queue<Task> taskQueue, InformationSystem infoSys) {
		this.name = name;
		this.taskQueue = taskQueue;
		this.infoSys = infoSys;
	}

	// TaskManager order of actions
	public void run() {
		while (!endOfDay) {
			t1 = taskQueue.extract(); // task manager trying to get task before other task managers
			if (t1 == null) {
				taskQueue.insert(t1);
				endOfDay = true;
				continue;
			}
			workOnTask();
		}
	}

	// task manager working on task
	private void workOnTask() {
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Strategy s1 = new Strategy(t1.getSerialNum(), createCodeName(t1), t1.getLevelNum(), t1.getClient(),
				estimatedTime(t1)); // creating Strategy
		synchronized(infoSys) { 
			infoSys.insert(s1); // inserting Strategy to information system
		}
	}

	// making anagram out of suspects name
	private String createCodeName(Task t1) {
		String s = t1.getSuspect();
		s = s.toLowerCase();
		String newWord = "";
		for (int i = 0; i < s.length() / 2 + 1; i++)
			newWord = newWord + s.charAt(i);
		s = s.substring(s.length() / 2 + 1) + newWord;
		String upper = "" + s.charAt(0);
		upper = upper.toUpperCase();
		return (upper + s.substring(1)); // s = upper + s.substring(1)
	}

	// calculating estimated time for operation
	private int estimatedTime(Task t1) {
		int time = (t1.getLevelNum() * t1.getClient()) * 1000;
		return time;
	}
	
	// fresh day for re-run
	protected void restartEndOfTheDay() {
		endOfDay = false;
	}
	
	// returns the object type
	protected String getType() {
		return type;
	}
}