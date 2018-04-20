package com.recycler.api;


import com.recycler.models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mihir on 14-09-2017.
 */

public interface ApiInterface {
    @GET("plastr-staging/index.php/companyactivity/getInterVeiewQuestion1")
    Call<UsersResponse> getUsers();
}
