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
package MEMORY;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stelliox.com
 */
public class GLOBAL_SPOCE {
    private static GLOBAL_SPOCE INSTANCE = null;
    private List<Data> list;

    public void reset() {
        INSTANCE = null;
    }
    
    class Data {
        public String var;
        public double value;
        
        public Data(String var, double value) {
            this.var = var;
            this.value = value;
        }
    }
        
    private GLOBAL_SPOCE() {
        list = new ArrayList();
    }
    
    private static void createInstance() {
        if(INSTANCE == null)
            INSTANCE = new GLOBAL_SPOCE();
    }
    
    public static GLOBAL_SPOCE getInstance() {
        if(INSTANCE == null)
            createInstance();
        
        return INSTANCE;
    }
    
    public void set(String var, double value) {
        list.add(new Data(var, value));
    }
    
    public double get(String var) {        
        for (Data data : list)
            if(data.var.equals(var))
                return data.value;
            
        return -1;
    }
    
    public String toString() {
        String result = "{";
            for (int i = 0; i < list.size(); i++) {
                result += list.get(i).var+":"+list.get(i).value;
                if(i+1 != list.size())
                    result += ", ";
            }                
            result += "}";
        return result;
    }
}
