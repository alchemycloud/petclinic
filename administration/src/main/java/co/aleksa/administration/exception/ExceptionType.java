package co.aleksa.administration.exception;

public enum ExceptionType {
    ACCESS_TOKEN_IS_EXPIRED,
    REFRESH_TOKEN_IS_EXPIRED,
    INSUFFICIENT_RIGHTS,
    CREDENTIALS_ARE_INVALID,
    EMAIL_ALREADY_IN_USER,
    EMAIL_NOT_VERIFIED,
    RESET_PASSWORD_CODE_IS_EXPIRED,
    EMAIL_VERIFICATION_CODE_IS_EXPIRED,
    INVALID_RESET_PASSWORD_CODE,
    INVALID_EMAIL_VERIFICATION_CODE_ERROR,
    NONEXISTENT_EMAIL
}