/* 
 * Copyright (C) 2017 Stelliox.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Test;

import INTERPRETER.Interpreter;
import PARSER.Parser;
import PARSER.AST;
import LEXER.Lexer;
import MEMORY.GLOBAL_SPOCE;
import SYSMBOLS.SymbolTableBuilder;

/**
 *
 * @author Stelliox.com
 */
public class Test {
    public static void main(String[] args) {
//        Lexer lexer = new Lexer(""
//                + "PROGRAM Psd;"
//                + " VAR"
//                + "    a, b : INTEGER;"
//                + "    y    : INTEGER;"
//                + " BEGIN {part10}"
//                + " a := 2;"
//                + " b := 10;"
//                + " y := 4;"
//                + "END.");
//        
//        Parser parser = new Parser(lexer);
//        parser.parser();
//        Interpreter interpreter = new Interpreter(parser);
//        interpreter.interpreter();
//        
//        System.out.println(interpreter.global_spoce.toString());
        
//        BuiltinTypeSymbol intBts = new BuiltinTypeSymbol("INTEGER");
//        BuiltinTypeSymbol realBts = new BuiltinTypeSymbol("REAL");
//        
//        VarSymbol varXsymbol = new VarSymbol("x", intBts.toString());
//        VarSymbol varYsymbol = new VarSymbol("y", realBts.toString());
//        
//        System.out.println(varXsymbol);
//        System.out.println(varYsymbol);
        
//        SymbolTable symbolTable = new SymbolTable();
//        BuiltinTypeSymbol builtinTypeSymbol = new BuiltinTypeSymbol("INTEGER");
//
//        symbolTable.define(builtinTypeSymbol);
//        
//    System.out.println(symbolTable);
//        
//        VarSymbol varSymbol = new VarSymbol("x", builtinTypeSymbol);
//        symbolTable.define(varSymbol);
//        
//    System.out.println(symbolTable);
//    
//        BuiltinTypeSymbol realType = new BuiltinTypeSymbol("REAL");
//        symbolTable.define(realType);
//        
//    System.out.println(symbolTable);
//    
//        VarSymbol varSymbol1 = new VarSymbol("y", realType);
//        symbolTable.define(varSymbol1);
//        
//    System.out.println(symbolTable);
        
        String text = "PROGRAM dia4;" +
                        "VAR" +
                        "   a : INTEGER;" +
                        "   PROCEDURE P1;" +
                        "   VAR" +
                        "       a : REAL;" +
                        "       k : INTEGER;" +
                        "       PROCEDURE P2;" +
                        "           VAR" +
                        "           a, z : INTEGER;" +
                        "       BEGIN {P2}" +
                        "           z := 777;" +
                        "       END;  {P2}" +
                        "   BEGIN {P1}" +
                        "   END;  {P1}" +
                        "BEGIN {dia4}" +
                        "   a := 10;" +
                        "END.  {dia4}";
        
        Lexer lexer = new Lexer(text);
        
//        Token token = lexer.getNextToken();
//        while (token.getType() != Token.TokenType.EOF) {            
//            System.out.println("-a"+token);
//            token = lexer.getNextToken();
//        }
        
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();
//        
        SymbolTableBuilder stb = new SymbolTableBuilder();
        stb.visitX(tree);
        System.out.println(stb.symTab.toString());
//        
        Interpreter interpreter = new Interpreter(tree);
        interpreter.interpret();
//        
        GLOBAL_SPOCE global_spoce = GLOBAL_SPOCE.getInstance();
        System.out.println(global_spoce);
    }
}
