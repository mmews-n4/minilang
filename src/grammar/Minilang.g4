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
program      : stmt+ EOF ;

stmt         : varDecl
             | outputStmt
             | printStmt
             ;

varDecl      : VAR identifier ASSIGN expr ;

outputStmt   : OUT expr ;

printStmt    : PRINT STRING ;

expr         : expr op=POWER expr
             | expr op=MULTIPLY expr
             | expr op=DIVIDE expr
             | expr op=PLUS expr
             | expr op=MINUS expr
             | LPAREN expr RPAREN
             | sequence
             | mapExpr
             | reduceExpr
             | IDENTIFIER
             | NUMBER
             ;

sequence         : LBRACE expr COMMA expr RBRACE ;

mapExpr          : MAP LPAREN expr COMMA identifier ARROW expr RPAREN ;

reduceExpr       : REDUCE LPAREN expr COMMA expr COMMA identifier identifier ARROW expr RPAREN ;

identifier       : IDENTIFIER ;

