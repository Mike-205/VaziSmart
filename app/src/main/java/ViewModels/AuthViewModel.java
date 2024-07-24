package ViewModels;

import android.content.Intent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import Repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;
import Utils.AuthException;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel(GoogleSignInClient googleSignInClient) {
        authRepository = new AuthRepository(googleSignInClient);
    }

    public void signInWithGoogle(Intent data) {
        authRepository.signInWithGoogle(data, credential -> {
            // Handle credential if needed
        });
    }

    public void signInWithEmailAndPassword(String email, String password) {
        authRepository.loginWithEmailAndPassword(email, password);
    }

    public void signUpWithEmailAndPassword(String email, String password) {
        authRepository.signUpWithEmailAndPassword(email, password);
    }

    public void logOut() {
        authRepository.logOut();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return authRepository.getUserLiveData();
    }

    public LiveData<Boolean> getLoggedOutLiveData() {
        return authRepository.getLoggedOutLiveData();
    }

    public LiveData<AuthException> getAuthErrorLiveData() {
        return authRepository.getAuthErrorLiveData();
    }
}
