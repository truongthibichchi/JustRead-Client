package com.github.barteksc.sample.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.StringUtility;



import java.util.ArrayList;
import java.util.List;

public class UserInformationActivity extends AppCompatActivity {

    public static List<BookModel> booksRead = new ArrayList<>();
    private ImageView ivAvatar;
    private TextView tvUsername;
    private EditText etFullname;
    private TextView tvCreatedTime;
    private EditText etBirthday;
    private EditText etAddress;
    private Button btnSave;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        findViewsByIds();

        showUserInformation();


    }

    private void showUserInformation() {
//        etFullname.setText(StringUtility.replaceNull(LogInActivity.userLogin.getFullname()));
//        etAddress.setText(StringUtility.replaceNull(LogInActivity.userLogin.getAddress()));
//        etBirthday.setText(StringUtility.replaceNull(LogInActivity.userLogin.getDateOfBirth()));
//        tvUsername.setText(StringUtility.replaceNull(LogInActivity.userLogin.getUsername()));
//        tvUsername.setText(StringUtility.replaceNull(LogInActivity.userLogin.getCreatedTime()));
//        if (LogInActivity.userLogin.getAvatar() != null) {
//            Glide.with(this.getApplicationContext()).load(ApiLink.HOST + LogInActivity.userLogin.getAvatar()).into(ivAvatar);
//        } else {
//            Glide.with(this.getApplicationContext()).load(R.drawable.ic_launcher).into(ivAvatar);
//        }
    }

    private void findViewsByIds() {
        ivAvatar = findViewById(R.id.img_user_info_avatar);
        tvUsername = findViewById(R.id.tv_user_info_username);
        etFullname = findViewById(R.id.tv_user_info_fullname);
        tvCreatedTime = findViewById(R.id.tv_user_info_created_time);
        etBirthday = findViewById(R.id.et_user_info_birthday);
        etAddress = findViewById(R.id.tv_user_info_address);
        btnSave = findViewById(R.id.btn_user_info_save);
        btnChangePassword = findViewById(R.id.btn_user_info_change_password);
    }



    private void setViewContent(UserModel usermodel) {
        etFullname.setText(StringUtility.replaceNull(usermodel.getFullname()));
        etAddress.setText(StringUtility.replaceNull(usermodel.getAddress()));
        etBirthday.setText(StringUtility.replaceNull(usermodel.getDateOfBirth()));
        tvUsername.setText(StringUtility.replaceNull(usermodel.getUsername()));
        tvUsername.setText(StringUtility.replaceNull(usermodel.getCreatedTime()));
//        if (LogInActivity.userLogin.getAvatar() != null) {
//            Glide.with(this.getApplicationContext()).load(ApiLink.HOST + LogInActivity.userLogin.getAvatar()).into(ivAvatar);
//        } else {
//            Glide.with(this.getApplicationContext()).load(R.drawable.ic_launcher).into(ivAvatar);
//        }
    }

    public void changePasswrod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ConstString.OOPS);
        builder.setMessage(ConstString.NULL_USER_INFOR);
        builder.setCancelable(false);
        builder.setPositiveButton(ConstString.YES, (dialogInterface, i) -> {
            Intent intent = new Intent(UserInformationActivity.this, UserInformationChangePasswordActivity.class);
            startActivity(intent);
        });
//        builder.setNegativeButton(ConstString.NO, (dialogInterface, i) -> setViewContent(LogInActivity.userLogin));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


//    void getReadingHistory() {
//        apiService.getReadingHistory(CreateJsonObject.username()).enqueue(new Callback<List<BookModel>>() {
//            @Override
//            public void onResponse(Call<List<BookModel>> call, Response<List<BookModel>> response) {
//                booksRead.clear();
//                if (response.body() != null) {
//                    if (response.body().size() > 0) {
//                        for (int i = 0; i < response.body().size(); i++) {
//                            BookModel book = BookModel.builder()
//                                    .bookAuthor(response.body().get(i).getBookAuthor())
//                                    .bookCategory(response.body().get(i).getBookCategory())
//                                    .bookDescription(response.body().get(i).getBookDescription())
//                                    .bookDownload(response.body().get(i).getBookDownload())
//                                    .bookFile(response.body().get(i).getBookFile())
//                                    .bookId(response.body().get(i).getBookId())
//                                    .bookImage(response.body().get(i).getBookImage())
//                                    .bookPage(response.body().get(i).getBookPage())
//                                    .bookPublicDate(response.body().get(i).getBookPublicDate())
//                                    .bookRatedTime(response.body().get(i).getBookRatedTime())
//                                    .bookRating(response.body().get(i).getBookRating())
//                                    .bookReadTimes(response.body().get(i).getBookReadTimes())
//                                    .bookTitle(response.body().get(i).getBookTitle())
//                                    .bookType(response.body().get(i).getBookType()).build();
//                            booksRead.add(i, book);
//                        }
//                        BooksAdapter booksAdapter = new BooksAdapter(getApplicationContext(), booksRead);
//                        bookReadList.setAdapter(booksAdapter);
//
//                        if (LogInActivity.userLogin == null) {
//                            showAlertDialogWithGivenContent();
//                        } else {
//                            setViewContent(LogInActivity.userLogin);
//                        }
//                        Toasty.success(getApplicationContext(), ConstString.GET_READING_HISTORY_SUCCESSFULL, Toast.LENGTH_SHORT, true).show();
//                    } else {
//                        Toasty.info(getApplicationContext(), ConstString.GET_READING_HISTORY_FAILURE, Toast.LENGTH_SHORT, true).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BookModel>> call, Throwable t) {
//                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
//            }
//        });
//    }

}
