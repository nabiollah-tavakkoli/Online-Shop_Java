package shop;

import java.util.Objects;

public class Item  extends Store{
	private String description;
	private double price;
	

	public Item(String name, String description, double price) {
		super(name);
		this.price = price;
		this.description = description;
		
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return super.toString()
						+ " [description=" + description 
						+ ", " 
						+ "cost=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(price, description);
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
		Item other = (Item) obj;
		return Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(description, other.description);
	}

	@Override
	public double getPrice() {
		return price;
	}
	
}
