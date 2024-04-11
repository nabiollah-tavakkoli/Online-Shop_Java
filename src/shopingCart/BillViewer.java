package shopingCart;

public class BillViewer implements BillObserver{
	private Cart myCart;
	private double updatedBill;
	
	public BillViewer(Cart myCart) {
		this.myCart = myCart;
		updatedBill = myCart.cartPrice();
	}

	@Override
	public void updatedBill() {
		updatedBill = this.myCart.cartPrice();
		
	}

	public double getUpdatedBill() {
		return updatedBill;
	}


}
