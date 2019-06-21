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
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("book_id")
    @Expose
    public String bookId;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("createdTime")
    @Expose
    public String created_time;
    @SerializedName("is_deleted")
    @Expose
    public String isDeleted;
}