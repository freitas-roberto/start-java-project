package br.eti.freitas.startproject.infrastructure.constant;

public class SecurityConstant {

	public static String USER = "user";
	public static String SECURITY_JWT_HEADER_AUTHENTICATION = "Authorization";
	public static String SECURITY_JWT_TOKEN_PREFIX = "Bearer ";
	public static String SECURITY_JWT_DEFAULT_ROLE_PREFIX="ROLE_";
	
	/* config for autheticate endpoints */
	public static String SECURITY_JWT_URI_AUTHENTICATE="/api/auth/**";
	public static String SCURIRTY_JWT_PACKAGE_AUTHENTICATE="br.eti.freitas.startproject.infrastructure.controller";
	
	/* config for application endpoints */
	public static String SECURITY_JWT_URI_APPLICATION="/api/v1/**";
	public static String SCURIRTY_JWT_PACKAGE_APPLICATION="br.eti.freitas.startproject.controller";
	
	public static String SECURITY_JWT_SECRET_KEY="4TVucOPKwjzolYzqpOFCog==";
	public static String SECURITY_JWT_AUTHORITIES = "roles";
	public static String SECURITY_JWT_EXPIRATION = "900000";

	public SecurityConstant() {}	

}
