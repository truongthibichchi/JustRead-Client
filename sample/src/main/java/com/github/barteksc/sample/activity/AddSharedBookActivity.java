package com.github.barteksc.sample.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSharedBookActivity extends AppCompatActivity {
    public APIService apiService;
    private Bundle mBundle;
    private String username;
    private String avatar;
    private String fullname;
    private String action;

    private String news_id;
    private String news_content;
    private String book_id;
    private String book_author;
    private String book_category;
    private String book_description;
    private String book_file;
    private String book_image;
    private String book_page;
    private String book_public_date;
    private String book_title;
    private String book_type;



    private ImageView imgAvatar;
    private TextView tvFullname;
    private EditText etContent;
    private ImageView imgBookImage;
    private EditText etBookTitle;
    private EditText etBookAuthor;
    private EditText etBookPublicDate;
    private EditText etBookPage;
    private EditText etBookDescription;
    private AppCompatSpinner spBookCategory;
    private AppCompatSpinner spBookType;
    private TextView tvFilename;
    private Button btnChooseFile;
    private Button btnAdd;
    private Button btnUpdate;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private Bitmap bmp;
    private Uri selectImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shared_book);

        findViewByIds();
        getDataFromBundle();
        setEventHandlers();
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
    }

    private void findViewByIds() {
        imgAvatar = findViewById(R.id.img_add_share_book_avatar);
        tvFullname = findViewById(R.id.tv_add_shared_book_fullname);
        etContent = findViewById(R.id.et_add_shared_book_content);
        imgBookImage = findViewById(R.id.img_add_shared_book_book_image);
        etBookTitle = findViewById(R.id.et_add_shared_book_title);
        etBookAuthor = findViewById(R.id.et_add_shared_book_author);
        etBookPublicDate = findViewById(R.id.et_add_shared_book_public_date);
        etBookPage = findViewById(R.id.et_add_shared_book_page);
        etBookDescription = findViewById(R.id.et_add_shared_book_description);
        spBookCategory = findViewById(R.id.sp_add_shared_book_category);
        spBookType = findViewById(R.id.sp_add_shared_book_type);
        tvFilename = findViewById(R.id.tv_add_shared_book_filename);
        btnChooseFile = findViewById(R.id.btn_add_shared_book_file);
        btnAdd = findViewById(R.id.btn_add_shared_book_add);
        btnUpdate = findViewById(R.id.btn_add_shared_book_edit);

    }


    private void getDataFromBundle() {
        mBundle = getIntent().getExtras();
        username = (String) mBundle.get("username");
        avatar = (String) mBundle.get("avatar");
        fullname = (String) mBundle.get("fullname");
        action = (String) mBundle.get("action");
        showUserInfo();
        if(action.equals(ConstString.ACTION_ADD)){
            btnAdd.setVisibility(View.VISIBLE);
        }
        else if(action.equals(ConstString.ACTION_UPDATE)) {
            news_id = (String) mBundle.get("news_id");
            news_content = (String) mBundle.get("news_content");
            book_id = (String) mBundle.get("book_id");
            book_author = (String) mBundle.get("book_author");
            book_category = (String) mBundle.get("book_category");
            book_description = (String) mBundle.get("book_description");
            book_file = (String) mBundle.get("book_file");
            book_image = (String) mBundle.get("book_image");
            book_page = (String) mBundle.get("book_page");
            book_public_date = (String) mBundle.get("book_public_date");
            book_title = (String) mBundle.get("book_title");
            book_type = (String) mBundle.get("book_type");
            btnUpdate.setVisibility(View.VISIBLE);

            showSharedBookInfo();
        }

    }

    private void showUserInfo() {
        Glide.with(getApplicationContext())
                .load(avatar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgAvatar);

        tvFullname.setText(fullname);
    }

    private void showSharedBookInfo() {
        etContent.setText(news_content);
        Glide.with(getApplicationContext())
                .load(book_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgBookImage);

        etBookTitle.setText(book_title);
        etBookAuthor.setText(book_author);
        etBookPublicDate.setText(book_public_date);
        etBookPage.setText(book_page);
        etBookDescription.setText(book_description);
    }

    private void setEventHandlers() {
        imgBookImage.setOnClickListener(view -> {
            final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(AddSharedBookActivity.this);
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

        btnAdd.setOnClickListener(View ->{
            addSharedBook();
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
                        .into(imgBookImage);
            } else if (requestCode == SELECT_FILE) {
                selectImageUri = data.getData();
                Glide.with(getApplicationContext())
                        .load(selectImageUri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imgBookImage);
            }
        }
    }

    private void addSharedBook() {
        RequestBody rqUsername =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), username);

        RequestBody rqBookType =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), spBookType.getSelectedItem().toString());

        RequestBody rqPage =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBookPage.getText().toString());

        RequestBody rqContent =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etContent.getText().toString());

        RequestBody rqTitle =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBookTitle.getText().toString());

        RequestBody rqAuthor =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBookAuthor.getText().toString());
        RequestBody rqPublicDate =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBookPublicDate.getText().toString());

        RequestBody rqDescription =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), etBookPublicDate.getText().toString());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ((BitmapDrawable) imgBookImage.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        MultipartBody.Part rqImage = MultipartBody.Part.createFormData(
                "book_image",
                "image.jpg",
                RequestBody.create(MediaType.parse("image/jpg"), bos.toByteArray())
        );


        apiService.addNews(
                rqUsername, rqBookType, rqTitle, rqAuthor, rqPublicDate, rqPage, rqDescription, rqContent, rqImage
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toasty.info(getApplicationContext(), response.body(), Toast.LENGTH_SHORT, true).show();
                    finish();
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

}
