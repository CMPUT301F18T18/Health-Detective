package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    //TextView usertext;
    //TextView phonetext;
    //TextView emailtext;
    //SignUpPresenter signUpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //usertext = findViewById(R.id.userEdit);
        //phonetext = findViewById(R.id.phoneNumEdit);
        //emailtext = findViewById(R.id.emailEdit);


        Button signUp = findViewById(R.id.signUpBtn);
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
        Intent intent = new Intent(this,MainActivity.class);

        //String user = (String) usertext.getText();
        //String phone = (String) phonetext.getText();
        //String email = (String) emailtext.getText();
        //signUpPresenter = new SignUpPresenter(user, phone, email, Boolean.TRUE);
        changeActivity(intent);
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
