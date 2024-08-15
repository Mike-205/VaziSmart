package Activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vazismart.R;

import java.util.ArrayList;

import Adapters.BrandAdapter;
import Adapters.SliderAdapter;
import ViewModels.BrandViewModel;
import ViewModels.SliderViewModel;

public class HomeActivity extends AppCompatActivity {
    private SliderAdapter sliderAdapter;
    private BrandAdapter brandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewPager2 sliderViewPager = findViewById(R.id.sliderViewPager);
        ProgressBar sliderProgressBar = findViewById(R.id.sliderProgressBar);

        sliderAdapter = new SliderAdapter(this, new ArrayList<>(), sliderProgressBar);
        sliderViewPager.setAdapter(sliderAdapter);

        SliderViewModel sliderViewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        sliderViewModel.getSliderLiveData().observe(this, sliderList -> {
            sliderAdapter.setSliders(sliderList);
        });

        RecyclerView brandRecyclerView = findViewById(R.id.brandsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        brandRecyclerView.setLayoutManager(layoutManager);

        BrandViewModel brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);

        brandViewModel.getBrandLiveData().observe(this, brands -> {
            brandAdapter = new BrandAdapter(this, brands);
            brandRecyclerView.setAdapter(brandAdapter);
        });
    }
}