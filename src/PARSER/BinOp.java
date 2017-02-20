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

import LEXER.Token;

/**
 *
 * @author Stelliox.com
 */
public class BinOp extends AST {
    private AST left;
    private Token token;
    private Token op;
    private AST right;
    
    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.token = this.op = op;
        this.right = right;
    }    

    public Token getOp() {
        return op;
    }    

    public AST getLeft() {
        return left;
    }

    public Token getToken() {
        return token;
    }

    public AST getRight() {
        return right;
    }
    
}
