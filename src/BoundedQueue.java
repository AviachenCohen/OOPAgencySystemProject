import java.util.Vector;

public class BoundedQueue<T> extends Queue<T> {
	private Vector<T> v;
	private int max;

	// BoundedQueue constructor
	public BoundedQueue(int n) {
		v = new Vector<T>();
		this.max = n;
	}

	// inserting operation to queue
	public synchronized void insert(T t) {
		while (v.size() == max)
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		if (t == null)
			v.add(0, null);
		else
			v.add(t);
		this.notifyAll();
	}

	// extracting operation from queue
	public synchronized T extract() {
		while (v.isEmpty())
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		if (v.elementAt(0) == null) {
			this.notifyAll();
			return v.remove(0);
		}
		T temp = v.remove(0);
		this.notifyAll();
		return temp;
	}

}