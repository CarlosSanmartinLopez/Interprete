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
package LEXER;

/**
 *
 * @author Stelliox.com
 */
public class Lexer {
    String text;
    private final char[] charText;
    
    private int pos = 0;
    private int peek_pos = 0;
    
    private Character current_char;

    public Lexer(String text) {
        this.text = text;
        charText = text.toCharArray();
        current_char = charText[pos];
    }
    
    protected void error() {
        System.out.println("Error Lexer");
    }
    
    protected void advance() {
        pos++;        
        if(pos > charText.length-1)
            current_char = null;
        else
            current_char = charText[pos];
    }
    
    protected String integer() {
        String result = ""; 
        while (current_char != null && Character.isDigit(current_char)) {            
            result += current_char;
            advance();
        }
        return result;
    }
    
    public Token getNextToken() {
        while (current_char != null) {  
            if(current_char == '{') {
                advance();
                skipComment();
                continue;
            }
            
            if(Character.isSpaceChar(current_char)) {
                skipWhiteSpace();
                continue;
            }
            
            if(Character.isAlphabetic(current_char))
                return id();
            
            if(Character.isDigit(current_char))
                return number();
            
            if(current_char == ':' && peek() == '=') {
                advance();
                advance();
                return new Token(Token.TokenType.ASSIGN, ":=");
            }
            
            if(current_char == ':') {
                advance();
                return new Token(Token.TokenType.COLON, ":");
            }
            
            if(current_char == ';') {
                advance();
                return new Token(Token.TokenType.SEMI, ";");
            }
            
            if(current_char == '.') {
                advance();
                return new Token(Token.TokenType.DOT, ".");
            }
            
            if(current_char == ',') {
                advance();
                return new Token(Token.TokenType.COMMA, ",");
            }
                 
            if(current_char == '+') {
                advance();
                return new Token(Token.TokenType.PLUS, "+");
            }
            if(current_char == '-') {
                advance();
                return new Token(Token.TokenType.MINUS, "-");
            }
            if(current_char == '*') {
                advance();
                return new Token(Token.TokenType.MUL, "*");
            }
            if(current_char == '/') {
                advance();
                return new Token(Token.TokenType.FLOAT_DIV, "/");
            }
            if(current_char == '(') {
                advance();
                return new Token(Token.TokenType.LPAREN, "(");
            }
            if(current_char == ')') {
                advance();
                return new Token(Token.TokenType.RPAREN, ")");
            }
        }
        
        return new Token(Token.TokenType.EOF, null);
    }
    
    private Token id() {
        String result = "";
        while (current_char != null && Character.isLetterOrDigit(current_char)) {
            result += current_char;
            advance();
        }
        
        Token token;
        
        try {
            if(result.equals("DIV"))
                token = new Token(Token.TokenType.INTEGER_DIV, result);
            else
                token = new Token(Token.TokenType.valueOf(result), result);
        } catch (Exception e) {
            token = new Token(Token.TokenType.ID, result);
        }
        return token;
    }
    
    private Character peek() {
        peek_pos = pos+1;
        if(peek_pos > text.length()-1)
            return null;
        else
            return charText[peek_pos];
    }
    
    private void skipWhiteSpace() {
        while (current_char != null && Character.isSpaceChar(current_char)) {
            advance();
        }
    }
    
    private void skipComment() {
        while (current_char != '}') {
            advance();
        }
        advance();
    }
    
    private Token number() {
        String result = "";
        while (current_char != null && Character.isDigit(current_char)) {
            result += current_char;
            advance();
        }
        
        if(current_char == '.') {
            result += current_char;
            advance();
            while (current_char != null && Character.isDigit(current_char)) {
                result += current_char;
                advance();
            }
            return new Token(Token.TokenType.REAL_CONST, result);
        } else
            return new Token(Token.TokenType.INTEGER_CONST, result);
    }
}
