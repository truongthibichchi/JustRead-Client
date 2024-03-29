package com.github.barteksc.sample.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.barteksc.sample.R;
import com.github.barteksc.sample.constant.ApiLink;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
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
        viewHolder.ivavatar = convertView.findViewById(R.id.img_comment_item_avatar);
    }

    private void setViewHolderItemContent(ViewHolder viewHolder, CommentModel comment) {
        viewHolder.tvusername.setText(comment.getCommentUsername());
        viewHolder.tvcontent.setText(comment.getCommentContent());
        Glide.with(context)
                .load(Uri.parse(ApiLink.HOST + comment.getCommentAvatar()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.ivavatar);
    }

}
