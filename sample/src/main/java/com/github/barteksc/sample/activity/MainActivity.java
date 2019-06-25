package com.github.barteksc.sample.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.HorizontalAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.HorizontalListView;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.OptionsItem;

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public SharedPreferences sharedPreferences;
    public boolean saveLogin;

    public BookModel book;
    public List<BookModel> books = new ArrayList<>();
    public List<BookModel> topBooks = new ArrayList<>();
    public List<BookModel> recommendBooks = new ArrayList<>();
    public HorizontalAdapter horizontalAdapter1, horizontalAdapter2, horizontalAdapter3;
    public UserModel userLogin;
    public APIService apiService;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView navView;
    private HorizontalListView horizontalListView1;
    private HorizontalListView horizontalListView2;
    private HorizontalListView horizontalListView3;
    private View headerView;
    private Menu menuNav;
    private ImageView imgHeaderAvatar;
    private TextView tvHeaderFullName;
    private MenuItem itemAdmin;
    private String username;
    private String password;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = (item -> {
//        switch (item.getItemId()) {
//            case R.id.menu_main_home:
//                return true;
//            case R.id.menu_main_book_news:
//                return true;
//            case R.id.menu_main_users:
//                return true;
//        }
//        return false;
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewsByIds();
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();

        setSupportActionBar(toolbar);
        fab.setOnClickListener(view->{

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        saveLogin = sharedPreferences.getBoolean("savelogin", true);
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);

        if (username ==null || password== null) {
            Intent intent = new Intent(MainActivity.this, LogInActivity_.class);
            startActivity(intent);

        } else {
            if (isNetworkAvailable()) {
                logInAPIExecute();

            } else {
                Toasty.info(getApplicationContext(), ConstString.NETWORK_ERROR, Toast.LENGTH_SHORT, true).show();
            }
        }
        navigationView.setNavigationItemSelectedListener(this);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void logInAPIExecute() {
        apiService.logIn(CreateJsonObject.usernameAndPassword(username, password)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    getNewestBooks();
                    getTopBooks();
                    getRecommendBooks();
                    getUserInformation();
                    setOnItemClickAction();

                } else {
                    Toasty.info(getApplicationContext(), ConstString.FAILURE_LOGIN, Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(MainActivity.this, LogInActivity_.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private void findViewsByIds() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        navView = findViewById(R.id.nav_bottom_view);

        horizontalListView1 = findViewById(R.id.hlv_main_all_books);
        horizontalListView2 = findViewById(R.id.hlv_main_top_books);
        horizontalListView3 = findViewById(R.id.hlv_main_recommend_book);
        headerView = navigationView.getHeaderView(0);
        imgHeaderAvatar = headerView.findViewById(R.id.img_header_avatar);
        tvHeaderFullName = headerView.findViewById(R.id.tv_header_fullname);

        menuNav = navigationView.getMenu();
        itemAdmin = menuNav.findItem(R.id.drawermenu_admin);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_main_all_shared_book) {
            Intent intent = new Intent(MainActivity.this, AllSharedBooksActivity.class);
            intent.putExtra("username", userLogin.getUserUsername());
            intent.putExtra("fullname", userLogin.getUserFullname());
            intent.putExtra("avatar", userLogin.getUserAvatar());
            intent.putExtra("is_admin", userLogin.getUserIsAdmin());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawermenu_user_info) {
            Intent intent = new Intent(MainActivity.this, UserInformationActivity.class);
            intent.putExtra("username", userLogin.getUserUsername());
            intent.putExtra("password", userLogin.getUserPassword());
            intent.putExtra("fullname", userLogin.getUserFullname());
            intent.putExtra("created_date", userLogin.getUserCreatedDate());
            intent.putExtra("address", userLogin.getUserAddress());
            intent.putExtra("date_of_birth", userLogin.getUserDateOfBirth());
            intent.putExtra("avatar", userLogin.getUserAvatar());
            intent.putExtra("is_admin", userLogin.getUserIsAdmin());
            startActivity(intent);

        } else if (id == R.id.drawermenu_reading_history) {
            Intent intent = new Intent(MainActivity.this, UserReadingHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.drawermenu_user_share_book) {
            Intent intent = new Intent(MainActivity.this, UserShareBookActivity_.class);
            intent.putExtra("username", userLogin.getUserUsername());
            intent.putExtra("password", userLogin.getUserPassword());
            intent.putExtra("fullname", userLogin.getUserFullname());
            intent.putExtra("created_date", userLogin.getUserCreatedDate());
            intent.putExtra("address", userLogin.getUserAddress());
            intent.putExtra("date_of_birth", userLogin.getUserDateOfBirth());
            intent.putExtra("avatar", userLogin.getUserAvatar());
            intent.putExtra("is_admin", userLogin.getUserIsAdmin());
            startActivity(intent);
        } else if (id == R.id.drawermenu_book_library) {
            Intent intent = new Intent(MainActivity.this, BookLibraryActivity_.class);
            startActivity(intent);
        } else if (id == R.id.drawermenu_all_user) {
            Intent intent = new Intent(MainActivity.this, UsersActivity_.class);
            startActivity(intent);
        } else if (id == R.id.drawermenu_admin) {
            Intent intent = new Intent(MainActivity.this, AdminActivity_.class);
            startActivity(intent);
        } else if (id == R.id.drawermenu_logout) {
            Intent intent = new Intent(MainActivity.this, LogInActivity_.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void getUserInformation() {
        userLogin = null;
        apiService.getUserInfo(CreateJsonObject.username(username)).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() != null) {
                    userLogin = UserModel.builder()
                            .userAvatar(response.body().userAvatar)
                            .userAddress(response.body().userAddress)
                            .userDateOfBirth(response.body().userDateOfBirth)
                            .userIsAdmin(response.body().userIsAdmin)
                            .userFullname(response.body().userFullname)
                            .userUsername(response.body().userUsername)
                            .userCreatedDate(response.body().userCreatedDate)
                            .userPassword(response.body().userPassword)
                            .build();
                    tvHeaderFullName.setText(userLogin.getUserFullname());
                    Glide.with(getApplicationContext())
                            .load(Uri.parse(ApiLink.HOST + userLogin.getUserAvatar()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgHeaderAvatar);


                    if (userLogin.userIsAdmin.equalsIgnoreCase("1")) {
                        itemAdmin.setVisible(true);
                    }
                }
                else {
                    Toasty.info(getApplicationContext(), response.message(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }


    private void getNewestBooks() {
        books.clear();
        apiService.getMenuBook().enqueue(new Callback<List<BookModel>>() {
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
                                .bookReadTime(response.body().get(i).getBookReadTime())
                                .bookIsDeleted(response.body().get(i).getBookIsDeleted())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookCreatedTime(response.body().get(i).getBookCreatedTime())
                                .bookType(response.body().get(i).getBookType()).build();
                        books.add(i, book);
                    }
                    horizontalAdapter1 = new HorizontalAdapter(getApplicationContext(), books);
                    horizontalListView1.setAdapter(horizontalAdapter1);
                    horizontalAdapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }


    private void getTopBooks() {
        topBooks.clear();
        apiService.getTopBooks().enqueue(new Callback<List<BookModel>>() {
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
                                .bookReadTime(response.body().get(i).getBookReadTime())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookIsDeleted(response.body().get(i).getBookIsDeleted())
                                .bookCreatedTime(response.body().get(i).getBookCreatedTime())
                                .bookType(response.body().get(i).getBookType()).build();
                        topBooks.add(i, book);
                    }
                    horizontalAdapter2 = new HorizontalAdapter(getApplicationContext(), topBooks);
                    horizontalListView2.setAdapter(horizontalAdapter2);
                    horizontalAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }


    void getRecommendBooks() {
        recommendBooks.clear();
        apiService.getRecommendBooks(CreateJsonObject.username(username)).enqueue(new Callback<List<BookModel>>() {
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
                                .bookReadTime(response.body().get(i).getBookReadTime())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookIsDeleted(response.body().get(i).getBookIsDeleted())
                                .bookCreatedTime(response.body().get(i).getBookCreatedTime())
                                .bookType(response.body().get(i).getBookType()).build();
                        recommendBooks.add(i, book);
                    }
                    horizontalAdapter3 = new HorizontalAdapter(getApplicationContext(), recommendBooks);
                    horizontalListView3.setAdapter(horizontalAdapter3);
                    horizontalAdapter3.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void horizontalOnItemClick(AdapterView<?> parentAdapter, int position) {
        book = (BookModel) parentAdapter.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
        intent.putExtra("book_id", book.getBookId());
        intent.putExtra("book_author", book.getBookAuthor());
        intent.putExtra("book_category", book.getBookCategory());
        intent.putExtra("book_description", book.getBookDescription());
        intent.putExtra("book_download", book.getBookDownload());
        intent.putExtra("book_file", book.getBookFile());
        intent.putExtra("book_image", book.getBookImage());
        intent.putExtra("book_page", book.getBookPage());
        intent.putExtra("book_public_date", book.getBookPublicDate());
        intent.putExtra("book_rated_time", book.getBookRatedTime());
        intent.putExtra("book_read_time", book.getBookReadTime());
        intent.putExtra("book_rating", book.getBookRating());
        intent.putExtra("book_title", book.getBookTitle());
        intent.putExtra("book_type", book.getBookType());
        intent.putExtra("book_is_deleted", book.getBookIsDeleted());
        intent.putExtra("book_created_time", book.getBookCreatedTime());
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