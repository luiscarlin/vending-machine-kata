package luis.carlin.vm.states;

/** 
 * The interface will be implemented by all state classes. 
 * State classes will provide logic for each input and output.
 */
public interface State {
	void insertCoin(double size, double weight); 
	void makeSelection(int button); 
	void returnCoins(); 
}
