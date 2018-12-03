package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.os.CpuUsageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.interactors.GetUser;
import cmput301f18t18.health_detective.domain.interactors.impl.GetUserImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.User;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements GetUser.Callback, View.OnClickListener, SignUpPresenter.View {
    private TextView userText, phoneText, emailText,cancelBtn;
    private CheckBox careCheck, patientCheck;
    private SignUpPresenter signUpPresenter;
    private Boolean activityType;
    private String user;
    private int type;
    private Button signUp;
    private int CP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        type = (int) intent.getSerializableExtra("type");

        if (type == 2) {
            user = (String) intent.getSerializableExtra("id");
            new GetUserImpl(this, user).execute();
        }




        signUp = findViewById(R.id.signUpBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        userText = findViewById(R.id.userEdit);
        phoneText = findViewById(R.id.phoneNumEdit);
        emailText = findViewById(R.id.emailEdit);
        careCheck = findViewById(R.id.CPcheckBox);
        patientCheck = findViewById(R.id.PcheckBox);
        //patientCheck.setChecked(true);
        if (type == 0) {
            careCheck.setOnClickListener(this);
            patientCheck.setOnClickListener(this);
        }

        ImageView image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.logo_transparent_background);
        if (type != 2) {
            signUpPresenter = new SignUpPresenter(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (activityType) {
            finish();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.signup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.CPcheckBox:
                patientCheck.setChecked(false);
                break;
            case R.id.PcheckBox:
                careCheck.setChecked(false);
                break;
            case R.id.signUpBtn:
                // if sign up completed set type to false if patient, true if CP
                // call presenter method createNewUser
                if (type == 2){
                    finish();
                }
                else {
                    Boolean type1 = false;
                    if (careCheck.isChecked()) {
                        type1 = true;
                    }
                    String user = userText.getText().toString();
                    String phone = phoneText.getText().toString();
                    String email = emailText.getText().toString();
                    if (activityType) {
                        signUpPresenter.editUserInfo(email, phone);
                    } else {
                        signUpPresenter.createNewUser(user, email, phone, type1);
                    }
                }
                break;
            case R.id.cancelBtn:
                finish();
        }
    }

    @Override
    public void onIsSignup() {
        activityType = false;
        init();
    }

    @Override
    public void onIsEditCareProvider(CareProvider careProvider) {
        user = careProvider.getUserId();
        new GetUserImpl(this, user).execute();
        activityType = true;
        careCheck.setChecked(true);
        editInit(careProvider);
    }

    @Override
    public void onIsEditPatient(Patient patient) {
        user = patient.getUserId();
        new GetUserImpl(this, user).execute();
        activityType = true;
        patientCheck.setChecked(true);
        editInit(patient);
    }

    @Override
    public void onCreatePatient() {
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        Toast.makeText(this, "Accounted created, logging in", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }

    @Override
    public void onCreateCareProvider() {
        Intent intent = new Intent(this, CareProPatientListActivity.class);
        startActivity(intent);
        Toast.makeText(this, "User Profile Edited", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidId() {
        Toast.makeText(this, "Invalid Id", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmail() {
        Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidPhoneNumber() {
        Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditUserSuccess() {
        Intent intent;
        if (CP == 0) {
            Toast.makeText(this, "User Profile Edited", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, PatientProblemsActivity.class);
        }
        else{
            Toast.makeText(this, "testing intent", Toast.LENGTH_SHORT).show();
            intent = new Intent(this,CareProPatientListActivity.class);
        }
        startActivity(intent);

    }

    private void editInit(User user) {

        if (type == 2){
            signUp.setText("back");
        }
        if (type == 1){
            signUp.setText("save");
        }
        userText.setText(user.getUserId());
        userText.setFocusable(false);
        phoneText.setText(user.getPhoneNumber());
        emailText.setText(user.getEmailAddress());
        careCheck.setEnabled(false);
        patientCheck.setEnabled(false);

        if (type == 2){
            phoneText.setFocusable(false);
            emailText.setFocusable(false);
        }


        init();
    }

    private void  init() {

        signUp.setOnClickListener(this);
        if (type != 2) {
            cancelBtn.setOnClickListener(this);
        }
        else {
//            signUp.setVisibility(View.INVISIBLE);
            cancelBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onGetUserPatientSuccess(Patient patient) {
        CP = 0;
        Log.d("abcdefg",patient.getUserId());
        if (type == 2) {
            editInit(patient);
        }

    }

    @Override
    public void onGetUserProviderSuccess(CareProvider careProvider) {
        CP = 1;
        Log.d("abcdefg",careProvider.getUserId());
        if (type == 2) {
            editInit(careProvider);
        }
    }

    @Override
    public void onGetUserInvalidUserId() {

    }

    @Override
    public void onGetUserDoesNotExist() {

    }
}
