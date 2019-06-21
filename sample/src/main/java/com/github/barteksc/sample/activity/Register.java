package com.github.barteksc.sample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.constant.ConstString;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.github.barteksc.sample.utilities.PasswordUtility;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;

@EActivity(R.layout.activity_register)
public class Register extends AppCompatActivity {

    @ViewById(R.id.input_username)
    EditText et_username;
    @ViewById(R.id.input_password)
    EditText et_password;
    @ViewById(R.id.intput_fullname)
    EditText et_fullname;
    @ViewById(R.id.btn_register)
    AppCompatButton btnRegister;

    @AfterViews
    public void init() {
        et_username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_account_circle_black_24, 0, 0, 0);
        et_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_black_24, 0, 0, 0);
        et_fullname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_face_black_24, 0, 0, 0);
    }

    @Click(R.id.btn_register)
    public void buttonRegisterAction() {
        if (isNull()) {
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_REGISTER, Toast.LENGTH_SHORT, true).show();
        } else {
            registerAPIExecute();
        }
    }

    public void registerAPIExecute() {
        apiService.register(prepareBodyRegister()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toasty.info(getApplicationContext(), response.body(), Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(Register.this, LogInActivity_.class);
                    startActivity(intent);
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

    JsonObject prepareBodyRegister() {
        JsonObject bodyRequest = new JsonObject();
        String passwordConvertedMd5 = PasswordUtility.convertMd5(et_password.getText().toString());
        bodyRequest.addProperty("username", et_username.getText().toString());
        bodyRequest.addProperty("password", passwordConvertedMd5);
        bodyRequest.addProperty("fullname", et_fullname.getText().toString());

        return bodyRequest;
    }

    boolean isNull() {
        return et_username.getText().length() == 0 || et_password.getText().length() == 0;
    }
}
