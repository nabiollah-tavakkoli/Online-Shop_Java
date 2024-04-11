package shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Department extends Store{

	private Collection<Store> storeItems = new ArrayList<>();
	
	public Department(String name) {
		super(name);
	}

	public Collection<Store> getStoreItems() {
		return storeItems;
	}

	@Override
	public String toString() {
		return "Department [storeItems=" + storeItems + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(storeItems);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(storeItems, other.storeItems);
	}
	
	public void add(Store item) {
		storeItems.add(item);
	}
	
	public void remove(Store item) {
		storeItems.remove(item);
	}
	
	public Optional<Store> findItemByName(String givenItemName){
		return storeItems
				.stream()
				.filter(item -> Objects.equals(item.getName(), givenItemName))
				.findFirst();
	}
	
	public Iterator<Store> departmentIterator(){
		return storeItems.iterator();
	}
	
	public Department sortByPrice() {
		Department sortedItems = new Department("" + this.getName());
		sortedItems.getStoreItems().addAll(
				this.storeItems.stream()
				.sorted(Comparator.comparingDouble(Store::getPrice))
				.collect(Collectors.toList()));
		return sortedItems;
	}

	@Override
	public double getPrice() {
		return storeItems
				.stream()
				.map(item -> item.getPrice())
				.collect(Collectors.summingDouble(a -> a));
	}

}
