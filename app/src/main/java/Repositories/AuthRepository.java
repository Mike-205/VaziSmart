package Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;

    public AuthRepository () {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        loggedOutLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void loginWithEmailAndPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        userLiveData.postValue(null);
                    }
                });
    }

    public void signUpWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        userLiveData.postValue(null);
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
