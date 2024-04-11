package discount;

public class DiscountStrategyFactory {
	
	public static DiscountStrategy absoluteDiscount(double discount) {
		return originialPrice -> originialPrice - discount;
	}

	public static DiscountStrategy percentageDiscount(int percentage) {
		return originialPrice -> originialPrice - (originialPrice * percentage / 100);
	}

}
