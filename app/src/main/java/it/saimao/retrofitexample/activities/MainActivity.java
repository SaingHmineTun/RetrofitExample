package it.saimao.retrofitexample.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import it.saimao.retrofitexample.adapter.PhotoAdapter;
import it.saimao.retrofitexample.databinding.ActivityMainBinding;
import it.saimao.retrofitexample.model.Photo;
import it.saimao.retrofitexample.retrofit.TypicodeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TypicodeService service = retrofit.create(TypicodeService.class);
        service.getPhotos().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    List<Photo> photoList = response.body();
                    updateListView(photoList);
                } else {
                    Toast.makeText(MainActivity.this, "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Request failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateListView(List<Photo> photoList) {
        PhotoAdapter adapter = new PhotoAdapter(this, photoList);
        binding.rvItems.setAdapter(adapter);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));
    }
}