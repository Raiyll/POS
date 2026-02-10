package Konfigurasi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ahmad
 */
public class Koneksi {
    public static Connection getConnection() {
        Connection conn = null;
        String url ="jdbc:mysql://localhost:3306/pos";
        String user ="root";
        String password ="";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e); }
        return conn;
    }
    
    public static void main(String[] args) {
        try {
            Connection c = Koneksi.getConnection();
            System.out.println(String.format("Koneksi database %s" + "Sukses Coy", c.getCatalog()));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public static Connection AMBIL_KONEKSI() {
    return getConnection();
}

    
}
