package ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Repositories.BrandRepository;
import models.Brand;

public class BrandViewModel extends ViewModel {
    private final LiveData<List<Brand>> brandLiveData;

    public BrandViewModel() {
        BrandRepository brandRepository = new BrandRepository();
        this.brandLiveData = brandRepository.getBrands();
    }

    public LiveData<List<Brand>> getBrandLiveData() {return brandLiveData;}
}
