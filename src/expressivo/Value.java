package expressivo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class Value implements Expression {
	private final double num;
	
	private void checkRep() {
		assert num >= 0 && num <= Double.MAX_VALUE : "invalid number value, rep voilated";
	}
	
	public Value(double num) {
		
		this.num = num;
		this.checkRep();
	}
	
	private double getNum() {
		return this.num;
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
	
	
	@Override
	public Expression differentiate(String variable)
	{
		assert variable != null && variable != "" : "the supplied variable for type variable is undefined";
		//derivative of a constant is always zero
		return new Value(0);
	}
	
	
	
	
	@Override
	public Expression addExpr(Expression otherExpr)
	{
		assert otherExpr != null ;
		
		if(this.equals(otherExpr))
		{
			double newNum = this.num * 2;
			return new Value(newNum);
		}
		
		Value zero = new Value(0);
		
		if(otherExpr.equals(zero))
		{
			return this;
		}
		else if(this.equals(zero))
		{
			return otherExpr;
		}
		else
		{
			
//			double num = this.num + otherExpr.getNum();
//			Value newVal = new /Value(num);
//		
//			
//			//return new Addition(this, newVal);
//			return newVal;
			return otherExpr.addConstant(this.num);
		}
		
		//return null;
		
	}
	
	@Override
	public Expression addConstant(double num)
	{
		return new Value(num + this.num);
	}
	
	@Override
	public Expression multiplyExpr(Expression factor)
	{
		if(factor.equals(new Value(0)))
		{
			return new Value(0);
		}
		if(factor.equals(new Value(1)))
		{
			return this;
		}
		if(this.equals(new Value(1)))
		{
			return factor;
		}
		
		return factor.addCoefficient(this.num);
	}
	
	@Override
	public Expression addVariable(String name)
	{
		assert name != "" &&  name != null;
		
		return new Addition(this, new Variable(name));
	}
	
	@Override 
	public Expression substitute(Map<String, Double> envoirnment)
	{
		return this;
	}
	
	@Override
	public Expression addCoefficient(double num)
	{
		return new Value(this.num * num);
	}
}
