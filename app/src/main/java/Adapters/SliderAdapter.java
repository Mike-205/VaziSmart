package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import models.Slider;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ImageViewHolder> {
    private List<Slider> sliders;
    private final Context context;
    private final FirebaseStorage storage;
    private final ProgressBar progressBar;

    public SliderAdapter(Context context, List<Slider> sliders, ProgressBar progressBar) {
        this.context = context;
        this.sliders = sliders;
        this.progressBar = progressBar;
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Slider slider = sliders.get(position);

        // Directly use the path from the Slider object
        String imageUrl = slider.getPath();

        if (imageUrl != null && !imageUrl.isEmpty()) {

            // Get a reference to the image from Firebase Storage
            StorageReference storageRef = storage.getReferenceFromUrl(imageUrl);

            // Get the download URL for the image
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Log.d("SliderAdapter", "Loading image from URL: " + uri.toString());
                // Use Glide to load the image from the download URL with increased timeout
                Glide.with(context)
                        .load(uri)
                        .apply(new RequestOptions()
                                .timeout(1000)) // Set timeout to 10 seconds
                        .into(holder.imageView);
                // Hide progress bar when loading is complete
                progressBar.setVisibility(ProgressBar.GONE);
            }).addOnFailureListener(exception -> {
                Log.d("SliderAdapter", "Failed to get download URL: " + exception.getMessage());
            });
        } else {
            Log.d("SliderAdapter", "Image URL is null or empty");
            // Hide progress bar if loading fails
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}