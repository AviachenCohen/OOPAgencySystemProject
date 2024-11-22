import java.io.BufferedReader;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Agency {
	public String name;
	protected double operatorsWorkingTime;
	protected int numOfExecutives;
	protected int numOfOper;
	private Vector<Call> callVec = new Vector<Call>();
	private Queue<Call> callQueue = new Queue<Call>();
	private Vector<String> nameVec = new Vector<String>();
	private Vector<workingInAgency> workers;
	private InformationSystem infoSys;
	private Queue<Task> taskQueue;
	private Queue<Operation> operDetails;
	private BoundedQueue<Operation> OperationBQueue;
	private Resources r1;
	private Strategy finish = new Strategy(0, "finish1", 0, 0, 0);

	// startHere constructor
	public Agency(String name, double operatorsWorkingTime, int numOfExecutives, int numOfOper)
			throws InterruptedException {
		this.name = name;
		this.operatorsWorkingTime = operatorsWorkingTime;
		this.numOfExecutives = numOfExecutives;
		this.numOfOper = numOfOper;
		readFromFile(this.name);
		createNames("names");
	}

	// run Agency people
	public void runP() {
		initializeDetails();
		checkOperAmount();
		createWorkers();
		workers.forEach((n) -> n.restartEndOfTheDay());
		callVec.forEach((n) -> n.start());
		workers.forEach((n) -> n.start());
	}

	// initializing workers/queue/information system/resources
	private void initializeDetails() {
		this.infoSys = new InformationSystem(finish);
		this.r1 = new Resources(40, 60, 50, 50);
		this.workers = new Vector<workingInAgency>();
		this.taskQueue = new Queue<Task>();
		this.operDetails = new Queue<Operation>();
		this.OperationBQueue = new BoundedQueue<Operation>(15);
	}

	// handle "extreme-case" were user wants more operations than amount of calls
	private void checkOperAmount() {
		if (numOfOper > callVec.size()) {
			numOfOper = callVec.size();
			String message = "There aren't enough calls to fulfil your request :("
					+ " System will create operations from every single call incoming, total of " + callVec.size()
					+ " operations.";
			JOptionPane.showMessageDialog(null, message);
		}
	}

	// creating agency workers
	private void createWorkers() {
		makeSecretary(5, 0);
		makeTaskManager(3, 5);
		makeOperator(3, 8);
		makeExecutive(5 + this.numOfExecutives, 11); // add GUI input (5+ GUI number);
		makeAgencyManager(1);
	}

	// creating 5 secretaries
	private void makeSecretary(int x, int index) {
		for (int i = 0; i < x; i++) {
			Secretary s1 = new Secretary(nameVec.elementAt(index), callQueue, taskQueue, callVec.size());
			workers.add(s1);
			index++;
		}
	}

	// creating 3 task managers
	private void makeTaskManager(int x, int index) {
		for (int i = 0; i < x; i++) {
			TaskManager ts1 = new TaskManager(nameVec.elementAt(index), taskQueue, infoSys);
			workers.add(ts1);
			index++;
		}
	}

	// creating 3 operator
	private void makeOperator(int x, int index) {
		for (int i = 0; i < x; i++) {
			Operator op1 = new Operator(nameVec.elementAt(index), this.operatorsWorkingTime, OperationBQueue, r1,
					infoSys, finish);
			workers.add(op1);
			index++;
		}
	}

	// creating 5 + ?(=GUI input) Executives
	private void makeExecutive(int x, int index) {
		for (int i = 0; i < x; i++) {
			Executive ex1 = new Executive(nameVec.elementAt(index), OperationBQueue, r1, operDetails);
			workers.add(ex1);
			index++;
		}
	}

	// creating agency manager
	private void makeAgencyManager(int x) {
		for (int i = 0; i < x; i++) {
			AgencyManager am1 = new AgencyManager(this.numOfOper, operDetails, taskQueue, OperationBQueue, infoSys,
					workers, finish);
			workers.add(am1);
		}
	}

	// reading calls from file
	private void readFromFile(String Calls) throws InterruptedException {
		File file = new File("Call.txt");
		BufferedReader inFile;
		try {
			inFile = new BufferedReader(new FileReader(file));
			Calls = inFile.readLine();
			Calls = inFile.readLine();
			while (Calls != null) {
				createCall(Calls);
				Calls = inFile.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// creating calls
	private void createCall(String Calls) throws InterruptedException {
		String convertArray[] = Calls.split("\t");
		String suspect = convertArray[0];
		String service = convertArray[1];
		String client = convertArray[2];
		int arrival = Integer.parseInt(convertArray[3]);
		int duration = Integer.parseInt(convertArray[4]);
		Call call = new Call(suspect, service, client, arrival, duration, callQueue);
		this.callVec.add(call);
	}

	// creating names for workers
	private void createNames(String names) throws InterruptedException {
		File file = new File("Name.txt");
		BufferedReader inFile;
		try {
			inFile = new BufferedReader(new FileReader(file));
			names = inFile.readLine();
			names = inFile.readLine();
			while (names != null) {
				String convertArray[] = names.split("\t");
				String name = convertArray[0];
				this.nameVec.add(name);
				names = inFile.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}