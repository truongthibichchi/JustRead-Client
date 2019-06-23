package com.github.barteksc.sample.api;

import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.model.NewsModel;
import com.github.barteksc.sample.model.UserModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface APIService {

    @POST(ApiLink.LOG_IN)
    Call<String> logIn(@Body JsonObject loginObjectJson);

    @POST(ApiLink.REGISTER)
    Call<String> register(@Body JsonObject registerInfo);

    @POST(ApiLink.GET_USER_INFO)
    Call<UserModel> getUserInfo(@Body JsonObject userInfo);

    @POST(ApiLink.UPDATE_USER_INFO)
    Call<String> updateUserInfo(@Body JsonObject userInfo);

    @POST(ApiLink.CHANGE_PASSWORD)
    Call<String> changePassword(@Body JsonObject userInfo);

    @POST(ApiLink.GET_BOOKS_BY_CATEGORY)
    Call<List<BookModel>> getBooksByCategory(@Body JsonObject categoryObjectJson);

    @POST(ApiLink.GET_READING_HISTORY)
    Call<List<BookModel>> getReadingHistory(@Body JsonObject username);

    @POST(ApiLink.GET_MENU_BOOKS)
    Call<List<BookModel>> getMenuBook();
    
    @POST(ApiLink.GET_RECOMMEND_BOOKs)
    Call<List<BookModel>> getRecommendBooks(@Body JsonObject username);

    @POST(ApiLink.GET_TOP_BOOKS)
    Call<List<BookModel>> getTopBooks();

    @POST(ApiLink.ADD_BOOK_TO_READING_HISTORY)
    Call<String> addReadingHistory(@Body JsonObject username);

    @Multipart
    @POST(ApiLink.ADD_NEWS)
    Call<String> addNews(@Url String url,
                         @Part MultipartBody.Part file,
                         @Part MultipartBody.Part jsonobject);

    @POST(ApiLink.ADD_COMMENT)
    Call<String> addComment(@Body JsonObject comment);

    @POST(ApiLink.GET_COMMENT)
    Call<List<CommentModel>> getComment(@Body JsonObject comment);

    @POST(ApiLink.ADD_RATING)
    Call<String> addRating(@Body JsonObject comment);


}
