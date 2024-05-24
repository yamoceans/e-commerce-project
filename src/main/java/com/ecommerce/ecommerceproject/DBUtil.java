package com.ecommerce.ecommerceproject;
import com.ecommerce.ecommerceproject.models.OrderListClass;
import com.ecommerce.ecommerceproject.models.ProductModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBUtil {

    public static String lastFoundPwd= "";

    public static Connection connectToDB() {
        try
        {
            String[] readCredentialsFromFile = DBUtil.getDBCredentials();
            String url = readCredentialsFromFile[0];
            String username = readCredentialsFromFile[1];
            String password = readCredentialsFromFile[2];


            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to connect to database");
            return null;
        }
    }

    private static String[] getDBCredentials() throws FileNotFoundException {
        File credFile = new File("C:\\Users\\yamoc\\IdeaProjects\\ecommerce-project\\src\\main\\resources\\dbCredentials.txt");
        Scanner reader = new Scanner(credFile);
        String[] retrievedCredentials = new String[3];

        for(int i = 0; i < 3; i++)
        {
            retrievedCredentials[i] = reader.nextLine();
        }

        return retrievedCredentials;
    }


    public static Boolean doesUserExist(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";

        try {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();


            if(!resultSet.next())
            {
                return false;
            }
            else if (username.equals(resultSet.getString(1)))
            {
                System.out.println("DB username: " + resultSet.getString(1));
                System.out.println("User Found");
                return true;
            }
            else
            {
                System.out.println("User Not Found");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    public static Boolean isEmailTaken(String email) {
        String sql = "SELECT email FROM users WHERE email = ?";

        try {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next())
            {
                return false;
            }
            else if (email.equals(resultSet.getString(1)))
            {
                System.out.println("DB email: " + resultSet.getString(1));
                System.out.println("Email Found");
                return true;
            }
            else
            {
                System.out.println("Email Not Found");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    public static String getUsernameWithID(int userID) {

        String sql = "SELECT username FROM users WHERE user_id = ?";

        try
        {
            if(userID != 0)
            {
                PreparedStatement statement = connectToDB().prepareStatement(sql);
                statement.setInt(1, userID);

                ResultSet resultSet = statement.executeQuery();

                if(!resultSet.next())
                {
                    return null;
                }

                System.out.println("DB username: " + resultSet.getString(1));

                return resultSet.getString(1);

            }
            else
            {
                return "";
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Could not retrieve username with ID");
            return "";
        }

    }


    public static int getUserIDFromSession(HttpServletRequest request) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String sql = "SELECT user_id FROM sessions WHERE session_id = ? AND end_time > ?";

        Cookie[] cookies = request.getCookies();
        Cookie sessionCookie = null;

        for(int i = 0; i < cookies.length; i++)
        {
            if(cookies[i].getName().equals("SESSIONID"))
            {
                sessionCookie = cookies[i];
            }
        }

        try
        {
            if(sessionCookie != null)
            {
                PreparedStatement statement = connectToDB().prepareStatement(sql);
                statement.setString(1, sessionCookie.getValue());
                statement.setString(2, localDateTime.toString());

                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                System.out.println("DB user ID: " + resultSet.getInt(1));

                return resultSet.getInt(1);
            }
            else
            {
                System.out.println("Could not obtain User ID from Session");
                return 0;
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
            return 0;
        }

    }

    public static int getUserIDFromOrder(int orderID) {

        String sql = "SELECT user_id FROM orders WHERE order_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, orderID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);

        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Could not optain user ID from order");
            return 0;
        }

    }



    public static int getUserIDFromUsername(String username) {

        String sql = "SELECT user_id FROM users WHERE username = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("DB user ID: " + resultSet.getInt(1));

            return resultSet.getInt(1);

        }
        catch (SQLException e)
        {
            System.out.println(e);
            return 0;
        }

    }

    public static String getUserRoleFromID(int userID) {

        String sql = "SELECT role FROM users WHERE user_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("DB user Role: " + resultSet.getString(1));

            if(resultSet.getString(1) == null)
            {
                return "";
            }
            else
            {
                return resultSet.getString(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public static String getUserEmailWithUsername(String username) {

        String sql = "SELECT email FROM users WHERE username = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("DB user email: " + resultSet.getString(1));

            return resultSet.getString(1);

        }
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public static String getUserEmailWithID(int userID) {

        String sql = "SELECT email FROM users WHERE user_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("DB user Email: " + resultSet.getString(1));

            return resultSet.getString(1);

        }
        catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }


    public static float getUserTotalGivenPaymentWithID(int userID) {

        String sql = "SELECT total_given_payment FROM users WHERE user_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("DB user total given payment: " + resultSet.getFloat(1));

            return resultSet.getFloat(1);

        }
        catch (SQLException e)
        {
            System.out.println(e);
            return 0;
        }
    }


    public static Boolean authenticateUser(String username, String password) {
        String sql = "SELECT user_id, username, password, email, company_name FROM users WHERE username = ?";

        try {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            System.out.println("DB user_id: " + resultSet.getInt(1));
            System.out.println("DB username: " + resultSet.getString(2));
            System.out.println("DB password: " + resultSet.getString(3));
            System.out.println("DB email: " + resultSet.getString(4));
            System.out.println("DB company_name: " + resultSet.getString(5));


            if (username.equals(resultSet.getString(2)) &&
                    SecUtil.encoder.matches(password, resultSet.getString(3))) {
                System.out.println("User authenticated");
                return true;
            } else {
                System.out.println("Unable to Find User");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static String getSessionFromID(int userID)
    {
        //TODO: Rename and analyse

        String sql = "SELECT session_id FROM sessions WHERE user_id = ?";
        String sessionID;

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            sessionID = resultSet.getString(1);

            if(sessionID != null)
            {
                System.out.println("Session Found: " + sessionID);
            }

            return sessionID;
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Unable to find session");
            return null;
        }
    }

    public static String createSession(String username, int minutes)
    {
        String sql = "INSERT INTO sessions (user_id, start_time, end_time, session_id) VALUES (?, ?, ?, ?)";

        String sessionID = SecUtil.genSessionID(50);

        LocalDateTime dateTime = LocalDateTime.now();

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);

            statement.setInt(1, DBUtil.getUserIDFromUsername(username));
            statement.setString(2, dateTime.toString());
            statement.setString(3, dateTime.plusMinutes(minutes).toString());
            statement.setString(4, sessionID);

            statement.executeUpdate();

            System.out.println("Session created");

            return sessionID;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "";
        }

    }

    public static void clearExpiredSessions()
    {
        LocalDateTime dateTime = LocalDateTime.now();

        String sql = "DELETE FROM sessions WHERE end_time < ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);

            statement.setString(1, dateTime.toString());

            statement.executeUpdate();

            System.out.println("Expired Sessions Cleared");

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Unable to clear session(s)");
        }

    }

    public static void clearExpiredUserSessions(int userID)
    {
        LocalDateTime dateTime = LocalDateTime.now();

        String sql = "DELETE FROM sessions WHERE user_id = ? AND end_time < ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);

            statement.setInt(1,userID);
            statement.setString(2, dateTime.toString());

            statement.executeUpdate();

            System.out.println("Expired Sessions with User ID: " + userID + " Cleared");

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Unable to Clear Session(s)");
        }

    }


    public static void removeCurrentSession(String sessionID)
    {
        String sql = "DELETE FROM sessions WHERE session_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setString(1, sessionID);

            statement.executeUpdate();

            System.out.println("Session Removed");

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Unable to remove session");
        }

    }



    public static void addUser(String username, String password, String email, String companyName, String userRole)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String hashed = SecUtil.hashPassword(password);
        String sql = "INSERT INTO users (username, password, email, company_name, total_given_payment, creation_date, role) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            if (!doesUserExist(username)) {
                PreparedStatement statement = connectToDB().prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, hashed);
                statement.setString(3, email);
                statement.setString(4, companyName);
                statement.setFloat(5, 0);
                statement.setDate(6, Date.valueOf(java.time.LocalDate.now()));
                statement.setString(7, userRole);

                statement.executeUpdate();

                System.out.println("User created");

            } else {
                System.out.println("User already exists");
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Unable to create user");
        }
    }

    public static void addOrder(int userID, int createdByID, String productList, String firstName, String lastName,
                                String companyName, float totalPayment, String registrationNumber, String bankAccountNumber, String email, String phoneNumber, String state,
                                String toAddress, String zipCode, Date creationDate, String returnAddress, int isActive)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String sql = "INSERT INTO orders" +
                " (user_id, created_by_id, product_list, first_name, last_name, company_name, " +
                "total_payment, registration_number, bank_account_number, email, phone_number, state, to_address, zip_code, creation_date, return_address, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setString(3, productList);

            if(createdByID != 0)
            {
                statement.setInt(2, createdByID);
            }
            else
            {
                statement.setNull(2, 0);
            }

            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setString(6, companyName);
            statement.setFloat(7, totalPayment);
            statement.setString(8, registrationNumber);
            statement.setString(9, bankAccountNumber);
            statement.setString(10, email);
            statement.setString(11, phoneNumber);
            statement.setString(12, state);
            statement.setString(13, toAddress);
            statement.setString(14, zipCode);
            statement.setDate(15, creationDate);
            statement.setString(16, returnAddress);
            statement.setInt(17, isActive);

            statement.executeUpdate();
            System.out.println("Order created");
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Unable to create order");
        }
    }

    public static Boolean useWholeCommonPwd(String desiredStr) {
        Boolean containsPwd = false;
        String sql = "SELECT * FROM common_passwords";

        try {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                if (desiredStr.equals(resultSet.getString(1))) {
                    System.out.println("Contains common password");
                    lastFoundPwd = resultSet.getString(1);
                    containsPwd = true;
                    break;
                } else {
                    System.out.println("Does not contain common password");
                    containsPwd = false;
                }
            }
            return containsPwd;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static List<ProductModel> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<ProductModel> products = new ArrayList<ProductModel>();

        try {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                products.add(new ProductModel(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getFloat(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));
            }

            return products;
        } catch (SQLException e) {
            System.out.println(e);
            return products;
        }
    }

    public static void completeOrder(int userID, int orderID, float totalGivenPayment)
    {
        System.out.println(orderID);
        String sql = "UPDATE orders SET is_active = 0 WHERE order_id = ?";
        String sqlCustomerTotalGivenPayment = "UPDATE users SET total_given_payment = total_given_payment + " + totalGivenPayment + " WHERE user_id = ?";

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, orderID);
            statement.executeUpdate();

            statement = connectToDB().prepareStatement(sqlCustomerTotalGivenPayment);
            statement.setInt(1, userID);
            statement.executeUpdate();

            System.out.println("Order completed");
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Cannot complete order");
        }
    }

    public static float getTotalPaymentFromOrder(int orderID)
    {
        String sql = "SELECT total_payment FROM orders WHERE order_id = ?";


        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getFloat(1);
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Could not retrieve total payment of order");
            return 0;
        }

    }

    public static List<OrderListClass> getAllOrders()
    {
        String sql = "SELECT * FROM orders";
        List<OrderListClass> orders = new ArrayList<OrderListClass>();

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                orders.add(new OrderListClass(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getDate(16),
                        resultSet.getString(17),
                        resultSet.getInt(18)));
            }

            return orders;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Cannot fetch orders");
            return orders;
        }
    }

    public static List<OrderListClass> getAllActiveInactiveOrders(int isActive)
    {
        String sql = "SELECT * FROM orders WHERE is_active = " + isActive;
        List<OrderListClass> orders = new ArrayList<OrderListClass>();

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                orders.add(new OrderListClass(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getDate(16),
                        resultSet.getString(17),
                        resultSet.getInt(18)));
            }

            return orders;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Cannot fetch orders");
            return orders;
        }
    }


    public static List<OrderListClass> getUserOrders(int userID)
    {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<OrderListClass> orders = new ArrayList<OrderListClass>();

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                orders.add(new OrderListClass(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getDate(16),
                        resultSet.getString(17),
                        resultSet.getInt(18)));
            }

            return orders;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Cannot fetch user orders");
            return orders;
        }
    }

    public static List<OrderListClass> getActiveInactiveUserOrders(int userID, int isActive)
    {
        String sql = "SELECT * FROM orders WHERE user_id = ? AND is_active = " + isActive;
        List<OrderListClass> orders = new ArrayList<OrderListClass>();

        try
        {
            PreparedStatement statement = connectToDB().prepareStatement(sql);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                orders.add(new OrderListClass(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15),
                        resultSet.getDate(16),
                        resultSet.getString(17),
                        resultSet.getInt(18)));
            }

            return orders;
        }
        catch (SQLException e)
        {
            System.out.println(e);
            System.out.println("Cannot fetch user orders");
            return orders;
        }
    }
}
