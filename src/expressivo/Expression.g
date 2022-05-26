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
    expr ::= product | sum ;
    sum ::= primitive (add primitive)* (multi number)*;
    product ::= primitive (multi primitive)* (add number)*;
    primitive ::= variable | number | '(' sum ')' | '(' product ')' ;
}
whitespace ::= [ \t\r\n];
number ::= [0-9]+('.'[0-9]+)*;
variable ::= [a-zA-Z]+;
add ::= '+';
multi ::= '*';


