package com.github.barteksc.sample.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private String is_admin;

    private Bitmap bmp;
    private Uri selectImageUri;
    public APIService apiService;

    private ImageView ivAvatar;
    private TextView tvUsername;
    private EditText etFullname;
    private TextView tvCreatedDate;
    private EditText etBirthday;
    private EditText etAddress;
    private Button btnSave;
    private Button btnChangePassword;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();

        findViewsByIds();
        getUserInfo();
        showUserInformation();
        setupEventHandlers();

    }

    private void setupEventHandlers() {
        btnSave.setOnClickListener(view -> {
            updateUserInfomation();
        });

        btnChangePassword.setOnClickListener(View -> {
            changePassword();
        });

        ivAvatar.setOnClickListener(view -> {
            final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(UserInformationActivity.this);
            builder.setTitle("Add image");
            builder.setItems(items, (dialog, i) -> {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                bmp = (Bitmap) bundle.get("data");
                Glide.with(getApplicationContext())
                        .load(bmp)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ivAvatar);
            } else if (requestCode == SELECT_FILE) {
                selectImageUri = data.getData();
                Glide.with(getApplicationContext())
                        .load(selectImageUri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ivAvatar);
            }
        }
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
        is_admin = (String) mBundle.get("is_admin");
    }

    private void showUserInformation() {
        tvUsername.setText(username);
        etFullname.setText(fullname);
        etAddress.setText(address);
        etBirthday.setText(date_of_birth);
        tvCreatedDate.setText(create_date);

        if (avatar != null) {
            Glide.with(getApplicationContext())
                    .load(Uri.parse(ApiLink.HOST + avatar))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
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
        builder.setCancelable(true);
        builder.setPositiveButton(ConstString.YES, (dialogInterface, i) -> {
            Intent intent = new Intent(UserInformationActivity.this, UserInformationChangePasswordActivity_.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateUserInfomation() {
        changePasswordAPIExecute();
    }

    public void changePasswordAPIExecute() {
        RequestBody rqUsername =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), tvUsername.getText().toString());

        RequestBody rqFullname =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etFullname.getText().toString());

        RequestBody rqDateOfBirth =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBirthday.getText().toString());

        RequestBody rqAddress =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etAddress.getText().toString());
        RequestBody rqIsAdmin =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), is_admin);


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        MultipartBody.Part rqAvatar = MultipartBody.Part.createFormData(
                "avatar",
                "avatar.jpg",
                RequestBody.create(MediaType.parse("image/jpg"), bos.toByteArray())
        );


        apiService.updateUserInfo(
                rqUsername, rqFullname, rqDateOfBirth, rqAddress, rqIsAdmin, rqAvatar
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toasty.info(getApplicationContext(), response.body(), Toast.LENGTH_SHORT, true).show();
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
