/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Soufiane
 */
public class Util {
 /**private static Connection sessionFactory = buildSessionFactory();
  
  private static Connection buildSessionFactory() {
        if (sessionFactory.equals(null)) {
            try {
                String dbURL3 = "jdbc:derby://localhost:1527/Annuair";
                Properties properties = new Properties();
                properties.put("create", "true");
                properties.put("user", "nbuser");
                properties.put("password", "Password");

                sessionFactory = DriverManager.getConnection(dbURL3, properties);
                if (sessionFactory != null) {
                    System.out.println("Connected to database ");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return sessionFactory;
  }
  
  public static Connection getConnection() {
    return sessionFactory;
  }
  * */
}
