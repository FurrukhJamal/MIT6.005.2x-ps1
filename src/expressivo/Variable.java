package expressivo;

import java.util.Map;

public class Variable implements Expression {

	private final String name;
	
	public Variable(String name) {
		this.name = name;
		this.checkRep();
	}
	
	private void checkRep() {
		assert this.name != null && this.name != "" : "rep voilated: name null or empty";
		assert this.name.length() > 0;
		assert this.name.matches("[a-zA-Z]+");
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object thatObj) {
		if(!(thatObj instanceof Variable)) {
			return false;
		}
		
		Variable thatobj = (Variable) thatObj;
		return this.name == thatobj.name;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		int temp = this.name.hashCode();
		
		result = prime * result + temp;
		return result;
	}
	
	@Override
	public Expression differentiate(String variable)
	{
//		System.out.println("differentiate hitting, variable is:" +variable);
//		Variable test = new Variable(variable);
//		Variable test2 = new Variable(variable);
//		System.out.println("this.name:" +this.name);
//		System.out.println("test.name:" +test.name);
//		System.out.println("test.equals(test2) :" +test.equals(test2));
//		System.out.println("Class of this:" +this.getClass());
//		System.out.println("this.equals(new Variable(variable)) : " +this.equals(new Variable(variable)));
//		System.out.println("this.name == variable" +this.name.equals(variable));
		if(this.name.equals(variable))
		{
			return new Value(1);
		}
		else
		{
			return new Value(0);
		}
	}
	
	@Override
	public Expression addExpr(Expression otherExpr)
	{
		assert otherExpr != null;
		if(otherExpr.equals(new Value(0)))
		{
			return this;
		}
		
		return otherExpr.addVariable(this.name);
	}
	
	@Override
	public Expression addVariable(String name)
	{
		return new Addition(this, new Variable(name));
	}
	
	@Override
	public Expression addConstant(double num)
	{
		assert num > 0 && Double.isFinite(num);
		Value newNum = new Value(num);
		if(newNum.equals(new Value(0)))
		{
			return this;
		}
		
		return new Addition(newNum, this);
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
		
		return new Multiplication(factor, this);
	}
	
	@Override
	public Expression substitute(Map<String, Double> env)
	{
		if(env.containsKey(this.name))
		{
			Double val = env.get(this.name);
			return new Value(val);
		}
		return this;
		
	}
	
	@Override 
	public Expression addCoefficient(double num) {
        assert num >= 0 && Double.isFinite(num);
        
        Value valNum = new Value(num);
        Value zero = new Value(0);
        if (valNum.equals(zero)) {
            return new Value(0);
        }
        if (valNum.equals(new Value(1))) {
            return this;
        }
        return new Multiplication(new Value(num), this);
	}
}
