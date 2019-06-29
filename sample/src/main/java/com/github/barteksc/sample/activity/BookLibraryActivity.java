package com.github.barteksc.sample.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.HorizontalAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.GeneralUtility;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.HorizontalListView;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookLibraryActivity extends AppCompatActivity {

    public List<BookModel> books = new ArrayList<>();
    public List<BookModel> result = new ArrayList<>();
    public APIService apiService;
    private Toolbar toolbar;
    public HorizontalAdapter horizontalAdapter;

    private ImageView search;
    private GridView gvBooks;
    private EditText searchBar;
    private AppCompatSpinner sp_category;
    private AppCompatSpinner sp_rating;
    private ContentLoadingProgressBar contentLoadingProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_library);
        findViewsByIds();
        apiService = ApiUtils.getAPIService();
        getAllBooks();
        setSupportActionBar(toolbar);
        //search.setOnClickListener(view -> search());
        gvBooks.setOnItemClickListener((parent, view, position, id) -> horizontalOnItemClick(parent, position));
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keySearch = searchBar.getText().toString().toLowerCase().trim();
                result = searchWithText(keySearch, books);
                horizontalAdapter = new HorizontalAdapter(getApplicationContext(), result);
                gvBooks.setAdapter(horizontalAdapter);
                horizontalAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String keySearch = searchBar.getText().toString().toLowerCase().trim();
                horizontalAdapter = new HorizontalAdapter(getApplicationContext(), searchWithSpinnerCategory(getCategory(sp_category.getSelectedItem().toString()),  searchWithText(keySearch, books)));
                gvBooks.setAdapter(horizontalAdapter);
                horizontalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findViewsByIds() {
        search = findViewById(R.id.img_book_library_search);
        gvBooks = findViewById(R.id.gv_all_books);
        toolbar = findViewById(R.id.toolbar_book_library);
        sp_category = findViewById(R.id.sp_book_library_category);
        sp_rating = findViewById(R.id.sp_book_library_rating);
        searchBar = findViewById(R.id.et_book_library_search);
        contentLoadingProgressBar = findViewById(R.id.address_looking_up);
    }


    private void getAllBooks() {
        books.clear();
        contentLoadingProgressBar.show();
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
                    contentLoadingProgressBar.hide();
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

   /* @RequiresApi(api = Build.VERSION_CODES.N)
    public void search(){
        searchWithSpinner();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<BookModel> searchWithSpinnerCategory(String category, List<BookModel> books) {
        return books.stream().filter(bookModel -> category.equals(bookModel.getBookCategory()))
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<BookModel> searchWithSpinnerRating(int rating, List<BookModel> books) {
        return books.stream().filter(bookModel -> Integer.valueOf(bookModel.getBookRating()) >= rating)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<BookModel> searchWithText(String keySearch, List<BookModel> books) {
        return books.stream()
                .filter(bookModel -> bookModel.getBookAuthor().toLowerCase().trim().contains(keySearch)
                        || bookModel.getBookTitle().toLowerCase().trim().contains(keySearch))
                .collect(Collectors.toList());
    }

    private int getRating(String spSelected) {
        if (spSelected.contains("1")) {
            return 20;
        } else if (spSelected.contains("2")) {
            return 40;
        } else if (spSelected.contains("3")) {
            return 60;
        } else {
            return 80;
        }
    }

    private String getCategory(String spSelected) {
        if (spSelected.contains("--")) {
            return "";
        } else {
            return spSelected;
        }
    }

    void horizontalOnItemClick(AdapterView<?> parentAdapter, int position) {
        BookModel book = (BookModel) parentAdapter.getItemAtPosition(position);
        Intent intent = new Intent(BookLibraryActivity.this, BookDetailActivity.class);
        GeneralUtility.putIntent(intent, book);
        startActivity(intent);
    }

}
