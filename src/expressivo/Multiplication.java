package expressivo;

import java.util.Map;

public class Multiplication implements Expression {

	//Abstraction function 
		/*
		 * represents Multiplication expression made up of two subExpression
		 * 
		 * Rep Invariant: left and right are twon non null immutable expressions
		 * 
		 * safety from exposure : All fields are private
		 * left and right are immutable
		 * */
		
		private final Expression left;
		private final Expression right;
		
		public Multiplication(Expression left, Expression right) {
			this.left = left;
			this.right = right; 
			this.checkRep();
		}
		
		private void checkRep() {
			assert this.left != null && this.right != null;
		}
		
		@Override
		public String toString() {
			String left = "(" + this.left.toString() + ")";
			String right = "(" + this.right.toString() + ")";
			
			
			return left + " * " + right;
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
			Expression diffLeft = this.left.differentiate(variable);
			Expression diffRight = this.right.differentiate(variable);
			
			return new Addition(new Multiplication(this.right, diffLeft), new Multiplication(this.left, diffRight));
		}
		
		public Expression addExpr(Expression otherExpr) {
			if(otherExpr.equals(new Value(0)))
			{
				return this;
			}
			
			if(this.equals(otherExpr))
			{
//				Expression newLeft = this.left.multiplyExpr(new Value(2));
//				Expression newRight = this.right.multiplyExpr(new Value(2));
				
				//return new Addition(newLeft, newRight);
				return this.multiplyExpr(new Value(2));
			}
//			if(this.left.equals(otherExpr))
//			{
//				return new Addition(this.left.multiplyExpr(new Value(2)) , this.right);
//			}
//			
//			if(this.right.equals(otherExpr))
//			{
//				return new Addition(this.right , this.right.multiplyExpr(new Value(2)) );
//			}
			
			return new Addition(this, otherExpr);
		}
		
		
		@Override
		public Expression addConstant(double num)
		{
			assert num >= 0 && Double.isFinite(num);
			Value zero = new Value(0);
			
			if(new Value(num).equals(zero))
			{
				return this;
			}
			
			return new Addition(this, new Value(num));
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
			System.out.println("In multiplication substitute");
			System.out.println("this.left" +this.left);
			System.out.println("this.right" +this.right);
			return this.left.substitute(env).multiplyExpr(this.right.substitute(env));
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
			
			return new Multiplication(this, new Value(num));
		}
}
