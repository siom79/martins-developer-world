grammar Robot;

instructions:
    (instruction ';')+
    EOF;

instruction:
    instructionDelay |
    instructionMouseMove |
    instructionCreateScreenCapture |
    instructionMouseClick |
    instructionKeyboardInput;

instructionDelay:
    'delay' paramMs=INTEGER;
instructionMouseMove:
    'mouseMove' x=INTEGER ',' y=INTEGER;
instructionCreateScreenCapture:
    'createScreenCapture' x=INTEGER ',' y=INTEGER ',' w=INTEGER ',' h=INTEGER  'file=' file=FILENAME;
instructionMouseClick:
    'mouseClick' button=buttonRule;
instructionKeyboardInput:
    'keyboardInput' input=STRING;

buttonRule:
    'button1' | 'button2' | 'button3';

INTEGER:
    [0-9]+;
WS:
    [ \t\r\n\u000C]+ -> skip;
COMMENT:
    '/*' .*? '*/' -> skip;
LINE_COMMENT:
    '//' ~[\r\n]* -> skip;
STRING:
    '"' ( '\\"' | . )*? '"';
FILENAME:
    FileNameChar+;
fragment FileNameChar:
    [a-zA-Z0-9/\\:_-$~.];