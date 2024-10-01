grammar cc;

// grammatik  parser

start : hardwareResult inputsResult outputsResult latchesResult defResult* updatesResult simInputsResult EOF;

hardwareResult : 'hardware' COLON IDENT;
inputsResult : 'inputs' COLON signalList;
outputsResult : 'outputs' COLON signalList;
latchesResult : 'latches' COLON signalList;
updatesResult : 'updates' COLON (IDENT EQUAL exp)*;
defResult : 'def' COLON (IDENT'(' signalList ')' EQUAL exp)*; 

simInputsResult: 'siminputs' COLON (IDENT EQUAL NUMBER)*;

signalList : (IDENT (IDENT)*)*; 


exp : NOT exp
     |IDENT'('exp')'
     |exp AND exp
     |exp exp
     |exp OR exp
     |'(' exp ')'
     | IDENTAPOSTROPHE
     | IDENT 
     ;


// TOKENS  lexer

IDENTAPOSTROPHE: IDENT APOSTROPHE;
NOT: '/';
AND: '*';
OR: '+';
NUMBER: [0-1]+;
APOSTROPHE: '\'';
COLON: ':';
EQUAL: '=';
IDENT: [a-zA-Z_][a-zA-Z0-9_]*;

WHITESPACES: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n\t]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip; 
COMMAS: ',' -> skip;