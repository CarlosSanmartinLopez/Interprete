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

import java.util.List;

/**
 *
 * @author Stelliox.com
 */
public class Block extends AST {
    private List<AST> declarations;
    private Compound compoundStatement;

    public Block(List<AST> declarations, Compound compoundStatement) {
        this.declarations = declarations;
        this.compoundStatement = compoundStatement;
    }

    public Compound getCompoundStatement() {
        return compoundStatement;
    }

    public List<AST> getDeclarations() {
        return declarations;
    }
}
