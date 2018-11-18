package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpPresenter.View {
    private TextView userText, phoneText, emailText;
    private CheckBox careCheck, patientCheck;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        userText = findViewById(R.id.userEdit);
        phoneText = findViewById(R.id.phoneNumEdit);
        emailText = findViewById(R.id.emailEdit);
        ImageView image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.logo_transparent_background);

        signUpPresenter = new SignUpPresenter(
                this,
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance()
        );

        Button signUp = findViewById(R.id.signUpBtn);
        careCheck = findViewById(R.id.CPcheckBox);
        patientCheck = findViewById(R.id.PcheckBox);
        patientCheck.setChecked(true);
        careCheck.setOnClickListener(this);
        patientCheck.setOnClickListener(this);
        signUp.setOnClickListener(this);
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
                Boolean type = false;
                if (careCheck.isChecked()){
                    type = true;
                }
                String user = userText.getText().toString();
                String phone = phoneText.getText().toString();
                String email = emailText.getText().toString();
                signUpPresenter.createNewUser(user,email,phone);
        }
    }

    @Override
    public void onCreatePatient(Patient patient) {
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        intent.putExtra("PATIENT", patient);
        Toast.makeText(this, "Accounted created, logging in", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }

    @Override
    public void onCreateCareProvider(CareProvider careProvider) {

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
}
