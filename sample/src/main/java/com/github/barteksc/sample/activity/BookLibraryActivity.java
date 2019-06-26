package com.github.barteksc.sample.activity;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.HorizontalAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.HorizontalListView;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookLibraryActivity extends AppCompatActivity {

    public List<BookModel> books = new ArrayList<>();
    public APIService apiService;
    private Toolbar toolbar;
    public HorizontalAdapter horizontalAdapter;

    public BookModel book;
    private GridView gvBooks;
    private AppCompatSpinner sp_category;
    private AppCompatSpinner sp_rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_library);
        findViewsByIds();
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();

        setSupportActionBar(toolbar);
        getAllBooks();

    }

    private void findViewsByIds() {
        gvBooks = findViewById(R.id.gv_all_books);
        toolbar = findViewById(R.id.toolbar_book_library);
        sp_category = findViewById(R.id.sp_book_library_category);
        sp_rating = findViewById(R.id.sp_book_library_rating);
    }

    private void getAllBooks() {
        books.clear();
        apiService.getAllBooks().enqueue(new Callback<List<BookModel>>() {
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
                    horizontalAdapter = new HorizontalAdapter(getApplicationContext(), books);
                    gvBooks.setAdapter(horizontalAdapter);
                    horizontalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

}
