package com.waremx.common.core.errors;

public class MocaErrApiResponse {
    public static final String NOT_FOUND = """
        {
            "path": "/api/resource/1",
            "timestamp": "2019-09-16T22:14:45.624+0000",
            "errors": [
                {
                    "mocaErrorCode": "MOCA-MOD-ERR001",
                    "code": 404,
                    "status": "Not Found",
                    "message": "Resource not found"
                }
            ]
        }
    """;

    public static final String NOT_AVAILABLE = """
        {
            "path": "/api/resource/1",
            "timestamp": "2019-09-16T22:14:45.624+0000",
            "errors": [
                {
                    "mocaErrorCode": "MOCA-MOD-ERR002",
                    "code": 400,
                    "status": "Bad Request",
                    "message": "Resource not available"
                }
            ]
        }
    """;

    public static final String ALREADY_EXISTS = """
        {
            "path": "/api/resource",
            "timestamp": "2019-09-16T22:14:45.624+0000",
            "errors": [
                {
                    "mocaErrorCode": "MOCA-MOD-ERR004",
                    "code": 400,
                    "status": "Bad Request",
                    "message": "Oops... Something went wrong, this resource already exists"
                }
            ]
        }
    """;

    public static final String ERROR_TO_CREATE = """
        {
            "path": "/api/resource",
            "timestamp": "2019-09-16T22:14:45.624+0000",
            "errors": [
                {
                    "mocaErrorCode": "MOCA-MOD-ERR003",
                    "code": 500,
                    "status": "Internal Server Error",
                    "message": "Oops... Something went wrong, resource could not be created"
                }
            ]
        }
    """;
}
