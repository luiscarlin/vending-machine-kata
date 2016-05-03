package luis.carlin.vm;

import java.util.Objects;

/**
 * Represents a Coin.
 */
public class Coin {
    
    public enum Weight { 
        HEAVY,
        MID,
        LIGHT
    }
    
    public enum Size { 
        LARGE,
        MEDIUM,
        SMALL
    }
    
    private Coin.Weight weight; 
    private Coin.Size size; 
    
    Coin(Coin.Weight weight, Coin.Size size) { 
        this.weight = weight;
        this.size = size;
    }
    
    @Override
    public boolean equals(Object coin) { 
      return coin instanceof Coin 
              && this.size == ((Coin)coin).size 
              && this.weight == ((Coin)coin).weight;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.size, this.weight);
    }
}
