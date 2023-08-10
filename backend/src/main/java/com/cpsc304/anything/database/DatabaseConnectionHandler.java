package com.cpsc304.anything.database;

import com.cpsc304.anything.Models.*;
import com.cpsc304.anything.Models.Collection;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    //private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    //private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    private HashMap<String, String> allowedPostCols = new HashMap<>();

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        allowedPostCols.put("postID", "P.postID");
        allowedPostCols.put("userID", "W.userID");
        allowedPostCols.put("categoryID", "C.categoryID");
        allowedPostCols.put("categoryName", "C.categoryName");
        allowedPostCols.put("title", "P.title");
        allowedPostCols.put("content", "P.content");
        allowedPostCols.put("alias", "W.alias");
        allowedPostCols.put("publishDate", "P.publishDate");
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Closed connection to Oracle.");
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public String userLogin(String email, String password) {
        try {
            System.out.println(email);
            System.out.println(password);
            PreparedStatement ps = connection.prepareStatement("SELECT userName FROM \"User\" WHERE email = ? AND userPassword = ?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            String result = null;
            if (rs.next()) {
                System.out.println("Has user");
                result = rs.getString(1);
                System.out.println(rs.getString(1));
            }
            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public String userRegistration(int userID, String email, String userName, String userPassword) {
        try {
            System.out.println(email);
            System.out.println(userName);
            System.out.println(userPassword);
            User user = new User(userID, userName, email, userPassword, null);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"User\" VALUES (?,?,?,?,?)");
            java.sql.Date sqlDate = new java.sql.Date(user.getRegistrationDate().getTime());
            ps.setInt(1, user.getUserID());
            ps.setDate(2, sqlDate);
            ps.setString(3, userName);
            ps.setString(4, email);
            ps.setString(5, userPassword);

            ResultSet rs = ps.executeQuery();
            connection.commit();
            String result = null;

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public User[] userList() {
        ArrayList<User> result = new ArrayList<User>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM \"User\"  LEFT JOIN \"Writer\" ON \"User\".userID = \"Writer\".userID");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("userID"),
                        rs.getString("userName"),
                        rs.getString("email"),
                        rs.getString("userPassword"),
                        rs.getString("alias"));
                Date registrationDate = rs.getDate("registrationDate");
                user.setRegistrationDate(registrationDate);

                result.add(user);
                System.out.println(user.userID);
            }

            rs.close();
            ps.close();
            return result.toArray(new User[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public Post[] postList(String columns) {
        ArrayList<Post> result = new ArrayList<>();
        StringBuilder queryStr = new StringBuilder("SELECT");
        int numSelected = 0;
        String[] cols = columns.split(",");
        HashSet<String> selectedCols = new HashSet<>();
        for (String col : cols) {
            String mappedCol = allowedPostCols.get(col);
            if (mappedCol != null) {
                if (numSelected > 0) {
                    queryStr.append(", ");
                } else {
                    queryStr.append(" ");
                }
                queryStr.append(mappedCol);
                numSelected++;
                selectedCols.add(col);
            }
        }
        if (numSelected == 0) {
            return null;
        }
        queryStr.append(" FROM \"Post\" P JOIN \"Writer\" W ON P.userID=W.userID JOIN \"Category\" C ON P.categoryID=C.categoryID");

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryStr.toString());

            while (rs.next()) {
                Post post = new Post(selectedCols.contains("postID") ? rs.getInt("postID") : null,
                        selectedCols.contains("title") ? rs.getString("title") : null,
                        selectedCols.contains("content") ? rs.getString("content") : null,
                        selectedCols.contains("categoryID") ? rs.getInt("categoryID") : null,
                        selectedCols.contains("categoryName") ? rs.getString("categoryName") : null,
                        selectedCols.contains("userID") ? rs.getInt("userID") : null,
                        selectedCols.contains("alias") ? rs.getString("alias") : null,
                        selectedCols.contains("publishDate") ? rs.getDate("publishDate") : null);
                result.add(post);
                System.out.println(post.postID);
            }

            rs.close();
            stmt.close();
            return result.toArray(new Post[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public String beWriter(int userID, String alias) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Writer\" VALUES (?,?)");
            ps.setInt(1, userID);
            ps.setString(2, alias);

            ResultSet rs = ps.executeQuery();
            connection.commit();
            String result = null;

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public Post getPost(int postID) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Post\" JOIN \"Writer\" ON \"Post\".userID=\"Writer\".userID JOIN \"Category\" ON \"Post\".categoryID=\"Category\".categoryID WHERE postID=(?)");
            ps.setInt(1, postID);

            Post post = null;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"),
                        rs.getDate("publishDate"));
            }

            rs.close();
            ps.close();
            return post;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public Category[] categoryList() {
        ArrayList<Category> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Category\"");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(rs.getInt("categoryID"),
                        rs.getString("categoryName"));

                result.add(category);
            }
            rs.close();
            ps.close();
            return result.toArray(new Category[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public Collection[] collectionList() {
        // return the list of all collections with the alias
        ArrayList<Collection> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM \"Collection\" JOIN \"Writer\" on \"Collection\".userID = \"Writer\".userID");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Collection coll = new Collection(rs.getInt("collectionID"),
                        rs.getString("title"),
                        rs.getInt("userID"),
                        rs.getString("alias"));

                result.add(coll);
            }

            rs.close();
            ps.close();
            return result.toArray(new Collection[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public Post[] postInColl(int collectionID) {
        // return the list of posts in this collection
        ArrayList<Post> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM \"Post\" JOIN \"CollectionContains\" ON \"Post\".postID = \"CollectionContains\".postID JOIN \"Category\" ON \"Post\".categoryID = \"Category\".categoryID JOIN \"Writer\" ON \"Post\".userID = \"Writer\".userID WHERE \"CollectionContains\".collectionID =(?)");

            ps.setInt(1, collectionID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("collectionID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"),
                        rs.getDate("publishDate"));
                result.add(post);
            }

            rs.close();
            ps.close();
            return result.toArray(new Post[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public BookmarkList[] getBookmarkLists(int userID) {
        ArrayList<BookmarkList> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM \"BookmarkList\" WHERE userID = ?");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookmarkList list = new BookmarkList(rs.getInt("userID"),
                        rs.getInt("listID"),
                        rs.getString("listName"));

                result.add(list);
            }

            rs.close();
            ps.close();
            return result.toArray(new BookmarkList[result.size()]);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public Post[] getPostsInBookmarkList(int userID, int listID) {
        ArrayList<Post> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT P.postID, P.title, P.publishDate, P.userID, P.categoryID, C.categoryName, W.alias FROM \"Post\" P, \"BookmarkListContains\" B, \"Writer\" W, \"Category\" C WHERE P.postID = B.postID AND P.userID = W.userID AND P.categoryID = C.categoryID AND B.userID = ? AND B.listID = ?");
            ps.setInt(1, userID);
            ps.setInt(2, listID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        null,
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"),
                        rs.getDate("publishDate"));

                result.add(post);
            }

            rs.close();
            ps.close();
            return result.toArray(new Post[result.size()]);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public boolean addBookmarkList(int userID, String listName) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO \"BookmarkList\" VALUES (?, (SELECT COALESCE(MAX(listID), 0) FROM \"BookmarkList\" WHERE userID = ?) + 1, ?)");
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            ps.setString(3, listName);
            int rowCount = ps.executeUpdate();
            connection.commit();


            ps.close();
            return rowCount == 1;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    public AvgViewResult[] getAvgViewsForPopularWriters() {
        ArrayList<AvgViewResult> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT R.userID, AVG(R.viewCount) AS avgViews FROM \"ReadCount\" R WHERE R.userID IN (SELECT userID FROM \"Writer\" W, \"Follows\" F WHERE F.followeeID = W.userID GROUP BY W.userID HAVING COUNT(*) >= 2) GROUP BY R.userID");

            while (rs.next()) {
                AvgViewResult r = new AvgViewResult(rs.getInt("userID"),
                        rs.getInt("avgViews"));

                result.add(r);
            }

            rs.close();
            stmt.close();
            return result.toArray(new AvgViewResult[result.size()]);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public SpamResult[] getPotentialSpamUsers() {
        ArrayList<SpamResult> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT C.userID, COUNT(*) AS numComments FROM \"Comment\" C GROUP BY C.userID HAVING COUNT(*) >= 2 AND 0 = (SELECT COUNT(*) FROM \"Follows\" F WHERE F.followeeID = C.userID)");

            while (rs.next()) {
                SpamResult r = new SpamResult(rs.getInt("userID"),
                        rs.getInt("numComments"));

                result.add(r);
            }

            rs.close();
            stmt.close();
            return result.toArray(new SpamResult[result.size()]);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public void login() {
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("ORACLE_USERNAME");
        String password = dotenv.get("ORACLE_PASSWORD");
        String url = dotenv.get("ORACLE_URL");

        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void dropBranchTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals("branch")) {
                    stmt.execute("DROP TABLE branch");
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public Count[] groupBy() {
        // count of posts in each category group by categoryName

        ArrayList<Count> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT \"Category\".categoryName, COUNT(*) AS \"postCount\" FROM \"Category\" JOIN \"Post\" ON \"Post\".categoryID=\"Category\".categoryID GROUP BY \"Category\".categoryName");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Count count = new Count(rs.getString("categoryName"),
                        rs.getInt("postCount"));
                result.add(count);
            }

            rs.close();
            ps.close();
            return result.toArray(new Count[result.size()]);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public Post[] filterPost(int categoryID) {
        ArrayList<Post> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Post\" JOIN \"Writer\" ON \"Post\".userID=\"Writer\".userID JOIN \"Category\" ON \"Post\".categoryID=\"Category\".categoryID WHERE \"Post\".categoryID = (?)");
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"),
                        rs.getDate("publishDate"));
                result.add(post);
                System.out.println(post.postID);
            }

            rs.close();
            ps.close();
            return result.toArray(new Post[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }


    public void deletePost(int postID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM \"Post\" WHERE postID=(?)");
            ps.setInt(1, postID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + "Post" + postID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public boolean editPost(int postID, String title, String content) {

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE \"Post\" SET title= ?, content= ? WHERE postID= ?");
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, postID);

            int rowCount = ps.executeUpdate();
            connection.commit();

            ps.close();
            return rowCount == 1;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }


    //Find all users who make posts in all categories
    public UserAllCate[] division() {
        ArrayList<UserAllCate> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT U.userID, U.email, U.userName, W.alias FROM \"User\" U, \"Writer\" W WHERE NOT EXISTS (SELECT categoryID FROM \"Category\" C WHERE NOT EXISTS (SELECT categoryID FROM \"Post\" P WHERE U.userID = P.userID AND C.categoryID = P.categoryID and U.userID=W.userID))");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserAllCate user = new UserAllCate(rs.getInt("userID"),
                        rs.getString("alias"),
                        rs.getString("userName"),
                        rs.getString("email")
                );
                result.add(user);
                System.out.println(user.userID);
            }

            rs.close();
            ps.close();
            return result.toArray(new UserAllCate[result.size()]);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }
}