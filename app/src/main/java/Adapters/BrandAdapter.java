package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vazismart.R;

import java.util.List;

import models.Brand;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private final List<Brand> brandList;
    private final Context context;

    public BrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.bind(brand);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        private final TextView brandName;
        private final ImageView brandImage;

        public BrandViewHolder(View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brandName);
            brandImage = itemView.findViewById(R.id.brandImage);
        }

        public void bind(Brand brand) {
            brandName.setText(brand.getName());
            Log.d("Image Path",brand.getPicUrl());
            Glide.with(context).load(brand.getPicUrl()).apply(new RequestOptions().timeout(10000)).into(brandImage);
        }
    }
}
