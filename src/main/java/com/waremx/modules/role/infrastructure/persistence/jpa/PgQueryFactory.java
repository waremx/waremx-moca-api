package com.waremx.modules.role.infrastructure.persistence.jpa;

public class PgQueryFactory {
    public static final String ROLE_TABLE_NAME = "t_moca_roles";
    public static final String ROLE_SCHEMA_NAME = "moca";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";
    public static final String CREATED_AT = "role_dt_created_at";
    public static final String UPDATED_AT = "role_dt_updated_at";
    public static final String IS_ACTIVE = "role_st_is_active";

    public static  final String PARAM_ROLE_LIMIT = "limit";
    public static  final String PARAM_ROLE_OFFSET = "offset";
    public static  final String PARAM_ROLE_NAME = "roleName";

    public static final String GET_ROLE_BY_NAME = "select role_id as roleId, " +
            "role_name as roleName, " +
            "role_dt_created_at as createdAt, " +
            "role_dt_updated_at as updatedAt, " +
            "role_st_is_active as isActive " +
            "from t_moca_roles " +
            "where role_name = :roleName;";

    public static final String ROLE_PAGINATION = "select role_id as roleId, " +
            "role_dt_created_at as createdAt, " +
            "role_dt_updated_at as updatedAt, " +
            "role_st_is_active as isActive " +
            "from t_moca_roles " +
            "limit coalesce(:limit, 10) " +
            "offset coalesce(:offset, 0);";
}
