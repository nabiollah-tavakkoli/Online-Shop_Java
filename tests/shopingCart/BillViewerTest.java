package shopingCart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import shop.Department;
import shop.Item;
import shop.Store;

public class BillViewerTest {
	
	 Store camera;
	 Store headphones;
	 Store eBookReader;
	 Store monitore;
	 Store printer;
	 Department electronics;
	
	@Before
	public void setUp(){
		camera = new Item("camera: WYZE Cam Pan v3", 
				"Indoor/Outdoor IP65-Rated 1080p, Rotating Motion-Tracking", 
				33.0);
		headphones = new Item("headphones: Sony WH-1000XM5", 
				"noise cancellation-two processors control 8 microphone, Multipoint connection", 
				389.0);
		eBookReader = new Item("eBookLeader: Kobo Libra 2", 
				"Adjustable Brightness and Color Temperature, Waterproof, Touchscreen, Blue Light Reduction", 
				169.0);
		monitore = new Item("monitore: Sceptre Curved", 
				"24-inch Gaming Monitor 1080p, Build-in Speakers", 
				89.0);
		printer = new Item("printer: HP OfficeJet Pro", 
				"6 MONTHS FREE INK, BEST FOR SMALL BUSINESSES AND HOME OFFICES, HP SMART APP", 
				166.0);
		
		electronics = new Department("Electronics");
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(headphones, camera, eBookReader));
		
	}

	@Test
	public void testBillViewer() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		BillViewer observer = new BillViewer(myCart);
		
		assertThat(myCart.cartPrice()).isEqualTo(observer.getUpdatedBill());
		
		myCart.attachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		double currentCartPrice = observer.getUpdatedBill();
		assertThat(myCart.cartPrice()).isEqualTo(currentCartPrice);
		
		myCart.detachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		assertThat(observer.getUpdatedBill()).isEqualTo(currentCartPrice);
		assertThat(myCart.cartPrice()).isNotEqualTo(currentCartPrice);
		
	}
	
	@Test
	public void testBillViewerWithAbsoluteDiscount() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		BillViewerWithAbsoluteDiscount observer = new BillViewerWithAbsoluteDiscount(myCart, 22.0);
		
		assertThat(myCart.cartPriceWithAbsoluteDiscount(22.0)).isEqualTo(observer.getUpdatedBill());
		
		myCart.attachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		double currentCartPrice = observer.getUpdatedBill();
		assertThat(myCart.cartPriceWithAbsoluteDiscount(22)).isEqualTo(currentCartPrice);
		
		myCart.detachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		assertThat(observer.getUpdatedBill()).isEqualTo(currentCartPrice);
		assertThat(myCart.cartPriceWithAbsoluteDiscount(22.0)).isNotEqualTo(currentCartPrice);
		
	}
	
	@Test
	public void testBillViewerWithPercentageDiscount() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		BillViewerWithPercentageDiscount observer = new BillViewerWithPercentageDiscount(myCart, 10);
		
		assertThat(myCart.cartPriceWithPercentageDiscount(10)).isEqualTo(observer.getUpdatedBill());
		
		myCart.attachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		double currentCartPrice = observer.getUpdatedBill();
		assertThat(myCart.cartPriceWithPercentageDiscount(10)).isEqualTo(currentCartPrice);
		
		myCart.detachObserver(observer);
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		assertThat(observer.getUpdatedBill()).isEqualTo(currentCartPrice);
		assertThat(myCart.cartPriceWithPercentageDiscount(10)).isNotEqualTo(currentCartPrice);
		
	}

}
