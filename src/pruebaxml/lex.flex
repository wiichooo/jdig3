package pruebaxml;

import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%
%{
String acumulador = "";
int contador = 0;

%}

%cup
%public
%line
%char
%16bit
%ignorecase
%class Yylex


LETRA=[a-zA-Z]
DIGITO=[0-9]
ID=({LETRA}|"'"|{DIGITO}|"-"|"("|"&"|"¿"|"¡")[^\n"="]*
NUM={DIGITO}{DIGITO}*
SPACE = [\ \r\t\f]
TAB = [/t]
ENTER=[\ \n]

%%
<YYINITIAL> {ID} {return new Symbol(sym.ID, new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> {NUM} {return new Symbol(sym.NUM, new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "=" {return new Symbol(sym.IGUAL , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> ":" {return new Symbol(sym.DOSPUNTOS , new Yytoken(yytext(),""+(yyline+1),""+yychar));}

<YYINITIAL> "[Dictionary]" {return new Symbol(sym.DICTIONARY , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "[Level]" {return new Symbol(sym.LEVEL , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "[IdItems]" {return new Symbol(sym.IDITEMS , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "[Item]" {return new Symbol(sym.ITEMS , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "[ValueSet]" {return new Symbol(sym.VALUESET , new Yytoken(yytext(),""+(yyline+1),""+yychar));}
<YYINITIAL> "[Record]" {return new Symbol(sym.RECORD , new Yytoken(yytext(),""+(yyline+1),""+yychar));}

<YYINITIAL> {SPACE} {/*ignored*/ }

<YYINITIAL> {TAB} {yychar+=5;}
<YYINITIAL> {ENTER} { yychar = 0;}


<YYINITIAL>. { 
     System.out.println("ERROR LEXICO: "+yytext() + 
						"en la linea " + (yyline+1) + 
						"en la columna " + yychar);
}