package com.ecommerce.ecommerceproject;

import com.ecommerce.ecommerceproject.models.LoginModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;

@Configuration
@EnableWebSecurity
public class SecUtil extends WebSecurityConfiguration
{
    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public SecUtil() {
        super();
    }
    
    public static String OTP;
    public static int loginAttemptCounter = 0;
    public static boolean isLoginLocked = false;

    private static final int TIMEOTP = 300000;
    private static final int TIMELOGINLOCK = 300000;

    private static int charLengthRequirement = 8;
    private static int numCapRequirement = 1;
    private static int numSpecRequirement = 1;
    private static int numDigitRequirement = 2;
    private static Boolean useCap = true;
    private static Boolean useSpec = true;
    private static Boolean useDigit = true;
    private static boolean useNoCommonPwd = true;
    private static boolean canContainSpace = true;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll());

        return http.build();
    }



    // ----------- Password Related Security ----------- //

    public static String hashPassword(String password)
    {
        String result = encoder.encode(password);
        System.out.println("Hashed Password: " + result);

        return result;
    }

    // ------------------------------------------------- //

    // ------------- Login related Security -------------- //
    public static String genOTP(int passLength)
    {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!½§#¤%&/(I)O=?`^*-_:;~|}][{€$£@<>\\/,.";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passLength; i++)
        {
            sb.append(values.charAt(random.nextInt(1, values.length())));
        }

        System.out.println("OTP: " + sb);
        return sb.toString();
    }

    public static String genSessionID(int sessionLength)
    {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!½§#¤%&IO=?`^*-_:~|€$£@";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sessionLength; i++)
        {
            sb.append(values.charAt(random.nextInt(1, values.length())));
        }

        System.out.println("SessionID: " + sb);
        return sb.toString();
    }


    public static void timerOTP()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                SecUtil.clearOTP();
            }
        };

        timer.schedule(task, TIMEOTP); //Delay is in milliseconds. 300000ms = 5 minutes

    }

    public static void timerLockedLogin()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                SecUtil.isLoginLocked = false;
                SecUtil.loginAttemptCounter = 0;
            }
        };

        timer.schedule(task, TIMELOGINLOCK); //Delay is in milliseconds. 300000ms = 5 minutes

    }


    public static void sendTwoFactorAuth(String receiver)
    {
        OTP = genOTP(10);

        EmailService.sendMail("theoceansfamily@gmail.com", "mfydlapaoezabadu", receiver, "Authentication", "Hello.\nHere is your passcode:\n" + OTP);

    }

    public static void clearOTP()
    {
        OTP = null;
        System.out.println("OTP has been cleared");
    }

    // ------------------------------------------------- //

    // ------------- Policy Related Security -------------- //
    public static String setPolicy(boolean needsCap, boolean needsSpec,
                                   boolean needsDigit, boolean needsNoCommonPwd, boolean allowSpace,
                                   int charLen, int capReq, int specReq, int numReq)
    {
        useCap = needsCap;
        useSpec = needsSpec;
        useDigit = needsDigit;
        canContainSpace = allowSpace;
        useNoCommonPwd = needsNoCommonPwd;

        charLengthRequirement = charLen;
        numCapRequirement = capReq;
        numSpecRequirement = specReq;
        numDigitRequirement = numReq;

        Boolean[] currentPolicyAttributes = {useCap, useSpec, useDigit, useNoCommonPwd};
        String[] policySentences = {"Minimum " + numCapRequirement + " capital character(s)\n"
                                    , "Minimum " + numSpecRequirement + " Special character(s)\n"
                                    , "Minimum " + numDigitRequirement + " digit(s)\n"
                                    , "No common password (e.g \"password\", \"123456789\", \"password123\")"
                                    , "No dictionary word(s)"};


        //Build string for policy to show on registration page
        StringBuilder policy = new StringBuilder();

        policy.append("* Minimum " + charLen + " characters\n");

        for(int i = 0; i < 4; i++)
        {
            if(currentPolicyAttributes[i])
            {
                policy.append("* " + policySentences[i]);
            }
        }

        return policy.toString();
    }

    public static Boolean isValidPassword(String password)
    {
        String minLength = String.valueOf(charLengthRequirement);
        String regexDigit = useDigit ? "(?=.*[0-9]{" + numDigitRequirement + "})" : "";
        String regexLower = "(?=.*[a-z])";
        String regexUpper = useCap ? "(?=.*[A-Z]{" + numCapRequirement + "})" : "";
        String regexSpecial = useSpec ? "(?=.*[!#¤%&/()=?@£$€{}~|*`^,.<>'¨_]{" + numSpecRequirement +"})" : "";
        String regexNoSpace = canContainSpace ? "" : "(?=\\S+$)";
        String regexMinChar = ".{" + minLength + ",}";

        String fullRegex =  "^" + regexDigit + regexLower + regexUpper +
                            regexSpecial + regexNoSpace + regexMinChar + "$";

        boolean hasCommonPassword = useNoCommonPwd ? DBUtil.useWholeCommonPwd(password) : false;

        System.out.println(fullRegex);
        System.out.println(password.matches(fullRegex));

        if(password.matches(fullRegex) && !hasCommonPassword)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    // ------------------------------------------------- //

}
