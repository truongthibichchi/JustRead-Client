package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.BooksAdapter;
import com.github.barteksc.sample.adapter.HorizontalAdapter;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.HorizontalListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;
import static com.github.barteksc.sample.activity.LogInActivity.books;
import static com.github.barteksc.sample.activity.LogInActivity.recommendBooks;
import static com.github.barteksc.sample.activity.LogInActivity.topBooks;
import static com.github.barteksc.sample.activity.LogInActivity.userLogin;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static BookModel book;
    private ActionBarDrawerToggle mToggle;
    @ViewById(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @ViewById(R.id.navigation_menu)
    NavigationView mNavigation;
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.HorizontalListView_1)
    HorizontalListView horizontalListView1;
    @ViewById(R.id.HorizontalListView_2)
    HorizontalListView horizontalListView2;
    @ViewById(R.id.HorizontalListView_3)
    HorizontalListView horizontalListView3;
    @ViewById(R.id.btn_report)
    Button btnReport;
    @ViewById(R.id.foryou)
    TextView foryou;

    @AfterViews
    public void init() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        initHorizontalListBooks();
        initHorizontalListTopBooks();
        getRecommendBooks();
        getUserInformation();
        setOnItemClickAction();
//        spCategory.setOnItemSelectedListener(spinnerOnItemSelectedListener);
    }


    //Cho nay de hien thi chi tiet sach, tam thoi khong co du lieu gi nha

    @Click(R.id.HorizontalListView_1)
    public void HorizontalListView_1() {
        Intent intent = new Intent(MainActivity.this, BookDetail_.class);
        startActivity(intent);
    }

    @Click(R.id.HorizontalListView_2)
    public void HorizontalListView_2() {
        Intent intent = new Intent(MainActivity.this, BookDetail_.class);
        startActivity(intent);
    }
    @Click(R.id.HorizontalListView_3)
    public void HorizontalListView_3() {
        Intent intent = new Intent(MainActivity.this, BookDetail_.class);
        startActivity(intent);
    }


    @Click(R.id.btn_report)
    public void setBtnReportAction() {
        Intent intent = new Intent(MainActivity.this, AdminActivity_.class);
        startActivity(intent);
    }

    void getRecommendBooks() {
        recommendBooks.clear();
        apiService.getRecommendBooks(CreateJsonObject.username()).enqueue(new Callback<List<BookModel>>() {
            @Override
            public void onResponse(Call<List<BookModel>> call, Response<List<BookModel>> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        BookModel book = BookModel.builder()
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
                                .bookReadTimes(response.body().get(i).getBookReadTimes())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookType(response.body().get(i).getBookType()).build();
                        recommendBooks.add(i, book);
                    }
                    initHorizontalListRecommendBooks();
                    if (recommendBooks.size() != 0) {
                        horizontalListView3.setVisibility(View.VISIBLE);
                        foryou.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }


//    final AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//            //getBooksByCategoryAPIExecute();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parentView) {
//            // your code here
//        }
//    };

//    @OptionsItem(R.id.user_information)
//    void transferToUserInformation() {
//        Intent intent = new Intent(MainActivity.this, UserInformation_.class);
//        startActivity(intent);
//    }
//
//    @OptionsItem(R.id.add_news)
//    void addnews() {
//        Intent intent = new Intent(MainActivity.this, AddNewsActivity_.class);
//        startActivity(intent);
//    }


//    void getBooksByCategoryAPIExecute() {
//        apiService.getBooksByCategory(CreateJsonObject.categoryValue(spCategory.getSelectedItem().toString())).enqueue(new Callback<List<BookModel>>() {
//            @Override
//            public void onResponse(Call<List<BookModel>> call, Response<List<BookModel>> response) {
//                if (response.body() != null) {
//                    books = response.body();
//                    Toasty.success(getApplicationContext(), ConstString.GET_USER_SUCCESSFULL, Toast.LENGTH_SHORT, true).show();
//
//                } else {
//                    Toasty.info(getApplicationContext(), response.message(), Toast.LENGTH_SHORT, true).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BookModel>> call, Throwable t) {
//                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
//            }
//        });
//    }

    void getUserInformation() {
        userLogin = null;
        apiService.getUserInfo(CreateJsonObject.username()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    userLogin = UserModel.builder()
                            .avatar(response.body().avatar)
                            .address(response.body().address)
                            .dateOfBirth(response.body().dateOfBirth)
                            .isAdmin(response.body().isAdmin)
                            .fullname(response.body().fullname)
                            .username(response.body().username)
                            .build();
                    if (userLogin.isAdmin.equalsIgnoreCase("1")) {
                        btnReport.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toasty.info(getApplicationContext(), response.message(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private void initHorizontalListBooks() {

        HorizontalAdapter horizontalAdapter1 = new HorizontalAdapter(getApplicationContext(), books);
        horizontalListView1.setAdapter(horizontalAdapter1);
        horizontalAdapter1.notifyDataSetChanged();
    }

    private void initHorizontalListTopBooks() {
        HorizontalAdapter horizontalAdapter2 = new HorizontalAdapter(getApplicationContext(), topBooks);
        horizontalListView2.setAdapter(horizontalAdapter2);
        horizontalAdapter2.notifyDataSetChanged();
    }

    private void initHorizontalListRecommendBooks() {
        HorizontalAdapter horizontalAdapter3 = new HorizontalAdapter(getApplicationContext(), recommendBooks);
        horizontalListView3.setAdapter(horizontalAdapter3);
        horizontalAdapter3.notifyDataSetChanged();
    }


    void horizontalOnItemClick(AdapterView<?> parentAdapter, int position) {
        book = (BookModel) parentAdapter.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, BookDetail_.class);
        startActivity(intent);
    }

    private void setOnItemClickAction() {
        horizontalListView1.setOnItemClickListener((AdapterView<?> parentAdapter, View view, int position, long id) -> {
            horizontalOnItemClick(parentAdapter, position);
        });
        horizontalListView2.setOnItemClickListener((parentAdapter, view, position, id) -> {
            horizontalOnItemClick(parentAdapter, position);
        });
        horizontalListView3.setOnItemClickListener((parentAdapter, view, position, id) -> {
            horizontalOnItemClick(parentAdapter, position);
        });
    }

}