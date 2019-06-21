package com.github.barteksc.sample.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.adapter.BooksAdapter;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.StringUtility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;

@EActivity(R.layout.activity_user_information)
@OptionsMenu(R.menu.edit_user_activity_options)
public class UserInformation extends AppCompatActivity {

    public static List<BookModel> booksRead = new ArrayList<>();

    @ViewById(R.id.user_fullname)
    TextView tvFullname;
    @ViewById(R.id.user_birthday)
    TextView tvBirthday;
    @ViewById(R.id.user_address)
    TextView tvAddress;
    @ViewById(R.id.user_avatar)
    ImageView ivAvatar;
    @ViewById(R.id.book_read_list)
    ListView bookReadList;

    @AfterViews
    public void init() {
        getReadingHistory();
    }

    @OptionsItem(R.id.edit_user)
    void transferToEditUser() {
        Intent intent = new Intent(UserInformation.this, EditUserInformation_.class);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    private void setViewContent(UserModel usermodel) {
        tvFullname.setText(StringUtility.replaceNull(usermodel.getFullname()));
        tvAddress.setText(StringUtility.replaceNull(usermodel.getAddress()));
        tvBirthday.setText(StringUtility.replaceNull(usermodel.getDateOfBirth()));
        if (LogInActivity.userLogin.getAvatar() != null) {
            Glide.with(this.getApplicationContext()).load(ApiLink.HOST + LogInActivity.userLogin.getAvatar()).into(ivAvatar);
        } else {
            Glide.with(this.getApplicationContext()).load(R.drawable.ic_launcher).into(ivAvatar);
        }
    }

    public void showAlertDialogWithGivenContent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ConstString.OOPS);
        builder.setMessage(ConstString.NULL_USER_INFOR);
        builder.setCancelable(false);
        builder.setPositiveButton(ConstString.YES, (dialogInterface, i) -> {
            Intent intent = new Intent(UserInformation.this, EditUserInformation.class);
            startActivity(intent);
        });
        builder.setNegativeButton(ConstString.NO, (dialogInterface, i) -> setViewContent(LogInActivity.userLogin));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    void getReadingHistory() {
        apiService.getReadingHistory(CreateJsonObject.username()).enqueue(new Callback<List<BookModel>>() {
            @Override
            public void onResponse(Call<List<BookModel>> call, Response<List<BookModel>> response) {
                booksRead.clear();
                if (response.body() != null) {
                    if (response.body().size() > 0) {
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
                            booksRead.add(i, book);
                        }
                        BooksAdapter booksAdapter = new BooksAdapter(getApplicationContext(), booksRead);
                        bookReadList.setAdapter(booksAdapter);

                        if (LogInActivity.userLogin == null) {
                            showAlertDialogWithGivenContent();
                        } else {
                            setViewContent(LogInActivity.userLogin);
                        }
                        Toasty.success(getApplicationContext(), ConstString.GET_READING_HISTORY_SUCCESSFULL, Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.info(getApplicationContext(), ConstString.GET_READING_HISTORY_FAILURE, Toast.LENGTH_SHORT, true).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

}
