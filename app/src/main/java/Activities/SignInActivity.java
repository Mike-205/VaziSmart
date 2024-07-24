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

public class SignInActivity extends AppCompatActivity {
    private EditText emailTextController;
    private EditText passwordTextController;
    private ProgressBar circularProgressIndicator;
    private AuthViewModel authViewModel;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        emailTextController = findViewById(R.id.emailTextField);
        passwordTextController = findViewById(R.id.PasswordTextField);
        circularProgressIndicator = findViewById(R.id.progressBar);
        Button signInBtn = findViewById(R.id.loginButton);
        ImageView googleSignInBtn = findViewById(R.id.google);
        TextView signUpLink = findViewById(R.id.signUpLink);

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

        signInBtn.setOnClickListener(v -> signIn());
        googleSignInBtn.setOnClickListener(v -> signInWithGoogle());

        authViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to HomePage
            }
        });

        authViewModel.getAuthErrorLiveData().observe(this, authException -> {
            if (authException != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, authException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        signUpLink.setOnClickListener(view -> navigateToSignUp());
    }

    private void signIn() {
        String email = emailTextController.getText().toString().trim();
        String password = passwordTextController.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailTextController.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextController.setError("Password is required");
            return;
        }
        circularProgressIndicator.setVisibility(View.VISIBLE);
        authViewModel.signInWithEmailAndPassword(email, password);
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

    private void navigateToSignUp() {
        Intent signUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpActivity);
        finish();
    }
}
