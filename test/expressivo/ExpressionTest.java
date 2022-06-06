/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Expression
    //Test for Variant.toString()
    //for Value
    @Test
    public void testToString_Value() {
    	Value v1 = new Value(0);
    	Value v2 = new Value(2.0);
    	Value v3 = new Value(3.123456);
    	Value v4 = new Value(0.0000009);
    	
    	assertEquals("checking numbers to five decimal place", v1.toString(), v4.toString());
    	assertEquals("Displaying numbers correctly", "2", v2.toString());
    	assertEquals("roundeing to 5 decimalplace down", "3.12345", v3.toString());
    }
    
    //for Variable
    @Test
    public void testToString_Variable() {
    	Variable a = new Variable("Foo");
    	
    	assertEquals("expected variable name there", "Foo", a.toString());
    	
    }
    
    
    //for Addition
    @Test
    public void testToString_Addition() {
    	Addition add = new Addition(new Variable("x"), new Value(2.000003));
    	String expectedString = "x + 2";
    	
    	assertEquals("expected addition Expression", expectedString, add.toString());
    }
    
    //for Multiplication
    @Test
    public void testToString_Multiplication() {
    	Multiplication multiExp_twoVals = new Multiplication(new Variable("x"), new Value(2.0));
    	String expectedString = "(x) * (2)";
    	
    	assertEquals("expected multiplication Expression", expectedString, multiExp_twoVals.toString());
    	
    	Multiplication multiExp_threeVals = new Multiplication(new Variable("x"), new Multiplication(new Value(2.0), new Variable("y")));
    	String expectedString2 = "(x) * ((2) * (y))";
    	assertEquals("expected multiplication Expression with three values", expectedString2, multiExp_threeVals.toString());
    	
    	
    	Multiplication multiExp_threeValsWithOneAddition = new Multiplication(new Variable("x"), new Addition(new Value(2.0), new Variable("y")));
    	String expectedString3 = "(x) * (2 + y)";
    	assertEquals("expected multiplication Expression with three values And Addition", expectedString3, multiExp_threeValsWithOneAddition.toString());
    }
    
    /*Tests for equals*/
    //for Variable
    @Test
    public void testEquals_Variable() {
    	Variable v1 = new Variable("FooBar");
    	Variable v2 = new Variable("FooBar");
    	
    	assertEquals("Same VAriable test", v2, v1);
    	assertTrue("expected variables are the same", v1.equals(v2));
    	assertEquals(v1.hashCode(), v2.hashCode());
    }
    
    //for Value
    @Test
    public void testEquals_Value() {
    	Value v1 = new Value(2.0);
    	Value v2 = new Value(2.000008);
    	Value v3 = new Value(2.0456);
    	Value v4 = new Value(2);
    	
    	assertEquals("Same values Test", v1, v2);
    	assertTrue("Expected Values to be same", v1.equals(v2));
    	assertFalse("Expected Values to be different", v1.equals(v3));
    	//assertEquals("Expected hashCode to be the same", v1.hashCode(), v2.hashCode());
    	//assertNotEquals("Expected hashCodes are different", v1.hashCode(), v3.hashCode());
    	assertEquals("Same hashCodes", v1.hashCode(), v4.hashCode());
    }
    
    /*Test for expressions*/
    @Test
    public void testExpressionNumAndVariable() {
    	String test = "x + 1";
    	Expression result = Expression.parse(test);
    	
    	assertEquals("x + 1 return as x + 1", new Addition(new Variable("x"), new Value(1)), result );
    	
    }
    
    @Test
    public void testExpressionSumOfProducts() {
    	String test = "(3)*(x) + y *(y)";
    	Expression result = Expression.parse(test);
    	
    	assertEquals("3 * x +y*y as 3 * x +y*y", new Addition(new Multiplication(new Value(3), new Variable("x")), new Multiplication(new Variable("y") , new Variable("y"))), result);
    }
    
    @Test
    public void testExpressionProductsofSums() {
    	String test = "(4+x)*(6+x)";
    	Expression result = Expression.parse(test);
    	
    	assertEquals("product of sums parsing", new Multiplication(new Addition(new Value(4), new Variable("x")), new Addition(new Value(6), new Variable("x"))).toString(), result.toString());
    		
    }
    
    @Test
    //for Value
    public void testdifferentiateValue() {
    	String test = "1";
    	Expression result = Expression.parse(test);
    	Expression differentiatedString = result.differentiate("x");
    	assertEquals("0", differentiatedString.toString());
    	
    }
    
    //for Variable 
    @Test
    public void testdDifferentiateVariable() {
    	String test = "x";
    	Expression result = Expression.parse(test);
    	
    	assertEquals(new Value(1), result.differentiate("x"));
    }
    
    //for Addition
    @Test
    public void testDifferentiateAddition() {
    	String test = "x + 1";
    	Expression result = Expression.parse(test);
    	
    	assertEquals(new Value(1), result.differentiate("x"));
   }
    
    //for Multiplication
    @Test
    public void testDifferentiateMultiplication() {
    	String test = "x*x";
    	Expression result = Expression.parse(test);
    	
    	assertEquals(new Addition(new Multiplication(new Variable("x"), new Value(1)),new Multiplication( new Variable("x"), new Value(1))), result.differentiate("x"));
    }
    
    @Test
    public void testaddExpr() {
    	String test = "x * 8";
    	Expression expr = Expression.parse(test);
    	String addedExpr = "x";
    	
    	String result  = "(x * 8) + x";
    	
    	assertEquals(expr.addExpr(Expression.parse(addedExpr)), Expression.parse(result));
    	assertEquals(new Addition(new Multiplication(new Variable("x"), new Value(8)), new Variable("x")),expr.addExpr(Expression.parse(addedExpr)) );
    }
    
    @Test
    public void testaddExprSum() {
    	String test = "x + 8";
    	Expression expr = Expression.parse(test);
    	Expression addedExpr = Expression.parse("x");
    	
    	String result = "x + x + 8";
    	
    	assertEquals(new Addition(new Addition(new Variable("x"), new Value(8)), new Variable("x")), expr.addExpr(addedExpr));
    	
    }
    @Test 
    public void testMultiplyExpr() {
    	String test = "x + 8";
    	Expression expr = Expression.parse(test);
    	
    	Expression addedExpr = Expression.parse("x");
    	
    	assertTrue(expr instanceof Addition);
    	assertEquals(Expression.parse("x * x + 8 "), expr.multiplyExpr(addedExpr));
    }
    
 //   @Test
//    public void testSimplify() {
//    	String test = "x";
//    	Expression expr = Expression.parse
//    }
}
