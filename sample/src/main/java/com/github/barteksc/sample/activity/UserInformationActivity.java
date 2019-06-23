package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    private Bundle mBundle;
    private String username;
    private String password;
    private String fullname;
    private String create_date;
    private String address;
    private String date_of_birth;
    private String avatar;

    private ImageView ivAvatar;
    private TextView tvUsername;
    private EditText etFullname;
    private TextView tvCreatedDate;
    private EditText etBirthday;
    private EditText etAddress;
    private Button btnSave;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        findViewsByIds();
        getUserInfo();
        showUserInformation();
        setupEventHandlers();

    }

    private void setupEventHandlers() {
        btnSave.setOnClickListener(view->{
            updateUserInfomation();
        });

        btnChangePassword.setOnClickListener(View -> {
            changePassword();
        });
    }

    private void updateUserInfomation() {
    }

    private void getUserInfo() {
        mBundle = getIntent().getExtras();
        username = (String) mBundle.get("username");
        password = (String) mBundle.get("password");
        fullname = (String) mBundle.get("fullname");
        create_date = (String) mBundle.get("created_date");
        date_of_birth = (String) mBundle.get("date_of_birth");
        address = (String) mBundle.get("address");
        avatar = (String) mBundle.get("avatar");
    }

    private void showUserInformation() {
        tvUsername.setText(username);
        etFullname.setText(fullname);
        etAddress.setText(address);
        etBirthday.setText(date_of_birth);
        tvCreatedDate.setText(create_date);

        if (avatar != null) {
            Glide.with(getApplicationContext())
                    .load(Uri.parse(ApiLink.HOST+avatar))
                    .into(ivAvatar);
        }
    }

    private void findViewsByIds() {
        ivAvatar = findViewById(R.id.img_user_info_avatar);
        tvUsername = findViewById(R.id.tv_user_info_username);
        etFullname = findViewById(R.id.tv_user_info_fullname);
        tvCreatedDate = findViewById(R.id.tv_user_info_created_date);
        etBirthday = findViewById(R.id.et_user_info_birthday);
        etAddress = findViewById(R.id.tv_user_info_address);
        btnSave = findViewById(R.id.btn_user_info_save);
        btnChangePassword = findViewById(R.id.btn_user_info_change_password);
    }


    public void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ConstString.OOPS);
        builder.setMessage(ConstString.NULL_USER_INFOR);
        builder.setCancelable(false);
        builder.setPositiveButton(ConstString.YES, (dialogInterface, i) -> {
            Intent intent = new Intent(UserInformationActivity.this, UserInformationChangePasswordActivity_.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        });
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
