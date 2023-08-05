package com.cpsc304.anything.database;

import com.cpsc304.anything.Models.Post;
import com.cpsc304.anything.Models.User;
import io.github.cdimascio.dotenv.Dotenv;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
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

            while(rs.next()) {
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
            User user = new User(userID, userName, email, userPassword,null);
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
                    "SELECT \"User\".userID, \"User\".registrationDate, \"User\".userName, \"User\".email, \"User\".userPassword, \"Writer\".alias FROM \"User\"  LEFT JOIN \"Writer\" ON \"User\".userID = \"Writer\".userID");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
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

    public Post[] postList() {
        ArrayList<Post> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Post\" JOIN \"Writer\" ON \"Post\".userID=\"Writer\".userID JOIN \"Category\" ON \"Post\".categoryID=\"Category\".categoryID");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Post post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"));
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

    public void beWriter(int userID) {

    }

    public void deleteBranch(int branchId) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
            ps.setInt(1, branchId);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateBranch(int id, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Post getPost(int postID) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Post\" JOIN \"Writer\" ON \"Post\".userID=\"Writer\".userID JOIN \"Category\" ON \"Post\".categoryID=\"Category\".categoryID WHERE postID=(?)");
            ps.setInt(1, postID);

            Post post = null;
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                post = new Post(rs.getInt("postID"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("categoryID"),
                        rs.getString("categoryName"),
                        rs.getInt("userID"),
                        rs.getString("alias"));
                Date publishTime = rs.getTime("publishTime");
                post.setPublishDate(publishTime);
                System.out.println(post.getPostID());
            }

            rs.close();
            ps.close();
            return post;
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
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void databaseSetup() {
        dropBranchTableIfExists();

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void dropBranchTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("branch")) {
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
}
