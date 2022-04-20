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
            super("Media not found", "10", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class IncorrectlyFormattedPathError extends ErrorMessage {
        public IncorrectlyFormattedPathError(String errorDetails) {
            super("Path incorrectly formatted", "20", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class IncorrectlyFormattedDataError extends ErrorMessage {
        public IncorrectlyFormattedDataError(String errorDetails) {
            super("Data incorrectly formatted", "21", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class EmptyMediaError extends ErrorMessage {
        public EmptyMediaError(String errorDetails) {
            super("Empty media", "30", errorDetails, HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public static final class ProductNotFoundError extends ErrorMessage {
        public ProductNotFoundError(String errorDetails) {
            super("Product not found", "40", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class UserCredentialNotCorrectError extends ErrorMessage {
        public UserCredentialNotCorrectError(String errorDetails) {
            super("Username or password not correct", "50", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class UserCredentialError extends ErrorMessage {
        public UserCredentialError(String errorDetails) {
            super("Username error", "51", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class OrderNotFoundError extends ErrorMessage {
        public OrderNotFoundError(String errorDetails) {
            super("Product not found", "60", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class SqlInternalError extends ErrorMessage {
        public SqlInternalError(String errorDetails) {
            super("Internal error", "210", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class InternalError extends ErrorMessage {
        public InternalError(String errorDetails) {
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
            super("Password not changed", "240", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class NotLogin extends ErrorMessage {
        public NotLogin(String errorDetails) {
            super("Login required to see this page", "260", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class ElementRedundant extends ErrorMessage {
        public ElementRedundant(String errorDetails) {
            super("Data element already present", "280", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    // Error code from 290

    public static final class startDateAfterDndDate extends ErrorMessage {
        public startDateAfterDndDate(String errorDetails) {
            super("The entered start date is later than the end date ", "290", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class emptyProductList extends ErrorMessage {
        public emptyProductList(String errorDetails) {
            super("The product list cannot be empty  ", "291", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    public static final class OrderError extends ErrorMessage {
        public OrderError(String errorDetails) {
            super("Order error", "300", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class OutOfStock extends ErrorMessage {
        public OutOfStock(String errorDetails) {
            super("Product are out of stock", "301", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }


    public static final class CancelOrderError extends ErrorMessage {
        public CancelOrderError(String errorDetails) {
            super("Unable to delete order", "302", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

}
