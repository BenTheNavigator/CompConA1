grammar cc;

start : hardwareResult inputsResult outputsResult latchesResult updatesResult simInputsResult EOF;

hardwareResult : 'hardware' COLON IDENT;

inputsResult : 'inputs' COLON signalList;

outputsResult : 'outputs' COLON signalList;

latchesResult : 'latches' COLON signalList;

updatesResult : 'updates' COLON updateList;
updateList : IDENT EQUAL expr+;
expr : NOT IDENT APOSTROPHE* (AND expr)*;

simInputsResult: 'siminputs' COLON simInputList;
simInputList: IDENT EQUAL NUMBER+;

signalList : IDENT (',' IDENT)*; 


AND: '*';
OR: '+';
NOT: '/';
NUMBER: [0-1]+;
APOSTROPHE: '\'';
COLON: ':';
EQUAL: '=';
IDENT: [a-zA-Z_][a-zA-Z0-9_]*;

WHITESPACES: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n\t]* -> skip;
