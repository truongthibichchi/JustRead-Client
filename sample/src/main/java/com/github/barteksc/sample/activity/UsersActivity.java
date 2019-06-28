package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.SharedBookAdapter;
import com.github.barteksc.sample.adapter.UsersAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private GridView gvAllUser;
    private UsersAdapter mAdapter;
    private List<UserModel> mUserModel = new ArrayList<>();
    public APIService apiService;
    public SharedPreferences sharedPreferences;
    private Bundle mBundle;
    private String username;
    private String avatar;
    private String fullname;
    private String isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        findViewByIds();
        getDataFromBundle();
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);

        username = sharedPreferences.getString("username", null);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Danh sách người dùng");
    }

    private void getDataFromBundle() {
        mBundle = getIntent().getExtras();
        username = (String) mBundle.get("username");
        avatar = (String) mBundle.get("avatar");
        fullname = (String) mBundle.get("fullname");
        isAdmin = (String) mBundle.get("is_admin");
    }

    @Override
    protected void onResume() {
        super.onResume();

        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        getUsersData();

    }

    private void getUsersData() {
        mUserModel.clear();
        apiService.getAllUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        UserModel userModel = UserModel.builder()
                                .userUsername(response.body().get(i).getUserUsername())
                                .userFullname(response.body().get(i).getUserFullname())
                                .userDateOfBirth(response.body().get(i).getUserDateOfBirth())
                                .userAddress(response.body().get(i).getUserAddress())
                                .userAvatar(response.body().get(i).getUserAvatar())
                                .userCreatedDate(response.body().get(i).getUserCreatedDate())
                                .userIsAdmin(response.body().get(i).getUserIsAdmin()).build();

                        mUserModel.add(i, userModel);
                    }
                    mAdapter = new UsersAdapter(getApplicationContext(), mUserModel, username, isAdmin);
                    gvAllUser.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private void findViewByIds() {
        gvAllUser = findViewById(R.id.gv_users);
    }

}
