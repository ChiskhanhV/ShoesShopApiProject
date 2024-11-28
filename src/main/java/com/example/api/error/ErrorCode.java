package com.example.api.error;

public enum ErrorCode {
	// General errors
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    BAD_REQUEST(400, "Bad request"),
    NOT_FOUND(404, "Resource not found"),
    UNAUTHORIZED(401, "Unauthorized access"),
    FORBIDDEN(403, "Forbidden access"),
    
    // User-related errors
    USER_NOT_FOUND(1001, "User not found"),
    DUPLICATE_USER(1002, "User already exists"),
    INVALID_CREDENTIALS(1003, "Invalid credentials"),
    
    // Order-related errors
    ORDER_NOT_FOUND(2001, "Order not found"),
    INVALID_ORDER_STATUS(2002, "Invalid order status"),
    ORDER_ALREADY_ASSIGNED(2003, "Order already assigned"),
    
    // Shipper-related errors
    SHIPPER_NOT_FOUND(3001, "Shipper not found"),
    DUPLICATE_SHIPPER(3002, "Shipper already exists"),
    
    // Voucher-related errors
    VOUCHER_NOT_FOUND(4001, "Voucher not found"),
    VOUCHER_EXPIRED(4002, "Voucher expired"),
    VOUCHER_USAGE_LIMIT_REACHED(4003, "Voucher usage limit reached"),
    VOUCHER_CREATION_FAILED(4004, "Failed to create voucher"),
	
	// Brand-related errors
    BRAND_NOT_FOUND(5001, "Brand not found"),
    DUPLICATE_BRAND(5002, "Brand already exists"),
    INVALID_BRAND_DATA(5003, "Invalid brand data provided"),
    BRAND_CREATION_FAILED(5004, "Failed to create brand"),
    
    // Category-related errors
    CATEGORY_NOT_FOUND(6001, "Category not found"),
    DUPLICATE_CATEGORY(6002, "Category already exists"),
    INVALID_CATEGORY_DATA(6003, "Invalid category data provided"),
    CATEGORY_CREATION_FAILED(6004, "Failed to create category");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
