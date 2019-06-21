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
@Builder
@Getter
@Setter
public final class BookModel {

    @SerializedName("book_id")
    @Expose
    private String bookId;
    @SerializedName("book_author")
    @Expose
    private String bookAuthor;
    @SerializedName("book_category")
    @Expose
    private String bookCategory;
    @SerializedName("book_description")
    @Expose
    private String bookDescription;
    @SerializedName("book_download")
    @Expose
    private String bookDownload;
    @SerializedName("book_file")
    @Expose
    private String bookFile;
    @SerializedName("book_image")
    @Expose
    private String bookImage;
    @SerializedName("book_page")
    @Expose
    private String bookPage;
    @SerializedName("book_public_date")
    @Expose
    private String bookPublicDate;
    @SerializedName("book_rated_time")
    @Expose
    private String bookRatedTime;
    @SerializedName("book_read_times")
    @Expose
    private String bookReadTimes;
    @SerializedName("book_rating")
    @Expose
    private String bookRating;
    @SerializedName("book_title")
    @Expose
    private String bookTitle;
    @SerializedName("book_type")
    @Expose
    private String bookType;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;

}
