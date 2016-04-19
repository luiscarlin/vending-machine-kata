package luis.carlin.vm.states;

import java.text.DecimalFormat;

import luis.carlin.vm.Product;
import luis.carlin.vm.VendingMachine;
import luis.carlin.vm.VendingMachineHelper;

/**
 * Vending machine has money
 */

public class HasMoney implements State {
	VendingMachine vendingMachine;
	
	public HasMoney(VendingMachine vendingMachine) { 
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
		}
		else { 
			// unrecognized coin
			vendingMachine.setNumCoinsRejected(vendingMachine.getNumCoinsRejected() + 1);
		}
	}

	@Override
	public void makeSelection(int button) {
		
		// display the price
		for (Product product : vendingMachine.getInventory()) { 
			if (product.getButton() == button) {
				
				if(product.getQuantity() == 0) { 
					vendingMachine.setDisplay("SOLD OUT");
					break;
				}
				
				if (product.getPrice() <= vendingMachine.getCurrentAmount()) { 
					// sell
					vendingMachine.setSoldProduct(product.getProductType());
					
					// update display
					vendingMachine.setDisplay("THANK YOU");
					
					// return change
					vendingMachine.setCoinReturn(vendingMachine.getCurrentAmount() - product.getPrice());
					
					// update currenAmount
					vendingMachine.setCurrentAmount(0);
					
					// change state to no money
					vendingMachine.setVMState(vendingMachine.getNoMoneyState());
					
				}
				else { 
					// not enough, so display price
					DecimalFormat df = new DecimalFormat("0.00");
					vendingMachine.setDisplay("PRICE: " + df.format(product.getPrice()));
				}
			}
		}
	}

	@Override
	public void returnCoins() {
		vendingMachine.setCoinReturn(vendingMachine.getCoinReturn() + vendingMachine.getCurrentAmount());
		
		vendingMachine.setCurrentAmount(0);
		
		vendingMachine.setDisplay("INSERT COIN");
		
		// change state to no money
		vendingMachine.setVMState(vendingMachine.getNoMoneyState());
	}
}