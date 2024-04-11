package shop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class DepartmentTest {

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
		
	}

	@Test
	public void testAddToStore() {
		electronics.add(camera);
		electronics.add(headphones);
		
		Collection<Store> electronicsItems = electronics.getStoreItems();
		assertThat(electronicsItems.size()).isNotZero();
		assertThat(electronicsItems.size()).isEqualTo(2);
		
	}
	
	@Test
	public void testRemoveFromStore() {
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(camera, headphones, monitore));
		
		assertThat(electronicsItems.size()).isEqualTo(3);
		assertThat(electronicsItems).containsExactlyInAnyOrder(camera, headphones, monitore);
		
		electronicsItems.remove(monitore);
		assertThat(electronicsItems.size()).isEqualTo(2);
		assertThat(electronicsItems).containsExactlyInAnyOrder(camera, headphones);
	}

	@Test
	public void testFindByName() {
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(camera, headphones));
		
		assertThat(electronics.findItemByName("headphones: Sony WH-1000XM5")).isPresent();
		assertThat(electronics.findItemByName("camera: WYZE Cam Pan v3")).isPresent();
		assertThat(electronics.findItemByName("camera: WYZE Cam Pan v3")).get().isEqualTo(camera);
		
		Department otherelectronics = new Department("other electronics");
		Collection<Store> otherelectronicItems = otherelectronics.getStoreItems();
		otherelectronicItems.addAll(Arrays.asList(monitore, printer));
		electronicsItems.add(otherelectronics);
		assertThat(electronics.findItemByName("other electronics")).isPresent();
	}
	
	@Test
	public void testRecursiveDepartmentIterator() {
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(camera, headphones));
		
		assertThat(electronics.departmentIterator())
		.toIterable()
		.containsExactlyInAnyOrder(headphones, camera);
	}
	
	@Test
	public void testGetPrice() {
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(camera, headphones));
		
		assertThat(camera.getPrice()).isEqualTo(33);
		assertThat(headphones.getPrice()).isEqualTo(389);
		assertThat(electronics.getPrice()).isEqualTo(422);
		
		electronicsItems.remove(headphones);
		assertThat(electronicsItems.size()).isEqualTo(1);
		assertThat(electronics.getPrice()).isEqualTo(33);
		
		Department otherelectronics = new Department("other electronics");
		Collection<Store> otherelectronicItems = otherelectronics.getStoreItems();
		otherelectronicItems.addAll(Arrays.asList(monitore, printer));
		electronicsItems.add(otherelectronics);
		assertThat(electronics.getPrice()).isEqualTo(288);
	}
	
	@Test
	public void testSortByPrice() {
		Collection<Store> electronicsItems = electronics.getStoreItems();
		electronicsItems.addAll(Arrays.asList(headphones, camera, eBookReader));
		assertThat(electronics.sortByPrice().getStoreItems()).containsExactly(camera, eBookReader, headphones);
		
		Department otherelectronics = new Department("other electronics");
		Collection<Store> otherelectronicItems = otherelectronics.getStoreItems();
		otherelectronicItems.addAll(Arrays.asList(monitore, printer));
		
		electronicsItems.add(otherelectronics);
		assertThat(electronics.sortByPrice().getStoreItems()).containsExactly(camera, eBookReader,otherelectronics, headphones);
	}
}
