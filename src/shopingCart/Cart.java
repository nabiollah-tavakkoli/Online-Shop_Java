package shopingCart;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import discount.DiscountStrategyFactory;
import shop.Department;
import shop.Store;

public class Cart extends AbstractViewer{
	
	private Department department;
	private int cartId;
	private static int lastCartId = 0;
	private Map<Store, Integer> cartMap = new HashMap<>();
	
	public Cart(Department department) {
		this.department = department;
		cartId = lastCartId++;
	}

	public int getCartId() {
		return cartId;
	}

	public Map<Store, Integer> getCartMap() {
		return cartMap;
	}
	
	public void addToCart(String itemName, int quantity)  throws NoSuchItemException{
		if(!getCartMap().containsKey(extractItem(itemName))) {
			getCartMap().put(extractItem(itemName), quantity);
			notifyObservers();
		}else {
			getCartMap().computeIfPresent(
					extractItem(itemName), 
				(k,v) -> v + quantity);
			notifyObservers();
		}
	}
	
	public void removeFromCart(String itemName, int quantity) throws NoSuchItemException {
		int CurrentItemQuantity = getCartMap().get(extractItem(itemName));
		if(quantity > CurrentItemQuantity) {
			throw new IndexOutOfBoundsException(
					"delete cannot proceed; there are only "+ CurrentItemQuantity + " " + itemName + " but not " + quantity);
		}else if(quantity < CurrentItemQuantity){
			getCartMap().computeIfPresent(
					extractItem(itemName), 
					(k,v) -> v - quantity);
			notifyObservers();
		}else {
			getCartMap().remove(extractItem(itemName));
			notifyObservers();
		}
	}

	private Store extractItem(String itemName) throws NoSuchItemException {
		return department.getStoreItems()
				.stream()
				.filter(e -> Objects.equals(e.getName(), itemName))
				.findFirst()
				.orElseThrow(() -> new NoSuchItemException(itemName));
	}

	private int getItemQuantity() {
		return getCartMap().values()
				.stream().reduce(0, Integer::sum);
	}

	protected String cartMapToString() {
		return getCartMap().keySet().stream()
			      .map(key -> key.getName() + " --- quantity: " + getCartMap().get(key))
			      .collect(Collectors.joining(",\n", "[", "]"));
	}
	

	public double cartPrice() {
		return cartMap.keySet()
				.stream()
				.map(key -> key.getPrice() * cartMap.get(key))
				.collect(Collectors.summingDouble(a -> a));
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + 
				" \n[Cart number = " + cartId + 
				" | Cart quantity = " + getItemQuantity() + 
				", \nCart list = " + cartMapToString() +
				", \nCart price = " + cartPrice() + "]";
	}
	
	public double cartPriceWithAbsoluteDiscount(double absoluteDiscount) {
		return DiscountStrategyFactory
				.absoluteDiscount(absoluteDiscount).applyDiscount(this.cartPrice());	
	}
	
	public double cartPriceWithPercentageDiscount(int percentage) {
		return DiscountStrategyFactory
				.percentageDiscount(percentage).applyDiscount(this.cartPrice());	
	}

}
