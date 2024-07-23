package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vazismart.R;

import ViewModels.AuthViewModel;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailController;
    private EditText passwordController;
    private EditText confirmPasswordController;
    private ProgressBar circularProgressIndicator;
    private AuthViewModel authViewModel;

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
        TextView signInLink = findViewById(R.id.signInLink);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        signUpBtn.setOnClickListener(v -> signUp());

        authViewModel.getAuthExceptionLiveData().observe(this, authException -> {
            if (authException != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, authException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        signInLink.setOnClickListener(v -> navigateToSignIn());
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

    private void navigateToSignIn() {
        Intent signInActivity = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(signInActivity);
        finish();
    }
}