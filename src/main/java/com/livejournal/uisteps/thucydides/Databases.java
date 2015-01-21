package com.livejournal.uisteps.thucydides;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m.prytkova
 */
public class Databases {

    public BaseConnect workWithDB() {
        return new BaseConnect();
    }

    public class BaseConnect {

        public void conect(String base) {

            String user = "root";//Логин пользователя
            String password = "";//Пароль пользователя
            String url = "jdbc:mysql://127.0.0.1:2222/" + base;//URL адрес
            String driver = "com.mysql.jdbc.Driver";//Имя драйвера
            ArrayList<String> answer = new ArrayList<>();

            try {
                Class.forName(driver);//Регистрируем драйвер
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection c = null;//Соединение с БД

            try {
                c = (Connection) DriverManager.getConnection(url, user, password);//Установка соединения с БД
                System.out.println("================ соединение есть");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Обязательно необходимо закрыть соединение
                try {
                    if (c != null) {
                        c.close();
                        System.out.println("================ соединение закрыто");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public class BaseSelect {

    }

}
