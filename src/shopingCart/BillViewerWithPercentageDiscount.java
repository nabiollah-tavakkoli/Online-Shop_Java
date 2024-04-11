package shopingCart;

public class BillViewerWithPercentageDiscount implements BillObserver{
	private Cart myCart;
	private int percentage;
	private double updatedBill;
	
	public BillViewerWithPercentageDiscount(Cart myCart, int percentage) {
		this.myCart = myCart;
		this.percentage = percentage;
		updatedBill = myCart.cartPriceWithPercentageDiscount(percentage);
	}

	@Override
	public void updatedBill() {
		updatedBill = this.myCart.cartPriceWithPercentageDiscount(percentage);
		
	}

	public double getUpdatedBill() {
		return updatedBill;
	}
}
