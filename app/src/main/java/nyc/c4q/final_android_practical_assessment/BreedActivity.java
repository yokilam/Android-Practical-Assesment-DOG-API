package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BreedActivity extends AppCompatActivity {

    private TextView welcomeUserMessage;
    private SharedPreferences loginSharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);

        welcomeUserMessage= findViewById(R.id.welcome_user_textView);

        Intent intent= getIntent();
        loginSharePref=getApplicationContext().getSharedPreferences(intent.getStringExtra("loginPrefKey"), MODE_PRIVATE);

        String checkUser= loginSharePref.getString("user", null);

        StringBuilder welcomeMessage= new StringBuilder();
        welcomeMessage.append("What kind of dog would you like to see, ").append(checkUser).append("?");

        if(checkUser!=null) {
            welcomeUserMessage.setText(welcomeMessage.toString());
        } else {
            Intent backToLoginIntent= new Intent(BreedActivity.this, LoginActivity.class);
            startActivity(backToLoginIntent);
        }
    }
}
