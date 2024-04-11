package shopingCart;

public class BillViewerWithAbsoluteDiscount implements BillObserver{
	private Cart myCart;
	private double absoluteDiscount;
	private double updatedBill;
	
	public BillViewerWithAbsoluteDiscount(Cart myCart, double absoluteDiscount) {
		this.myCart = myCart;
		this.absoluteDiscount = absoluteDiscount;
		updatedBill = myCart.cartPriceWithAbsoluteDiscount(absoluteDiscount);
	}

	@Override
	public void updatedBill() {
		updatedBill = this.myCart.cartPriceWithAbsoluteDiscount(absoluteDiscount);
		
	}

	public double getUpdatedBill() {
		return updatedBill;
	}

}
