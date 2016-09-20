package lanet.bhavin.rxjavasample.interfaces;

import lanet.bhavin.rxjavasample.global.BuildVars;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by lcom75 on 20/9/16.
 */

public class UserService {
    private UserApi userApi;

    public UserService() {
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

        userApi = restAdapter.create(UserApi.class);
    }

    public UserApi getUserApi() {
        return userApi;
    }
}
