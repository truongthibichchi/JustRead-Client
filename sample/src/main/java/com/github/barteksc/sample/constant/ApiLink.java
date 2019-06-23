package com.github.barteksc.sample.constant;

import lombok.Builder;

@Builder
public class ApiLink {
    public final static String HOST = "http://192.168.1.103:4600";

    //login + register
    public final static String LOG_IN = "/login";
    public final static String REGISTER = "/register";

    //user info
    public final static String GET_USER_INFO = "/get-user-info";
    public final static String UPDATE_USER_INFO = "/update-user-info";
    public final static String CHANGE_PASSWORD = "/change-password";

    //reading history
    public final static String GET_READING_HISTORY = "/get-reading-history";
    public final static String ADD_BOOK_TO_READING_HISTORY = "/add-book-to-reading-history";

    //category+books
    public final static String GET_CATEGORY = "/get-category";
    public final static String GET_BOOKS_BY_CATEGORY = "/get-books-by-category";
    public final static String GET_MENU_BOOKS = "/get-menu-book";
    public final static String GET_TOP_BOOKS = "/get-top-books";
    public final static String GET_RECOMMEND_BOOKs = "/get-recommend-books";

    //news
    public final static String GET_NEWS = "/get-news";
    public final static String ADD_NEWS = "/add-news";
    public final static String UPDATE_NEWS = "/update-news";
    public final static String REMOVE_NEWS = "/remove-news";

    //upload-file
    public final static String TRANSFER_FILE = HOST + "/transfer-file";

    //comment
    public final static String ADD_COMMENT = "/add-comment";
    public final static String GET_COMMENT = "/get-comment";

    public final static String ADD_RATING = "/add-rating";


}
