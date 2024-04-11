package shopingCart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import shop.Department;
import shop.Item;
import shop.Store;

public class CartTest {

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
	public void testCartMapToString() {
		Cart myCart = new Cart(electronics);
		
		assertThat(myCart.cartMapToString()).isEqualTo("[]");
		
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		assertThat(myCart.cartMapToString()).isEqualTo(
				 "[camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				 + "eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				 + "headphones: Sony WH-1000XM5 --- quantity: 2]");
	}
	
	@Test
	public void testAddToCart() throws NoSuchItemException {
		Cart myCart = new Cart(electronics);
		
		myCart.addToCart("camera: WYZE Cam Pan v3", 1);
		myCart.addToCart("eBookLeader: Kobo Libra 2", 2);
		
		assertThat(myCart.cartMapToString()).isEqualTo(
				"[camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				+ "eBookLeader: Kobo Libra 2 --- quantity: 2]");
		
		myCart.addToCart("headphones: Sony WH-1000XM5", 1);
		
		assertThat(myCart.cartMapToString()).isEqualTo(
				"[camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				+ "eBookLeader: Kobo Libra 2 --- quantity: 2,\n"
				+ "headphones: Sony WH-1000XM5 --- quantity: 1]");

	}
	
	@Test
	public void testRemoveFromCartWithException() throws NoSuchItemException {
		Cart myCart = new Cart(electronics);
		
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		assertThat(myCart.cartMapToString()).isEqualTo(
				 "[camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				 + "eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				 + "headphones: Sony WH-1000XM5 --- quantity: 2]");
		
		assertThrows(
				"delete cannot proceed; there are only 1 camera: WYZE Cam Pan v3 but not 4", 
				IndexOutOfBoundsException.class,
				() -> myCart.removeFromCart("camera: WYZE Cam Pan v3", 4));

	}
	
	@Test
	public void testRemoveFromOrder() throws NoSuchItemException {
		Cart myCart = new Cart(electronics);
		
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		assertThat(myCart.cartMapToString()).isEqualTo(
				 "[camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				 + "eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				 + "headphones: Sony WH-1000XM5 --- quantity: 2]");
		
		myCart.removeFromCart("camera: WYZE Cam Pan v3", 1);
		
		assertThat(myCart.cartMapToString()).isEqualTo(
				 "[eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				+ "headphones: Sony WH-1000XM5 --- quantity: 2]");
		
		myCart.removeFromCart("headphones: Sony WH-1000XM5", 1);
		
		assertThat(myCart.cartMapToString()).isEqualTo(
				 "[eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				+ "headphones: Sony WH-1000XM5 --- quantity: 1]");
	}
	
	@Test
	public void testUpdatedBill() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		assertThat(myCart.cartPrice()).isEqualTo(980.0);
	}
	
	@Test
	public void testToString() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		assertThat(myCart.toString()).isEqualTo(
				"Cart \n"
				+ "[Cart number = 1 | Cart quantity = 4, \n"
				+ "Cart list = ["
				+ "camera: WYZE Cam Pan v3 --- quantity: 1,\n"
				+ "eBookLeader: Kobo Libra 2 --- quantity: 1,\n"
				+ "headphones: Sony WH-1000XM5 --- quantity: 2], \n"
				+ "Cart price = 980.0]");
	}
	
	@Test
	public void testGetTotalPriceWithDiscount() {
		Cart myCart = new Cart(electronics);
		Map<Store, Integer>orderMap = myCart.getCartMap();
		orderMap.put(camera, 1);
		orderMap.put(headphones, 2);
		orderMap.put(eBookReader, 1);
		
		assertThat(myCart.cartPriceWithAbsoluteDiscount(80)).isEqualTo(900);
		assertThat(myCart.cartPriceWithPercentageDiscount(10)).isEqualTo(882.0);
	}

}
