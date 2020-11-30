package com.example.assignment2.websecurity;

import com.example.assignment2.SpringApplicationContext;

public class SecurityConstants {
	public static final long EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/user";
	public static final String TOKEN_SECRET = "vissers";
	public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/users/password-reset";
    public static final String H2_CONSOLE = "/h2-console/**";
	
	public static String getTokenSecret()
	{
        var appProperties = SpringApplicationContext.getBean("AppProperties");
        return ((SecurityConstants) appProperties).getTokenSecret();
	}
}
