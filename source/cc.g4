grammar cc;

start : hardwareResult inputsResult outputsResult latchesResult defResult updatesResult simInputsResult EOF;

hardwareResult : 'hardware' COLON IDENT;
inputsResult : 'inputs' COLON signalList;
outputsResult : 'outputs' COLON signalList;
latchesResult : 'latches' COLON signalList;
defResult : 'def' COLON  defList;
updatesResult : 'updates' COLON updateList;
simInputsResult: 'siminputs' COLON simInputList;


defList :  IDENT '(' IDENT (',' IDENT)+ ')' EQUAL expr;
updateList : IDENT EQUAL expr;
simInputList: IDENT EQUAL NUMBER+;
signalList : IDENT (',' IDENT)*; 

expr : NOT expr
     | IDENT APOSTROPHE*
     | expr AND expr 
     | expr OR expr 
     | expr expr  
     | '(' expr ')'
     ;



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
