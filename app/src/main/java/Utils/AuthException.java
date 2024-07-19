package Utils;

public class AuthException extends Exception {
    public static final String DEFAULT_MESSAGE = "An error occurred! Please try again.";

    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
        super(DEFAULT_MESSAGE);
    }

    private static String processErrorCode(String code) {
        System.out.println("Raw error code: " + code);
        if (code.startsWith("ERROR_")) {
            code = code.substring(6);
        }
        String processedCode = code.toLowerCase();
        System.out.println("processed error code: " + processedCode);
        return code.toLowerCase();
    }

    public static AuthException fromCode(String code) {
        switch (processErrorCode(code)) {
            case "invalid_email":
                return new AuthException("Invalid email. Please check email format.");
            case "user_disabled":
                return new AuthException("This user has been disabled. Please contact support for help.");
            case "user_not_found":
                return new AuthException("Email not found! Please create an account.");
            case "invalid_credential":
                return new AuthException("Invalid email or password.");
            case "too_many_requests":
                return new AuthException("Too many requests. Please try again later.");
            case "email_already_in_use":
                return  new AuthException("An account already exists for that email.");
            case "operation_not_allowed":
                return new AuthException("This operation is not allowed. Please contact support for help.");
            case "weak_password":
                return new AuthException("Please enter a stronger password.");
            default:
                return new AuthException();
        }
    }
}
