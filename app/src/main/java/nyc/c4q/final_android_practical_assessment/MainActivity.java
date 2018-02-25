package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button submitButton;
    private SharedPreferences loginSharePreference;
    private static final String SHARE_PREF_KEY="loginSharedPrefenceres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = loginSharePreference.edit();
                if (username.getText().toString() == null) {
                    username.setError("Please enter a username!");
                } else if (password.getText().toString() == null) {
                    password.setError("Please enter a password");
                } else if (password.getText().toString().contains(username.getText().toString())) {
                    password.setError("The password cannot contain username");
                } else {
                    if (!password.getText().toString().contains(username.getText().toString())) {
                        editor.putString("user" + username.getText().toString(), username.getText().toString());
                        editor.putString("password" + username.getText().toString(), password.getText().toString());
                        editor.putBoolean("isLoggedIn" + username.getText().toString(), true);
                        editor.commit();
                        Intent intent= new Intent(MainActivity.this, BreedActivity.class);
                        intent.putExtra("username", username.getText().toString());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void setUpViews() {
        username= findViewById(R.id.username_edittext);
        password= findViewById(R.id.password_edittext);
        submitButton= findViewById(R.id.submit_button);

        loginSharePreference= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, MODE_PRIVATE);
    }
}
