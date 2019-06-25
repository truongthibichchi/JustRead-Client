package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.github.barteksc.sample.activity.LogInActivity_;
import com.github.barteksc.sample.activity.MainActivity;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.ReadModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReadModel> mReadModelList;
    private String username;
    private String bookImage;
    private ReadModel read;
    public APIService apiService = ApiUtils.getAPIService();

    public ReadAdapter(Context mContext, List<ReadModel> mReadModelList, String username) {
        this.mContext = mContext;
        this.mReadModelList = mReadModelList;
        this.username = username;
    }

    @Override

    public int getCount() {
        return mReadModelList.size();
    }

    @Override
    public Object getItem(int position) {

        return mReadModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View recycled, ViewGroup parent) {
        View itemView;
        read = mReadModelList.get(position);
        if (recycled == null) {
            itemView = View.inflate(mContext, R.layout.item_user_reading_history, null);
        } else {
            itemView = recycled;
        }

        ImageView imgBookImage = itemView.findViewById(R.id.img_item_user_read_book_image);
        TextView tvBookTitle = itemView.findViewById(R.id.tv_item_user_read_book_title);
        TextView tvBookRating = itemView.findViewById(R.id.tv_item_user_read_book_rating);
        TextView tvReadStartTime = itemView.findViewById(R.id.tv_item_user_read_start_time);
        TextView tvReadEndTime = itemView.findViewById(R.id.tv_item_user_read_end_time);
        Button btnDone = itemView.findViewById(R.id.btn_item_user_read_done);
        Button btnDelete = itemView.findViewById(R.id.btn_item_user_read_delete);
        LinearLayout ll_item = itemView.findViewById(R.id.ll_item_user_read);

        bookImage = mReadModelList.get(position).getBookImage();
        if (bookImage.charAt(0)=='/'){
            bookImage = ApiLink.HOST+bookImage;
        }

        tvBookTitle.setText(mReadModelList.get(position).getBookTitle());
        tvBookRating.setText(mReadModelList.get(position).getBookRating()+"%");
        tvReadStartTime.setText(mReadModelList.get(position).getReadStartedTime());
        tvReadEndTime.setText(mReadModelList.get(position).getReadEndedTime());
        if(mReadModelList.get(position).getReadEndedTime()==null){
            btnDone.setVisibility(View.VISIBLE);
        }
        else {
            btnDone.setVisibility(View.GONE);
        }

        if(mReadModelList.get(position).getReadIsDeleted().equals("0")){
            btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            btnDelete.setVisibility(View.GONE);
        }

        Glide
                .with(mContext)
                .load(Uri.parse(bookImage))
                .into(imgBookImage);

        itemView.setTag(mReadModelList.get(position).getReadBookId());

        imgBookImage.setOnClickListener(view -> {

            Intent intent = new Intent(mContext, BookDetailActivity.class);
            intent.putExtra("book_id", read.getBookId());
            intent.putExtra("book_author", read.getBookAuthor());
            intent.putExtra("book_category", read.getBookCategory());
            intent.putExtra("book_description", read.getBookDescription());
            intent.putExtra("book_download", read.getBookDownload());
            intent.putExtra("book_file", read.getBookFile());
            intent.putExtra("book_image", bookImage);
            intent.putExtra("book_page", read.getBookPage());
            intent.putExtra("book_public_date", read.getBookPublicDate());
            intent.putExtra("book_rated_time", read.getBookRatedTime());
            intent.putExtra("book_read_time", read.getBookReadTime());
            intent.putExtra("book_rating", read.getBookRating());
            intent.putExtra("book_title", read.getBookTitle());
            intent.putExtra("book_type", read.getBookType());
            intent.putExtra("book_is_deleted", read.getBookIsDeleted());
            intent.putExtra("book_created_time", read.getBookCreatedTime());
            mContext.startActivity(intent);
        });

        btnDone.setOnClickListener(View ->{
            apiService.updateReadDone(CreateJsonObject.readingHistory(read.getBookId(), username)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Chúc mừng bạn đã đọc xong!", Toast.LENGTH_SHORT).show();
                        tvReadEndTime.setText(response.body());
                        btnDone.setVisibility(android.view.View.GONE);

                    } else {
                        Toasty.info(mContext, "Failed", Toast.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    HandleAPIResponse.handleFailureResponse(mContext, t);
                }
            });
        });

        btnDelete.setOnClickListener(View -> {
            apiService.deleteReadBook(CreateJsonObject.readingHistory(read.getBookId(), username)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, response.body(), Toast.LENGTH_SHORT).show();
                        btnDelete.setVisibility(android.view.View.GONE);
                        ll_item.setVisibility(android.view.View.GONE);

                    } else {
                        Toasty.info(mContext, "Failed", Toast.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    HandleAPIResponse.handleFailureResponse(mContext, t);
                }
            });
        });

        return itemView;
    }
}
