package com.github.barteksc.sample.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;

@EActivity(R.layout.activity_user_infomation_change_password)
public class UserInformationChangePasswordActivity extends AppCompatActivity {

    @ViewById(R.id.et_user_change_pass_present_pass)
    EditText etPresentPass;
    @ViewById(R.id.et_user_change_pass_new_pass)
    EditText etNewPass;
    @ViewById(R.id.et_user_change_pass_new_pass_again)
    EditText etNewPassAgain;
    @ViewById(R.id.btn_user_change_pass_save)
    Button btnSave;

//    int year, month, day;

    @AfterViews
    protected void init() {
//        final Calendar c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DAY_OF_MONTH);
        etPresentPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_face_black_24, 0, 0, 0);
        etPresentPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_home_black_24, 0, 0, 0);
        etPresentPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_cake_black_24, 0, 0, 0);
    }

//    @Click(R.id.input_birthday)
//    public void datePicker() {
//        DatePickerDialog.OnDateSetListener listener = (view, year, monthOfYear, dayOfMonth) -> inputBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
//        DatePickerDialog dpDialog = new DatePickerDialog(UserInformationChangePasswordActivity.this, listener, year, month, day);
//        dpDialog.show();
//    }

    boolean isNull() {
        return etPresentPass.getText().length() == 0 || etNewPass.getText().length() == 0 || etNewPassAgain.getText().length() == 0;
    }

    @Click(R.id.btn_user_change_pass_save)
    void changePassword(){
        if(isNull()){
            Toasty.info(getApplicationContext(), ConstString.NULL_INPUT_REGISTER, Toast.LENGTH_SHORT, true).show();
        }
        else{
            return;
//            changePasswordAPIExecute();
        }
    }


    public void changePasswordAPIExecute() {
        apiService.changePassword(prepareBodyUpdateUser()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toasty.info(getApplicationContext(), response.body(), Toast.LENGTH_SHORT, true).show();
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
        bodyRequest.addProperty("username",LogInActivity.usernameLogin);
        String password = PasswordUtility.convertMd5(etPresentPass.getText().toString());
        String newPassword = PasswordUtility.convertMd5(etNewPass.getText().toString());
        bodyRequest.addProperty("password",password);
        bodyRequest.addProperty("new_password", newPassword);
        return bodyRequest;
    }



}
