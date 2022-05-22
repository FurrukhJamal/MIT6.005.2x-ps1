package expressivo;

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
}
