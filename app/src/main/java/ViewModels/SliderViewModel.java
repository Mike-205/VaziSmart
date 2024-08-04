package ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Repositories.SliderRepository;
import models.Slider;

public class SliderViewModel extends ViewModel {
    private final SliderRepository sliderRepository;
    private final LiveData<List<Slider>> sliderLiveData;

    public SliderViewModel() {
        sliderRepository = new SliderRepository();
        sliderLiveData = sliderRepository.getSliders();
    }

    public LiveData<List<Slider>> getSliderLiveData() {
        return sliderLiveData;
    }
}
