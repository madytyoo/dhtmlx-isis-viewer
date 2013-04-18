/**
 * Dhtmlx Isis Viewer Grammar
 */
grammar dhtmlx;

options
{
  language = Java;
}

@header {
    package com.mylaensys.isis.dsl.dhtmlx;
    import  java.util.*;
}

@members {
    ParserHelper helper;
}

WS                      : [ \t\r\n]+ -> skip ;
LBRACKET                : '[';
RBRACKET                : ']';
LPAREN                  : '(';
RPAREN                  : ')';
INTEGER                 : ('0' | '1'..'9' '0'..'9'*);
SEMI                    : ':';
ROW                     : ';';
COL                     : ',';
DOT                     : '.';
LINK                    : '->';
LT                      : '<';
GT                      : '>';
SEPARATOR               : '|';
SPACER                  : '||';
SETTINGS                : 'default';
HEX                     : ('0'..'9'|'a'..'f'|'A'..'F') ;
ESCAPE                  : '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\') | UNI;
UNI                     : '\\' 'u' HEX HEX HEX HEX;
LETTER                  : '\u0024' |               // $
                          '\u0041'..'\u005a' |     // A-Z
                          '\u005f' |               // _
                          '\u0061'..'\u007a';      // a-z;

DIGIT                   :  '\u0030'..'\u0039';      // 0-9
STRING                  :  '"' ( ESCAPE | ~('\\'|'"') )* '"';

COMMENT                 :   '/*' .*? '*/' -> channel(HIDDEN);
ID                      :   ('_')? LETTER (LETTER|DIGIT)*;


dhtmlx                  :   (view | form);

view                    :   layout
                            | tabbar
                            | collection
                            | prompt
                            | form;


expression              :   property
                            | action
                            | expression '.' ID
                            | expression '.' 'self'
                            | expression '.' action;

property                :   ID
                            ;



action                  :   ID LPAREN RPAREN
                            ;

prompt                  :   (p='prompt') (LBRACKET RBRACKET)
                            { helper.addPrompt( $p ); }
                            ;

link                    :   (ID LINK);


/* DHTMLX specific */

/* dhtmlxLayout */

layout                  :   (pattern=('1C'|'2E'|'2U'|'3E'|'3W'|'3J'|'3T'|'3L'|'3U')
                            { helper.createLayout( $pattern ); }
                            (LBRACKET cells c=RBRACKET))
                            { helper.addContent( $c ); }
                            ;

cells                   :   (id)? (cell)*;

cell                    :   (name=('a'|'b'|'c') {  helper.createLayoutCell( $name );  }
                            (LBRACKET cellcontent c=RBRACKET))
                            { helper.addLayoutCell( $c ); }
                            ;

cellcontent                 :((width | height | text)*)?
                            (toolbar)?
                            (view)?
                            (status)? ;

/* dhtmlxToolbar &  dhtmlxStatusBar */

toolbar                 :   (t='@Top') { helper.createToolbar($t); }
                            (LBRACKET toolbarItems t=RBRACKET)
                            { helper.addToolbar( $t ); }
                            ;

toolbarItems            :   (toolbarItem) (',' (toolbarItem))*;

toolbarItem             :   toolbarButton
                            | toolbarText
                            | toolbarSeparator
                            | toolbarSpacer;

toolbarButton           :   (item=ID LPAREN RPAREN)  { helper.addToolbarItem( $item ); }
                            ;

toolbarText             :   item=STRING { helper.addToolbarItem( $item ); }
                            ;

toolbarSpacer           :   item=SPACER { helper.addToolbarItem( $item ); }
                            ;

toolbarSeparator        :   item=SEPARATOR { helper.addToolbarItem( $item ); }
                            ;

status                  :   (t='@Bottom') { helper.createStatusBar($t); }
                            (LBRACKET paging t=RBRACKET)
                            { helper.addStatusBar( $t ); };

paging                  :   ('1..N') | ('..') ;

/* collections */
collection              :   (expression)? LT  (grid | dataview) t=GT
                            { helper.addCollection( $t, $expression.text ); }
                            ;

grid                    :   t='\'' list=columns '\'' { helper.createGrid( $t, $list.names ); }
                            ((id)*)?
                            ;

columns                     returns [List<String> names]
                            @init { $names = new ArrayList<String>(); }:
                            ID { $names.add( $ID.text ); }
                            (',' (ID)  { $names.add( $ID.text ); } )*
                            ;

dataview                :   template=STRING { helper.createDataView( $template ); }
                            ((id | width | height)*)?
                            ;

/* dhtmlxTabbar */
tabbar                  :   ((t='Tab')   { helper.createTabbar( $t ); }
                            (LBRACKET tabs c=RBRACKET))
                            { helper.addContent( $c ); };

tabs                    :   ((id  | orientation | active)*)? (tab)*;

tab                     :   (name=ID) { helper.createTabCell( $name ); }
                            (LBRACKET tabcontent c=RBRACKET)
                            { helper.addTabCell( $c ); };

tabcontent              :   ((size)*)?
                            (toolbar)?
                            (view)?
                            (status)? ;

/* forms */
form                    :   (settings)? (group);

newcolumn               :   t=COL { helper.addNewColumn($t); };


group                   :   (block newcolumn?)* | widgetList
                            ;

block                   :   l=STRING? (t1=LBRACKET { helper.createBlock( $t1,$l ); }
                             group
                            t2=RBRACKET { helper.addBlock( $t2 ); }
                            );


widgetList              :   (widget newcolumn?)*;


widget                  :   collection
                            |(expression) {  helper.addFormWidget( $expression.start.getLine(),$expression.start.getCharPositionInLine(),$expression.text ); }
                            /*|(expression t=LT '\'' list=columns '\'' GT) { helper.addFormCollection( $t,$expression.text, $list.names ); }*/
                            ;



settings                :   ((s=SETTINGS) { helper.createSettings( $s ); }
                            ('{' ((position
                                        | labelWidth
                                        | labelHeight
                                        | inputWidth
                                        | inputHeight
                                        | labelAlign
                                        )*)? t='}' { helper.addSettings( $t ); }))
                            ;


attribute               :   id
                            | position
                            | labelWidth
                            | labelHeight
                            | inputWidth
                            | inputHeight
                            | labelAlign
                            | width
                            | height
                            | size
                            | orientation;

position                :   (name='position' SEMI (value='label-left' | value='label-right' | value='label-top' ))
                            { helper.setAttribute($name,$value.text); }
                            ;

labelWidth              :   (name='labelWidth' SEMI ( value=INTEGER | value='auto'  ))
                            { helper.setAttribute($name,$value.text); }
                            ;

labelHeight             :   (name='labelHeight' SEMI ( value=INTEGER | value='auto'  ))
                             { helper.setAttribute($name,$value.text); }
                             ;

inputWidth              :   (name='inputWidth' SEMI ( value=INTEGER | value='auto'  ))
                            { helper.setAttribute($name,$value.text); }
                            ;

inputHeight             :   (name='inputHeight' SEMI ( value=INTEGER | value='auto'  ))
                            { helper.setAttribute($name,$value.text); }
                            ;


labelAlign              :   (name='labelAlign' SEMI ( value='left' | value='right' | value='center' ))
                            { helper.setAttribute($name,$value.text); }
                            ;

id                      :   (name='id' SEMI value=ID )
                            { helper.setAttribute($name,$value.text); }
                            ;

text                      : (name='text' SEMI value=STRING )
                            { helper.setStringAttribute($name,$value.text); }
                            ;


width                   :   (name='width' SEMI  value=INTEGER )
                            { helper.setAttribute($name,$value.text); }
                            ;

height                  :   (name='height' SEMI  value=INTEGER )
                            { helper.setAttribute($name,$value.text); }
                            ;

size                    :   (name='size' SEMI  value=INTEGER )
                            { helper.setAttribute($name,$value.text); }
                            ;

active                  :   (name='active' SEMI  value=ID)
                            { helper.setAttribute($name,$value.text); }
                            ;

orientation             :   (name='mode' SEMI  (value='top' | value='bottom'))
                            { helper.setAttribute($name,$value.text); }
                            ;







