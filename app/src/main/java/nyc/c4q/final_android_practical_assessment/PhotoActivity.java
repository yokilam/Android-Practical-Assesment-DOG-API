package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private SharedPreferences loginSharePref;
    private ImageView bigDogImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        bigDogImageView= findViewById(R.id.big_dog_image_view);

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra("url");
        Log.d("onCreate: ", imageUrl);
        loginSharePref = getApplicationContext()
                .getSharedPreferences(intent.getStringExtra("loginPrefKey"), MODE_PRIVATE);
        Glide.with(this)
                .load(imageUrl)
                .into(bigDogImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                clearSharedPreference();
                backToLoginIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearSharedPreference() {
        SharedPreferences.Editor editor= loginSharePref.edit();
        editor.clear().commit();
    }

    private void backToLoginIntent() {
        Intent backToLoginIntent = new Intent(PhotoActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
    }

}
