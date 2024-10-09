grammar cc;

// grammatik  parser

start : hardwareResult inputsResult outputsResult latchesResult defResult* updatesResult simInputsResult EOF;

hardwareResult : h='hardware' COLON x=IDENT                          #hardwareRes;
inputsResult   : i='inputs' COLON x=signalList                         #inputsRes;
outputsResult : o='outputs' COLON x=signalList                         #outputsRes;   
latchesResult : l='latches' COLON x=signalList                         #latchesRes;         
updatesResult : u='updates' COLON (IDENT EQUAL exp)*                 #updatesRes;
defResult : d='def' COLON (x=IDENT'(' signalList ')' EQUAL exp)*     #defRes;
simInputsResult: s='siminputs' COLON (IDENT EQUAL NUMBER)*           #simInputsRes;    
signalList : (x=IDENT (','IDENT)*)*                                #signalListRes;


exp : NOT exp                            #NOT 
     |x=IDENT e1=exps                    #FUNCTION
     |e1=exp AND e2=exp                  #AND
     |e1=exp e2=exp                      #AND
     |e1=exp OR e2=exp                   #OR
     |'(' e1=exp ')'                     #PARENTHESES
     | y=IDENTAPOSTROPHE                 #IDENTAPOSTROPHE
     | x=IDENT                           #IDENT
     ;


exps : '('exp (','exp)* ')' #EXPRESSIONS;
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