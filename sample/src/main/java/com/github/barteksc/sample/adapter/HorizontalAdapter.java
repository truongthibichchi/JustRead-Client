package com.github.barteksc.sample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.activity.BookDetail_;
import com.github.barteksc.sample.activity.MainActivity;
import com.github.barteksc.sample.model.BookModel;

import java.util.List;

public class HorizontalAdapter extends ArrayAdapter<BookModel> {

    private Context context;
    private List<BookModel> books;

    public HorizontalAdapter(Context context, List<BookModel> books) {
        super(context, 0, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public BookModel getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_list_row_item, null, true);

            holder.tvname = convertView.findViewById(R.id.name);
            holder.iv = convertView.findViewById(R.id.imgView);
            holder.tvrating = convertView.findViewById(R.id.rating);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvname.setText(books.get(position).getBookTitle());
        holder.tvrating.setText(books.get(position).getBookRating() + "%");
        if (books.get(position).getBookImage() != null) {
            Glide.with(context).load(books.get(position).getBookImage()).into(holder.iv);
        } else {
            holder.iv.setImageResource(R.drawable.ic_launcher);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvname, tvrating;
        ImageView iv;
    }

}