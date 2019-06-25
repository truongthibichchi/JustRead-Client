package com.github.barteksc.sample.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.github.barteksc.sample.activity.PDFViewActivity.PERMISSION_CODE;
import static com.github.barteksc.sample.activity.PDFViewActivity.REQUEST_CODE;

@EActivity(R.layout.activity_add_news)
public class AddNewsActivity extends AppCompatActivity {

//    @ViewById(R.id.input_title)
//    EditText title;
//    @ViewById(R.id.input_author)
//    EditText author;
//    @ViewById(R.id.book_public_date)
//    EditText publicDate;
//    @ViewById(R.id.book_pages)
//    EditText pages;
//    @ViewById(R.id.book_descriptions)
//    EditText description;
//    @ViewById(R.id.btn_get_file)
//    Button getFile;
//
//
//    @AfterViews
//    protected void init() {
//    }
//
//    @Click(R.id.btn_get_file)
//    void pickFile() {
//        new MaterialFilePicker()
//                .withActivity(AddNewsActivity.this)
//                .withRequestCode(10)
//                .start();
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            pickFile();
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
//            }
//        }
//    }
//
//    ProgressDialog progress;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        if (requestCode == 10 && resultCode == RESULT_OK) {
//
//            progress = new ProgressDialog(AddNewsActivity.this);
//            progress.setTitle("Uploading");
//            progress.setMessage("Please wait...");
//            progress.show();
//
//            Thread t = new Thread(() -> {
//
//                File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
//                String content_type = getMimeType(f.getPath());
//
//                String file_path = f.getAbsolutePath();
//                OkHttpClient client = new OkHttpClient();
//                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);
//
//                RequestBody request_body = new MultipartBody.Builder()
//                        .addFormDataPart("username", LogInActivity.usernameLogin)
//                        .addFormDataPart("book_type", "text")
//                        .addFormDataPart("book_title", title.getText().toString())
//                        .addFormDataPart("book_author", author.getText().toString())
//                        .addFormDataPart("book_public_date", publicDate.getText().toString())
//                        .addFormDataPart("book_page", pages.getText().toString())
//                        .addFormDataPart("book_description", description.getText().toString())
//                        .addFormDataPart("book_image", content_type)
//                        .addFormDataPart("book_category", "test")
//                        .addFormDataPart("content", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
//                        .build();
//
//                Request request = new Request.Builder()
//                        .url("http://35.193.223.167:4600/add-news")
//                        .post(request_body)
//                        .build();
//
//                try {
//                    Response response = client.newCall(request).execute();
//
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Error : " + response);
//                    }
//
//                    progress.dismiss();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            });
//
//            t.start();
//
//
//        }
//    }
//
//    private String getMimeType(String path) {
//
//        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
//
//        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//    }

}
