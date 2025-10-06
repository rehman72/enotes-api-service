package com.project.enotes_api_service.util;

public class Constants {
    public static  final String emailRegex="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";

    public static final  String mobileRegex="^[7-9][0-9]{9}$";

    public static final String ROLE_ADMIN="hasRole('ADMIN')";

    public static final String ROLE_USER="hasRole('ROLE_USER')";

    public static final String ROLE_ADMIN_USER="hasAnyRole('ROLE_USER','ADMIN')";

    public static final String defaultPageNo="0";
    public static final String defaultPageSize="10";
}
