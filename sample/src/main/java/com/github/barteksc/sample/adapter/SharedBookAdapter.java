package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.util.AndroidException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.activity.AddSharedBookActivity;
import com.github.barteksc.sample.activity.BookDetailActivity;
import com.github.barteksc.sample.activity.PDFViewActivity_;
import com.github.barteksc.sample.activity.UserInformationActivity;
import com.github.barteksc.sample.activity.UserInformationChangePasswordActivity_;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.model.ReadModel;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedBookAdapter extends BaseAdapter {
    private Context mContext;
    private List<SharedBookModel> mSharedBookModelList;
    private SharedBookModel sharedBook;
    public APIService apiService = ApiUtils.getAPIService();
    public String userLogin;
    public String isAdmin;

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

    public SharedBookAdapter(Context mContext, List<SharedBookModel> mSharedBookModelList, String userLogin, String isAdmin) {
        this.mContext = mContext;
        this.mSharedBookModelList = mSharedBookModelList;
        this.userLogin = userLogin;
        this.isAdmin = isAdmin;
    }

    @Override
    public int getCount() {
        return mSharedBookModelList.size();
    }

    @Override
    public Object getItem(int position) {

        return mSharedBookModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View recycled, ViewGroup parent) {
        View itemView;
        sharedBook = mSharedBookModelList.get(position);
        if (recycled == null) {
            itemView = View.inflate(mContext, R.layout.item_shared_book, null);
        } else {
            itemView = recycled;
        }
        tvUserFullname = itemView.findViewById(R.id.tv_item_shared_book_fullname);
        tvNewsContent = itemView.findViewById(R.id.tv_item_shared_book_content);
        imgAvatar = itemView.findViewById(R.id.img_item_shared_book_avatar);
        imgBookImage = itemView.findViewById(R.id.img_item_shared_book_book_image);
        tvBookTitle = itemView.findViewById(R.id.tv_item_shared_book_title);
        tvBookRating = itemView.findViewById(R.id.tv_item_shared_book_rating);
        tvBookComment = itemView.findViewById(R.id.tv_item_shared_book_comment);
        llItem = itemView.findViewById(R.id.ll_item_shared_book);
        btnRating = itemView.findViewById(R.id.btn_item_shared_book_rating);
        btnComment = itemView.findViewById(R.id.btn_item_shared_book_comment);
        btnSave = itemView.findViewById(R.id.btn_item_shared_book_save);
        imgDelete = itemView.findViewById(R.id.img_item_shared_book_delete);

        if(userLogin.equals(mSharedBookModelList.get(position).getNewsUsername())|| isAdmin.equals("1")){
            imgDelete.setVisibility(View.VISIBLE);
        }
        else{
            imgDelete.setVisibility(View.GONE);
        }

        getCommentByBookId(sharedBook.getBookId());
        tvBookTitle.setText(mSharedBookModelList.get(position).getBookTitle());
        tvNewsContent.setText(mSharedBookModelList.get(position).getNewsContent());
        tvBookRating.setText(mSharedBookModelList.get(position).getBookRating() + "%");
        tvUserFullname.setText(mSharedBookModelList.get(position).getNewsUserFullname());
        Glide.with(mContext)
                .load(Uri.parse(ApiLink.HOST + mSharedBookModelList.get(position).getBookImage()))
                .into(imgBookImage);

        Glide.with(mContext)
                .load(Uri.parse(ApiLink.HOST + mSharedBookModelList.get(position).getNewsUserAvatar()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgAvatar);

//        getUserInfoByUsername(sharedBook.getNewsUsername());

        setEventHandler(sharedBook, itemView);
        itemView.setTag(mSharedBookModelList.get(position).getBookId());


        return itemView;
    }

    private void setEventHandler(SharedBookModel sharedBook, View itemView) {
        btnRating.setOnClickListener(View -> {

        });

        imgDelete.setOnClickListener(View ->{
            apiService.removeNews(CreateJsonObject.removeNews(sharedBook.getNewsId(),
                    sharedBook.getNewsBookId())).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    itemView.setVisibility(android.view.View.GONE);
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
