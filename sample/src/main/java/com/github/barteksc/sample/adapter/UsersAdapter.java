package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.activity.BookDetailActivity;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends BaseAdapter {
    private List<UserModel> users;
    private Context context;

    private Context mContext;
    private List<UserModel> mUserModel;
    private UserModel userModel;
    public APIService apiService = ApiUtils.getAPIService();
    public String userLogin;

    ImageView imgAvatar;
    TextView tvUserFullname;
    TextView tvNewsContent;
    ImageView imgBookImage;
    TextView tvBookTitle;
    TextView tvBookRating;
    TextView tvBookComment;
    LinearLayout llItem;
    ImageView imgDelete;
    Button btnRating;
    Button btnComment;
    Button btnSave;

    public UsersAdapter(Context mContext, List<UserModel> mUserModel, String userLogin) {
        this.mContext = mContext;
        this.mUserModel = mUserModel;
        this.userLogin = userLogin;
    }

    @Override
    public int getCount() {
        return mUserModel.size();
    }

    @Override
    public Object getItem(int position) {

        return mUserModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View recycled, ViewGroup parent) {
        View itemView;
        userModel = mUserModel.get(position);
        if (recycled == null) {
            itemView = View.inflate(mContext, R.layout.item_user, null);
        } else {
            itemView = recycled;
        }
        tvUserFullname = itemView.findViewById(R.id.tv_item_user_fullname);
        imgAvatar = itemView.findViewById(R.id.img_item_user_avatar);

        tvUserFullname.setText(mUserModel.get(position).getUserFullname());
        Glide.with(mContext)
                .load(Uri.parse(ApiLink.HOST + mUserModel.get(position).getUserAvatar()))
                .into(imgAvatar);

        return itemView;
    }

    private void setEventHandler(SharedBookModel sharedBook) {
        btnRating.setOnClickListener(View -> {

        });

        imgDelete.setOnClickListener(View ->{
            apiService.removeNews(CreateJsonObject.removeNews(sharedBook.getNewsId(),
                    sharedBook.getNewsBookId())).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(mContext, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            });
        });

        imgBookImage.setOnClickListener(View -> {
            Intent intent = new Intent(mContext, BookDetailActivity.class);
            intent.putExtra("book_id", sharedBook.getBookId());
            intent.putExtra("book_author", sharedBook.getBookAuthor());
            intent.putExtra("book_category", sharedBook.getBookCategory());
            intent.putExtra("book_description", sharedBook.getBookDescription());
            intent.putExtra("book_download", sharedBook.getBookDownload());
            intent.putExtra("book_file", sharedBook.getBookFile());
            intent.putExtra("book_image", ApiLink.HOST + sharedBook.getBookImage());
            intent.putExtra("book_page", sharedBook.getBookPage());
            intent.putExtra("book_public_date", sharedBook.getBookPublicDate());
            intent.putExtra("book_rated_time", sharedBook.getBookRatedTime());
            intent.putExtra("book_read_time", sharedBook.getBookReadTime());
            intent.putExtra("book_rating", sharedBook.getBookRating());
            intent.putExtra("book_title", sharedBook.getBookTitle());
            intent.putExtra("book_type", sharedBook.getBookType());
            intent.putExtra("book_is_deleted", sharedBook.getBookIsDeleted());
            intent.putExtra("book_created_time", sharedBook.getBookCreatedTime());
            mContext.startActivity(intent);
        });
        btnComment.setOnClickListener(View -> {
            Intent intent = new Intent(mContext, BookDetailActivity.class);
            intent.putExtra("book_id", sharedBook.getBookId());
            intent.putExtra("book_author", sharedBook.getBookAuthor());
            intent.putExtra("book_category", sharedBook.getBookCategory());
            intent.putExtra("book_description", sharedBook.getBookDescription());
            intent.putExtra("book_download", sharedBook.getBookDownload());
            intent.putExtra("book_file", sharedBook.getBookFile());
            intent.putExtra("book_image", ApiLink.HOST + sharedBook.getBookImage());
            intent.putExtra("book_page", sharedBook.getBookPage());
            intent.putExtra("book_public_date", sharedBook.getBookPublicDate());
            intent.putExtra("book_rated_time", sharedBook.getBookRatedTime());
            intent.putExtra("book_read_time", sharedBook.getBookReadTime());
            intent.putExtra("book_rating", sharedBook.getBookRating());
            intent.putExtra("book_title", sharedBook.getBookTitle());
            intent.putExtra("book_type", sharedBook.getBookType());
            intent.putExtra("book_is_deleted", sharedBook.getBookIsDeleted());
            intent.putExtra("book_created_time", sharedBook.getBookCreatedTime());
            mContext.startActivity(intent);
        });

        btnSave.setOnClickListener(View -> {
            apiService.addReadingHistory(CreateJsonObject.readingHistory(sharedBook.getBookId(),
                    sharedBook.getNewsUsername())).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(mContext, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(mContext, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void getCommentByBookId(String bookId) {
        apiService.getComment(CreateJsonObject.bookId(bookId)).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if (response.isSuccessful()) {
                    int commentNumber = response.body().size();
                    tvBookComment.setText(commentNumber + " bình luận");
                } else {
                    Toasty.error(mContext, ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(mContext, t);
            }
        });

    }
}
