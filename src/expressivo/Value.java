package expressivo;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Value implements Expression {
	private final double num;
	
	private void checkRep() {
		assert num >= 0 && num <= Double.MAX_VALUE : "invalid number value, rep voilated";
	}
	
	public Value(double num) {
		this.num = num;
		this.checkRep();
	}
	
	@Override 
	public String toString() {
		DecimalFormat myFormatter = new DecimalFormat("###.#####");
        myFormatter.setRoundingMode(RoundingMode.DOWN);
        String output = myFormatter.format(this.num);

        this.checkRep();
        return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		long temp;
//		temp = Double.doubleToLongBits(num);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		long hashforDouble = Double.doubleToLongBits(this.num);
		result = prime * result + (int)hashforDouble;
		return result;
	}

	@Override
	public boolean equals(Object thatObj) {
		if(!(thatObj instanceof Value)) {
			return false;
			
		}
		Value thatObject = (Value) thatObj;
		double thatNum = Double.parseDouble(thatObject.toString());
		final double EPSILON = 0.00001;
		
		return Math.abs(this.num - thatNum) < EPSILON;
	}
	
}
