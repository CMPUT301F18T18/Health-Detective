package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.data.repository.ElasticSearchController;
import cmput301f18t18.health_detective.domain.executor.ThreadExecutor;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.LoginPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signUp;
    private EditText userIdField;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loginPresenter = new LoginPresenter(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                ElasticSearchController.getInstance()
        );

        Button loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpText);

        userIdField = findViewById(R.id.userIdLogin);

        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                String userId = userIdField.getText().toString().trim();
                loginPresenter.tryLogin(this, userId);
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
