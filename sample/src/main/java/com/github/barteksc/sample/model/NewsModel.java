package com.github.barteksc.sample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NewsModel {
    @SerializedName("news_id")
    @Expose
    public String newsId;
    @SerializedName("news_username")
    @Expose
    public String newsUsername;
    @SerializedName("news_book_id")
    @Expose
    public String newsBookId;
    @SerializedName("news_content")
    @Expose
    public String newsContent;
    @SerializedName("news_created_time")
    @Expose
    public String newsCreated_time;
    @SerializedName("news_is_deleted")
    @Expose
    public String newsIsDeleted;
}