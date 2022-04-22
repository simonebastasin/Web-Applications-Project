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

    /**
     * Application specific errors
     */

    public static final class UserCredentialError extends ErrorMessage {
        public UserCredentialError(String errorDetails) {
            super("Login credentials are not correct", "100", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class ChangePasswordError extends ErrorMessage {
        public ChangePasswordError(String errorDetails) {
            super("Unable to change password", "101", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class NotLoggedInError extends ErrorMessage {
        public NotLoggedInError(String errorDetails) {
            super("Login required to see this page", "102", errorDetails, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public static final class AlreadyLoggedInError extends ErrorMessage {
        public AlreadyLoggedInError(String errorDetails) {
            super("User already logged in", "103", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    public static final class IncorrectlyFormattedPathError extends ErrorMessage {
        public IncorrectlyFormattedPathError(String errorDetails) {
            super("Path incorrectly formatted", "104", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class IncorrectlyFormattedDataError extends ErrorMessage {
        public IncorrectlyFormattedDataError(String errorDetails) {
            super("Data incorrectly formatted", "105", errorDetails, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static final class OrderError extends ErrorMessage {
        public OrderError(String errorDetails) {
            super("Order error", "106", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class ProductOutOfStockError extends ErrorMessage {
        public ProductOutOfStockError(String errorDetails) {
            super("Product are out of stock", "107", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class CancelOrderError extends ErrorMessage {
        public CancelOrderError(String errorDetails) {
            super("Unable to delete order", "108", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class CustomerRedundantError extends ErrorMessage {
        public CustomerRedundantError(String errorDetails) {
            super("Customer unique data already present", "109", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    /**
     * Employee specific errors
     */

    public static final class EmptyMediaError extends ErrorMessage {
        public EmptyMediaError(String errorDetails) {
            super("Media cannot be empty", "200", errorDetails, HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public static final class MediaNotFoundError extends ErrorMessage {
        public MediaNotFoundError(String errorDetails) {
            super("Media not found", "201", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class ProductNotFoundError extends ErrorMessage {
        public ProductNotFoundError(String errorDetails) {
            super("Product not found", "202", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class OrderNotFoundError extends ErrorMessage {
        public OrderNotFoundError(String errorDetails) {
            super("Order not found", "203", errorDetails, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static final class UploadMediaError extends ErrorMessage {
        public UploadMediaError(String errorDetails) {
            super("Upload media error", "204", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class EmptyProductListError extends ErrorMessage {
        public EmptyProductListError(String errorDetails) {
            super("Product list cannot be empty", "205", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class DatesTimelineError extends ErrorMessage {
        public DatesTimelineError(String errorDetails) {
            super("Start date cannot be later than end date", "206", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class EmployeeRedundantError extends ErrorMessage {
        public EmployeeRedundantError(String errorDetails) {
            super("Employee username already present", "207", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    public static final class ProductRedundantError extends ErrorMessage {
        public ProductRedundantError(String errorDetails) {
            super("Product alias already present", "208", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    public static final class CategoryRedundantError extends ErrorMessage {
        public CategoryRedundantError(String errorDetails) {
            super("Category name already present", "209", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    public static final class CreateDiscountError extends ErrorMessage {
        public CreateDiscountError(String errorDetails) {
            super("Unable to create discount", "210", errorDetails, HttpServletResponse.SC_CONFLICT);
        }
    }

    public static final class DeleteEmployeeError extends ErrorMessage {
        public DeleteEmployeeError(String errorDetails) {
            super("Unable to delete employee", "211", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class DeleteCustomerError extends ErrorMessage {
        public DeleteCustomerError(String errorDetails) {
            super("Unable to delete customer", "212", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class DeleteProductError extends ErrorMessage {
        public DeleteProductError(String errorDetails) {
            super("Unable to delete product", "213", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    public static final class DeleteDiscountError extends ErrorMessage {
        public DeleteDiscountError(String errorDetails) {
            super("Unable to delete discount", "214", errorDetails, HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    /**
     * Internal errors
     */

    public static final class SqlInternalError extends ErrorMessage {
        public SqlInternalError(String errorDetails) {
            super("SQL Internal error", "998", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static final class InternalError extends ErrorMessage {
        public InternalError(String errorDetails) {
            super("Internal error", "999", errorDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
