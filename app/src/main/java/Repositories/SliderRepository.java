package Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import Utils.FirestoreConstants;
import models.Slider;

public class SliderRepository {
    private final FirebaseFirestore firestoreDb;
    private final MutableLiveData<List<Slider>> sliderLiveData;

    public SliderRepository() {
        firestoreDb = FirebaseFirestore.getInstance();
        sliderLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Slider>> getSliders() {
        loadSliders();
        return sliderLiveData;
    }

    private void loadSliders() {
        firestoreDb.collection(FirestoreConstants.BANNERCOLLECTION)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Slider> sliderList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Slider slider = document.toObject(Slider.class);
                        sliderList.add(slider);
                    }
                    sliderLiveData.setValue(sliderList);
                })
                .addOnFailureListener(error -> {
                    if (error instanceof FirebaseFirestoreException) {
                        FirebaseFirestoreException firestoreException = (FirebaseFirestoreException) error;
                        System.out.println("Error Code: "+firestoreException.getCode());
                    } else {
                        System.out.println("Unknown error Occurred");
                    }
                });
    }
}
