/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowerstore ;

import java.sql.*;


public class database {
    
public static Connection con(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect =DriverManager.getConnection("jdbc:mysql://localhost/flower", "root", "");
            return connect;
       }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}