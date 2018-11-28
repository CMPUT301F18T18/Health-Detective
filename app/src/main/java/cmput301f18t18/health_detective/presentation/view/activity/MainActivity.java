package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.interactors.ContextChange;
import cmput301f18t18.health_detective.domain.interactors.impl.ContextOnInto;
import cmput301f18t18.health_detective.domain.model.CareProvider;
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.LoginPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                                                LoginPresenter.View {
    private TextView signUp;
    private EditText userIdField;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.loginPresenter = new LoginPresenter(this);

        Button loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpText);

        userIdField = findViewById(R.id.userIdLogin);

        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

        ImageView userLoginIcon = findViewById(R.id.user_login_icon);
        userLoginIcon.setImageResource(R.drawable.baseline_account_circle_black_48);
        ImageView logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo_transparent_background);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                String userId = userIdField.getText().toString().trim();
                loginPresenter.tryLogin(userId);
                break;
            case R.id.signUpText:
                Intent signUpIntent = new Intent(this,SignUpActivity.class);
                changeActivity(signUpIntent);
                break;

        }
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    public void onLoginPatient(Patient patient) {
        // Not sure what you do with this information but here it is
        Intent intent = new Intent(this, PatientProblemsActivity.class);
        intent.putExtra("PATIENT", patient);
        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();
        this.startActivity(intent);
    }

    @Override
    public void onLoginCareProvider(CareProvider careProvider) {

    }

    @Override
    public void onInvalidUserId() {
        Toast.makeText(this, "Invalid UserId", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDoesNotExist() {
        Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
    }
}
