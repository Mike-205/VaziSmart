package Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vazismart.R;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

import Utils.AuthException;
import ViewModels.AuthViewModel;

public class SignInActivity extends AppCompatActivity {
    private EditText emailTextController;
    private EditText passwordTextController;
    private ProgressBar circularProgressIndicator;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        emailTextController = findViewById(R.id.emailTextField);
        passwordTextController = findViewById(R.id.PasswordTextField);
        Button signInBtn = findViewById(R.id.loginButton);
        circularProgressIndicator = findViewById(R.id.progressBar);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        signInBtn.setOnClickListener(v -> signIn());


        authViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to HomePage
            }
        });


        authViewModel.getAuthExceptionLiveData().observe(this, authException -> {
            if (authException != null) {
                circularProgressIndicator.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, authException.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
        authViewModel.loginWithEmailAndPassword(email, password);
    }
}