import java.util.Vector;
import javax.swing.JOptionPane;

public class AgencyManager extends workingInAgency {
	private String type = "AgencyManager";
	private int GUIOperAmount;
	private Queue<Operation> operDetails;
	private Queue<Task> taskQueue;
	private BoundedQueue<Operation> OperationBQueue;
	private Vector<workingInAgency> workers;
	private InformationSystem infoSys;
	private Strategy finish;

	// AgencyManager constructor
	public AgencyManager(int GUIOperAmount, Queue<Operation> operDetails, Queue<Task> taskQueue,
			BoundedQueue<Operation> OperationBQueue, InformationSystem infoSys, Vector<workingInAgency> workers,
			Strategy finish) {
		this.GUIOperAmount = GUIOperAmount;
		this.operDetails = operDetails;
		this.taskQueue = taskQueue;
		this.OperationBQueue = OperationBQueue;
		this.workers = workers;
		this.infoSys = infoSys;
		this.finish = finish;
	}

	// agency manager order of actions
	public void run() {
		int operAmount = 0, totalTime = 0;
		while (operAmount < GUIOperAmount) {
			int howLong = operDetails.extract().practicalTime(type, 0);
			operAmount++;
			totalTime = totalTime + howLong;
		}
		notifyWorkers();
		finishTheDay();
		String message = "Amount of operations done today: " + operAmount + "\n"
				+ "total time it took for all operations: " + totalTime + " seconds";
		JOptionPane.showMessageDialog(null, message);
	}

	// let workers know that day has come to an end
	private void notifyWorkers() {
		taskQueue.insert(null);
		synchronized (infoSys) {
			infoSys.insert(finish);
		}
		OperationBQueue.insert(null);
	}

	// finishing the day for each worker, accordingly
	private void finishTheDay() {
		for (int i = 0; i < workers.size(); i++) {
			if (workers.elementAt(i).getType().equals("Secretary")
					|| workers.elementAt(i).getType().equals("AgencyManager"))
				continue;
			try {
				workers.elementAt(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// returns the object type
	public String getType() {
		return type;
	}

	// fresh day for re-run
	protected void restartEndOfTheDay() {
		endOfDay = false;
	}
}