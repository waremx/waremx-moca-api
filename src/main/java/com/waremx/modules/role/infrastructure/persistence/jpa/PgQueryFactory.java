package com.waremx.modules.role.infrastructure.persistence.jpa;

public class PgQueryFactory {
    public static final String ROLE_TABLE_NAME = "t_moca_roles";
    public static final String ROLE_SCHEMA_NAME = "moca";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_tx_name";
    public static final String ROLE_DISPLAY_NAME = "role_tx_display_name";
    public static final String CREATED_BY = "role_tx_created_by";
    public static final String UPDATED_BY = "role_tx_updated_by";
    public static final String CREATED_AT = "role_dt_created_at";
    public static final String UPDATED_AT = "role_dt_updated_at";
    public static final String IS_ACTIVE = "role_st_is_active";
    public static final String IS_PROTECTED = "role_st_is_protected";

    public static  final String PARAM_ROLE_LIMIT = "limit";
    public static  final String PARAM_ROLE_OFFSET = "offset";
    public static  final String PARAM_ROLE_NAME = "name";
    public static  final String PARAM_ROLE_UPDATED_AT = "now";
    public static  final String PARAM_ROLE_UPDATED_BY = "by";

    public static final String GET_ROLE_BY_NAME = "select role_id as roleId, " +
            "role_tx_name as name, " +
            "role_tx_display_name as displayName, " +
            "role_tx_created_by as createdBy, " +
            "role_tx_updated_by as updatedBy, " +
            "role_dt_created_at as createdAt, " +
            "role_dt_updated_at as updatedAt, " +
            "role_st_is_active as isActive, " +
            "role_st_is_protected as isProtected " +
            "from moca.t_moca_roles " +
            "where role_tx_name = :name";

    public static final String ROLE_PAGINATION = "select role_id as roleId, " +
            "role_dt_created_at as createdAt, " +
            "role_dt_updated_at as updatedAt, " +
            "role_st_is_active as isActive " +
            "from t_moca_roles " +
            "limit coalesce(:limit, 10) " +
            "offset coalesce(:offset, 0);";

    public static final String DISABLE_ROLE = "update " +
            "moca.t_moca_roles set " +
            "role_st_is_active = false, " +
            "role_tx_updated_by = :by, " +
            "role_dt_updated_at = :now " +
            "where role_tx_name = :name";
}
