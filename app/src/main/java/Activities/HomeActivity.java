package Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vazismart.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.SliderAdapter;
import ViewModels.SliderViewModel;

public class HomeActivity extends AppCompatActivity {
    private SliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewPager2 sliderViewPager = findViewById(R.id.sliderViewPager);
        adapter = new SliderAdapter(this, new ArrayList<>());
        sliderViewPager.setAdapter(adapter);

        SliderViewModel sliderViewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        sliderViewModel.getSliderLiveData().observe(this, sliderList -> {
            List<String> imageUrls = new ArrayList<>();
            for (models.Slider slider : sliderList) {
                imageUrls.add(slider.getImageUrl());
            }
            adapter.setImageUrls(imageUrls);
        });
    }
}