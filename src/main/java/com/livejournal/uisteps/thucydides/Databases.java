package com.livejournal.uisteps.thucydides;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;

/**
 *
 * @author m.prytkova
 */
public class Databases {

    Connection c = null;//Соединение с БД
    List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();

    public BaseConnect workWithDB() {
        return new BaseConnect();
    }

    public class BaseConnect {

        public BaseSelect conect() {
            answers.clear();
            c = null;

            String driver = "com.mysql.jdbc.Driver";//Имя драйвера

            try {
                Class.forName(driver);//Регистрируем драйвер
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            return new BaseSelect();
        }

        public String getUserPassword(String user) {
            String select = "SELECT user.userid, user.user, password.password "
                    + "FROM user "
                    + "LEFT JOIN password "
                    + "ON user.userid=password.userid "
                    + "WHERE user.user = '" + user + "' ";
            String password = conect()
                    .select(select, "password")
                    .finish().get(0).get(0);
            return password;
        }

        public ArrayList<String> findAllFriends(String user) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "';";
            ArrayList<String> friendid = conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid = '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ");";
            return conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
        }

        public String findFriend(String user) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "';";
            ArrayList<String> friendid = conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid = '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ") and user like '%test%';";
            ArrayList<String> ans = conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public String findFriendWithoutGroup(String user) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "' and groupmask = '1';";
            ArrayList<String> friendid = conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid = '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ") and user like '%test%';";
            ArrayList<String> ans = conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public String findNotFriend(String user) {
            ArrayList<String> ans = findNotFriends(user, 100);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public ArrayList<String> findNotFriends(String user, Integer limit) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "';";
            ArrayList<String> friendid = conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid != '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ") and user like '%test%' "
                    + "and status = 'A' "
                    + "and statusvis = 'V' "
                    + "and statusvisdate >= adddate(now(), interval - 500 day) "
                    + "limit " + limit + ";";
            return conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
        }

        public List<ArrayList<String>> findAllFriendsInGroups(String user) {

            ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
            String select1 = "select * from friends "
                    + "where userid = "
                    + "(select userid from user where user = '" + user + "') "
                    + "and groupmask > 1;";
            List<ArrayList<String>> table = conect()
                    .select(select1, "friendid")
                    .select(select1, "groupmask")
                    .finish();
            ans.addAll(table);
            ArrayList<String> usernames = new ArrayList<String>();
            for (int j = 0; j < ans.get(0).size(); j++) {
                String dopselect = "select user from user where userid = '" + ans.get(0).get(j) + "';";
                String username = conect()
                        .select(dopselect, "user")
                        .finish()
                        .get(0)
                        .get(0);
                usernames.add(username);
            }
            ans.set(0, usernames);

            ArrayList<String> groups_list = new ArrayList<String>();
            for (int j = 0; j < ans.get(1).size(); j++) {

                char[] myCharArray = Integer
                        .toBinaryString(Integer.valueOf(ans.get(1).get(j)))
                        .toCharArray();

                String groupsid = "";
                for (int i = 1; i < myCharArray.length; i++) {
                    if (myCharArray[i] == '1') {
                        String select3 = "select groupname from lj_c2.friendgroup2 "
                                + "where userid = (select userid from user where user = '" + user + "') "
                                + "and groupnum = '" + i + "';";
                        String groupname = conect()
                                .select(select3, "groupname")
                                .finish()
                                .get(0)
                                .get(0);
                        groupsid = groupsid + groupname + ";";
                    }
                }
                groups_list.add(groupsid);
            }
            ans.set(1, groups_list);
            return ans;
        }

        public String findFriendInGroup(String user) {
            String ans = "";
            ArrayList<String> answer = findAllFriendsInGroups(user).get(0);
            for (int i = 0; i < answer.size(); i++) {
                if (answer.get(i).contains("test")) {
                    ans = answer.get(i);
                    i = answer.size();
                }
            }
            if (ans.isEmpty()) {
                ans = answer.get(0);
            }
            return ans;
        }

        public ArrayList<String> findFriendInGroup(String user, String group) {
            ArrayList<String> ans = new ArrayList<String>();
            List<ArrayList<String>> answer = findAllFriendsInGroups(user);
            for (int i = 0; i < answer.get(0).size(); i++) {
                if (answer.get(1).get(i).contains(group)) {
                    ans.add(answer.get(0).get(i));
                }
            }
            return ans;
        }

        ///////////////////////////
        ///Community
        ////////////
        private ArrayList<String> findUserInCommunity(String community, String type) {
            String select = "select r.targetid from user u left join reluser r on "
                    + "u.userid=r.userid where u.user = '" + community + "' and r.type='" + type + "';";
            ArrayList<String> targetid = workWithDB().conect()
                    .select(select, "targetid")
                    .finish()
                    .get(0);
            ArrayList<String> user = new ArrayList<String>();
            for (int i = 0; i < targetid.size(); i++) {
                String select2 = "Select user from user where userid = " + targetid.get(i);
                String ans = workWithDB().conect()
                        .select(select2, "user")
                        .finish()
                        .get(0)
                        .get(0);

                user.add(ans);
            }
            return user;
        }

        public String findMaintainerInComminuty(String community) {
            ArrayList<String> users = findUserInCommunity(community, "A");
            return users.get(new Random().nextInt(users.size()));
        }

        public ArrayList<String> findMembersInCommunity(String community) {
            String select = "select user from user where userid = "
                    + "(select userid from friends where friendid = "
                    + "(select userid as comm from user where user='" + community + "')and userid "
                    + "not in (select targetid from reluser where userid =friendid))";
            ArrayList<String> user = workWithDB().conect()
                    .select(select, "user")
                    .finish()
                    .get(0);
            return user;
        }

        public String findMemberInCommunityNotInGroup(String community) {
            String select = "select friendid from friends where userid="
                    + "(select userid from user where user='" + community + "') and "
                    + "friendid not in(select targetid from reluser where "
                    + "userid=(select userid from user where user='" + community + "')) and groupmask=1";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select, "friendid")
                    .finish()
                    .get(0);
            ArrayList<String> users = new ArrayList<String>();
            for (int i = 0; i < ans.size(); i++) {
                String select2 = "Select user from user where userid = " + ans.get(i);
                String ans1 = workWithDB().conect()
                        .select(select2, "user")
                        .finish()
                        .get(0)
                        .get(0);
                users.add(ans1);
            }
            return users.get(new Random().nextInt(users.size()));
        }

    }

    public class BaseSelect {

        public BaseSelect select(String select, String column) {

            String user = "root";//Логин пользователя
            String password = "";//Пароль пользователя
            String url = "jdbc:mysql://127.0.0.1:2222/livejournal";//URL адрес
            ArrayList<String> answer = new ArrayList<>();

            try {
                c = (Connection) DriverManager.getConnection(url, user, password);//Установка соединения с БД
                ThucydidesUtils.putToSession("connect", c);
                Statement st = c.createStatement();//Готовим запрос
                ResultSet rs = st.executeQuery(select);//Выполняем запрос к БД, результат в переменной rs
                while (rs.next()) {
                    answer.add(rs.getString(column));
                    // System.out.println(rs.getString(column));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            answers.add(answer);
            return new BaseSelect();
        }

        public List<ArrayList<String>> finish() {
            //Обязательно необходимо закрыть соединение
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            return answers;
        }
    }
}
