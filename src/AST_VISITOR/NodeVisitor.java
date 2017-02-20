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
package AST_VISITOR;

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
import PARSER.BinOp;
import INTERPRETER.Interpreter;
import PARSER.ProcedureDecl;

/**
 *
 * @author Stelliox.com
 */
public class NodeVisitor {
    public double visit(AST node) {
        if(node instanceof BinOp) 
            return ((Interpreter)this).visitBinOp((BinOp)node);
        else if(node instanceof Num)
            return Double.parseDouble(((Interpreter)this).visitNum((Num)node));
        else if(node instanceof UnaryOP)
            return ((Interpreter)this).visitUnaryOP((UnaryOP)node);
        else if(node instanceof Compound)
            ((Interpreter)this).visitCompound((Compound)node);
        else if(node instanceof Assign)
            ((Interpreter)this).visitAssign((Assign)node);
        else if(node instanceof Var)
            return ((Interpreter)this).visitVar((Var)node);
        else if(node instanceof NoOp)
            ((Interpreter)this).visitNoOp((NoOp)node);
        else if(node instanceof Program)
            ((Interpreter)this).visitProgram((Program)node);
        else if(node instanceof Block)
            ((Interpreter)this).visitBlock((Block)node);
        else if(node instanceof VarDecl)
            ((Interpreter)this).visitVarDecl((VarDecl)node);
        else if(node instanceof ProcedureDecl)
            ((Interpreter)this).visitProcedureDescl((ProcedureDecl)node);
        else {
            System.out.println("No existe metodo "+node.getClass().getName());
            return -1;                
        }
        return 0;                        
    }
}
