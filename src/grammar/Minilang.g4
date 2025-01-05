grammar Minilang;

// Lexer rules
// Operators
PLUS         : '+' ;
MINUS        : '-' ;
MULTIPLY     : '*' ;
DIVIDE       : '/' ;
POWER        : '^' ;
ASSIGN       : '=' ;
COMMA        : ',' ;
LBRACE       : '{' ;
RBRACE       : '}' ;
LPAREN       : '(' ;
RPAREN       : ')' ;
ARROW        : '->' ;

// Keywords
VAR          : 'var' ;
OUT          : 'out' ;
PRINT        : 'print' ;
MAP          : 'map' ;
REDUCE       : 'reduce' ;


NUMBER       : '-'?[0-9]+ ('.' [0-9]+)? ;
IDENTIFIER   : [a-zA-Z_][a-zA-Z0-9_]* ;
STRING       : '"' (~'"')* '"' ;

WS           : [ \t\r\n]+ -> skip ;



// Parser rules
program      : stmt+ ;

stmt         : varDecl
             | outputStmt
             | printStmt
             ;

varDecl      : VAR IDENTIFIER ASSIGN expr ;

outputStmt   : OUT expr ;

printStmt    : PRINT STRING ;

expr         : expr op=PLUS expr
             | expr op=MINUS expr
             | expr op=MULTIPLY expr
             | expr op=DIVIDE expr
             | expr op=POWER expr
             | LPAREN expr RPAREN
             | IDENTIFIER
             | sequence
             | NUMBER
             | mapExpr
             | reduceExpr
             ;

sequence     : LBRACE expr COMMA expr RBRACE ;

mapExpr      : MAP LPAREN expr COMMA IDENTIFIER ARROW expr RPAREN ;

reduceExpr   : REDUCE LPAREN expr COMMA expr COMMA IDENTIFIER IDENTIFIER ARROW expr RPAREN ;
