package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.vazismart.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import models.Slider;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ImageViewHolder> {
    private List<Slider> sliders;
    private final Context context;
    private final FirebaseStorage storage;

    public SliderAdapter(Context context, List<Slider> sliders) {
        this.context = context;
        this.sliders = sliders;
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
                // Use Glide to load the image from the download URL
                Glide.with(context)
                        .load(uri)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.brand_background) // Optional placeholder
                                .error(R.drawable.textfield_bg) // Optional error image
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.imageView);
            }).addOnFailureListener(exception -> {
                Log.d("SliderAdapter", "Failed to get download URL: " + exception.getMessage());
                Glide.with(context).load(R.drawable.banner_1).into(holder.imageView);
            });
        } else {
            Log.d("SliderAdapter", "Image URL is null or empty");
            Glide.with(context).load(R.drawable.banner_1).into(holder.imageView);
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

