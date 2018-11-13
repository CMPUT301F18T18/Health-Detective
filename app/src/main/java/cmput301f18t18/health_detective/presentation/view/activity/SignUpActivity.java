package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView userText, phoneText, emailText;
    private CheckBox careCheck, patientCheck;
    static SignUpPresenter signUpPresenter = new SignUpPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userText = findViewById(R.id.userEdit);
        phoneText = findViewById(R.id.phoneNumEdit);
        emailText = findViewById(R.id.emailEdit);


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
                Intent intent = new Intent(this,PatientProblemsActivity.class);
                if (careCheck.isChecked()){
                    type = true;
                }
                String user = userText.getText().toString();
                String phone = phoneText.getText().toString();
                String email = emailText.getText().toString();
                signUpPresenter.createNewUser(user,email,phone,type);
                changeActivity(intent);
        }

    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
