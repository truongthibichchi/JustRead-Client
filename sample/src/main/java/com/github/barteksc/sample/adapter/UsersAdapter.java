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
import com.github.barteksc.sample.activity.PersonalPageActivity;
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
    private Context mContext;
    private List<UserModel> mUserModel;
    private UserModel userModel;
    public APIService apiService = ApiUtils.getAPIService();
    public String userLogin;

    ImageView imgAvatar;
    TextView tvUserFullname;
    LinearLayout llItem;

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
        llItem = itemView.findViewById(R.id.ll_item_user);

        tvUserFullname.setText(mUserModel.get(position).getUserFullname());
        Glide.with(mContext)
                .load(Uri.parse(ApiLink.HOST + mUserModel.get(position).getUserAvatar()))
                .into(imgAvatar);

        llItem.setOnClickListener(view ->{
            Intent intent = new Intent(mContext, PersonalPageActivity.class);
            intent.putExtra("username",mUserModel.get(position).getUserUsername() );
            intent.putExtra("avatar", ApiLink.HOST+mUserModel.get(position).getUserAvatar());
            intent.putExtra("created_date", mUserModel.get(position).getUserCreatedDate());
            intent.putExtra("fullname", mUserModel.get(position).getUserFullname());
            intent.putExtra("is_admin", mUserModel.get(position).getUserIsAdmin());
            mContext.startActivity(intent);
        });

        return itemView;
    }


}
