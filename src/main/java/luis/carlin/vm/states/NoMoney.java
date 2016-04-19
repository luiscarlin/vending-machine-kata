package luis.carlin.vm.states;

import java.text.DecimalFormat;

import luis.carlin.vm.Product;
import luis.carlin.vm.VendingMachine;
import luis.carlin.vm.VendingMachineHelper;

/**
 * Vending machine does not have money
 */

public class NoMoney implements State {
	VendingMachine vendingMachine;
	
	public NoMoney(VendingMachine vendingMachine) { 
		this.vendingMachine = vendingMachine;
	} 
	
	@Override
	public void insertCoin(double size, double weight) {
				
		double valueOfCoin = VendingMachineHelper.getValueOfCoin(size, weight);
		
		if (valueOfCoin != -1) { 
			// add value to currentAmount
			vendingMachine.setCurrentAmount(vendingMachine.getCurrentAmount() + valueOfCoin);
				
			// update display 
			DecimalFormat df = new DecimalFormat("0.00");
			vendingMachine.setDisplay("CREDIT: " + df.format(vendingMachine.getCurrentAmount()));
			
			// change state to has money
			vendingMachine.setVMState(vendingMachine.getHasMoneyState());
		}
		else { 
			// unrecognized coin
			vendingMachine.setNumCoinsRejected(vendingMachine.getNumCoinsRejected() + 1);
		}
	}

	@Override
	public void makeSelection(int button) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		for (Product product : vendingMachine.getInventory()) { 
			if (product.getButton() == button) { 
				
				if (product.getQuantity() == 0) { 
					vendingMachine.setDisplay("SOLD OUT"); 
				}
				else 
					vendingMachine.setDisplay("PRICE: " + df.format(product.getPrice()));
				break;
			}
		}
	}

	@Override
	public void returnCoins() {
		// doesn't do anything
	}
}