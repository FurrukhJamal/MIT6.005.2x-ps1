package expressivo;

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
}
