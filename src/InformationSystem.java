import java.util.Vector;

public class InformationSystem {
	private Vector<Strategy> s;
	private Strategy finish;

	// InformationSystem constructor
	public InformationSystem(Strategy finish) {
		s = new Vector<Strategy>();
		this.finish = finish;
	}

	// inserting Strategy to information System
	public synchronized void insert(Strategy st) {
		if (st == finish)
			s.add(0, st);
		else
			s.add(st);
		this.notifyAll();
	}

	// extracting Strategy from information System
	public synchronized Strategy extract() {
		if (s.isEmpty())
			return null;
		if (s.elementAt(0) == finish) {
			this.notifyAll();
			return s.get(0);
		}
		Strategy temp = getMin();
		return temp;
	}

	// deleting strategy from information system
	public synchronized void deleteFromVec(Strategy temp) {
		s.remove(temp);
	}

	// get minimum object by comparing according to object type
	private synchronized Strategy getMin() {
		Strategy min = s.elementAt(0);
		for (int i = 1; i < s.size(); i++) {
			if (min.compareTo(s.elementAt(i)) > 0)
				min = s.elementAt(i);
		}
		return min;
	}

}