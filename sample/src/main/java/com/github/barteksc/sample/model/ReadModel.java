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
public class ReadModel {
    @SerializedName("read_book_id")
    @Expose
    public String readBookId;
    @SerializedName("read_started_time")
    @Expose
    public String readStartedTime;
    @SerializedName("read_ended_time")
    @Expose
    public String readEndedTime;
    @SerializedName("read_is_deleted")
    @Expose
    public String readIsDeleted;
}

