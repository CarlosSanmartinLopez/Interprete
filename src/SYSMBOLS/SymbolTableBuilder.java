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
package SYSMBOLS;

import AST_VISITOR.NodeVisitor;
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
import PARSER.ProcedureDecl;
import PARSER.BinOp;

/**
 *
 * @author Stelliox.com
 */
public class SymbolTableBuilder extends NodeVisitor {
    public SymbolTable symTab;
    
    public void visitX(AST node) {
        if(node instanceof BinOp)
            visitBinOP((BinOp)node);
        else if(node instanceof Num)
            visitNum((Num)node);
        else if(node instanceof UnaryOP)
            visitUnaryOP((UnaryOP)node);
        else if(node instanceof Compound)
            visitCompound((Compound)node);
        else if(node instanceof NoOp)
            visitNoOP((NoOp)node);
        else if(node instanceof Program)
            visitProgram((Program)node);
        else if(node instanceof Block)
            visitBlock((Block)node);
        else if(node instanceof VarDecl)
            visitVarDecl((VarDecl)node);
        else if(node instanceof Assign)
            visitAssign((Assign)node);
        else if(node instanceof Var)
            visitVar((Var)node);
        else if(node instanceof ProcedureDecl)
            visitProcedureDescl((ProcedureDecl)node);
        else {
            System.out.println("No existe metodo "+node.getClass().getName());
        }                       
    }
    
    public SymbolTableBuilder() {
        this.symTab = new SymbolTable();
    }
    
    public void visitBlock(Block node) {
        for (AST declaration : node.getDeclarations()) {
            visitX(declaration);
        }
        visitX(node.getCompoundStatement());
    }
    
    public void visitProgram(Program node) {
        visitX(node.getBlock());
    }
    
    public void visitBinOP(BinOp node) {
        visitX(node.getLeft());
        visitX(node.getRight());
    }
    
    public void visitNum(Num node) {
        
    }
    
    public void visitUnaryOP(UnaryOP node) {
        visitX(node.getExpr());
    }
    
    public void visitCompound(Compound node) {
        for (AST col : node.children) {
            visitX(col);
        }
    }
    
    public void visitNoOP(NoOp node) {
    
    }
    
    public void visitVarDecl(VarDecl node) {
        String typeName = node.getTypeNode().getToken().getValue();
        Symbol typeSymbol = symTab.lookup(typeName);
        String varName = node.getVarNode().getToken().getValue();
        
        VarSymbol varSymbol = new VarSymbol(varName, typeSymbol);
        symTab.define(varSymbol);
    }
    
    public void visitAssign(Assign node) {
        String varName = ((Var)node.getLeft()).getToken().getValue();
        Symbol varSymbol = symTab.lookup(varName);
        
        if(varSymbol == null) {
            System.out.println("ERROR "+varName);
        }
    }
    
    public void visitVar(Var node) {
        String varName = node.getToken().getValue();
        Symbol valSymbol = symTab.lookup(varName);
        
        if(valSymbol == null) {
            System.out.println("ERROR "+varName);
        }
    }
    
    public void visitProcedureDescl(ProcedureDecl node) {
        
    }
}
