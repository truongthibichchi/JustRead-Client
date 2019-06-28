package com.github.barteksc.sample.api;

import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.CommentModel;
import com.github.barteksc.sample.model.NewsModel;
import com.github.barteksc.sample.model.ReadModel;
import com.github.barteksc.sample.model.SharedBookModel;
import com.github.barteksc.sample.model.UserModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @POST(ApiLink.GET_ALL_USERS)
    Call<List<UserModel>> getAllUsers();

    @POST(ApiLink.GET_USER_INFO)
    Call<UserModel> getUserInfo(@Body JsonObject userInfo);

    @Multipart
    @POST(ApiLink.UPDATE_USER_INFO)
    Call<String> updateUserInfo(@Part("username") RequestBody username,
                                @Part("fullname") RequestBody fullname,
                                @Part("date_of_birth") RequestBody date_of_birth,
                                @Part("address") RequestBody address,
                                @Part("is_admin") RequestBody is_admin,
                                @Part MultipartBody.Part avatar);

    @POST(ApiLink.CHANGE_PASSWORD)
    Call<String> changePassword(@Body JsonObject userInfo);

    @POST(ApiLink.GET_BOOKS_BY_CATEGORY)
    Call<List<BookModel>> getBooksByCategory(@Body JsonObject categoryObjectJson);

    @POST(ApiLink.GET_READING_HISTORY)
    Call<List<ReadModel>> getReadingHistory(@Body JsonObject username);

    @POST(ApiLink.GET_ALL_BOOKS)
    Call<List<BookModel>> getAllBooks();

    @POST(ApiLink.GET_MENU_BOOKS)
    Call<List<BookModel>> getMenuBook();
    
    @POST(ApiLink.GET_RECOMMEND_BOOKs)
    Call<List<BookModel>> getRecommendBooks(@Body JsonObject username);

    @POST(ApiLink.GET_TOP_BOOKS)
    Call<List<BookModel>> getTopBooks();

    @POST(ApiLink.ADD_BOOK_TO_READING_HISTORY)
    Call<String> addReadingHistory(@Body JsonObject username);

    @POST(ApiLink.UPDATE_READ_DONE)
    Call<String> updateReadDone(@Body JsonObject readObjectJson);

    @POST(ApiLink.DELETE_READ_BOOK)
    Call<String> deleteReadBook(@Body JsonObject readObjectJson);

    @POST(ApiLink.GET_NEWS)
    Call<List<SharedBookModel>> getNews();

    @POST(ApiLink.GET_NEWS_BY_USERNAME)
    Call<List<SharedBookModel>> getNewsByUserName(@Body JsonObject username);

    @Multipart
    @POST(ApiLink.ADD_NEWS)
    Call<String> addNews(@Part("username") RequestBody news_username,
                         @Part("book_type") RequestBody book_type,
                         @Part("book_title") RequestBody book_title,
                         @Part("book_author") RequestBody book_author,
                         @Part("book_public_date") RequestBody book_public_date,
                         @Part("book_page") RequestBody book_page,
                         @Part("book_description") RequestBody book_description,
                         @Part("content") RequestBody news_content,
                         @Part MultipartBody.Part book_image,
                         @Part MultipartBody.Part book_file);

    @POST(ApiLink.REMOVE_NEWS)
    Call<String> removeNews(@Body JsonObject newsObjectJson);

    @Multipart
    @POST(ApiLink.UPDATE_NEWS)
    Call<String> updateNews(@Part("username") RequestBody news_username,
                         @Part("book_type") RequestBody book_type,
                         @Part("book_title") RequestBody book_title,
                         @Part("book_author") RequestBody book_author,
                         @Part("book_public_date") RequestBody book_public_date,
                         @Part("book_page") RequestBody book_page,
                         @Part("book_description") RequestBody book_description,
                         @Part("content") RequestBody news_content,
                         @Part MultipartBody.Part book_image);

    @POST(ApiLink.ADD_COMMENT)
    Call<String> addComment(@Body JsonObject comment);

    @POST(ApiLink.GET_COMMENT)
    Call<List<CommentModel>> getComment(@Body JsonObject comment);

    @POST(ApiLink.ADD_RATING)
    Call<String> addRating(@Body JsonObject comment);


}
