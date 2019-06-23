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
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("fullname")
    @Expose
    public String fullname;
    @SerializedName("date_of_birth")
    @Expose
    public String dateOfBirth;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("is_admin")
    @Expose
    public String isAdmin;
    @SerializedName("created_time")
    @Expose
    public String createdTime;
}
