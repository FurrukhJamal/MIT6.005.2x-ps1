package expressivo;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import lib6005.parser.*;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
	enum ExpressionGrammar {ROOT, SUM, PRIMITIVE, NUMBER, WHITESPACE, PRODUCT, VARIABLE, ADD, MULTI, EXPR, SINGLE};
    
    // Datatype definition
    //   TODO
	/**Expression = Value(num : double)
	 * 				+ Variable(name : String)
	 * 				+ Addition(left : Expression , right : Expression)
	 * 				+ Muliplication(left : Expression , right : Expression)
	 */
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        //throw new RuntimeException("unimplemented");
    	try {
			Parser<ExpressionGrammar> parser = GrammarCompiler.compile(new File("src/expressivo/Expression.g"), ExpressionGrammar.ROOT);
			ParseTree<ExpressionGrammar> tree = parser.parse(input);
			//tree.display();
			//System.out.println(tree.getContents());
			Variable test = new Variable("aaaa");
			return buildAST(tree);
			
		} catch (UnableToParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		//return null;
    }
    
    /*Helper function for parse to build an abstract syntax tree*/
    public static Expression buildAST(ParseTree<ExpressionGrammar> p) {
    	//System.out.println(" name of node at the start of function:" +p.getName());
    	List<ParseTree<ExpressionGrammar>> childrenOfRoot = p.children();
    	//System.out.println("Children of " +p.getName() + ": " +childrenOfRoot);
    	//System.out.println("Size of childen of " + p.getName() + ":" + childrenOfRoot.size());
    	//System.out.println("Name of children of root" +childrenOfRoot.get(0).getName());
    	List<ParseTree<ExpressionGrammar>> childrenByName = p.childrenByName(ExpressionGrammar.PRODUCT);
    	//System.out.println("List formed by childrenByNAme:" +childrenByName);
    	
    	switch (p.getName()) {
    		case ROOT:
    			return buildAST(p.childrenByName(ExpressionGrammar.EXPR).get(0));
    			
    		case EXPR : 
    			//System.out.println("children in EXpr: " +p.children());
    			//System.out.println("Product in EXPR:" +p.childrenByName(ExpressionGrammar.PRODUCT).get(0));
    			//System.out.println("ADD in EXPR: " +p.childrenByName(ExpressionGrammar.SUM).get(0));
    			return buildAST(p.children().get(0));
    		
    		case SUM:{
    			
    			boolean first = true;
                Expression result = null;
                
                for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRODUCT)){                
                    if(first){
                        result = buildAST(child);
                        first = false;
                    }else{
                        result = new Addition(result, buildAST(child));
                    }
                }
                if (first) {
                    throw new RuntimeException("sum must have a non whitespace child:" + p);
                }
                return result;
    		}
    		
    		case PRODUCT:
    		{
    			boolean first = true;
                Expression result = null;
                
                if(p.childrenByName(ExpressionGrammar.PRIMITIVE).size() == 1)
                {
                	return buildAST(p.childrenByName(ExpressionGrammar.PRIMITIVE).get(0));
                }
                
                for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRIMITIVE)){                
                    if(first){
                        result = buildAST(child);
                        first = false;
                    }else{
                        result = new Multiplication(result, buildAST(child));
                    }
                }
                if (first) {
                    throw new RuntimeException("sum must have a non whitespace child:" + p);
                }
                return result;
    		}
    			    			
    		case NUMBER:
    			//System.out.println(p.getContents() + " created");
    			return new Value(Double.parseDouble(p.getContents()));
    			
    		case PRIMITIVE:
    		{
    			if(p.childrenByName(ExpressionGrammar.EXPR).size() == 1)
    			{
    				return buildAST(p.childrenByName(ExpressionGrammar.EXPR).get(0));
    			}
    			else if(p.childrenByName(ExpressionGrammar.VARIABLE).size() == 1)
    			{
    				return buildAST(p.childrenByName(ExpressionGrammar.VARIABLE).get(0));
    			}
    			else
    			{
    				return buildAST(p.childrenByName(ExpressionGrammar.NUMBER).get(0));
    			}
    		}
    		
    		
    		case VARIABLE:
    			return new Variable(p.getContents());
    			
    		case WHITESPACE:
    			throw new RuntimeException("You should never reach here:" + p);
    		
    			
    			
    			
    			
//    		case NUMBER : 
//    			System.out.println("number \"" + p.getContents() + "\" created" );
//    			return new Value(Double.parseDouble(p.getContents()));
//    			
//    		case ROOT:
//    			//p.display();
//    			//root will have either a sum or a product
//    			List<ParseTree<ExpressionGrammar>> childOfRootSUM = p.childrenByName(ExpressionGrammar.SUM);
//    			System.out.println("childOfRootSUM in Root case" +childOfRootSUM);
//    			System.out.println("is childOfRootSUM empty: " + childOfRootSUM.isEmpty());
//    			
//    			if(childOfRootSUM.isEmpty()) {
//    				//its a product
//    				return buildAST(p.childrenByName(ExpressionGrammar.PRODUCT).get(0));
//    			}
//    			else {
//    				//is a sum
//    				return buildAST(p.childrenByName(ExpressionGrammar.SUM).get(0));
//    			}
//    		
//    		case SUM:
//    			boolean first = true;
//    			Expression result = null;
//    			
//    			for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRIMITIVE)) {
//    				if(first) {
//    					result = buildAST(child);
//    					first = false;
//    				}
//    				else {
//    					result = new Addition(result, buildAST(child));
//    				}
//    			}
//    			
//    			if(first)
//    			{
//    				throw new RuntimeException("sum must have a non whitespace child:" + p);
//    			}
//    			return result; 
//    			
//    		case PRIMITIVE:
//    			//Primitive will have either a number, a variable, sum or a product.
//    			System.out.println("Inside case PRIMITIVE");
//    			System.out.println("children of primitive in Case primitive" +p.children());
//    			if(p.childrenByName(ExpressionGrammar.NUMBER).isEmpty() && p.childrenByName(ExpressionGrammar.VARIABLE).isEmpty() && p.childrenByName(ExpressionGrammar.SUM).isEmpty())
//    			{
//    				//Product
//    				System.out.println("inside if for product in PRIMITIVE case");
//    				return buildAST(p.childrenByName(ExpressionGrammar.PRODUCT).get(0));
//    			}
//    			else if(p.childrenByName(ExpressionGrammar.NUMBER).isEmpty() && p.childrenByName(ExpressionGrammar.VARIABLE).isEmpty() && p.childrenByName(ExpressionGrammar.PRODUCT).isEmpty())
//    			{
//    				//SUM
//    				System.out.println("inside if for sum in PRIMITIVE case");
//    				return buildAST(p.childrenByName(ExpressionGrammar.SUM).get(0));
//    			}
//    			else if(p.childrenByName(ExpressionGrammar.NUMBER).isEmpty())
//    			{
//    				//Variable
//    				System.out.println("inside if for variable in PRIMITIVE case");
//    				return buildAST(p.childrenByName(ExpressionGrammar.VARIABLE).get(0));    				
//    			}
//    			else 
//    			{
//    				//Number
//    				System.out.println("inside if for number in PRIMITIVE case");
//    				return buildAST(p.childrenByName(ExpressionGrammar.NUMBER).get(0));
//    			}	
//    			
//    		case PRODUCT:
//    			/**Product would have Primitive as child so either return a multiplication(val one, val two)
//    			 * or just a single number by taking that child and calling buildAST recurrsively to get it to tuhe number child*/
//    			boolean firstProduct = true;
//    			Expression resultProduct = null;
//    			Integer counter = 0;
//    			System.out.println("p.getContents in case PRODUCT: " +p.getContents());
//    			List<ParseTree<ExpressionGrammar>> childrenOfProduct = p.childrenByName(ExpressionGrammar.PRIMITIVE);
//    			System.out.println("Number of PRIMITIVE child in product " +childrenOfProduct);
//    			List<ParseTree<ExpressionGrammar>> SignsForProduct = p.childrenByName(ExpressionGrammar.ADD);
//    			System.out.println("Number of ADD children in Product case: " +SignsForProduct.size());
//    			
//    			int testFlag = 2;
//    			for(ParseTree<ExpressionGrammar> child : p.childrenByName(ExpressionGrammar.PRIMITIVE))
//    			{
//    				if(firstProduct)
//    				{
//    					resultProduct = buildAST(child);
//        				firstProduct = false;
//        				counter += 1;
//    				}
//    				else
//    				{
//    					if(counter < 2)
//    					{
//    						resultProduct = new Multiplication(resultProduct, buildAST(child));
//        					counter += 1;
//    					}
//    					else
//    					{
//    						System.out.println("in else condition counter > 2");
//    						if(p.childrenByName(ExpressionGrammar.ADD).size() == 0)
//    						{
//    							
//    							resultProduct = new Multiplication(resultProduct, buildAST(child));
//            					counter += 1;
//    						}
//    						else
//    						{
//    							int counterForAllChildren = 0;
//    							System.out.println("Children before loop:" +p.children());
//    							
//    							for(ParseTree<ExpressionGrammar> eachchild : p.children())
//    							{
//    								
//    								System.out.println("Name: "+eachchild.getName());
//    								System.out.println("testFlag: " +testFlag);
//    								System.out.println("counterForAllChildren: " +counterForAllChildren);
//    								if(eachchild.getName().toString() == "ADD" && counterForAllChildren >= testFlag)
//    								{
//    									
//    									System.out.println("inside ADD clause");
//    									if(p.children().get(counterForAllChildren + 1).getName().toString() != "WHITESPACE")
//    									{
//    										resultProduct = new Addition(resultProduct, buildAST(p.children().get(counterForAllChildren + 1)));
//        									counter +=1;
//    									}
//    									else  
//    									{
//    										//TO skip if next sibling in tree is a WhiteSPace or sequences of White spaces
//    										for (int i = counterForAllChildren + 1; i < p.children().size(); i++)
//    										{
//    											System.out.println("Debug in For loop to skip whitespace: Name: " +p.children().get(i).getName().toString());
//    											if(p.children().get(i).getName().toString() == "WHITESPACE")
//    											{
//    												continue;
//    											}
//    											else
//    											{
//    												resultProduct = new Addition(resultProduct, buildAST(p.children().get(i)));
//    	        									counter +=1;
//    	        								}
//    										}
//    									}
//    									
//    									
//    									testFlag = counterForAllChildren + 1;
//    									
//    									
//    									
//    									
//    									
//    									break;
//    								}
//    								else if(eachchild.getName().toString() == "MULTI" && counterForAllChildren >= testFlag && eachchild.getName().toString() != "WHITESPACE")
//    								{
//    									
//    									
//    									
//    									System.out.println("inside Multi clause");
//    									resultProduct = new Multiplication(resultProduct, buildAST(p.children().get(counterForAllChildren + 1)));
//    									counter +=1;
//    									
//    									
//    									testFlag = counterForAllChildren + 1;
//    									
//    									
//    									
//    								}
//    								counterForAllChildren +=1;
//    								//testFlag += 1;
//    							}
//    						}
//    					}
//    					
//    					
//    				}
//    				
//    			}
//    			
//    			if(firstProduct)
//    			{
//    				throw new RuntimeException("product must have a non whitespace child:" + p);
//    			}
//    			
//    			return resultProduct;
//    			
//    		case VARIABLE:
//    			return new Variable(p.getContents());
//    			
//    		case WHITESPACE:
//    			throw new RuntimeException("You should never reach here:" + p);
    		
    	}
		//System.out.println("Hitting null in AST");
    	return null;
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    
    
    /*
     * @param
     * String representation of an expression
     * @return an differentiated Expression where a Variable is converted to 1 and an Value to zero and subsequent 
     * multiplication and additions call this function recursively*/
    	
    public Expression differentiate(String variable);
    
    
    /*
     * @param and Expression to be added to this expression
     * x => x + new expression*/
    public Expression addExpr(Expression otherExpr);

	/*@param
	 * double value of what is to be added
	 * @returns expression such that 1 + new Val or variable + variable or recursive addition or multplication for subsequent types*/
    public Expression addConstant(double num);
    
    
    
    public Expression multiplyExpr(Expression factorToMultiplyWith);
    
    
    public Expression addVariable(String name);

	//public Expression addExpr(Value otherExpr);
    
    
    /*
     * @param takes in a mapping for all the values for variables in the envoirnment.
     * The variables are case sensitive and the envoirnment must not be empty null and assumes if the key is there in enovirnment it wont be empty
     * @returns and simplfied expression*/
    public Expression substitute(Map<String, Double> envoirnment);
    
    
    
    public Expression addCoefficient(double num);

	
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
