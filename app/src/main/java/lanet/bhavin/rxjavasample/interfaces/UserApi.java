package lanet.bhavin.rxjavasample.interfaces;

import java.util.List;
import java.util.Map;

import lanet.bhavin.rxjavasample.models.User;
import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by lcom75 on 20/9/16.
 */

public interface UserApi {
    @GET("/2.2/users")
    public Observable<List<User>> getUsers(@QueryMap Map<String, String> options);
}
