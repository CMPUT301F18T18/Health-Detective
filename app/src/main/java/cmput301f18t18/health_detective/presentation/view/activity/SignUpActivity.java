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
        type = (int) intent.getSerializableExtra("type");  // get type, representing which use the activity has
        // type
        // 0 - creating new user
        // 1 - editing own user info
        // 2 - viewing another user, no editing

        // get userid currently viewing and recieve type User from that
        if (type == 2) {
            user = (String) intent.getSerializableExtra("id");
            new GetUserImpl(this, user).execute();
        }

        // set view Id's
        signUp = findViewById(R.id.signUpBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        userText = findViewById(R.id.userEdit);
        phoneText = findViewById(R.id.phoneNumEdit);
        emailText = findViewById(R.id.emailEdit);
        careCheck = findViewById(R.id.CPcheckBox);
        patientCheck = findViewById(R.id.PcheckBox);

        // if editing allow user type to be selected
        if (type == 0) {
            careCheck.setOnClickListener(this);
            patientCheck.setOnClickListener(this);
        }
        ImageView image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.logo_transparent_background);

        // if not viewing someones edit create signUpPresenter
        if (type != 2) {
            signUpPresenter = new SignUpPresenter(this);
        }
    }

    // back returns to last activity or mainactivity if signing up
    @Override
    public void onBackPressed() {
        if(type != 2) {
            if (activityType) {
                finish();
                return;
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
            }
        }
    }

    // onClick method to handle clicks
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.CPcheckBox:
                patientCheck.setChecked(false); // set other checkbox to false
                break;
            case R.id.PcheckBox:
                careCheck.setChecked(false);    // set other checkbox to false
                break;
            case R.id.signUpBtn:
                // if viewing other profile return to past activity
                if (type == 2){
                    finish();
                }
                // if sign up completed set type to false if patient, true if CP
                // call presenter method createNewUser
                else {
                    Boolean type1 = false;
                    if (careCheck.isChecked()) {
                        type1 = true;
                    }
                    // get user entered txt for update/sign up
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
                // if cancel return to last activity
            case R.id.cancelBtn:
                finish();
                break;
        }
    }
    // on signup set activity type and initialize
    @Override
    public void onIsSignup() {
        activityType = false;
        init();
    }
    // called on editing a care provider, calls editInit with Care Provider
    @Override
    public void onIsEditCareProvider(CareProvider careProvider) {
        user = careProvider.getUserId();
        new GetUserImpl(this, user).execute();
        activityType = true;
        careCheck.setChecked(true);
        editInit(careProvider);
    }

    // called on editing a patient, calls editInit with patient
    @Override
    public void onIsEditPatient(Patient patient) {
        user = patient.getUserId();
        new GetUserImpl(this, user).execute();
        activityType = true;
        patientCheck.setChecked(true);
        editInit(patient);
    }

    // called after successful creation of Care provider, changes activity
    @Override
    public void onCreatePatient() {
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        Toast.makeText(this, "Account created, logging in", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }

    // called after successful creation of patient, changes activity
    @Override
    public void onCreateCareProvider() {
        Intent intent = new Intent(this, CareProPatientListActivity.class);
        Toast.makeText(this, "Account created, logging in", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    // constraint check on userId
    @Override
    public void onInvalidId() {
        Toast.makeText(this, "Invalid Id", Toast.LENGTH_SHORT).show();
    }

    // constraint check on email
    @Override
    public void onInvalidEmail() {
        Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
    }

    // constraint check on phoneNumber
    @Override
    public void onInvalidPhoneNumber() {
        Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
    }

    // called after successful edit, changes intent
    @Override
    public void onEditUserSuccess() {
        Intent intent;
        if (CP == 0) {
            intent = new Intent(this, PatientProblemsActivity.class);
        }
        else{
            intent = new Intent(this,CareProPatientListActivity.class);
        }
        startActivity(intent);

    }

    // initialize the edit part of activity
    private void editInit(User user) {

        if (type == 2){
            signUp.setText("back");
        }
        if (type == 1){
            signUp.setText("save");
        }
        // set textviews to user information
        userText.setText(user.getUserId());
        userText.setFocusable(false);
        phoneText.setText(user.getPhoneNumber());
        emailText.setText(user.getEmailAddress());
        careCheck.setEnabled(false);
        patientCheck.setEnabled(false);
        // if viewing others profile set editable to false
        if (type == 2){
            phoneText.setFocusable(false);
            emailText.setFocusable(false);
        }
        init();
    }

    // initialize, set on clicks and visibility according to type
    private void  init() {
        signUp.setOnClickListener(this);
        if (type != 2) {
            cancelBtn.setOnClickListener(this);
        }
        else {
            cancelBtn.setVisibility(View.INVISIBLE);
        }
    }

    // method called after getting patient from passed user id
    @Override
    public void onGetUserPatientSuccess(Patient patient) {
        CP = 0;
        patientCheck.setChecked(true);
        if (type == 2) {
            editInit(patient);
        }
    }

    // method called after getting care provider from passed user id
    @Override
    public void onGetUserProviderSuccess(CareProvider careProvider) {
        CP = 1;
        careCheck.setChecked(true);
        if (type == 2) {
            editInit(careProvider);
        }
    }

    // method for invalid userId
    @Override
    public void onGetUserInvalidUserId() {

    }

    // method for non existential user
    @Override
    public void onGetUserDoesNotExist() {

    }
}
