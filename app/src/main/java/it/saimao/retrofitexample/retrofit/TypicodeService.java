package it.saimao.retrofitexample.retrofit;

import java.util.List;

import it.saimao.retrofitexample.model.Album;
import it.saimao.retrofitexample.model.Photo;
import it.saimao.retrofitexample.model.user.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TypicodeService {
    @GET("photos")
    Call<List<Photo>> getPhotos();

    @GET("photos/{id}")
    Call<Photo> getPhotoById(@Path("id") int id);

    @GET("albums/{id}")
    Call<Album> getAlbumById(@Path("id") int id);

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

}
