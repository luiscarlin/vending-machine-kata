package luis.carlin.vm;

/**
 * This interface represents the possible inputs that the user could do. 
 * The interface will be implemented by all state classes, and they will provide logic for these user inputs.
 */
public interface VMState { 
	
	void insertCoin(double size, double weight); 
	void makeSelection(int button); 
	void returnCoins(); 
}