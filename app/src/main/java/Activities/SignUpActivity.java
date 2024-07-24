package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vazismart.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

import ViewModels.AuthViewModel;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailController;
    private EditText passwordController;
    private EditText confirmPasswordController;
    private ProgressBar circularProgressIndicator;
    private AuthViewModel authViewModel;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        emailController = findViewById(R.id.emailEditText);
        passwordController = findViewById(R.id.passwordEditText);
        confirmPasswordController = findViewById(R.id.confirmPasswordEditText);
        circularProgressIndicator = findViewById(R.id.progressBar);
        Button signUpBtn = findViewById(R.id.signUpbtn);
        ImageView googleSignInBtn = findViewById(R.id.google);
        TextView signInLink = findViewById(R.id.signInLink);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        authViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (AuthViewModel.class.isAssignableFrom(modelClass)) {
                    return Objects.requireNonNull(modelClass.cast(new AuthViewModel(mGoogleSignInClient)));
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(AuthViewModel.class);

        signUpBtn.setOnClickListener(v -> signUp());

        authViewModel.getAuthErrorLiveData().observe(this, authException -> {
            if (authException != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, authException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        authViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "Account creation successful!", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to HomePage
            }
        });

        signInLink.setOnClickListener(v -> navigateToSignIn());

        googleSignInBtn.setOnClickListener(v -> signInWithGoogle());
    }

    private void signUp() {
        String email = emailController.getText().toString().trim();
        String password = passwordController.getText().toString().trim();
        String confirmPassword = confirmPasswordController.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailController.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordController.setError("Password is required");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordController.setError("Confirm password required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordController.setError("Passwords do not match");
            return;
        }

        circularProgressIndicator.setVisibility(View.VISIBLE);
        authViewModel.signUpWithEmailAndPassword(email, password);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    authViewModel.signInWithGoogle(data);
                }
            });

    private void navigateToSignIn() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(signInActivity);
        finish();
    }
}