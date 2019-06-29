package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.CommentAdapter;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;


import java.util.List;

import es.dmoral.toasty.Toasty;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDetailActivity extends AppCompatActivity {
    public APIService apiService;
    public SharedPreferences sharedPreferences;
    private String username;
    private Bundle mBundle;
    private String book_id;
    private String book_author;
    private String book_description;
    private String book_category;
    private String book_download;
    private String book_file;
    private String book_image;
    private String book_page;
    private String book_public_date;
    private String book_rated_time;
    private String book_read_time;
    private String book_rating;
    private String book_title;
    private String book_type;
    private String book_is_deleted;
    private String book_created_time;
    private int rated_time;
    private int read_time;

    private ImageView imgBookImage;
    private TextView tvBookTitle;
    private RatingBar rbRatingBar;
    private TextView tvBookCategory;
    private TextView tvBookAuthor;
    private TextView tvBookPublicDate;
    private TextView tvBookPage;
    private TextView tvBookRating;
    private TextView tvBookRatedTime;
    private TextView tvBookReadTime;
    private TextView tvBookType;
    private EditText etCommentContent;
    private Button btnSendComment, btnShowBookDescription, btnReadBook;
    private ListView lvCommentList;

    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        findViewsByIds();
        getDataFromBundle();
        disPlayBookInfo();


    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpEventHandler();
        getCommentAPIExecute();
    }

    private void findViewsByIds() {
        imgBookImage = findViewById(R.id.img_book_detail_book_image);
        tvBookTitle = findViewById(R.id.tv_book_detail_book_title);
        rbRatingBar = findViewById(R.id.rb_book_detail_ratingbar);
        tvBookCategory = findViewById(R.id.tv_book_detail_book_category);
        tvBookAuthor = findViewById(R.id.tv_book_detail_book_author);
        tvBookPublicDate = findViewById(R.id.tv_book_detail_book_public_date);
        tvBookPage = findViewById(R.id.tv_book_detail_book_page);
        tvBookRating = findViewById(R.id.tv_book_detail_book_rating);
        tvBookRatedTime = findViewById(R.id.tv_book_detail_book_rated_time);
        tvBookReadTime = findViewById(R.id.tv_book_detail_book_read_time);
        tvBookType = findViewById(R.id.tv_book_detail_book_type);
        etCommentContent = findViewById(R.id.et_book_detail_comment_content);
        lvCommentList = findViewById(R.id.lv_book_detail_comment_list);
        btnSendComment = findViewById(R.id.btn_book_detail_send_comment);
        btnShowBookDescription = findViewById(R.id.btn_book_detail_book_description);
        btnReadBook = findViewById(R.id.btn_book_detail_read_book);

    }

    private void getDataFromBundle() {

        mBundle = getIntent().getExtras();
        book_id = (String) mBundle.get("book_id");
        book_author = (String) mBundle.get("book_author");
        book_category = (String) mBundle.get("book_category");
        book_description = (String) mBundle.get("book_description");
        book_download = (String) mBundle.get("book_download");
        book_file = (String) mBundle.get("book_file");
        book_image = (String) mBundle.get("book_image");
        book_page = (String) mBundle.get("book_page");
        book_public_date = (String) mBundle.get("book_public_date");
        book_rated_time = (String) mBundle.get("book_rated_time");
        book_read_time = (String) mBundle.get("book_read_time");
        book_rating = (String) mBundle.get("book_rating");
        book_title = (String) mBundle.get("book_title");
        book_type = (String) mBundle.get("book_type");
        book_is_deleted = (String) mBundle.get("book_is_deleted");
        book_created_time = (String) mBundle.get("book_created_time");
        rated_time = Integer.parseInt(book_rated_time);
        read_time = Integer.parseInt(book_read_time);
    }

    private void disPlayBookInfo() {
        tvBookTitle.setText(book_title);
        tvBookCategory.setText(book_category);
        tvBookAuthor.setText(book_author);
        tvBookPage.setText(book_page);
        tvBookPublicDate.setText(book_public_date);
        tvBookRating.setText(book_rating + "/100");
        tvBookRatedTime.setText(book_rated_time);
        tvBookReadTime.setText(book_read_time);
        tvBookType.setText(book_type);
        Glide.with(getApplicationContext())
                .load(book_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgBookImage);

    }


    private void setUpEventHandler() {

        btnShowBookDescription.setOnClickListener(View ->{
            showBookDescription();
        });

        btnReadBook.setOnClickListener(View ->{
            addReadingHistory();
        });

        btnSendComment.setOnClickListener(view->{
            sendComment();
        });


        rbRatingBar.setOnRatingBarChangeListener((ratingBar, v, b) ->
                apiService.addRating(CreateJsonObject.rating(book_id, ratingBar.getRating())).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            tvBookRating.setText(response.body() + "/100");
                            rated_time +=1;
                            tvBookRatedTime.setText(Integer.toString(rated_time));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();

                    }
                }));


    }


    void showBookDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
        builder.setMessage(book_description);
        builder.setCancelable(true);

        builder.setNegativeButton(
                "Cancel",
                (dialog, id) ->
                        dialog.cancel()
        );


        AlertDialog alert = builder.create();
        alert.show();
    }

    void sendComment(){
        if (isNull()) {
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT, Toast.LENGTH_SHORT, true).show();
        } else {
            addCommentAPIExecute(etCommentContent.getText().toString());

        }
    }

    boolean isNull(){
        return etCommentContent.getText().length() == 0 || etCommentContent.getText().length() == 0;
    }

//    private void showAddItemDialog(Context c) {
//        final EditText taskEditText = new EditText(c);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(taskEditText);
//        builder.setMessage("Add your comment about this book");
//        builder.setCancelable(true);
//        builder.setPositiveButton("Add", (dialogInterface, i) -> {
//            addCommentAPIExecute(String.valueOf(taskEditText.getText()));
//        });
//        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
//
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    void addCommentAPIExecute(String content) {
        apiService.addComment(CreateJsonObject.comment(book_id, username, content)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toasty.info(getApplicationContext(), ConstString.SUCCESS_STATUS, Toast.LENGTH_SHORT, true).show();
                    etCommentContent.setText("");
                    getCommentAPIExecute();
                } else {
                    Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void getCommentAPIExecute(){
        apiService.getComment(CreateJsonObject.bookId(book_id)).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if (response.isSuccessful()) {
                    commentAdapter = new CommentAdapter(getApplicationContext(), response.body());
                    lvCommentList.setAdapter(commentAdapter);
                } else {
                    Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void addReadingHistory(){
        apiService.addReadingHistory(CreateJsonObject.readingHistory(book_id, username)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Intent intent = new Intent(BookDetailActivity.this, PDFViewActivity_.class);
                intent.putExtra("book_file", book_file);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Intent intent = new Intent(BookDetailActivity.this, PDFViewActivity_.class);
                startActivity(intent);
            }
        });
    }

}
