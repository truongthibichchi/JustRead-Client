package com.github.barteksc.sample.utilities;

import android.content.Intent;

import com.github.barteksc.sample.constant.ApiLink;
import com.github.barteksc.sample.model.BookModel;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class GeneralUtility {
    public static String ratingFloatToIntPercent(float x){
        return "" + ((int) (x * 20));
    }

    public static String removeAccent(String s){
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String formatString(String s){
        return removeAccent(s.toLowerCase().trim());
    }

    public static Intent putIntent(Intent intent, BookModel book) {
        intent.putExtra("book_id", book.getBookId());
        intent.putExtra("book_author", book.getBookAuthor());
        intent.putExtra("book_category", book.getBookCategory());
        intent.putExtra("book_description", book.getBookDescription());
        intent.putExtra("book_download", book.getBookDownload());
        intent.putExtra("book_file", book.getBookFile());
        String bookImage = book.getBookImage();
        if (bookImage.charAt(0)=='/'){
            bookImage = ApiLink.HOST+book.getBookImage();
        }
        intent.putExtra("book_image", bookImage);
        intent.putExtra("book_page", book.getBookPage());
        intent.putExtra("book_public_date", book.getBookPublicDate());
        intent.putExtra("book_rated_time", book.getBookRatedTime());
        intent.putExtra("book_read_time", book.getBookReadTime());
        intent.putExtra("book_rating", book.getBookRating());
        intent.putExtra("book_title", book.getBookTitle());
        intent.putExtra("book_type", book.getBookType());
        intent.putExtra("book_is_deleted", book.getBookIsDeleted());
        intent.putExtra("book_created_time", book.getBookCreatedTime());
        return intent;
    }
}
