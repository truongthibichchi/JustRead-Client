package com.github.barteksc.sample.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.PasswordUtility;
import com.github.barteksc.sample.utilities.ToastyConfigUtility;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EActivity(R.layout.activity_user_infomation_change_password)
public class UserInformationChangePasswordActivity extends AppCompatActivity {
    public APIService apiService;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private Bundle mBundle;
    private String username;
    private String password;
    @ViewById(R.id.et_user_change_pass_present_pass)
    EditText etPresentPass;
    @ViewById(R.id.et_user_change_pass_new_pass)
    EditText etNewPass;
    @ViewById(R.id.et_user_change_pass_new_pass_again)
    EditText etNewPassAgain;
    @ViewById(R.id.btn_user_change_pass_save)
    Button btnSave;

    @AfterViews
    protected void init() {
        apiService = ApiUtils.getAPIService();
        ToastyConfigUtility.createInstance();
        etNewPassAgain.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_stars_black_18, 0, 0, 0);
        etNewPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_stars_black_18, 0, 0, 0);
        etPresentPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_black_24, 0, 0, 0);
        mBundle = getIntent().getExtras();
        username = (String) mBundle.get("username");
        password = (String) mBundle.get("password");
    }


    boolean isNull() {
        return etPresentPass.getText().length() == 0 || etNewPass.getText().length() == 0 || etNewPassAgain.getText().length() == 0;
    }

    @Click(R.id.btn_user_change_pass_save)
    void changePassword(){
        if(isNull()){
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_REGISTER, Toast.LENGTH_SHORT, true).show();
        }
        else if(wrongPassword()){
            Toasty.info(getApplicationContext(), ConstString.WRONG_PASSWORD, Toast.LENGTH_SHORT, true).show();

        }
        else if(passwordNotMatch()){
            Toasty.info(getApplicationContext(), ConstString.PASSWORD_NOT_MATCH, Toast.LENGTH_SHORT, true).show();

        }
        else {
            changePasswordAPIExecute();
        }
    }

    private boolean wrongPassword() {
        String inputPasswrod = PasswordUtility.convertMd5(etPresentPass.getText().toString());
        return !(inputPasswrod.equals(password));
    }

    private boolean passwordNotMatch() {
        return !(etNewPassAgain.getText().toString().equals(etNewPass.getText().toString()));
    }


    public void changePasswordAPIExecute() {
        apiService.changePassword(prepareBodyUpdateUser()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toasty.info(getApplicationContext(), response.body(), Toast.LENGTH_SHORT, true).show();
                    sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("password", etNewPass.getText().toString());
                } else {
                    Toasty.info(getApplicationContext(), ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private JsonObject prepareBodyUpdateUser() {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("username", username);
        String newPassword = PasswordUtility.convertMd5(etNewPass.getText().toString());
        bodyRequest.addProperty("new_password", newPassword);
        return bodyRequest;
    }



}
