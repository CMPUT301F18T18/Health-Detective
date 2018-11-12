package cmput301f18t18.health_detective.presentation.view.activity.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmput301f18t18.health_detective.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        TextView signup = findViewById(R.id.signUpText);

        loginButton.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                Intent problemsIntent = new Intent(this,PatientProblemsActivity.class);
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
