package com.livejournal.uisteps.thucydides;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;

/**
 *
 * @author m.prytkova
 */
public class DatabasesData extends Databases {

    public UserData userData() {
        return new UserData();
    }

    public Discovery discovery() {
        return new Discovery();
    }

    public UserSettings userSettings() {
        return new UserSettings();
    }

    public Friends friends() {
        return new Friends();
    }

    public PostsUser postsUser() {
        return new PostsUser();
    }

    public Community community() {
        return new Community();
    }

    public BannedUser bannedUser() {
        return new BannedUser();
    }

    public PrivilegeUser privUser() {
        return new PrivilegeUser();
    }

    public class UserData extends DatabasesData {

        public String getUserPassword(String user) {
            String select = "SELECT user.userid, user.user, password.password "
                    + "FROM user "
                    + "LEFT JOIN password "
                    + "ON user.userid=password.userid "
                    + "WHERE user.user = '" + user + "' ";
            String password = workWithDB().conect()
                    .select(select, "password")
                    .finish().get(0).get(0);
            return password;
        }

        public Boolean isPaid(String user) {
            String select1 = "SELECT caps & 1<<3 = 8 as paid, caps & 1<<4=16 as perm "
                    + "FROM user "
                    + "WHERE user='"
                    + user
                    + "';";
            List<ArrayList<String>> paid = workWithDB().conect()
                    .select(select1, "paid, perm")
                    .finish();
            return "1".equals(paid.get(0).get(0)) || "1".equals(paid.get(0).get(1));
        }
    }

    public class UserSettings extends DatabasesData {

        public String getCyrSetting(String user) {
            String select1 = "SELECT caps "
                    + "FROM user "
                    + "WHERE user='"
                    + user
                    + "';";
            String caps = workWithDB().conect()
                    .select(select1, "caps")
                    .finish()
                    .get(0)
                    .get(0);
            switch (Integer.valueOf(caps) & 1024) {
                case 0:
                    return "NONCYR";
                case 1024:
                    return "CYR";
                default:
                    Assert.assertTrue("Unknown user type", false);
                    return "Unknown user type";
            }
        }

        public Boolean getAdaptiveSetting(String user) {
            String select1 = "SELECT * "
                    + "FROM user "
                    + "WHERE user='"
                    + user
                    + "';";
            List<ArrayList<String>> user_atr = workWithDB().conect()
                    .select(select1, "clusterid, userid")
                    .finish();
            String select2 = "SELECT value "
                    + "FROM lj_c"
                    + user_atr.get(0).get(0)
                    + ".userproplite2 "
                    + "WHERE upropid = '402' and userid='"
                    + user_atr.get(0).get(1)
                    + "';";
            try {
                return "1".equals(workWithDB().conect()
                        .select(select2, "value")
                        .finish()
                        .get(0)
                        .get(0));
            } catch (Exception ex) {
                return false;
            }
        }

        public String getStyle(String user) {
            String select1 = "SELECT * "
                    + "FROM user "
                    + "WHERE user='"
                    + user
                    + "';";
            List<ArrayList<String>> user_atr = workWithDB().conect()
                    .select(select1, "clusterid, userid")
                    .finish();
            String select2 = "SELECT * "
                    + "FROM lj_c"
                    + user_atr.get(0).get(0)
                    + ".userproplite2 "
                    + "WHERE upropid = '96' and userid = '"
                    + user_atr.get(0).get(1)
                    + "';";
            String styleid = workWithDB().conect()
                    .select(select2, "value")
                    .finish()
                    .get(0)
                    .get(0);
            String select3 = "SELECT name "
                    + "FROM s2styles "
                    + "WHERE userid= '"
                    + user_atr.get(0).get(1)
                    + "' and styleid = '" + styleid + "';";
            return workWithDB().conect()
                    .select(select3, "name")
                    .finish()
                    .get(0)
                    .get(0);
        }

        public String getInMyOnStyleSetting(String user) {

            String select1 = "SELECT * "
                    + "FROM user "
                    + "WHERE user='"
                    + user
                    + "';";
            List<ArrayList<String>> user_atr = workWithDB().conect()
                    .select(select1, "clusterid, userid")
                    .finish();
            String select2 = "SELECT value "
                    + "FROM lj_c"
                    + user_atr.get(0).get(0)
                    + ".userproplite2 "
                    + "WHERE upropid = '175' and userid='"
                    + user_atr.get(0).get(1)
                    + "';";
            try {
                return workWithDB().conect()
                        .select(select2, "value")
                        .finish()
                        .get(0)
                        .get(0);
            } catch (Exception ex) {
                return "n";
            }
        }

    }

    public class Friends extends DatabasesData {

        public ArrayList<String> findAllFriends(String user) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "';";
            ArrayList<String> friendid = workWithDB().conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid = '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ");";
            return workWithDB().conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
        }

        public String findFriend(String user) {
            ArrayList<String> ans = findAllFriends(user);
            String select = "select user from user "
                    + "where (user = '" + ans.get(0) + "' ";
            for (int i = 1; i < ans.size(); i++) {
                select = select + " or user = '" + ans.get(i) + "'";
            }
            select = select + ") and user like '%test%' "
                    + "and user !='" + user + "'"
                    + "and statusvis = 'V'"
                    + "and journaltype = 'P'";
            ans = workWithDB().conect()
                    .select(select, "user")
                    .finish()
                    .get(0);
            ArrayList<String> answer = new ArrayList<String>();
            for (String an : ans) {
                if (!userData().getUserPassword(an).contains("md5:")) {
                    answer.add(an);
                }
            }
            return answer.get(new Random().nextInt(ans.size()));
        }

        public ArrayList<String> findNotFriends(String user, Integer limit) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "';";
            ArrayList<String> friendid = workWithDB().conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid != '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " and userid != '" + friendid.get(i) + "'";
            }
            select2 = select2 + ") and user like '%test%' "
                    + "and statusvis = 'V'"
                    + "and journaltype = 'P'"
                    + "and statusvisdate >= adddate(now(), interval - 500 day) "
                    + "and user !='" + user + "'"
                    + "limit " + limit + ";";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            ArrayList<String> answer = new ArrayList<String>();
            for (String an : ans) {
                if (!userData().getUserPassword(an).contains("md5:")) {
                    answer.add(an);
                }
            }
            return answer;
        }

        public String findNotFriend(String user) {
            ArrayList<String> ans = findNotFriends(user, 100);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public List<ArrayList<String>> findAllFriendsInGroups(String user) {

            ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
            String select1 = "select * from friends "
                    + "where userid = "
                    + "(select userid from user where user = '" + user + "') "
                    + "and groupmask > 1;";
            List<ArrayList<String>> table = workWithDB().conect()
                    .select(select1, "friendid")
                    .select(select1, "groupmask")
                    .finish();
            ans.addAll(table);
            ArrayList<String> usernames = new ArrayList<String>();
            for (int j = 0; j < ans.get(0).size(); j++) {
                String dopselect = "select user from user where userid = '" + ans.get(0).get(j) + "';";
                String username = workWithDB().conect()
                        .select(dopselect, "user")
                        .finish()
                        .get(0)
                        .get(0);
                usernames.add(username);
            }
            ans.set(0, usernames);

            ArrayList<String> groups_list = new ArrayList<String>();

            String dop_script = "select clusterid, user from user "
                    + "where user = '" + user + "';";
            String clusterid = workWithDB().conect()
                    .select(dop_script, "clusterid")
                    .finish()
                    .get(0)
                    .get(0);

            for (int j = 0; j < ans.get(1).size(); j++) {
                char[] myCharArray = Integer
                        .toBinaryString(Integer.valueOf(ans.get(1).get(j)))
                        .toCharArray();

                String groupsid = "";
                for (int i = 1; i < myCharArray.length; i++) {
                    if (myCharArray[i] == '1') {
                        String select3 = "select groupname from lj_c" + clusterid + ".friendgroup2 "
                                + "where userid = (select userid from user where user = '" + user + "') "
                                + "and groupnum = '" + i + "';";
                        String groupname = workWithDB().conect()
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

        public String findFriendWithoutGroup(String user) {
            String select1 = "select u.user, u.userid, f.friendid from user u "
                    + "left join friends f on u.userid = f.userid "
                    + "where u.user = '" + user + "' and groupmask = '1';";
            ArrayList<String> friendid = workWithDB().conect()
                    .select(select1, "friendid")
                    .finish()
                    .get(0);
            String select2 = "select user from user "
                    + "where (userid = '" + friendid.get(0) + "' ";
            for (int i = 1; i < friendid.size(); i++) {
                select2 = select2 + " or userid = '" + friendid.get(i) + "'";
            }
            select2 = select2 + ") and user like '%test%' "
                    + "and statusvis = 'V'"
                    + "and journaltype = 'P'";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            ArrayList<String> answer = new ArrayList<String>();
            for (String an : ans) {
                if (!userData().getUserPassword(an).contains("md5:")) {
                    answer.add(an);
                }
            }
            return answer.get(new Random().nextInt(ans.size()));
        }
    }

    public class Community extends DatabasesData {

        private ArrayList<String> findUserInCommunity(String community, String type) {
            String select = "select r.targetid from user u left join reluser r on "
                    + "u.userid=r.userid where u.user = '" + community + "' and r.type='" + type + "';";
            ArrayList<String> targetid = workWithDB().conect()
                    .select(select, "targetid")
                    .finish()
                    .get(0);
            ArrayList<String> user = new ArrayList<String>();
            for (String targetid1 : targetid) {
                String select2 = "Select user from user where userid = " + targetid1;
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
            for (String an : ans) {
                String select2 = "Select user from user where userid = " + an;
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

    public class BannedUser extends DatabasesData {

        public String findUserNotInBannedList(String user) {
            String select1 = "Select userid from user where user='" + user + "'";
            String userid = workWithDB().conect()
                    .select(select1, "userid")
                    .finish()
                    .get(0)
                    .get(0);
            String select2 = "Select user from user where user like '%test%' and user !='" + user + "'"
                    + "and userid not in(select targetid from reluser where type !='B' and userid =" + userid + ") "
                    + "and statusvisdate >= adddate(now(), interval - 500 day) limit 100;";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public String findUserInBannedList(String user) {
            String select1 = "Select userid from user where user='" + user + "'";
            String userid = workWithDB().conect()
                    .select(select1, "userid")
                    .finish()
                    .get(0)
                    .get(0);
            String select2 = "select user from user where userid "
                    + "in(select targetid from reluser where type='B' and userid=" + userid + ")";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select2, "user")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }
    }

    public class PrivilegeUser extends DatabasesData {

        public String findUserWithPrivDiscovery() {
            String select = "select user.user, priv_list.privcode, priv_map.arg  from user "
                    + "left join priv_map on priv_map.userid=user.userid "
                    + "left join priv_list on priv_list.prlid=priv_map.prlid "
                    + "where priv_list.privcode='siteadmin' "
                    + "and (priv_map.arg='discovery' or priv_map.arg='*') "
                    + "and user.user like '%test%';";
            ArrayList<String> ans = workWithDB().conect()
                    .select(select, "user")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }
    }

    public class Discovery extends DatabasesData {

        public String selectIdCategories(String usualCategories) {
            int specialCategories = (Boolean.valueOf(usualCategories)) ? 0 : 1;
            String select = "select id from discovery_categories where special=" + specialCategories;
            ArrayList<String> ans = workWithDB().conect()
                    .select(select, "id")
                    .finish()
                    .get(0);
            return ans.get(new Random().nextInt(ans.size()));
        }

        public String selectKeywordCategories(String idCategory) {
            String select = "select keyword from discovery_categories where id = " + idCategory;
            String ans = workWithDB().conect()
                    .select(select, "keyword")
                    .finish().get(0).get(0);
            return ans;
        }

        public String selectNameCategory(String idCategory) {
            String select = "select name from discovery_categories where id = " + idCategory;
            String ans = workWithDB().conect()
                    .select(select, "name")
                    .finish().get(0).get(0);
            return ans;
        }

        public String selectJItemIdMagazine() {
            String select = "select max(magazine_jitemid) from magazine";
            String ans = workWithDB().conect()
                    .select(select, "magazine_jitemid*256")
                    .finish().get(0).get(0);
            return ans;
        }
    }

    public class PostsUser extends DatabasesData {

        public String getJitemidPost(String security, String user) {
            String select = "select jitemid*256+anum from lj_c2.log2 "
                    + "where journalid in(select userid from user where user='" + user + "') "
                    + "and security '" + security + "' order "
                    + "by rand() limit 1";
            String ans = workWithDB().conect()
                    .select(select, "jitemid*256+anum")
                    .finish().get(0).get(0);
            return ans;
        }
    }
}
