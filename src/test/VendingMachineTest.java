package test;

import java.util.List;

import main.CoinConstants;
import main.Product;
import main.VendingMachine;
import main.VendingMachine.Inventory;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the vending machine. Uses BDD style tests with snake casing.
 * @author Luis
 *
 */

public class VendingMachineTest {
	
	private VendingMachineTest GIVEN = this, WHEN = this, THEN = this, AND = this;
	
	private VendingMachine vendingMachine; 
	private enum ValidCoin { NICKEL, DIME, QUARTER; } 
	
	@Before
	public void setupTest() { 
		vendingMachine = new VendingMachine();
	}
	
	/*
	 * ACCEPT COINS
	 * STORY: As a vendor, I want a vending machine that accepts coins, so that I can collect money from the customer
	 */
	
	@Test
	public void verify_display_initial_state() { 
		
		WHEN.the_current_amount_in_the_machine_is(0);
		THEN.the_display_shows("INSERT COIN");
	}
	
	@Test
	public void machine_will_accept_nickel_coins_based_on_measurements() {
		
		WHEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.NICKEL);
		THEN.the_display_shows("CREDIT: 0.05");
		AND.the_current_amount_in_the_machine_is(0.05);
	}
	
	@Test
	public void machine_will_accept_dime_coins_based_on_measurement() {
		
		WHEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.DIME);
		THEN.the_display_shows("CREDIT: 0.10");
		AND.the_current_amount_in_the_machine_is(0.10);
	}
	
	@Test
	public void machine_will_accept_quarter_coins_based_on_measurements() {
		
		WHEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		THEN.the_display_shows("CREDIT: 0.25");
		AND.the_current_amount_in_the_machine_is(0.25);
	}
	
	@Test
	public void machine_will_reject_coins_with_unknown_size_and_weight() {
		
		GIVEN.the_display_shows("INSERT COIN");
		WHEN.i_input_a_coin_with_unknown_size_and_weight(123, 45);
		THEN.the_display_shows("INSERT COIN");
		AND.the_number_of_coins_in_the_coin_return_is(1);
	}
	
	/*
	 * SELECT PRODUCT
	 * STORY: I want customers to select products, so that I can give them an incentive to put money in the machine
	 */
	
	@Test
	public void selecting_cola() {
		
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.COLA.getButton());
		THEN.the_display_shows("PRICE: 1.00"); 
	}
	
	@Test
	public void selecting_chips() {
		
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CHIPS.getButton());
		THEN.the_display_shows("PRICE: 0.50"); 
	}
	
	@Test
	public void selecting_candy() {
		
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CANDY.getButton());;
		THEN.the_display_shows("PRICE: 0.65"); 
	}
	
	@Test
	public void selling_a_product_after_inserting_coins() { 
		
		GIVEN.the_display_shows("INSERT COIN");
		WHEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.COLA.getButton());
		THEN.the_display_shows("THANK YOU");
		AND.the_machine_dispenses_the_product(VendingMachine.Inventory.COLA);
	}
	
	@Test
	public void returning_to_initial_state_after_selling_a_product() { 
		
		GIVEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CHIPS.getButton());
		WHEN.the_display_shows("THANK YOU");
		AND.the_machine_dispenses_the_product(VendingMachine.Inventory.CHIPS);
		THEN.the_display_shows("INSERT COIN"); 
		AND.the_machine_dispenses_the_product(null);
		AND.the_current_amount_in_the_machine_is(0);
	}
	
	@Test
	public void display_price_only_once_if_not_enough_money() { 
		
		GIVEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.the_display_shows("CREDIT: 0.25");
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CANDY.getButton());
		THEN.the_display_shows("PRICE: 0.65");
		AND.the_display_shows("CREDIT: 0.25");
	}
	
	@Test
	public void display_price_only_once_if_in_initial_state() { 
		
		GIVEN.the_display_shows("INSERT COIN");
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CANDY.getButton());
		THEN.the_display_shows("PRICE: 0.65");
		AND.the_display_shows("INSERT COIN");
	}
	
	/*
	 * MAKE CHANGE
	 * STORY: As a vendor, I want customers to receive correct change, so that they will use the vending machine again
	 */
	@Test
	public void receive_change() { 
		
		GIVEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CANDY.getButton());
		THEN.the_coin_return_compartment_contains(0.10);
	}
	
	/*
	 * RETURN COINS
	 * STORY: As a customer, I want to have my money returned, so that I can change my mind about buying stuff from the vending machine
	 */
	@Test
	public void return_coins() { 
		
		GIVEN.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.DIME);
		WHEN.i_press_return_coins();
		THEN.the_coin_return_compartment_contains(0.35);
		AND.the_display_shows("INSERT COIN");
	}
	
	/*
	 * SOLD OUT
	 * STORY: As a customer, I want to be told when the item I have selected is not available, so that I can select another item
	 */
	@Test
	public void select_sold_out_item_after_entering_money() { 
		
		GIVEN.i_buy_all_items_of_a_product(VendingMachine.Inventory.CANDY); 
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.QUARTER);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.DIME);
		AND.i_input_the_valid_weight_and_size_for_a_coin(ValidCoin.NICKEL);
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.CANDY.getButton());
		THEN.the_display_shows("SOLD OUT");
		AND.the_display_shows("CREDIT: 0.65");
	}
	
	@Test
	public void select_sold_out_item_without_entering_money() { 
		
		GIVEN.i_buy_all_items_of_a_product(VendingMachine.Inventory.COLA); 
		AND.the_display_shows("INSERT COIN");
		WHEN.i_select_a_product_by_pressing_a_button(VendingMachine.Inventory.COLA.getButton());
		THEN.the_display_shows("SOLD OUT");
		AND.the_display_shows("INSERT COIN");
	}
	
	// Helper methods **********************************************************

	private void i_buy_all_items_of_a_product(Inventory item) {
		List<Product> inventory = vendingMachine.getInventory(); 
		
		for (Product product : inventory) { 
			if (product.getProductType() == item) { 
				product.setQuantity(0);
			}
		}
	}

	private void i_press_return_coins() {
		vendingMachine.returnCoins();
		
	}

	private void the_coin_return_compartment_contains(double expectedChange) {
		vendingMachine.getCoinReturn();
	}

	private void the_machine_dispenses_the_product(Inventory product) {
		assertTrue("Incorrect sold product", vendingMachine.getSoldProduct() == product);
	}

	private void i_select_a_product_by_pressing_a_button(int button) {
		vendingMachine.makeSelection(button);	
	}

	private void the_number_of_coins_in_the_coin_return_is(int expectedNumberOfCoinsInCoinReturn) {
		assertEquals("Incorrect number of coins in the coin return", expectedNumberOfCoinsInCoinReturn, vendingMachine.getNumCoinsRejected());
	}

	private void i_input_a_coin_with_unknown_size_and_weight(double badSize, double badWeight) {
		vendingMachine.insertCoin(badSize, badWeight);
	}

	private void the_current_amount_in_the_machine_is(double expectedAmount) {
		assertTrue("Expected " + expectedAmount + " in the vending maachine", expectedAmount == vendingMachine.getCurrentAmount());
	}

	private void the_display_shows(String expectedMessage) {
		assertEquals("Incorrect message in the display", expectedMessage, vendingMachine.getDisplay());
	}

	private void i_input_the_valid_weight_and_size_for_a_coin(ValidCoin validCoin) {
		
		if (validCoin == ValidCoin.NICKEL) {
			vendingMachine.insertCoin(CoinConstants.NICKEL_SIZE, CoinConstants.NICKEL_WEIGHT);
		}
		else if (validCoin == ValidCoin.DIME) {
			vendingMachine.insertCoin(CoinConstants.DIME_SIZE, CoinConstants.DIME_WEIGHT);
		}
		else if (validCoin == ValidCoin.QUARTER) {
			vendingMachine.insertCoin(CoinConstants.QUARTER_SIZE, CoinConstants.QUARTER_WEIGHT);
		}
	}
}
