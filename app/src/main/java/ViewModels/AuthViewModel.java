package ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import Repositories.AuthRepository;
import Utils.AuthException;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final LiveData<FirebaseUser> userLiveData;
    private final LiveData<Boolean> loggedOutLiveData;
    private final LiveData<AuthException> authExceptionLiveData;

    public AuthViewModel() {
        authRepository = new AuthRepository();
        userLiveData = authRepository.getUserLiveData();
        loggedOutLiveData = authRepository.getLoggedOutLiveData();
        authExceptionLiveData = authRepository.getAuthErrorLiveData();
    }

    public void loginWithEmailAndPassword(String email, String password) {
        authRepository.loginWithEmailAndPassword(email, password);
    }

    public void signUpWithEmailAndPassword(String email, String password) {
        authRepository.signUpWithEmailAndPassword(email, password);
    }

    public void signOut() {
        authRepository.logOut();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public LiveData<AuthException> getAuthExceptionLiveData() {
        return authExceptionLiveData;
    }
}
