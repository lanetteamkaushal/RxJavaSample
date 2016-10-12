package lanet.bhavin.rxjavasample.interfaces;

import lanet.bhavin.rxjavasample.global.BuildVars;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by lcom75 on 12/10/16.
 */

public class QuestionService {

    private QuestionApi questionApi;

    public QuestionService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildVars.BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        questionApi = restAdapter.create(QuestionApi.class);
    }

    public QuestionApi getQuestionApi() {
        return questionApi;
    }
}
