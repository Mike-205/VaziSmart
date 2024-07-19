package Utils;

public class SignUpWithEmailAndPasswordFailure extends Exception {
    private static final String DEFAULT_MESSAGE = "An error occurred! Please try again.";

    public SignUpWithEmailAndPasswordFailure(String message) {
        super(message);
    }

    public SignUpWithEmailAndPasswordFailure() {
        super(DEFAULT_MESSAGE);
    }

    public static SignUpWithEmailAndPasswordFailure fromCode(String code) {
        switch (code) {
            case "invalid-email":
                return new SignUpWithEmailAndPasswordFailure("Invalid email. Please check email format.");
            case "email-already-in-use":
                return  new SignUpWithEmailAndPasswordFailure("An account already exists for that email.");
            case "user-disabled":
                return new SignUpWithEmailAndPasswordFailure("This user has been disabled. Please contact support for help.");
            case "operation-not-allowed":
                return new SignUpWithEmailAndPasswordFailure("This operation is not allowed. Please contact support for help.");
            case "weak-password":
                return new SignUpWithEmailAndPasswordFailure("Please enter a stronger password.");
            default:
                return new SignUpWithEmailAndPasswordFailure();
        }
    }
}

