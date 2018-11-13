package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmput301f18t18.health_detective.MainThreadImpl;
import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.executor.impl.ThreadExecutorImpl;
import cmput301f18t18.health_detective.domain.repository.mock.UserRepoMock;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView usertext;
    private TextView phonetext;
    private TextView emailtext;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usertext = findViewById(R.id.userEdit);
        phonetext = findViewById(R.id.phoneNumEdit);
        emailtext = findViewById(R.id.emailEdit);

        signUpPresenter = new SignUpPresenter(
                ThreadExecutorImpl.getInstance(),
                MainThreadImpl.getInstance(),
                new UserRepoMock()
        );


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

        String user = usertext.getText().toString();
        String phone = phonetext.getText().toString();
        String email = emailtext.getText().toString();
        signUpPresenter.createNewUser(user,email,phone);
        changeActivity(intent);
    }

    public void changeActivity(Intent intent){
        startActivity(intent);
    }
}
