package shopingCart;

import java.util.ArrayList;
import java.util.Collection;

public class AbstractViewer {
	
	private Collection<BillObserver> observers = new ArrayList<>();
	
	public void attachObserver(BillObserver observer) {
		observers.add(observer);
	}
	
	public void detachObserver(BillObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		observers.forEach(BillObserver::updatedBill);
	}

}
