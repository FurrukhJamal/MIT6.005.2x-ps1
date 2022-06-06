/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Commands.differentiate() and Commands.simplify()
    @Test
    public void testSimplifyOneVariable() {
    	String test = "x";
    	Expression expr = Expression.parse(test);
    	
    	Map<String,Double> env = new HashMap<>();
    	env.put("x", 10.0);
    	env.put("y", 100.0);
    	
    	String simplified = Commands.simplify(test, env);
    	
    	assertEquals("expected expression in string format", "10", simplified);
    	   			
    }
    
    @Test
    public void testSimplifiyAddition() {
    	String test = "x + 1";
    	Expression expr = Expression.parse(test);
    	
    	Map<String,Double> env = new HashMap<>();
    	env.put("x", 10.0);
    	env.put("y", 100.0);
    	
    	//System.out.println("Expression in test in string " +expr.toString());
    	//System.out.println(expr instanceof Addition);
    	String simplified = Commands.simplify(expr.toString(), env);
    	assertEquals("expected expression in string format", new Value(11).toString(), simplified);
    }
    
    @Test
    public void testsimplifyMultiplication() {
    	String test = "x * x";
    	Expression expr = Expression.parse(test);
    	
    	Map<String,Double> env = new HashMap<>();
    	env.put("x", 10.0);
    	env.put("y", 100.0);
    	
    	String simplified = Commands.simplify(expr.toString(), env);
    	assertEquals("expected expression for multiplication test", new Value(100).toString(),simplified);
    }
    
    @Test
    public void testsimplifyMultplication3x3y()
    {
    	String test = "x * x * x + y * y * y";
    	Expression expr = Expression.parse(test);
    	
    	Map<String,Double> env = new HashMap<>();
    	env.put("x", 10.0);
    	env.put("z", 100.0);
    	
    	String simplified = Commands.simplify(expr.toString(), env);
    	assertEquals("expected expression for x*x*x+y*y*y", new Addition(
    			new Multiplication(new Multiplication(new Variable("y"), new Variable("y")),new Variable("y")),
    			new Value(1000)
    			).toString(), simplified);
    }
}
