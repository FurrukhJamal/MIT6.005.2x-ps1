/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". Below, "root" is the start rule.
 *
 * For more information, see the parsers reading.
 */
@skip whitespace{
    root ::= expr;
    expr ::= sum;
    sum ::= product ( add  product)*  ;
    product ::=  primitive (multi primitive)*;
    primitive ::= variable | number | '(' expr ')' ;
    single ::= (add primitive multi); 
}
whitespace ::= [ \t\r\n];
number ::= [0-9]+('.'[0-9]+)*;
variable ::= [a-zA-Z]+;
add ::= '+';
multi ::= '*';


