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
package INTERPRETER;

import AST_VISITOR.NodeVisitor;
import PARSER.Parser;
import PARSER.Type;
import PARSER.VarDecl;
import PARSER.Block;
import PARSER.Program;
import PARSER.NoOp;
import PARSER.Var;
import PARSER.Assign;
import PARSER.Compound;
import PARSER.UnaryOP;
import PARSER.Num;
import PARSER.AST;
import MEMORY.GLOBAL_SPOCE;
import PARSER.ProcedureDecl;
import PARSER.BinOp;

/**
 *
 * @author Stelliox.com
 */
public class Interpreter extends NodeVisitor {
    private Parser parser = null;
    private AST tree = null;
    public GLOBAL_SPOCE global_spoce = GLOBAL_SPOCE.getInstance();
    
    public Interpreter(Parser parser) {
        this.parser = parser;
    }
    
    public Interpreter(AST tree) {
        this.tree = tree;
    }
    
    public double visitBinOp(BinOp node) {
        switch(node.getOp().getType()) {
            case PLUS:
                return visit(node.getLeft()) + visit(node.getRight());
            case MINUS:
                return visit(node.getLeft()) - visit(node.getRight());
            case MUL:
                return visit(node.getLeft()) * visit(node.getRight());
            case INTEGER_DIV:
                return visit(node.getLeft()) / visit(node.getRight());
            case FLOAT_DIV:
                return visit(node.getLeft()) / visit(node.getRight());
        }
        return 0;
    }
    
    public String visitNum(Num node) {
        return node.getValue();
    }
    
    public double visitUnaryOP(UnaryOP node) {
        switch(node.getOp().getType()) {
            case PLUS:
                return +visit(node.getExpr());
            case MINUS:
                return -visit(node.getExpr());
            default:
                return 0;
        }
    }
    
    public void visitCompound(Compound node) {
        for (AST child : node.children) {
            visit(child);
        }
    }
    
    public void visitAssign(Assign node) {
        String varName = ((Var)node.getLeft()).getToken().getValue();
        global_spoce.set(varName, visit(node.getRight()));
    }
    
    public double visitVar(Var node) {
        String varName = node.getToken().getValue();
        double val = global_spoce.get(varName);
        
        if(val == -1)
            System.out.println("Error");
        
        return val;
    }
    
    public void visitNoOp(NoOp node) {
        
    }
    
    public void visitProgram(Program node) {
        visit(node.getBlock());
    }
    
    public void visitBlock(Block node) {
        for (AST declaration : node.getDeclarations())
            visit(declaration);
        
        visit(node.getCompoundStatement());
    }
    
    public void visitVarDecl(VarDecl node) {
        
    }
    
    public void visitType(Type node) {
        
    }
    
    public void visitProcedureDescl(ProcedureDecl node) {
        
    }
    
    public double interpret() {
        if(tree == null)
            tree = parser.parse();
        
        if(tree == null)
            return -1;
                    
        return visit(tree);
    }
}
