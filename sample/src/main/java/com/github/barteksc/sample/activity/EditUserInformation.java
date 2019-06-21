package com.github.barteksc.sample.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.utilities.HandleAPIResponse;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;

@EActivity(R.layout.activity_edit_user_information)
public class EditUserInformation extends AppCompatActivity {

    @ViewById(R.id.input_fullname)
    EditText inputFullname;
    @ViewById(R.id.input_address)
    EditText inputAddress;
    @ViewById(R.id.input_birthday)
    EditText inputBirthday;
    @ViewById(R.id.btn_edit)
    Button btnEdit;

    int year, month, day;

    @AfterViews
    protected void init() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        inputFullname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_face_black_24, 0, 0, 0);
        inputAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_home_black_24, 0, 0, 0);
        inputBirthday.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_cake_black_24, 0, 0, 0);
    }

    @Click(R.id.input_birthday)
    public void datePicker() {
        DatePickerDialog.OnDateSetListener listener = (view, year, monthOfYear, dayOfMonth) -> inputBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        DatePickerDialog dpDialog = new DatePickerDialog(EditUserInformation.this, listener, year, month, day);
        dpDialog.show();
    }

   @Click(R.id.btn_edit)
    public void editUser() {
        apiService.updateUserInfo(prepareBodyUpdateUser()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                HandleAPIResponse.handleFailureResponse(getApplicationContext(), t);
            }
        });
    }

    private JsonObject prepareBodyUpdateUser() {
        JsonObject bodyRequest = new JsonObject();
        bodyRequest.addProperty("fullname",inputFullname.getText().toString());
        bodyRequest.addProperty("date_of_birth",inputBirthday.getText().toString());
        bodyRequest.addProperty("address",inputAddress.getText().toString());
        return bodyRequest;
    }

}
