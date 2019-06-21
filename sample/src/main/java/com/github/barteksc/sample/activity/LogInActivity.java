package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.PasswordUtility;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_log_in)
public class LogInActivity extends AppCompatActivity {

    public static UserModel userLogin;
    public static List<BookModel> books = new ArrayList<>();
    public static List<BookModel> topBooks = new ArrayList<>();
    public static List<BookModel> recommendBooks = new ArrayList<>();

    @ViewById(R.id.input_username)
    EditText et_username;

    @ViewById(R.id.input_password)
    EditText et_password;

    @ViewById(R.id.tv_register)
    TextView tv_register;

    @ViewById(R.id.btn_login)
    AppCompatButton btn_login;

    public static APIService apiService;
    public static String usernameLogin;

    @AfterViews
    public void init( ) {
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        et_username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_account_circle_black_24,0,0,0);
        et_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_black_24,0,0,0);
        getLimitedBooks();
        getTopBooks();
    }

    @Click(R.id.btn_login)
    public void buttonLoginAction() {
        if (isNull()) {
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_LOGIN, Toast.LENGTH_SHORT, true).show();
        } else {
//            logInAPIExecute();
            Intent intent = new Intent(LogInActivity.this, MainActivity_.class);
            startActivity(intent);
        }
    }

    @Click(R.id.tv_register)
    public void tvRegisterAction(){
        Intent intent = new Intent(LogInActivity.this, Register_.class);
        startActivity(intent);
        cleanInputText();
    }

    public void logInAPIExecute() {
        apiService.logIn(CreateJsonObject.usernameAndPassword(et_username.getText().toString(),et_password.getText().toString())).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toasty.info(getApplicationContext(), ConstString.SUCCESS_LOGIN, Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(LogInActivity.this, MainActivity_.class);
                    startActivity(intent);
                    usernameLogin = et_username.getText().toString();
                    cleanInputText();

                }else {
                    Toasty.info(getApplicationContext(), ConstString.FAILURE_LOGIN, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void getLimitedBooks() {
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
                                .bookReadTimes(response.body().get(i).getBookReadTimes())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookType(response.body().get(i).getBookType()).build();
                        books.add(i, book);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void getTopBooks() {
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
                                .bookReadTimes(response.body().get(i).getBookReadTimes())
                                .bookTitle(response.body().get(i).getBookTitle())
                                .bookType(response.body().get(i).getBookType()).build();
                        topBooks.add(i, book);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }


    boolean isNull() {
        return et_username.getText().length() == 0 || et_password.getText().length() == 0;
    }

    private void cleanInputText(){
        et_username.setText("");
        et_password.setText("");
    }
}
