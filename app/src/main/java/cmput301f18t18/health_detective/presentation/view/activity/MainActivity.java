package cmput301f18t18.health_detective.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmput301f18t18.health_detective.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);


        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                EditText userid = (EditText) findViewById(R.id.userIdLogin);
                String user = userid.getText().toString();
                Toast.makeText(this,user,Toast.LENGTH_SHORT).show();
                break;
            case R.id.signUpButton:
                Toast.makeText(this,"signup",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
