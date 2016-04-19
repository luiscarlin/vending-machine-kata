package luis.carlin.vm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import luis.carlin.vm.states.*;

/**
 * This class represents the context of the vending machine finite state machine.
 */
public class VendingMachine {
	
	public enum Inventory { 
		COLA 	(1), 
		CHIPS 	(2), 
		CANDY	(3); 
		
	    private final int button;
	    Inventory(int button) { this.button = button; }
	    public int getButton() { return button; }
	} 
	
	// states
	private State noMoney; 
	private State hasMoney;
	
	// current state
	private State vmState; 
	
	private double currentAmount;
	private String display; 
	private int numCoinsRejected;
	private Inventory soldProduct;
	private double coinReturn;
	
	private List<Product> inventory;
	
	public VendingMachine() { 
		noMoney = new NoMoney(this); 
		hasMoney = new HasMoney(this); 
		
		// start values 
		currentAmount = 0;
		display = "INSERT COIN"; 
		numCoinsRejected = 0;
		coinReturn = 0;
		soldProduct = null;
		
		// set initial state
		vmState = noMoney;
		
		// populate inventory
		inventory = new ArrayList<Product>();

		Product cola = new Product(); 
		cola.setProductType(Inventory.COLA);
		cola.setButton(Inventory.COLA.getButton());
		cola.setPrice(1.00);
		cola.setQuantity(5);

		Product chips = new Product(); 
		chips.setProductType(Inventory.CHIPS);
		chips.setButton(Inventory.CHIPS.getButton());
		chips.setPrice(0.50);
		chips.setQuantity(10);

		Product candy = new Product(); 
		candy.setProductType(Inventory.CANDY);
		candy.setButton(Inventory.CANDY.getButton());
		candy.setPrice(0.65);
		candy.setQuantity(3);

		inventory.add(cola); 
		inventory.add(chips); 
		inventory.add(candy); 
	}

	/**
	 * Sets the next state
	 * @param nextVMState next state
	 */
	public void setVMState(State nextVMState) { 
		vmState = nextVMState; 
	}
	
	/**
	 * Returns the no money state. Needed by state classes to switch states
	 * @return the noMoney state
	 */
	public State getNoMoneyState() { return noMoney; }

	/**
	 *  Returns the has money state. Needed by state classes to switch states
	 * @return the hasMoney state
	 */
	public State getHasMoneyState() { return hasMoney; }
	
	/**
	 * Adds a certain amount to the current amount of money in the machine
	 * @param moneyToAdd
	 */
	public void addToCurrentAmount(int moneyToAdd) { 
		currentAmount += moneyToAdd;
	}
	
	/**
	 * Inserts a coin based on its measurements
	 * @param size
	 * @param weight
	 */
	public void insertCoin(double size, double weight) { 
		vmState.insertCoin(size, weight);
	}
	
	/**
	 * Selects a product based on it's name. Either cola, chips, or candy
	 * @param productName name of the product
	 */
	public void makeSelection(int button) { 
		vmState.makeSelection(button);
	}
	
	/**
	 * Returns the coins that were previously added in the return coin compartment
	 */
	public void returnCoins() { 
		vmState.returnCoins();
	}
	
	// getters and setters
	public double getCurrentAmount() { return currentAmount; }
	public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; } 
	
	public void setDisplay(String display) { this.display = display; } 
	public String getDisplay() { 
		String toReturn = display;
		
		if (display == "THANK YOU") { 
			display = "INSERT COIN";
		}
		else if ((display.contains("PRICE")) || (display == "SOLD OUT")) { 
			if (currentAmount == 0) { 
				display = "INSERT COIN";
			}
			else { 
				DecimalFormat df = new DecimalFormat("0.00");
				display = "CREDIT: " + df.format(currentAmount);
			}
		}

		return toReturn; 
	} 
	
	public void setNumCoinsRejected(int numCoinsRejected) { this.numCoinsRejected = numCoinsRejected; } 
	public int getNumCoinsRejected() { return numCoinsRejected; } 
	
	public List<Product> getInventory() { return inventory; }

	public void setSoldProduct(Inventory soldProduct) { this.soldProduct = soldProduct; }
	public Inventory getSoldProduct() { 
		Inventory toReturn = soldProduct; 
		
		if(soldProduct != null) { 
			soldProduct = null;
		}
		
		return toReturn; 
	}
	
	public double getCoinReturn() { return coinReturn; }
	public void setCoinReturn(double coinReturn) { this.coinReturn = coinReturn; }
}