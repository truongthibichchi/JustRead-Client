package com.github.barteksc.sample.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.HorizontalAdapter;
import com.github.barteksc.sample.adapter.ReadAdapter;
import com.github.barteksc.sample.adapter.SharedBookAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSharedBooksActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private GridView gvAllSharedBooks;
    private SharedBookAdapter mAdapter;
    private List<SharedBookModel> mSharedBookList = new ArrayList<>();
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
        setContentView(R.layout.activity_all_shared_books);
        findViewByIds();
        getDataFromBundle();
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);

        username = sharedPreferences.getString("username", null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bản tin chia sẻ sách");
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
        getSharedBooksData();

    }

    private void getSharedBooksData() {
        mSharedBookList.clear();
        apiService.getNews().enqueue(new Callback<List<SharedBookModel>>() {
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
                    mAdapter = new SharedBookAdapter(getApplicationContext(), mSharedBookList, username);
                    gvAllSharedBooks.setAdapter(mAdapter);
//                    gvAllSharedBooks.setOnItemLongClickListener((parent, view, position, id) -> {
//                        final CharSequence[] items = {"Edit", "Delete"};
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                        builder.setItems(items, (dialog, which) -> {
//                            Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
//
//                        });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//
//                        return true;
//                    });
                }
            }

            @Override
            public void onFailure(Call<List<SharedBookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_shared_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_shared_book_create_new:
                Intent intent = new Intent(AllSharedBooksActivity.this, AddSharedBookActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("fullname", fullname);
                intent.putExtra("avatar", ApiLink.HOST + avatar);
                intent.putExtra("is_admin", isAdmin);
                intent.putExtra("action", ConstString.ACTION_ADD);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.toolbar_all_shared_book);
        gvAllSharedBooks = findViewById(R.id.gv_all_shared_book);
    }
}
