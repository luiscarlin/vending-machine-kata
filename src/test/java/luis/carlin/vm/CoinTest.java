package luis.carlin.vm;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for Coin Class 
 */
public class CoinTest {
    
    @Test
    public void whenSimilarCoinsAreComparedReturnTrue() { 
        Coin coinOne = new Coin(Coin.Weight.HEAVY, Coin.Size.LARGE); 
        Coin coinTwo = new Coin(Coin.Weight.HEAVY, Coin.Size.LARGE);      
        assertTrue(coinOne.equals(coinTwo));
        assertTrue(coinTwo.equals(coinOne));
    }
}