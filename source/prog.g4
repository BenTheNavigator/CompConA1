grammar prog;

start : ('hardware' h = hardwareresult | COLON | AND | OR | NOT | EQUAL | NUMBER)+ EOF;
hardwareresult : IDENT;

AND: '*';
OR: '+';
NOT: '/';
NUMBER: [0-1]+;
COLON: ':';
EQUAL: '=';

IDENT: [a-zA-Z_][a-zA-Z0-9_]*;


WHITESPACES: [ \t\r\n]+ -> skip;

COMMENT: '//' ~[\r\n\t]* -> skip;