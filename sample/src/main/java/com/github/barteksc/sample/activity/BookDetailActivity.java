package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.CommentAdapter;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.github.barteksc.sample.activity.LogInActivity.apiService;
//import static com.github.barteksc.sample.activity.LogInActivity.usernameLogin;
//import static com.github.barteksc.sample.activity.MainActivity.book;

@EActivity(R.layout.activity_book_detail)
public class BookDetailActivity extends AppCompatActivity {

    @ViewById(R.id.img_book_detail_book_image)
    ImageView bookImage;
    @ViewById(R.id.tv_book_detail_book_title)
    TextView bookTitle;
    @ViewById(R.id.rb_book_detail_ratingbar)
    RatingBar ratingBar;
    @ViewById(R.id.tv_book_detail_book_category)
    TextView bookCategory;
    @ViewById(R.id.tv_book_detail_book_author)
    TextView bookAuthor;
    @ViewById(R.id.tv_book_detail_book_public_date)
    TextView bookPublicDate;
    @ViewById(R.id.tv_book_detail_book_pages)
    TextView bookPages;
    @ViewById(R.id.tv_book_detail_book_rating)
    TextView bookRating;
    @ViewById(R.id.tv_book_detail_book_rated_time)
    TextView bookRatedTime;
    @ViewById(R.id.et_book_detail_comment_content)
    EditText commentContent;
    @ViewById(R.id.lv_book_detail_comment_list)
    ListView lvComment;

    private CommentAdapter commentAdapter;


    @AfterViews
    public void init() {
//        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> apiService.addRating(CreateJsonObject.rating(book.getBookId(), ratingBar.getRating())).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()){
//                    Toasty.success(getApplicationContext(), ConstString.SUCCESS_STATUS, Toast.LENGTH_SHORT, true).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
//
//            }
//        }));

//        if (book.getBookImage() != null) {
//            Glide.with(this).load(book.getBookImage()).into(bookImage);
//        } else {
//            bookImage.setImageResource(R.drawable.ic_launcher);
//        }
//
//        bookTitle.setText(book.getBookTitle());
//        bookCategory.setText(book.getBookCategory());
//        bookAuthor.setText(book.getBookAuthor());
//        bookPublicDate.setText(book.getBookPublicDate());
//        bookPages.setText(book.getBookPage());
//        bookRating.setText(book.getBookRating());
//        bookRatedTime.setText(book.getBookRatedTime());
//        getCommentAPIExecute();
    }


////    @Click(R.id.btn_book_detail_read_book)
////    void transferToBookViewer() {
////        addReadingHistory();
////    }
//
//    @Click(R.id.btn_book_detail_book_description)
//    void showBookDescription(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
//        builder.setMessage(book.getBookDescription());
//        builder.setCancelable(true);
//
//        builder.setNegativeButton(
//                "Cancel",
//                (dialog, id)->
//                        dialog.cancel()
//        );
//
//
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

//    @Click(R.id.btn_book_detail_send_comment)
//    void sendComment(){
//        if (isNull()) {
//            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_LOGIN, Toast.LENGTH_SHORT, true).show();
//        } else {
//            addCommentAPIExecute(commentContent.getText().toString());
//
//        }
//    }

    boolean isNull() {
        return commentContent.getText().length() == 0 || commentContent.getText().length() == 0;
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

//    void addCommentAPIExecute(String content) {
//        apiService.addComment(CreateJsonObject.comment(book.getBookId(), usernameLogin, content)).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    Toasty.info(getApplicationContext(), ConstString.SUCCESS_STATUS, Toast.LENGTH_SHORT, true).show();
//                    finish();
//                    startActivity(getIntent());
//                } else {
//                    Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
//            }
//        });
//    }

//    void getCommentAPIExecute() {
//        apiService.getComment(CreateJsonObject.bookId(book.getBookId())).enqueue(new Callback<List<CommentModel>>() {
//            @Override
//            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
//                if (response.isSuccessful()) {
//                    commentAdapter = new CommentAdapter(getApplicationContext(), response.body());
//                    lvComment.setAdapter(commentAdapter);
//                    Toasty.info(getApplicationContext(), ConstString.SUCCESS_STATUS, Toast.LENGTH_SHORT, true).show();
//                } else {
//                    Toasty.error(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
//                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
//            }
//        });
//    }
//
//    void addReadingHistory() {
//        apiService.addReadingHistory(CreateJsonObject.readingHistory(book.getBookId(), usernameLogin)).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Intent intent = new Intent(BookDetailActivity.this, PDFViewActivity_.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Intent intent = new Intent(BookDetailActivity.this, PDFViewActivity_.class);
//                startActivity(intent);
//            }
//        });
//    }

}
