import java.util.Vector;

public class Queue<T> {
	private Vector<T> v;

	// Queue constructor
	public Queue() {
		v = new Vector<T>();
	}

	// inserting call/task to queue
	public synchronized void insert(T t) {
		if (t == null)
			v.add(0, null);
		else
			v.add(t);
		this.notifyAll();
	}

	// extracting call/task from queue
	public synchronized T extract() {
		while (v.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (v.elementAt(0) == null) {
			this.notifyAll();
			return v.remove(0);
		}
		T temp = v.remove(0);
		return temp;
	}

}