package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.model.CommentModel;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentModel> {

    private List<CommentModel> comments;
    private Context context;

    public CommentAdapter(Context context, List<CommentModel> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    public class ViewHolder {
        TextView tvusername, tvcontent;
        ImageView ivavatar;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        CommentModel comment = comments.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
            viewHolder = new ViewHolder();
            this.setViewHolder(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        this.setViewHolderItemContent(viewHolder, comment);

        return convertView;
    }

    private void setViewHolder(ViewHolder viewHolder, View convertView) {
        viewHolder.tvusername = convertView.findViewById(R.id.tv_comment_item_fullname);
        viewHolder.tvcontent = convertView.findViewById(R.id.tv_content);
    }

    private void setViewHolderItemContent(ViewHolder viewHolder, CommentModel comment) {
        viewHolder.tvusername.setText(comment.getUsername());
        viewHolder.tvcontent.setText(comment.getContent());
    }

}
