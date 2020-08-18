package leetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ron
 * @date 2020/8/7 下午 05:32
 */
public class EMTest {

    public static void main(String[] args) {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mycenter?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "ron", "ron123456");
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM oh_ci limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
