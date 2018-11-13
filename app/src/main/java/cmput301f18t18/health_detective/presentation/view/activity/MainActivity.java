package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.LoginPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signUp;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (loginPresenter == null){
            loginPresenter = new LoginPresenter();
        }

        Button loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpText);

        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                Intent problemsIntent = new Intent(this,PatientProblemsActivity.class);
                String userId = signUp.getText().toString();
                loginPresenter.tryLogin(userId);

                changeActivity(problemsIntent);
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
}
