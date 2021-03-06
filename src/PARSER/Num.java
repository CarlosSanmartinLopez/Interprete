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
public class Num extends AST {
    private Token token;
   
    public Num(Token.TokenType tokenType, String value) {
        token = new Token(tokenType, value);
    }

    public Num(Token token) {
        this(token.getType(), token.getValue());
    }

    public Token.TokenType getTokenType() {
        return token.getType();
    }

    public String getValue() {
        return token.getValue();
    }
    
}
