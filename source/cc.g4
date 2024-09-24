grammar cc;

// grammatik  parser

start : hardwareResult inputsResult outputsResult latchesResult updatesResult simInputsResult EOF;

hardwareResult : 'hardware' COLON IDENT;

inputsResult : 'inputs' COLON signalList;

outputsResult : 'outputs' COLON signalList;

latchesResult : 'latches' COLON signalList;

updatesResult : 'updates' COLON IDENT EQUAL exp;

optDef : (IDENT'(' signalList ')' EQUAL exp)*;

exp : IDENT
     |NOT exp
     |exp AND exp
     |exp exp
     |exp OR exp
     |IDENT'('exp')'
     |'(' exp ')'
     ;

simInputsResult: 'siminputs' COLON IDENT EQUAL NUMBER;

signalList : IDENT (',' IDENT)*; 


// TOKENS  lexer

NOT: '/';
AND: '*';
OR: '+';
NUMBER: [0-1]+;
APOSTROPHE: '\'';
COLON: ':';
EQUAL: '=';
IDENT: [a-zA-Z_][a-zA-Z0-9_]* [']?;

WHITESPACES: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n\t]* -> skip;
