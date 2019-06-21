package com.github.barteksc.sample.entity;

import org.androidannotations.annotations.AfterViews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    private String linkImage;
    private String bookTitle;
}
