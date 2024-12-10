package it.saimao.retrofitexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.saimao.retrofitexample.activities.DetailActivity;
import it.saimao.retrofitexample.databinding.ItemBinding;
import it.saimao.retrofitexample.model.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {


    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        var photo = photos.get(position);
        var binding = holder.getBinding();
        binding.tvTitle.setText(photo.getTitle());
        if (photo.getThumbnailUrl() != null && !photo.getThumbnailUrl().isBlank()) {
            Picasso.get().load(photo.getThumbnailUrl()).into(binding.ivThumbnail);
        }
        binding.cvItem.setOnClickListener(v -> {
            Intent it = new Intent(context, DetailActivity.class);
            it.putExtra("id", photo.getId());
            context.startActivity(it);
        });

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ItemBinding binding;

        public PhotoViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemBinding getBinding() {
            return binding;
        }
    }

}
