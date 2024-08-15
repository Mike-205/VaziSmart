package Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import Utils.FirestoreConstants;
import models.Brand;

public class BrandRepository {
    private final FirebaseFirestore firestoreDb;
    private final MutableLiveData<List<Brand>> brandLiveData;

    public BrandRepository() {
        firestoreDb = FirebaseFirestore.getInstance();
        brandLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Brand>> getBrands() {
        loadBrands();
        return brandLiveData;
    }

    private void loadBrands() {
        firestoreDb.collection(FirestoreConstants.BRANDS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Brand> brandList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Brand brand = documentSnapshot.toObject(Brand.class);
                        brandList.add(brand);
                    }
                    brandLiveData.setValue(brandList);
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
