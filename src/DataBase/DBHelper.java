package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *   DEVELOPED BY STELLIOX.COM                                     *
 *   ECUADOR - LOJA - 2015                                         *
 *   @author stelliox.com                                          *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public final class DBHelper {
    
    public  final String DB_NAME = "database.sqlite";
    private Connection conn;
    
    public DBHelper() throws ClassNotFoundException {
        
        try {
            this.conn = connect();
            onCreate(this.conn);
            this.conn.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public Connection connect() throws ClassNotFoundException, SQLException{
        String dir = System.getProperty("user.dir")+"\\";
          System.out.println(dir);
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:"+dir+DB_NAME);
        return conn; 
    }
    
    public Connection getConn(){
        return this.conn;
    }
    
    public void onCreate(Connection conn) throws SQLException{
        try (Statement stat = conn.createStatement()) {            
            stat.executeUpdate(DataBaseManager.CREATE_TABLE_PROJECTS);
        }
    }
        
}
