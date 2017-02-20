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
package PARSER;

import LEXER.Lexer;
import LEXER.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stelliox.com
 */
public class Parser {
    Lexer lexer;
    private Token currentToken;
    
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }
    
    private void error() {
        System.out.println("Error Parse");
    }
    
    private void eat(Token.TokenType tokenType) {
        if(currentToken.getType() == tokenType)
            currentToken = lexer.getNextToken();
        else
            error();
    }
    
    private AST factor() {
        Token token = currentToken;
        
        AST node;
        switch(token.getType()) {
            case PLUS:
                eat(Token.TokenType.PLUS);
                node = new UnaryOP(token, factor());
                return node;
            case MINUS:
                eat(Token.TokenType.MINUS);
                node = new UnaryOP(token, factor());
                return node;
            case INTEGER_CONST:
                eat(Token.TokenType.INTEGER_CONST);
                return new Num(token);
            case REAL_CONST:
                eat(Token.TokenType.REAL_CONST);
                return new Num(token);
            case LPAREN:
                eat(Token.TokenType.LPAREN);
                node = expr();
                eat(Token.TokenType.RPAREN);
                return node;
            default:
                node = variable();
                return node;
        }
    }
    
    private AST term() {
        AST node = factor();
        
        Token token;
        while (currentToken.getType() == Token.TokenType.MUL ||
               currentToken.getType() == Token.TokenType.INTEGER_DIV ||
               currentToken.getType() == Token.TokenType.FLOAT_DIV) {            
            token = currentToken;
            if(token.getType() == Token.TokenType.MUL)
                eat(Token.TokenType.MUL); 
            else if(token.getType() == Token.TokenType.INTEGER_DIV)
                eat(Token.TokenType.INTEGER_DIV); 
            else if(token.getType() == Token.TokenType.FLOAT_DIV)
                eat(Token.TokenType.FLOAT_DIV); 
                
            node = new BinOp(node, token, factor());
        }
        
        return node;
    }
    
    private AST expr() {
        AST node  = term();
        Token token;
        while (currentToken.getType() == Token.TokenType.PLUS ||            
               currentToken.getType() == Token.TokenType.MINUS ) {
            token = currentToken;
            if(token.getType() == Token.TokenType.PLUS) {
                eat(Token.TokenType.PLUS); 
            } else
                if(token.getType() == Token.TokenType.MINUS) {
                    eat(Token.TokenType.MINUS); 
                }
            node = new BinOp(node, token, term());
        }
        return node;
    }
    
    public AST parse() {
        Program node = program();
        
        if(currentToken.getType() != Token.TokenType.EOF)
            error();
                    
        return node;
    }

    public Token getCurrentToken() {
        return currentToken;
    }
    
    public Program program() {
        eat(Token.TokenType.PROGRAM);
        Var varNode = variable();
        String progName = varNode.getToken().getValue();
        
        eat(Token.TokenType.SEMI);
        Block blockNode = block();
        Program programNode = new Program(progName, blockNode);
        eat(Token.TokenType.DOT);
        
        return programNode;
    }
    
    public Compound compoundStatement() {
        eat(Token.TokenType.BEGIN);
        List<AST> nodes = statementList();
        eat(Token.TokenType.END);
        
        Compound root = new Compound();
        for (AST node : nodes)
            root.children.add(node);
        
        return root;
    }
    
    public List<AST> statementList() {
        AST node = statement();
        
        List<AST> result = new ArrayList<>();
        result.add(node);
        
        while (currentToken.getType() == Token.TokenType.SEMI) {            
            eat(Token.TokenType.SEMI);
            result.add(statement());
        }
        
//        if(currentToken.getType() == Token.TokenType.ID)
//            error();
        
        return result;
    }
    
    public AST statement() {
        AST node;
        
        if(currentToken.getType() == Token.TokenType.BEGIN)
            node = compoundStatement();
        else if(currentToken.getType() == Token.TokenType.ID)
            node = assignmentStatement();
        else
            node = empty();
        
        return node;
    }
    
    public Assign assignmentStatement() {
        AST left = variable();
        Token token = currentToken;
        
        eat(Token.TokenType.ASSIGN);
        
        AST right = expr();
        Assign node = new Assign(left, token, right);
        
        return node;
    }
    
    public Var variable() {
        Var node = new Var(currentToken);
        eat(Token.TokenType.ID);
        return node;
    }
    
    public NoOp empty() {
        return new NoOp();
    }
    
    public Block block() {
        List<AST> declarationsNode = declarations();
        Compound compoundStatementNode = compoundStatement();
        Block node = new Block(declarationsNode, compoundStatementNode);
                
        return node;
    }
    
    public List<AST> declarations() {
        List<AST> declarations = new ArrayList();
        
        if(currentToken.getType() == Token.TokenType.VAR) {
            eat(Token.TokenType.VAR);            
            while (currentToken.getType() == Token.TokenType.ID) {                
                declarations.addAll(variableDeclaration());
                eat(Token.TokenType.SEMI);
            }
        }
        
        String procName;
        while (currentToken.getType() == Token.TokenType.PROCEDURE) {            
            eat(Token.TokenType.PROCEDURE);
            procName = currentToken.getValue();
            eat(Token.TokenType.ID);
            eat(Token.TokenType.SEMI);
            
            Block blockNode = block();
            ProcedureDecl procDecl = new ProcedureDecl(procName, blockNode);
            declarations.add(procDecl);
            eat(Token.TokenType.SEMI);
        }
        return declarations;
    }
    
    public List<VarDecl> variableDeclaration() {
        List<Var> varNodes = new ArrayList();
        varNodes.add(new Var(currentToken));
        eat(Token.TokenType.ID);
        
        while (currentToken.getType() == Token.TokenType.COMMA)  {            
            eat(Token.TokenType.COMMA);
            varNodes.add(new Var(currentToken));
            eat(Token.TokenType.ID);
        }
        
        eat(Token.TokenType.COLON);
        
        Type typeNode = typeSpec();
        
        List<VarDecl> varDeclarations = new ArrayList();
        for (Var varNode : varNodes) {
            varDeclarations.add(new VarDecl(varNode, typeNode));
        }
        
        return varDeclarations;
    }
    
    private Type typeSpec() {
        Token token = currentToken;
        
        if(currentToken.getType() == Token.TokenType.INTEGER)
            eat(Token.TokenType.INTEGER);
        else
            eat(Token.TokenType.REAL);
        
        return new Type(token);
    }
}
