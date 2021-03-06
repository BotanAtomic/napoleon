grammar Napoleon;

chunk
    : NEWLINE* block NEWLINE* EOF
    ;

block
    : statementGroup? NEWLINE* returnStatement? NEWLINE*
    ;

returnStatement
    : 'return' expressionList?
    ;

statementGroup:
    statementOrExpression (NEWLINE+ statementOrExpression)*
    ;

statementOrExpression
    : statement | expression
    ;

statement
    : variableAssignment
    | variableOperatorAssignment
    | deleteVar
    | breakStatement
    | whileLoop
    | forLoop
    | foreachLoop
    | condition
    | functionDeclaration
    ;


variableAssignment
    : staticVariable? var_ '=' expression
    ;

variableOperatorAssignment
    : var_ operatorAssignment expression
    ;

deleteVar
    : 'delete' (NAME varSuffix+)
    ;

breakStatement
    : 'break'
    ;


whileLoop
    : 'while' expression 'do' NEWLINE* block 'end'
    ;

forLoop
    : 'for' NAME '=' expression ',' expression (',' expression)? 'do' NEWLINE* block 'end'
    ;

foreachLoop
    : 'for' NAME 'in' expression 'do' NEWLINE* block 'end'
    ;


condition
    : 'if' expression 'then' NEWLINE* block elseifCondition* elseCondition? 'end'
    ;

elseifCondition
    : 'elseif' expression 'then' NEWLINE* block
    ;

elseCondition
    : 'else' NEWLINE* block
    ;


functionDeclaration
    : 'function' NAME funcbody
    ;


funcbody
    : '(' nameList? ')' NEWLINE* block NEWLINE* 'end'
    ;


nameList
    : functionNameField (',' functionNameField)*
    ;

functionNameField
    : NAME ('=' expression)?
    ;

expressionList
    : expression (',' expression)*
    ;

expression
    : string varSuffix* args*                               #stringExpression // do not parse here
    | number                                                #numberExpression // do not parse here
    | bool                                                  #boolExpression // do not parse here
    | prefixexp                                             #prefixexpExpression // do not parse here
    | functionCall                                          #fc
    | tableconstructor varSuffix* args*                     #tableconstructorExpression // do not parse here
    | <assoc=right> expression operatorPower expression     #operatorPowerExpression
    | operatorUnary expression                              #operatorUnaryExpression
    | expression operatorMulDivMod expression               #operatorMulDivModExpression
    | expression operatorAddSub expression                  #operatorAddSubExpression
    | expression operatorComparison expression              #operatorComparisonExpression
    | expression operatorAnd expression                     #operatorAndExpression
    | expression operatorOr expression                      #operatorOrExpression
    | expression operatorBitwise expression                 #operatorBitwiseExpression
    | 'null'                                                #nullExpression
    | jsonObject varSuffix* args*                           #jsonExpression // do not parse here
    | 'function' (NAME (',' NAME)*)? '->' expression        #lambdaExpression
    | '@' NAME '(' NEWLINE* mendatoryNamedExpressionList? NEWLINE* ')'        #strategyFunctionExpression
    ;

jsonPair
    : NEWLINE* (('[' expression ']') | NAME | string) ':' expression NEWLINE*
    ;

jsonObject
    : '{' NEWLINE* (jsonPair ( ',' jsonPair )*)? NEWLINE* '}'
    ;

prefixexp
    : varOrExp args*
    ;

functionCall
    : varOrExp args+
    ;

varOrExp
    : var_ | '(' NEWLINE* expression NEWLINE* ')'
    ;

var_
    : (NAME | '(' NEWLINE* expression NEWLINE* ')' varSuffix) varSuffix*
    ;

varSuffix
    : args* ('[' expression ']' | NEWLINE* '.' NAME)
    ;

mendatoryNamedExpressionList
    : NEWLINE* mendatoryNamedExpression (',' NEWLINE* mendatoryNamedExpression)*
    ;

mendatoryNamedExpression
    : NAME '='  expression
    ;

args
    : '(' NEWLINE* namedExpressionList? NEWLINE* ')'
    ;

namedExpressionList
    : NEWLINE* namedExpression ( ',' NEWLINE* namedExpression)*
    ;

namedExpression
    : ((NAME '=' ) ? expression)
    ;


tableconstructor
    : '[' fieldlist? ']'
    ;

fieldlist
    : NEWLINE* expression (',' NEWLINE* expression)* NEWLINE*
    ;

staticVariable
    : 'static';

operatorOr
	: 'or';

operatorAnd
	: 'and';

operatorAssignment
	: '+=' | '-=' | '/=' | '//=' | '*=';

operatorComparison
	: '<' | '>' | '<=' | '>=' | '!=' | '==';

operatorAddSub
	: '+' | '-';

operatorMulDivMod
	: '*' | '/' | '%' | '//';

operatorBitwise
	: '&' | '|' | '^' | '<<' | '>>';

operatorUnary
    : 'not' | '-' | '~';

operatorPower
    : '**';

number
    : INT | HEX | FLOAT
    ;

string
    : NORMALSTRING | CHARSTRING | LONGSTRING
    ;

bool
    : 'true' | 'false'
    ;

// LEXER

NAME
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

NORMALSTRING
    : '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

CHARSTRING
    : '\'' ( EscapeSequence | ~('\''|'\\') )* '\''
    ;

LONGSTRING
    : '""' NESTED_STR '""'
    ;

fragment
NESTED_STR
    : '"' .*? '"'
    ;

INT
    : Digit+
    ;

HEX
    : '0' [xX] HexDigit+
    ;

FLOAT
    : Digit+ '.' Digit* ExponentPart?
    | '.' Digit+ ExponentPart?
    | Digit+ ExponentPart
    ;

fragment
ExponentPart
    : [eE] [+-]? Digit+
    ;

fragment
HexExponentPart
    : [pP] [+-]? Digit+
    ;

fragment
EscapeSequence
    : '\\' [abfnrtvz"'\\]
    | '\\' '\r'? '\n'
    | DecimalEscape
    | HexEscape
    | UtfEscape
    ;
fragment
DecimalEscape
    : '\\' Digit
    | '\\' Digit Digit
    | '\\' [0-2] Digit Digit
    ;
fragment
HexEscape
    : '\\' 'x' HexDigit HexDigit
    ;
fragment
UtfEscape
    : '\\' 'u{' HexDigit+ '}'
    ;
fragment
Digit
    : [0-9]
    ;
fragment
HexDigit
    : [0-9a-fA-F]
    ;

LINE_COMMENT
    : '#' ~[\r\n\f]* -> skip
    ;
WS
    : [ \t\u000C]+ -> skip
    ;


NEWLINE : [\r\n] ;
