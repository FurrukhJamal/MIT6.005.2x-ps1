package expressivo;

import java.util.Map;

public class Addition implements Expression {
	//Abstraction function 
	/*
	 * represents addition expression made up of two subExpression
	 * 
	 * Rep Invariant: left and right are twon non null immutable expressions
	 * 
	 * safety from exposure : All fields are private
	 * left and right are immutable
	 * */
	
	private final Expression left;
	private final Expression right;
	
	public Addition(Expression left, Expression right) {
		this.left = left;
		this.right = right; 
		this.checkRep();
	}
	
	private void checkRep() {
		assert this.left != null && this.right != null;
	}
	
	@Override
	public String toString() {
		return this.left.toString() + " + " + this.right.toString();
	}
	
	@Override
	public boolean equals(Object thatObj)
	{
		if(!(thatObj instanceof Addition)) {
			return false;
		}
		
		Addition thatObject = (Addition) thatObj;
		return this.toString().equals(thatObject.toString());
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		int temp = this.left.hashCode() + this.right.hashCode();
		
		result = result * prime + temp;
		return result;
	}
	
	@Override
	public Expression differentiate(String variable)
	{
		System.out.println("differentiate method of addition hitting");
		Expression leftDerivative = this.left.differentiate(variable);
		Expression rightDerivative = this.right.differentiate(variable);
		
		System.out.println("expression after differentiation in addition:" +leftDerivative.addExpr(rightDerivative));
		
		return leftDerivative.addExpr(rightDerivative);
	}
	
	@Override
	public Expression addConstant(double num)
	{
		assert num >= 0 && Double.isFinite(num);
		Value val = new Value(num);
		if(this.left.equals(val))
		{
			Expression newLeft = this.left.addConstant(num);
			return new Addition(newLeft, this.right );
		}
		if(this.right.equals(val))
		{
			Expression newRight = this.right.addConstant(num);
			return new Addition(this.left, newRight);
		}
		
		return new Addition(val, this);
	}
	
	@Override
	public Expression addExpr(Expression expr)
	{
		if(expr.equals(new Value(0)))
		{
			return this;
		}
		if(this.equals(expr))
		{
			//multiply by two since it is the same expression
			Expression newLeft = this.left.multiplyExpr(new Value(2));
			Expression newRight = this.right.multiplyExpr(new Value(2));
			
			return new Addition(newLeft, newRight);
		}
		//if lef or right expression is just equal
		if(this.left.equals(expr))
		{
			Expression newLeft = this.left.multiplyExpr(new Value(2));
			return new Addition(newLeft, this.right);
		}
		if(this.right.equals(expr))
		{
			Expression newRight = this.right.multiplyExpr(new Value(2));
			return new Addition(this.left, newRight);
		}
		
		return new Addition(this, expr);
	}
	
	
	@Override
	public Expression multiplyExpr(Expression factor)
	{
		Value zero = new Value(0);
		if(factor.equals(zero))
		{
			return zero;
		}
		
		if(factor.equals(new Value(1)))
		{
			return this;
		}
		
		if(factor instanceof Value || factor instanceof Variable && (this.left instanceof Value || this.left instanceof Variable))
		{
			return new Addition(new Multiplication(factor, this.left), this.right);
		}
		if(factor instanceof Value || factor instanceof Variable && (this.right instanceof Value || this.right instanceof Variable))
		{
			return new Addition(this.left, new Multiplication(factor, this.right));
		}
		
		return new Multiplication(this, factor);
	}
	
	@Override
	public Expression addVariable(String name)
	{
		assert name != null && name != "";
		return new Addition(this, new Variable(name));
	}
	
	@Override
	public Expression substitute(Map<String, Double> env)
	{
		//System.out.println("Substitude hitting of Addition");
		//System.out.println("This.left:" +this.left);
		//System.out.println("instance of this.left variable: " +(this.left instanceof Variable));
		//System.out.println("this.right: " +this.right);
		Expression newValLeft = null;
		Expression newValRight = null;
//		for(String key: env.keySet())
//		{
//			System.out.println("key: " +key);
//			Variable var = new Variable(key);
//			System.out.println(var.toString());
//			System.out.println("this.left.equals(var) : " +this.left.equals(var));
//			if(this.left.equals(var))
//			{
//				System.out.println("if condition for left in substitue hitting");
//				newValLeft = new Value(env.get(key));
//				
//			}
//			
//			
//			if(this.right.equals(var))
//			{
//				System.out.println("if condition for right in substitue hitting");
//				newValRight = new Value(env.get(key));
//			}
//			
//		}
//		
//		if(newValLeft == null && newValRight == null)
//		{
//			System.out.println("both are null in addition substitute");
//			return this;
//		}
//		else if(newValLeft !=  null && newValRight == null)
//		{
//			return new Addition(newValLeft, this.right);
//		}
//		else if(newValLeft == null && newValRight != null)
//		{
//			return new Addition(this.left, newValRight);
//		}
//		
//		return new Addition(newValLeft, newValRight);
		
		assert env != null;
		return this.left.substitute(env).addExpr(this.right.substitute(env));
	}
	
	
	@Override
	public Expression addCoefficient(double num)
	{
		if(new Value(num).equals(new Value(0)))
		{
			return new Value(0);
		}
		if(new Value(num).equals(new Value(1)))
		{
			return new Value(1);
		}
		
		return this.left.addCoefficient(num).addExpr(this.right.addCoefficient(num));
	}

}
