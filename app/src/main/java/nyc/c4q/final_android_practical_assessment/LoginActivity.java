package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button submitButton;
    private SharedPreferences loginSharePreference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();
        String checkUser= loginSharePreference.getString("user", null);

        if(checkUser!= null){
            intentToBreedActivity();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString() == null) {
                    username.setError("Please enter a username!");
                } else if (password.getText().toString() == null) {
                    password.setError("Please enter a password");
                } else if (password.getText().toString().contains(username.getText().toString()) ||
                        username.getText().toString().contains(password.getText().toString())) {
                    password.setError("The password cannot contain username");
                } else {
                    if (!password.getText().toString().contains(username.getText().toString())) {
                        editor.putString("user", username.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putBoolean("isLoggedIn", true);
                        editor.commit();
                        intentToBreedActivity();
                    }
                }
            }
        });
    }

    private void intentToBreedActivity() {
        Intent intent= new Intent(LoginActivity.this, BreedActivity.class);
        intent.putExtra("loginPrefKey", Constants.SHARED_PREF_KEY);
        startActivity(intent);
    }

    private void setUpViews() {
        username= findViewById(R.id.username_edittext);
        password= findViewById(R.id.password_edittext);
        submitButton= findViewById(R.id.submit_button);

        loginSharePreference= getApplicationContext().getSharedPreferences(Constants.SHARED_PREF_KEY, MODE_PRIVATE);
        editor = loginSharePreference.edit();
    }
}
