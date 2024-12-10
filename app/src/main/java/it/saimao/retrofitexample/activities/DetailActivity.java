package it.saimao.retrofitexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import it.saimao.retrofitexample.R;
import it.saimao.retrofitexample.databinding.ActivityDetailBinding;
import it.saimao.retrofitexample.model.Album;
import it.saimao.retrofitexample.model.Photo;
import it.saimao.retrofitexample.model.user.User;
import it.saimao.retrofitexample.retrofit.TypicodeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();
    }

    private void initUi() {
        Intent it = getIntent();
        int id = it.getIntExtra("id", -1);
        if (id != -1) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TypicodeService service = retrofit.create(TypicodeService.class);
            service.getPhotoById(id).enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    if (response.isSuccessful()) {
                        Photo photo = response.body();
                        Picasso.get().load(photo.getUrl()).into(binding.ivPhoto);
                        binding.tvTitle.setText(photo.getTitle());
                        service.getAlbumById(photo.getAlbumId()).enqueue(new Callback<Album>() {
                            @Override
                            public void onResponse(Call<Album> call, Response<Album> response) {
                                if (response.isSuccessful()) {
                                    Album album = response.body();
                                    binding.tvAlbum.setText(album.getTitle());
                                    service.getUserById(album.getUserId()).enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            if (response.isSuccessful()) {
                                                User user = response.body();
                                                binding.tvUser.setText(user.getUsername());
                                                binding.tvEmail.setText(user.getEmail());
                                                binding.tvPhone.setText(user.getPhone());
                                                binding.tvCity.setText(user.getAddress().getCity());
                                            } else {
                                                Toast.makeText(DetailActivity.this, "Getting User is not successful!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable throwable) {
                                            Toast.makeText(DetailActivity.this, "Getting User Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(DetailActivity.this, "Getting Album is not successful", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Album> call, Throwable throwable) {
                                Toast.makeText(DetailActivity.this, "Getting Album Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(DetailActivity.this, "Getting Photo is not successful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable throwable) {
                    Toast.makeText(DetailActivity.this, "Getting Photo Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}