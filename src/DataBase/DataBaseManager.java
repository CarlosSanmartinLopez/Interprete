package DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *   DEVELOPED BY STELLIOX.COM                                     *
 *   ECUADOR - LOJA - 2015                                         *
 *   @author stelliox.com                                          *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public final class DataBaseManager {
        
    public Connection conn;
    
    public static final String TABLE_PROJECTS = "project";
        public static final String CN_ID = "id";
        public static final String CN_NAME = "name";
        public static final String CN_DIR = "directory";
        
    public static final String CREATE_TABLE_PROJECTS = "CREATE TABLE IF NOT EXISTS "+TABLE_PROJECTS+"("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CN_NAME + " VARCHAR(25) NOT NULL,"
            + CN_DIR + " TEXT NOT NULL);";  
    
    private final DBHelper helper;
    
    public DataBaseManager() throws ClassNotFoundException, SQLException{
        helper = new DBHelper();
        this.conn = helper.getConn();
    }
    
    private void executeSQL(String sentenceSql)throws ClassNotFoundException, SQLException{
        try (Connection conn = helper.connect();) {
            Statement stat = conn.createStatement();
            stat.executeUpdate(sentenceSql);
            stat.close();
            this.conn.close();
        }
    }
    
    public void setProject(String name, String dir) throws ClassNotFoundException, SQLException{
        String insert = "INSERT INTO "+ TABLE_PROJECTS+ "("
                + CN_ID + ","
                + CN_NAME + ","
                + CN_DIR + ") VALUES(NULL,"
                +"'"+ name + "',"
                +"'"+ dir + "');";
        
        executeSQL(insert);
    }
    
    public ArrayList<Model> getList() throws ClassNotFoundException, SQLException{
        String getdata = "SELECT * FROM "+TABLE_PROJECTS+" ;" ;
        try (Connection conn = helper.connect(); Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(getdata);
            ArrayList<Model> rsList = new ArrayList<Model>();
            
            while(rs.next()){
                Model model = new Model();
                    model.setId(rs.getString(CN_ID));
                    model.setName(rs.getString(CN_NAME));
                    model.setDir(rs.getString(CN_DIR));  
                rsList.add(model);
            }
            this.conn.close();
            
            return rsList;
        }
    }
    
    public Model getProject(String id) throws ClassNotFoundException, SQLException{
        String getdata = "SELECT * FROM "+TABLE_PROJECTS+" WHERE "+CN_ID+"='"+id+"' LIMIT 1;" ;
        try (Connection conn = helper.connect(); Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(getdata);
            
            Model model = new Model();
            while(rs.next()){
                model.setId(rs.getString(CN_ID));
                model.setName(rs.getString(CN_NAME));
                model.setDir(rs.getString(CN_DIR));  
            }
            this.conn.close();
            
            return model;
        }
    }
    
    public void removeProject(String dir) throws ClassNotFoundException, SQLException{
        String insert = "DELETE FROM "+ TABLE_PROJECTS+ " WHERE "+CN_DIR+"='"+dir+"';";
        executeSQL(insert);
    }
}
