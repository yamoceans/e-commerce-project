package com.ecommerce.ecommerceproject;

import com.ecommerce.ecommerceproject.models.OrderListClass;
import com.ecommerce.ecommerceproject.models.ProductListModel;
import com.ecommerce.ecommerceproject.models.ProductModel;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {

    @Test
    void connectToDBTest()
    {
        Connection connection = DBUtil.connectToDB();
        assertNotNull(connection);
    }

    @Test
    void doesUserExistTestTrue()
    {
        String[] users = {"yamoceans", "admin", "TheEngineer"};
        boolean userExists;

        for(int i = 0; i < 3; i++)
        {
            userExists = DBUtil.doesUserExist(users[i]);
            assertTrue(userExists);
        }
    }

    @Test
    void doesUserExistTestFalse()
    {
        String[] users = {"NotRealUser", "ASpy", "Nully"};
        boolean userExists;

        for(int i = 0; i < 3; i++)
        {
            userExists = DBUtil.doesUserExist(users[i]);
            assertFalse(userExists);
        }
    }

    @Test
    void isEmailTakenTestTrue1()
    {
        boolean emailIsTaken = DBUtil.isEmailTaken("yam.oceans@protonmail.com");
        assertTrue(emailIsTaken);
    }
    @Test
    void isEmailTakenTestTrue2()
    {
        boolean emailIsTaken = DBUtil.isEmailTaken("yocean22@student.aau.dk");
        assertTrue(emailIsTaken);
    }
    @Test
    void isEmailTakenTestFalse1()
    {
        boolean emailIsTaken = DBUtil.isEmailTaken("FakeEmail@yay.com");
        assertFalse(emailIsTaken);
    }
    @Test
    void isEmailTakenTestFalse2()
    {
        boolean emailIsTaken = DBUtil.isEmailTaken("");
        assertFalse(emailIsTaken);
    }


    @Test
    void getUsernameWithIDTest1()
    {
        String usernameToTest = "yamoceans";
        String usernameFromDB = DBUtil.getUsernameWithID(1);

        assertEquals(usernameToTest, usernameFromDB);
    }
    @Test

    void getUsernameWithIDTest2()
    {
        String usernameToTest = "TheEngineer";
        String usernameFromDB = DBUtil.getUsernameWithID(31);

        assertEquals(usernameToTest, usernameFromDB);
    }

    @Test
    void getUsernameWithIDTest3()
    {
        String usernameToTest = "";
        String usernameFromDB = DBUtil.getUsernameWithID(1);

        assertNotEquals(usernameToTest, usernameFromDB);
    }

    @Test
    void getUserIDFromSession()
    {
        //NOTE: Cannot test this due to getUserIDFromSession requires a real HttpServletRequest
        /*MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("SESSIONID", "mON-Bk__7!fM46DoBrfRz!jHLQs8w`1*#3dPm*Dm!!@~kE^y6?");

        int userID = DBUtil.getUserIDFromSession((HttpServletRequest) request);

        assertEquals(31, userID);*/

    }

    @Test
    void getUserIDFromOrderTest1()
    {
        int userID = DBUtil.getUserIDFromOrder(3);
        assertEquals(1, userID);
    }
    @Test
    void getUserIDFromOrderTest2()
    {
        int userID = DBUtil.getUserIDFromOrder(5);
        assertEquals(15, userID);
    }
    @Test
    void getUserIDFromOrderTest3()
    {
        int userID = DBUtil.getUserIDFromOrder(0);
        assertNotEquals(1, userID);
    }

    @Test
    void getUserIDFromUsernameTest1()
    {
        int userID = DBUtil.getUserIDFromUsername("yamoceans");
        assertEquals(1, userID);
    }
    @Test
    void getUserIDFromUsernameTest2()
    {
        int userID = DBUtil.getUserIDFromUsername("admin");
        assertEquals(14, userID);
    }
    @Test
    void getUserIDFromUsernameTest3()
    {
        int userID = DBUtil.getUserIDFromUsername("TheEngineer");
        assertEquals(31, userID);
    }
    @Test
    void getUserIDFromUsernameTest4()
    {
        int userID = DBUtil.getUserIDFromUsername("");
        assertEquals(0, userID);
    }

    @Test
    void getUserRoleFromIDTest1()
    {
        String role = DBUtil.getUserRoleFromID(1);
        assertEquals("customer", role);
    }
    @Test
    void getUserRoleFromIDTest2()
    {
        String role = DBUtil.getUserRoleFromID(31);
        assertEquals("customer", role);
    }
    @Test
    void getUserRoleFromIDTest3()
    {
        String role = DBUtil.getUserRoleFromID(14);
        assertEquals("admin", role);
    }
    @Test
    void getUserRoleFromIDTest4()
    {
        String role = DBUtil.getUserRoleFromID(0);
        assertEquals(null, role);
    }

    @Test
    void getUserEmailWithUsernameTest1()
    {
        String email = DBUtil.getUserEmailWithUsername("yamoceans");
        assertEquals("yam.oceans@protonmail.com", email);
    }
    @Test
    void getUserEmailWithUsernameTest2()
    {
        String email = DBUtil.getUserEmailWithUsername("admin");
        assertEquals("yam.oceans@protonmail.com", email);
    }
    @Test
    void getUserEmailWithUsernameTest3()
    {
        String email = DBUtil.getUserEmailWithUsername("TheEngineer");
        assertEquals("yocean22@student.aau.dk", email);
    }
    @Test
    void getUserEmailWithUsernameTest4()
    {
        String email = DBUtil.getUserEmailWithUsername("NotRealEmail@gmail.com");
        assertEquals(null, email);
    }


    @Test
    void getUserEmailWithIDTest1()
    {
        String email = DBUtil.getUserEmailWithID(1);
        assertEquals("yam.oceans@protonmail.com", email);
    }
    @Test
    void getUserEmailWithIDTest2()
    {
        String email = DBUtil.getUserEmailWithID(31);
        assertEquals("yocean22@student.aau.dk", email);
    }
    @Test
    void getUserEmailWithIDTest3()
    {
        //not real userID
        String email = DBUtil.getUserEmailWithID(0);
        assertEquals(null, email);
    }

    @Test
    void getUserTotalGivenPaymentWithIDTest1()
    {
        float totalGivenPayment = DBUtil.getUserTotalGivenPaymentWithID(1);
        assertEquals(927.00, totalGivenPayment);
    }
    @Test
    void getUserTotalGivenPaymentWithIDTest2()
    {
        float totalGivenPayment = DBUtil.getUserTotalGivenPaymentWithID(31);
        assertEquals(32.00, totalGivenPayment);
    }
    @Test
    void getUserTotalGivenPaymentWithIDTest3()
    {
        float totalGivenPayment = DBUtil.getUserTotalGivenPaymentWithID(0);
        assertEquals(0, totalGivenPayment);
    }

    @Test
    void authenticateUserTest1()
    {
        boolean authenticated = DBUtil.authenticateUser("yamoceans", "Successstar1717!");
        assertTrue(authenticated);
    }
    @Test
    void authenticateUserTest2()
    {
        boolean authenticated = DBUtil.authenticateUser("TheEngineer", "Engineering101!");
        assertTrue(authenticated);
    }
    @Test
    void authenticateUserTest3()
    {
        boolean authenticated = DBUtil.authenticateUser("yamoceans", "Successstar");
        assertFalse(authenticated);
    }

    @Test
    void getSessionFromIDTest1()
    {
        String session = DBUtil.getSessionFromID(1);
        String currentSession = "to¤FW#~Iz6LZhMzxDxl§CMcI½W9OB¤w?2ylTB2h5u7Kalo:MKM";
        assertEquals(currentSession, session);
    }
    @Test
    void getSessionFromIDTest2()
    {
        String session = DBUtil.getSessionFromID(1);
        String currentSession = "V968~PQZzDTW4Nd9£";
        assertNotEquals(currentSession, session);
    }

    @Test
    void createSessionTest()
    {
        assertNotNull(DBUtil.createSession("yamoceans", 15));
    }

    @Test
    void clearExpiredSession()
    {
        //Cannot really unittest this one, however I can observe the result on
        DBUtil.clearExpiredSessions();

        //I observed that it indeed throw out all expired sessions
    }

    @Test
    void clearExpiredUserSessions()
    {
        DBUtil.clearExpiredUserSessions(1);
        //Test later when they are actually expired
        //Passed
    }

    @Test
    void removeCurrentSession()
    {
        //DBUtil.createSession("yamoceans", 15);
        //String sessionID = DBUtil.getSessionFromID(DBUtil.getUserIDFromUsername("yamoceans"));
        //DBUtil.removeCurrentSession(sessionID);
        //It passed
    }

    @Test
    void addUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        DBUtil.addUser("testUser", SecUtil.hashPassword("password"), "testemail@gmail.com", "TestCompany corp", "customer");
        assertTrue(DBUtil.doesUserExist("testUser"));
    }

    @Test
    void addOrderTest1() throws NoSuchAlgorithmException, InvalidKeySpecException {

        DBUtil.addOrder(1,0, null,"test","test","testcompany",0,"123","1234","testemail@test.com","12345678", "AC", "testaddress", "testzipcode", new Date(1999, 9, 15), "testReturn", 1);
        List<OrderListClass> orders = DBUtil.getAllOrders();

        //We observe the database and see the result has been made.
    }
    @Test
    void addOrderTest2() throws NoSuchAlgorithmException, InvalidKeySpecException {

        DBUtil.addOrder(0,0, null,"test","test","testcompany",0,"123","1234","testemail@test.com","12345678", "AC", "testaddress", "testzipcode", new Date(1999, 9, 15), "testReturn", 1);
        List<OrderListClass> orders = DBUtil.getAllOrders();

        //Passed, as the order was not created.
    }

    @Test
    void useWholeCommonPwdTest1()
    {
        Boolean isCommonPass = DBUtil.useWholeCommonPwd("Successstar1717!");
        assertFalse(isCommonPass);
    }

    @Test
    void useWholeCommonPwdTest2()
    {
        Boolean isCommonPass = DBUtil.useWholeCommonPwd("password");
        assertTrue(isCommonPass);
    }

    @Test
    void getAllProducts()
    {
        List<ProductModel> productlist = DBUtil.getAllProducts();

        for(int i = 0; i < productlist.size(); i++)
        {
            System.out.println(productlist.get(i).getID());
            System.out.println(productlist.get(i).getName());
            System.out.println(productlist.get(i).getDate());
            System.out.println(productlist.get(i).getPrice());
            System.out.println(productlist.get(i).getStatus());
            System.out.println(productlist.get(i).getAvailableAmount());
            System.out.println(productlist.get(i).getMaterialType());
            System.out.println(productlist.get(i).getProductType());
        }

        //Passed

    }

    @Test
    void completeOrderTest1CorrectUserIDAndOrderID()
    {
        DBUtil.completeOrder(15, 10, 500);
    }
    @Test
    void completeOrderTest2WrongUserID()
    {
        DBUtil.completeOrder(15, 27, 500);
        //Test updates order but not the user balance
    }
    @Test
    void completeOrderTest3OrderIDDoesNotExist()
    {
        DBUtil.completeOrder(1, 1, 500);
        //Passed
    }
    @Test
    void completeOrderTest4OrderIDDoesNotExist()
    {
        DBUtil.completeOrder(1, 1, 500);
        //Passed
    }

    @Test
    void getTotalPaymentFromOrderTest1()
    {
        float totalPayment = DBUtil.getTotalPaymentFromOrder(3);
        assertEquals(703, totalPayment);
    }
    @Test
    void getTotalPaymentFromOrderTest2()
    {
        float totalPayment = DBUtil.getTotalPaymentFromOrder(25);
        assertEquals(84, totalPayment);
    }
    @Test
    void getTotalPaymentFromOrderTest3()
    {
        float totalPayment = DBUtil.getTotalPaymentFromOrder(0);
        assertEquals(0, totalPayment);
    }

    @Test
    void getAllOrders()
    {
        List<OrderListClass> orderList = DBUtil.getAllOrders();

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }

    @Test
    void getAllActiveInactiveOrdersTestActiveOrders()
    {
        List<OrderListClass> orderList = DBUtil.getAllActiveInactiveOrders(1);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }
    @Test
    void getAllActiveInactiveOrdersTestInactiveOrders()
    {
        List<OrderListClass> orderList = DBUtil.getAllActiveInactiveOrders(0);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }


    @Test
    void getUserOrdersTestWithRealUser()
    {
        List<OrderListClass> orderList = DBUtil.getUserOrders(1);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }

    @Test
    void getUserOrdersTestWithNotRealUser()
    {
        List<OrderListClass> orderList = DBUtil.getUserOrders(0);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }

    @Test
    void getActiveInactiveUserOrdersTestActiveWithRealUser()
    {
        List<OrderListClass> orderList = DBUtil.getActiveInactiveUserOrders(1, 1);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }
    @Test
    void getActiveInactiveUserOrdersTestInactiveWithRealUser()
    {
        List<OrderListClass> orderList = DBUtil.getActiveInactiveUserOrders(1, 0);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }
    @Test
    void getActiveInactiveUserOrdersTestWithFakeUser()
    {
        List<OrderListClass> orderList = DBUtil.getActiveInactiveUserOrders(0, 1);

        for(int i = 0; i < orderList.size(); i++)
        {
            System.out.println(orderList.get(i).getOrderID());
            System.out.println(orderList.get(i).getUserID());
            System.out.println(orderList.get(i).getCreatedByID());
            System.out.println(orderList.get(i).getProductList());
            System.out.println(orderList.get(i).getFirstName());
            System.out.println(orderList.get(i).getLastName());
            System.out.println(orderList.get(i).getCompanyName());
            System.out.println(orderList.get(i).getTotalPayment());
            System.out.println(orderList.get(i).getRegistrationNumber());
            System.out.println(orderList.get(i).getBankAccountNumber());
            System.out.println(orderList.get(i).getEmail());
            System.out.println(orderList.get(i).getPhoneNumber());
            System.out.println(orderList.get(i).getState());
            System.out.println(orderList.get(i).getToAddress());
            System.out.println(orderList.get(i).getZipCode());
            System.out.println(orderList.get(i).getCreationDate());
            System.out.println(orderList.get(i).getReturnAddress());
            System.out.println(orderList.get(i).getIsActive());
        }
    }

}