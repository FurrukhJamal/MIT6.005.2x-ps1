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
    public void testExpression() {
    	
    }
}
