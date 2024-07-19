package Utils;

public class SignInWithEmailAndPasswordFailure extends Exception {
    public static final String DEFAULT_MESSAGE = "An error occurred! Please try again.";

    public SignInWithEmailAndPasswordFailure(String message) {
        super(message);
    }

    public SignInWithEmailAndPasswordFailure() {
        super(DEFAULT_MESSAGE);
    }

    public static SignInWithEmailAndPasswordFailure fromCode(String code) {
        switch (code) {
            case "invalid-email":
                return new SignInWithEmailAndPasswordFailure("Invalid email. Please check email format.");
            case "user-disabled":
                return new SignInWithEmailAndPasswordFailure("This user has been disabled. Please contact support for help.");
            case "user-not-found":
                return new SignInWithEmailAndPasswordFailure("Email not found! Please create an account.");
            case "invalid-credential":
                return new SignInWithEmailAndPasswordFailure("Invalid email or password.");
            case "too-many-requests":
                return new SignInWithEmailAndPasswordFailure("Too many requests. Please try again later.");
            default:
                return new SignInWithEmailAndPasswordFailure();
        }
    }
}
