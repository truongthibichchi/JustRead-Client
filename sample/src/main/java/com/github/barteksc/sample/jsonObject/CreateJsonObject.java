package com.github.barteksc.sample.jsonObject;

import com.github.barteksc.sample.activity.LogInActivity;
import com.github.barteksc.sample.utilities.PasswordUtility;
import com.google.gson.JsonObject;

import static com.github.barteksc.sample.utilities.GeneralUtility.ratingFloatToIntPercent;

public class CreateJsonObject {

    public static JsonObject usernameAndPassword(String username, String plaintext) {
        JsonObject bodyRequest = new JsonObject();
        String passwordConvertedMd5 = PasswordUtility.convertMd5(plaintext);
        bodyRequest.addProperty("username",username);
        bodyRequest.addProperty("password",passwordConvertedMd5);
        return bodyRequest;
    }

    public static JsonObject categoryValue(String category) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("category", category);
        return bodyRequest;
    }

    public static JsonObject username(String username) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("username", username);
        return bodyRequest;
    }

    public static JsonObject comment(String bookId, String username, String content) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("book_id", bookId);
        bodyRequest.addProperty("username", username);
        bodyRequest.addProperty("content", content);
        return bodyRequest;
    }

    public static JsonObject bookId(String bookId) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("book_id", bookId);
        return bodyRequest;
    }

    public static JsonObject readingHistory(String bookId, String username) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("book_id", bookId);
        bodyRequest.addProperty("username", username);
        return bodyRequest;
    }

    public static JsonObject rating(String bookId, float rating) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("book_id", bookId);
        bodyRequest.addProperty("rating", ratingFloatToIntPercent(rating));
        return bodyRequest;
    }

    public static JsonObject removeNews(String newsId, String bookId) {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("news_id", newsId);
        bodyRequest.addProperty("book_id", bookId);
        return bodyRequest;
    }
}
