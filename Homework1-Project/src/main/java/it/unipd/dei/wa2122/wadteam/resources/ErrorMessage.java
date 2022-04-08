package it.unipd.dei.wa2122.wadteam.resources;

import jakarta.servlet.http.HttpServletResponse;

public abstract sealed class ErrorMessage extends Message  {

    final int httpErrorCode;

    private ErrorMessage(String message, String errorCode, String errorDetails, int httpErrorCode) {
        super(message, errorCode, errorDetails);
        this.httpErrorCode = httpErrorCode;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public static final class MediaNotFoundError extends ErrorMessage {
        public MediaNotFoundError(String errorDetails) {
            super("Media Not Found", "10", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class IncorrectlyFormattedPathError extends ErrorMessage {
        public IncorrectlyFormattedPathError(String errorDetails) {
            super("Incorrectly Formatted Path Error", "20", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class EmptyMediaError extends ErrorMessage {
        public EmptyMediaError(String errorDetails) {
            super("Empty Media", "30", errorDetails, HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public static final class ProductNotFoundError extends ErrorMessage {
        public ProductNotFoundError(String errorDetails) {
            super("Product Not Found", "40", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class UserCredentialNotCorrectError extends ErrorMessage {
        public UserCredentialNotCorrectError(String errorDetails) {
            super("Username or password aren't correct", "50", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class UserCredentialError extends ErrorMessage {
        public UserCredentialError(String errorDetails) {
            super("Username error", "51", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class OrderNotFoundError extends ErrorMessage {
        public OrderNotFoundError(String errorDetails) {
            super("Product Not Found", "60", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class SqlInternalError extends ErrorMessage {
        public SqlInternalError(String errorDetails) {
            super("Internal error", "210", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class InternalError extends ErrorMessage {
        private InternalError(String errorDetails) {
            super("Internal error", "200", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class UploadError extends ErrorMessage {
        public UploadError(String errorDetails) {
            super("Upload error", "220", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    public static final class ChangePasswordError extends ErrorMessage {
        public ChangePasswordError(String errorDetails) {
            super("password not changed", "240", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
