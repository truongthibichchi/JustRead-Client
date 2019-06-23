package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.model.BookModel;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<BookModel> {

    private List<BookModel> books;
    private Context context;

    public BooksAdapter(Context context, List<BookModel> books) {
        super(context, 0, books);
        this.context = context;
        this.books = books;
    }

    public class ViewHolder {
        TextView tvBookTitle, tvBookAuthor, tvBookCategory;
        ImageView ivBookImage;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        BookModel book = books.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book_list_column, parent, false);
            viewHolder = new ViewHolder();
            this.setViewHolder(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        this.setViewHolderItemContent(viewHolder, book);

        return convertView;
    }

    private void setViewHolder(ViewHolder viewHolder, View convertView) {
        viewHolder.ivBookImage = convertView.findViewById(R.id.img_book_item_book_image);
        viewHolder.tvBookAuthor = convertView.findViewById(R.id.tv_book_item_book_author);
        viewHolder.tvBookCategory = convertView.findViewById(R.id.tv_book_item_book_category);
        viewHolder.tvBookTitle = convertView.findViewById(R.id.tv_book_item_book_title);
    }

    private void setViewHolderItemContent(ViewHolder viewHolder, BookModel book) {
        if(book.getBookImage() != null) {
            Glide.with(getContext()).load(book.getBookImage()).into(viewHolder.ivBookImage);
        }
        else {
            viewHolder.ivBookImage.setImageResource(R.drawable.ic_launcher);
        }

        viewHolder.tvBookTitle.setText(book.getBookTitle());
        viewHolder.tvBookCategory.setText(book.getBookCategory());
        viewHolder.tvBookAuthor.setText(book.getBookAuthor());
    }

}
