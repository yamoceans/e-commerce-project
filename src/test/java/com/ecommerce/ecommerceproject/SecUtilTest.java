package com.ecommerce.ecommerceproject;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.internet.AddressException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecUtilTest {

    @Test
    void hashPasswordTest()
    {
        // To see if this works, we use the function that then hashes and salts the password,
        // then we perform matches() to see if the password and the hashed+salted match.

        String hashAndSalted = SecUtil.hashPassword("Successstar1717!");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        assertTrue(encoder.matches("Successstar1717!", hashAndSalted));
    }

    @Test
    void genOTPTest1()
    {
        assertNotNull(SecUtil.genOTP(10));
    }
    @Test
    void genOTPTest2()
    {
        assertEquals("", SecUtil.genOTP(0));
    }

    @Test
    void genSessionIDTest1()
    {
        assertNotNull(SecUtil.genSessionID(50));
    }
    @Test
    void genSessionIDTest2()
    {
        assertEquals("", SecUtil.genSessionID(0));
    }

    @Test
    void timerOTPTest()
    {
        //How do I test this?
        //SecUtil.timerOTP();
    }

    @Test
    void timerLockedLogin()
    {
        //Come back to this
        //SecUtil.timerLockedLogin();

    }

    @Test
    void sendTwoFactorAuthTest1()
    {
        SecUtil.sendTwoFactorAuth("yam.oceans@protonmail.com");
        //Passed
    }
    @Test
    void sendTwoFactorAuthTest2()
    {
        SecUtil.sendTwoFactorAuth("yocean22@student.aau.dk");
        //Passed
    }
    @Test
    void sendTwoFactorAuthTest3()
    {
        assertThrows(RuntimeException.class, () -> {
            SecUtil.sendTwoFactorAuth("");
        });
    }

    @Test
    void clearOTP()
    {
        SecUtil.OTP = SecUtil.genOTP(5);
        String OTP = SecUtil.OTP;
        System.out.println("OTP pre clearOTP(): " + OTP);
        SecUtil.clearOTP();
        System.out.println("OTP post clearOTP(): " + SecUtil.OTP);
        assertEquals(null, SecUtil.OTP);
    }

    @Test
    void setPolicyTest1()
    {
        assertEquals("* Minimum 15 characters\n" +
                "* Minimum 1 capital character(s)\n" +
                "* Minimum 1 Special character(s)\n" +
                "* Minimum 1 digit(s)\n" +
                "* No common password (e.g \"password\", \"123456789\", \"password123\")", SecUtil.setPolicy(true, true, true, true, true, 15, 1, 1, 1));
    }
    @Test
    void setPolicyTest2()
    {
        assertEquals("* Minimum 5 characters\n" +
                "* Minimum 1 Special character(s)\n" +
                "* Minimum 1 digit(s)\n" +
                "* No common password (e.g \"password\", \"123456789\", \"password123\")", SecUtil.setPolicy(false, true, true, true, true, 5, 1, 1, 1));
    }


    @Test
    void isValidPasswordTest1()
    {
        //We need to first set a policy
        SecUtil.setPolicy(
                true,
                true,
                true,
                true,
                false,
                15,
                1,
                1,
                1);

        String password = "Successstar1717!";
        assertTrue(SecUtil.isValidPassword(password));
    }
    @Test
    void isValidPasswordTest2()
    {
        //We need to first set a policy
        SecUtil.setPolicy(
                true,
                true,
                true,
                true,
                false,
                15,
                1,
                1,
                1);

        String password = "Successstar";
        assertFalse(SecUtil.isValidPassword(password));
    }

    @Test
    void isValidPasswordTest3()
    {
        //We need to first set a policy
        SecUtil.setPolicy(
                true,
                true,
                true,
                true,
                false,
                15,
                1,
                1,
                1);

        String password = "password";
        assertFalse(SecUtil.isValidPassword(password));
    }
    @Test
    void isValidPasswordTest4()
    {
        //We need to first set a policy
        SecUtil.setPolicy(
                true,
                true,
                true,
                true,
                false,
                15,
                1,
                1,
                1);

        String password = "";
        assertFalse(SecUtil.isValidPassword(password));
    }

}