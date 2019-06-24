package com.github.barteksc.sample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @SerializedName("user_username")
    @Expose
    public String userUsername;
    @SerializedName("user_password")
    @Expose
    public String userPassword;
    @SerializedName("user_fullname")
    @Expose
    public String userFullname;
    @SerializedName("user_date_of_birth")
    @Expose
    public String userDateOfBirth;
    @SerializedName("user_address")
    @Expose
    public String userAddress;
    @SerializedName("user_avatar")
    @Expose
    public String userAvatar;
    @SerializedName("user_is_admin")
    @Expose
    public String userIsAdmin;
    @SerializedName("user_created_date")
    @Expose
    public String userCreatedDate;
}
