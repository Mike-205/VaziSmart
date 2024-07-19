package Utils;

public class AuthException extends Exception {
    public static final String DEFAULT_MESSAGE = "An error occurred! Please try again.";

    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
        super(DEFAULT_MESSAGE);
    }

    public static AuthException fromCode(String code) {
        switch (code) {
            case "invalid-email":
                return new AuthException("Invalid email. Please check email format.");
            case "user-disabled":
                return new AuthException("This user has been disabled. Please contact support for help.");
            case "user-not-found":
                return new AuthException("Email not found! Please create an account.");
            case "invalid-credential":
                return new AuthException("Invalid email or password.");
            case "too-many-requests":
                return new AuthException("Too many requests. Please try again later.");
            case "email-already-in-use":
                return  new AuthException("An account already exists for that email.");
            case "operation-not-allowed":
                return new AuthException("This operation is not allowed. Please contact support for help.");
            case "weak-password":
                return new AuthException("Please enter a stronger password.");
            default:
                return new AuthException();
        }
    }
}
