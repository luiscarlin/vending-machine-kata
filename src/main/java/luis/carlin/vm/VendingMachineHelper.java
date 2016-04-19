package luis.carlin.vm;

public class VendingMachineHelper {
	/**
	 * Returns the value of the coin based on its measurements
	 * @param size		of the coin
	 * @param weight	of the coin
	 * @return the value of the coin or -1 if not valid
	 */
	public static double getValueOfCoin(double size, double weight) { 
		
		if((size == CoinConstants.NICKEL_SIZE) && (weight == CoinConstants.NICKEL_WEIGHT)) { 
			return 0.05;
		}
		else if((size == CoinConstants.DIME_SIZE) && (weight == CoinConstants.DIME_WEIGHT)) { 
			return 0.10;
		} 
		else if((size == CoinConstants.QUARTER_SIZE) && (weight == CoinConstants.QUARTER_WEIGHT)) { 
			return 0.25;
		}
		else { 
			return -1;
		}	
	}
}
