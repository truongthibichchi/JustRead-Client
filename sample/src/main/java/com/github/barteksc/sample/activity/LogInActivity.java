package com.github.barteksc.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.jsonObject.CreateJsonObject;
import com.github.barteksc.sample.model.BookModel;
import com.github.barteksc.sample.model.UserModel;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_log_in)
public class LogInActivity extends AppCompatActivity {
    public String username;
    public String password;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public boolean saveLogin;

    @ViewById(R.id.et_login_username)
    EditText et_username;

    @ViewById(R.id.et_login_password)
    EditText et_password;

    @ViewById(R.id.cb_login_remember_me)
    CheckBox cb_save_login;

    @ViewById(R.id.tv_login_register)
    TextView tv_register;

    @ViewById(R.id.btn_login_login)
    AppCompatButton btn_login;

    public static APIService apiService;
    public static String usernameLogin;


    @AfterViews
    public void init( ) {

        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        et_username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_account_circle_black_24,0,0,0);
        et_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_black_24,0,0,0);

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        saveLogin = sharedPreferences.getBoolean("savelogin", true);
        if(saveLogin==true){
            et_username.setText(sharedPreferences.getString("username", null));
            et_password.setText(sharedPreferences.getString("password", null));
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Click(R.id.btn_login_login)
    public void buttonLoginAction() {
        if (isNull()) {
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_LOGIN, Toast.LENGTH_SHORT, true).show();
        } else {

            if(isNetworkAvailable()){
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                logInAPIExecute(username, password);
            }
            else{
                Toasty.info(getApplicationContext(), ConstString.NETWORK_ERROR, Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Click(R.id.tv_login_register)
    public void tvRegisterAction(){
        Intent intent = new Intent(LogInActivity.this, RegisterActivity_.class);
        startActivity(intent);
        cleanInputText();
    }

    public void logInAPIExecute(String username, String password) {
        apiService.logIn(CreateJsonObject.usernameAndPassword(username,password)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    editor.putBoolean("savelogin", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    Toasty.info(getApplicationContext(), ConstString.SUCCESS_LOGIN, Toast.LENGTH_SHORT, true).show();
                    showMainActivity();
                    cleanInputText();

                }else {
                    Toasty.info(getApplicationContext(), ConstString.FAILURE_LOGIN, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    void showMainActivity(){
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
    }




    boolean isNull() {
        return et_username.getText().length() == 0 || et_password.getText().length() == 0;
    }

    private void cleanInputText(){
        et_username.setText("");
        et_password.setText("");
    }
}
