package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.SharedBookAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalPageActivity extends AppCompatActivity {
    private GridView gvAllSharedBooks;
    private SharedBookAdapter mAdapter;
    private List<SharedBookModel> mSharedBookList = new ArrayList<>();
    public APIService apiService;
    public SharedPreferences sharedPreferences;
    private Bundle mBundle;
    private String username;
    private String avatar;
    private String createdDate;
    private String fullname;
    private String isAdmin;
    private String userLogin;

    private CircleImageView imgAvatar;
    private TextView tvUsername;
    private TextView tvFullname;
    private TextView tvCreatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        findViewByIds();
        getDataFromBundle();
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        userLogin = sharedPreferences.getString("username", null);
    }

    private void getDataFromBundle() {
        mBundle = getIntent().getExtras();
        username = (String) mBundle.get("username");
        avatar = (String) mBundle.get("avatar");
        fullname = (String) mBundle.get("fullname");
        createdDate = (String) mBundle.get("created_date");
        isAdmin = (String) mBundle.get("is_admin");
    }

    @Override
    protected void onResume() {
        super.onResume();

        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        getSharedBooksData();

    }

    private void getSharedBooksData() {
        mSharedBookList.clear();
        apiService.getNewsByUserName(CreateJsonObject.username(username)).enqueue(new Callback<List<SharedBookModel>>() {
            @Override
            public void onResponse(Call<List<SharedBookModel>> call, Response<List<SharedBookModel>> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        SharedBookModel sharedBookModel = SharedBookModel.builder()
                                .newsId(response.body().get(i).getNewsId())
                                .newsUsername(response.body().get(i).getNewsUsername())
                                .newsUserFullname(response.body().get(i).getNewsUserFullname())
                                .newsUserAvatar(response.body().get(i).getNewsUserAvatar())
                                .newsBookId(response.body().get(i).getNewsBookId())
                                .newsContent(response.body().get(i).getNewsContent())
                                .newsCreated_time(response.body().get(i).getNewsCreated_time())
                                .newsIsDeleted(response.body().get(i).getNewsIsDeleted())
                                .bookAuthor(response.body().get(i).getBookAuthor())
                                .bookCategory(response.body().get(i).getBookCategory())
                                .bookDescription(response.body().get(i).getBookDescription())
                                .bookDownload(response.body().get(i).getBookDownload())
                                .bookFile(response.body().get(i).getBookFile())
                                .bookId(response.body().get(i).getBookId())
                                .bookImage(response.body().get(i).getBookImage())
                                .bookPage(response.body().get(i).getBookPage())
                                .bookPublicDate(response.body().get(i).getBookPublicDate())
                                .bookRatedTime(response.body().get(i).getBookRatedTime())
                                .bookRating(response.body().get(i).getBookRating())
                                .bookReadTime(response.body().get(i).getBookReadTime())
                                .bookIsDeleted(response.body().get(i).getBookIsDeleted())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookCreatedTime(response.body().get(i).getBookCreatedTime())
                                .bookType(response.body().get(i).getBookType()).build();
                        mSharedBookList.add(i, sharedBookModel);
                    }
                    mAdapter = new SharedBookAdapter(getApplicationContext(), mSharedBookList, userLogin, isAdmin);
                    gvAllSharedBooks.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SharedBookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private void findViewByIds() {
        gvAllSharedBooks = findViewById(R.id.gv_personal_shared_books);
        imgAvatar = findViewById(R.id.img_personal_avatar);
        tvUsername = findViewById(R.id.tv_personal_username);
        tvCreatedDate = findViewById(R.id.tv_personal_created_date);
        tvFullname = findViewById(R.id.tv_personal_fullname);
    }
}
