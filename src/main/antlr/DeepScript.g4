grammar DeepScript;

chunk
    : NEWLINE* block NEWLINE* EOF
    ;

block
    : (statementOrExpression)* NEWLINE* returnStatement? NEWLINE*
    ;

statementOrExpression
    : (statement NEWLINE*) | (expression NEWLINE*)
    ;

statement
    : variableAssignment
    | breakStatement
    | whileLoop
    | repeatLoop
    | forLoop
    | foreachLoop
    | condition
    | functionDeclaration
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

whileLoop
    : 'while' expression 'do' NEWLINE* block 'end'
    ;

repeatLoop
    : 'repeat' NEWLINE* block 'until' expression
    ;

forLoop
    : 'for' NAME '=' expression ',' expression (',' expression)? 'do' NEWLINE* block 'end'
    ;

foreachLoop
    : 'for' NAME 'in' expression 'do' NEWLINE* block 'end'
    ;

returnStatement
    : 'return' expressionList?
    ;

breakStatement
    : 'break'
    ;

variableAssignment
    : var_ '=' expression
    ;

functionDeclaration
    : 'function' NAME funcbody
    ;

nameList
    : functionNameField (',' functionNameField)*
    ;

functionNameField
    : NAME ('=' expression)?
    ;

namedExpressionList
    : namedExpression (',' namedExpression)*
    ;

namedExpression
    : ((NAME '=' ) ? expression)
    ;

expressionList
    : expression (',' expression)*
    ;

expression
    : string                                                #stringExpression // do not parse here
    | number                                                #numberExpression // do not parse here
    | bool                                                  #boolExpression // do not parse here
    | prefixexp                                             #prefixexpExpression // do not parse here
    | functionCall                                          #fc
    | tableconstructor                                      #tableconstructorExpression // do not parse here
    | <assoc=right> expression operatorPower expression     #operatorPowerExpression
    | operatorUnary expression                              #operatorUnaryExpression
    | expression operatorMulDivMod expression               #operatorMulDivModExpression
    | expression operatorAddSub expression                  #operatorAddSubExpression
    | expression operatorComparison expression              #operatorComparisonExpression
    | expression operatorAnd expression                     #operatorAndExpression
    | expression operatorOr expression                      #operatorOrExpression
    | expression operatorBitwise expression                 #operatorBitwiseExpression
    | 'null'                                                #nullExpression
    ;

prefixexp
    : varOrExp args*
    ;

functionCall
    : varOrExp args+
    ;

varOrExp
    : var_ | '(' expression ')'
    ;

var_
    : (NAME | '(' expression ')' varSuffix) varSuffix*
    ;

varSuffix
    : args* ('[' expression ']' | '.' NAME)
    ;

args
    : '(' namedExpressionList? ')'
    ;

funcbody
    : '(' nameList? ')' NEWLINE* block 'end'
    ;

tableconstructor
    : '{' fieldlist? '}'
    ;

fieldlist
    : expression (',' expression)*
    ;

operatorOr
	: 'or';

operatorAnd
	: 'and';

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
    : '[' NESTED_STR ']'
    ;

fragment
NESTED_STR
    : '[' .*? ']'
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
COMMENT
    : '--[' NESTED_STR ']' -> channel(HIDDEN)
    ;
LINE_COMMENT
    : '--'
    (                                               // --
    | '[' '='*                                      // --[==
    | '[' '='* ~('='|'['|'\r'|'\n') ~('\r'|'\n')*   // --[==AA
    | ~('['|'\r'|'\n') ~('\r'|'\n')*                // --AAA
    ) ('\r\n'|'\r'|'\n'|EOF)
    -> channel(HIDDEN)
    ;
WS
    : [ \t\u000C]+ -> skip
    ;


NEWLINE : [\r\n] ;
