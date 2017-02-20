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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Stelliox.com
 */
public class SymbolTable {
    Map<String,Symbol> symbols;
    
    public SymbolTable() {
        symbols  = new LinkedHashMap<String,Symbol>();
        define(new BuiltinTypeSymbol("INTEGER"));
        define(new BuiltinTypeSymbol("REAL"));
    }
    
    public void define(Symbol symbol) {
//        System.out.println("DEFINE : "+symbol);
        this.symbols.put(symbol.name, symbol);
    }
    
    public Symbol lookup(String name) {
        System.out.println("LOOKUP : "+name);
        Symbol symbol = this.symbols.get(name);
        return symbol;
    }
    
    public String toString() {
        String result =  "Symbols: [";
                
        Iterator<String> it = symbols.keySet().iterator();
        
        while (it.hasNext()) {
            String key = it.next();
            result += symbols.get(key);
            
            if(it.hasNext())
                result += ", ";
        }
        result += "]";
        return result;
    }
}
