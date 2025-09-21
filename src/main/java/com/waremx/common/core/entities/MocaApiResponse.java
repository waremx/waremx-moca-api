package com.waremx.common.core.entities;

public class MocaApiResponse {
    public static final String INSERT =
            "{\n" +
            "  \"name\": \"SOMETHING_ROLE\",\n" +
            "  \"displayName\": \"Something\",\n" +
            "  \"isProtected\": true\n" +
            "}";

    public static final String ROLE_RESPONSE = """
        {
            "mocaStatusCode": "MOCA-COD-00X",
            "statusCode": 200,
            "status": "Resource",
            "message": "Resource retrieved successfully",
            "timestamp": "2025-09-16T20:14:35.816107",
            "data": {
                "name": "SM_ROLE",
                "displayName": "Something",
                "createdAt": "2025-09-07T07:45:28.975058",
                "updatedAt": "2025-09-16T20:09:29.962014",
                "isActive": true,
                "isProtected": true
            }
        }
    """;

    public static final String CREATE_ROLE_RESPONSE = """
        {
            "mocaStatusCode": "MOCA-COD-00X",
            "statusCode": 201,
            "status": "Create resource",
            "message": "Resource created successfully",
            "timestamp": "2025-09-16T20:14:35.816107",
            "data": {
                "name": "SM_ROLE",
                "displayName": "Something",
                "createdAt": "2025-09-07T07:45:28.975058",
                "updatedAt": "2025-09-16T20:09:29.962014",
                "isActive": true,
                "isProtected": true
            }
        }
    """;

    public static final String ROLE_LIST_RESPONSE = """
        {
            "mocaStatusCode": "MOCA-COD-00X",
            "statusCode": 200,
            "status": "List roles",
            "message": "All active roles were obtained",
            "timestamp": "2025-09-21T15:44:11.542777",
            "data": [
                {
                    "name": "SM_ROLE",
                    "displayName": "Something",
                    "createdAt": "2025-09-07T07:45:28.975058",
                    "updatedAt": "2025-09-20T18:32:35.85777",
                    "isActive": true,
                    "isProtected": true
                },
                {
                    "name": "SOMETHING_ROLE",
                    "displayName": "Something",
                    "createdAt": "2025-09-20T19:37:04.680281",
                    "updatedAt": "2025-09-20T19:43:18.706774",
                    "isActive": true,
                    "isProtected": true
                }
            ]
        }
    """;
}
