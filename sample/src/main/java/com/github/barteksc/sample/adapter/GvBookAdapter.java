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
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GvBookAdapter extends BaseAdapter {
    private Context context;
    private List<BookModel> books;
    String bookImage;

    public GvBookAdapter(Context context, List<BookModel> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;
        if (convertView == null) {
            itemView = View.inflate(context, R.layout.item_book, null);
        } else {
            itemView = convertView;
        }

        TextView tvBookTile = convertView.findViewById(R.id.tv_item_book_book_title);
        ImageView imgBookImage = convertView.findViewById(R.id.img_item_book_book_image);
        TextView tvBookRating = convertView.findViewById(R.id.tv_item_book_book_rating);
        bookImage = books.get(position).getBookImage();

        if (bookImage.charAt(0)=='/'){
            bookImage = ApiLink.HOST+bookImage;
        }

        tvBookTile.setText(books.get(position).getBookTitle());
        tvBookRating.setText(books.get(position).getBookRating()+"%");
        Glide
                .with(context)
                .load(Uri.parse(bookImage))
                .into(imgBookImage);

        itemView.setTag(books.get(position).getBookId());

//        imgBookImage.setOnClickListener(view -> {
//
//            Intent intent = new Intent(context, BookDetailActivity.class);
//            intent.putExtra("book_id", books.get(position).getBookId());
//            intent.putExtra("book_author", books.get(position).getBookAuthor());
//            intent.putExtra("book_category", books.get(position).getBookCategory());
//            intent.putExtra("book_description", books.get(position).getBookDescription());
//            intent.putExtra("book_download", books.get(position).getBookDownload());
//            intent.putExtra("book_file", books.get(position).getBookFile());
//            intent.putExtra("book_image", bookImage);
//            intent.putExtra("book_page", books.get(position).getBookPage());
//            intent.putExtra("book_public_date", books.get(position).getBookPublicDate());
//            intent.putExtra("book_rated_time", books.get(position).getBookRatedTime());
//            intent.putExtra("book_read_time", books.get(position).getBookReadTime());
//            intent.putExtra("book_rating", books.get(position).getBookRating());
//            intent.putExtra("book_title", books.get(position).getBookTitle());
//            intent.putExtra("book_type", books.get(position).getBookType());
//            intent.putExtra("book_is_deleted", books.get(position).getBookIsDeleted());
//            intent.putExtra("book_created_time", books.get(position).getBookCreatedTime());
//            context.startActivity(intent);
//        });

        return itemView;
    }
}
