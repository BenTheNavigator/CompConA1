grammar cc;

// grammatik  parser

start : hardwareResult inputsResult outputsResult latchesResult defResult* updatesResult simInputsResult EOF;

hardwareResult : 'hardware' COLON IDENT                          # hardwareRes ;
inputsResult   : 'inputs' COLON signalList                       # inputsRes ;
outputsResult : 'outputs' COLON signalList                       # outputsRes ;   
latchesResult : 'latches' COLON signalList                       # latchesRes ;         
updatesResult : 'updates' COLON (IDENT EQUAL exp)*               # updatesRes ;
defResult : 'def' COLON (IDENT'(' signalList ')' EQUAL exp)*     # defRes ;

simInputsResult: 'siminputs' COLON (IDENT EQUAL NUMBER)*         # simInputsRes ;    

signalList : (IDENT (','IDENT)*)*                                # signalListRes ;


exp : NOT exp                      #NOT 
     |x=IDENT '('exp (','exp)* ')'   #FUNCTION
     |e1=exp AND e2=exp                  #AND
     |e1=exp e2=exp                      #AND
     |e1=exp OR e2=exp                   #OR
     |'(' e1=exp ')'                  #PARENTHESES
     | y=IDENTAPOSTROPHE             #IDENTAPOSTROPHE
     | x=IDENT                       #IDENT
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