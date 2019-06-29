package com.github.barteksc.sample.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.BooksAdapter;
import com.github.barteksc.sample.adapter.ReadAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.ReadModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserReadingHistoryActivity extends AppCompatActivity {
    public APIService apiService;
    public SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    public GridView mGridView;
    public List<ReadModel> mReadModel;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reading_history);
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        findViewsByIds();

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lịch sử đọc sách");

        setupEventHandlers();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mReadModel = new ArrayList<>();
        getReadingHistory();
    }

    private void setupEventHandlers() {
    }

    private void getReadingHistory() {
        apiService.getReadingHistory(CreateJsonObject.username(username)).enqueue(new Callback<List<ReadModel>>() {
            @Override
            public void onResponse(Call<List<ReadModel>> call, Response<List<ReadModel>> response) {
                mReadModel.clear();
                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        for (int i = 0; i < response.body().size(); i++) {
                            ReadModel read = ReadModel.builder()
                                    .readBookId(response.body().get(i).getReadBookId())
                                    .readStartedTime(response.body().get(i).getReadStartedTime())
                                    .readEndedTime(response.body().get(i).getReadEndedTime())
                                    .readIsDeleted(response.body().get(i).getReadIsDeleted())
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
                                    .bookTitle(response.body().get(i).getBookTitle())
                                    .bookType(response.body().get(i).getBookType()).build();

                            mReadModel.add(i, read);
                        }
                        ReadAdapter readAdapter = new ReadAdapter(getApplicationContext(), mReadModel, username);
                        mGridView.setAdapter(readAdapter);
                    }
                    Toasty.success(getApplicationContext(), ConstString.GET_READING_HISTORY_SUCCESSFULL, Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(getApplicationContext(), ConstString.GET_READING_HISTORY_FAILURE, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReadModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private void findViewsByIds() {
        mGridView = findViewById(R.id.gv_user_reading_history);
        toolbar = findViewById(R.id.toolbar_reading_history);
    }
}
