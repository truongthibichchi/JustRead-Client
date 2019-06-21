package com.github.barteksc.sample.api;

public class ApiUtils {

    private ApiUtils() {}

    public static APIService getAPIService() {
        return RetrofitClient.getClient().create(APIService.class);
    }
}
