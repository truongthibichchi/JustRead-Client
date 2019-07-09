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
@Builder
@Setter
public class CommentModel {
    @SerializedName("comment_id")
    @Expose
    public String commentId;
    @SerializedName("comment_avatar")
    @Expose
    public String commentAvatar;
    @SerializedName("comment_username")
    @Expose
    public String commentUsername;
    @SerializedName("comment_content")
    @Expose
    public String commentContent;
    @SerializedName("comment_created_time")
    @Expose
    public String commentCreatedTime;
    @SerializedName("comment_is_deleted")
    @Expose
    public String commentIsDeleted;
}
