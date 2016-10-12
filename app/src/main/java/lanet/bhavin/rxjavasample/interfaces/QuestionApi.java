package lanet.bhavin.rxjavasample.interfaces;

import java.util.Map;

import lanet.bhavin.rxjavasample.models.Questions;
import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by lcom75 on 12/10/16.
 */

public interface QuestionApi {
    @GET("/2.2/questions")
    public Observable<Questions> getUsers(@QueryMap Map<String, String> options);
}
