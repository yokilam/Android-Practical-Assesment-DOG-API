package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.final_android_practical_assessment.Interface.DogService;
import nyc.c4q.final_android_practical_assessment.model.OneRandomImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BreedActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView welcomeUserMessage;
    private ImageView terrierImageView, poodleImageView, spanielImageView, retrieverImageView;
    private SharedPreferences loginSharePref;
    private DogService dogService;
    private CardView terrierCardView, poodleCardView, spanielCardView, retrieverCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);

        setUpViews();

        checkUserFromSharedPrefs();

        getRetrofitForOneRandomBreedImage("terrier");
        getRetrofitForOneRandomBreedImage("poodle");
        getRetrofitForOneRandomBreedImage("spaniel");
        getRetrofitForOneRandomBreedImage("retriever");

        terrierCardView.setOnClickListener(this);
        poodleCardView.setOnClickListener(this);
        retrieverCardView.setOnClickListener(this);
        spanielCardView.setOnClickListener(this);

    }

    private void setUpViews() {
        welcomeUserMessage = findViewById(R.id.welcome_user_textView);
        terrierImageView = findViewById(R.id.terrier_imageView);
        poodleImageView = findViewById(R.id.poodle_imageView);
        retrieverImageView = findViewById(R.id.retriever_imageView);
        spanielImageView = findViewById(R.id.spaniel_imageView);

        terrierCardView= findViewById(R.id.terrier_cardview);
        poodleCardView= findViewById(R.id.poodle_cardview);
        retrieverCardView= findViewById(R.id.retriever_cardview);
        spanielCardView= findViewById(R.id.spaniel_cardview);

    }

    private void checkUserFromSharedPrefs() {
        Intent intent = getIntent();
        loginSharePref = getApplicationContext().getSharedPreferences(intent.getStringExtra("loginPrefKey"), MODE_PRIVATE);

        String checkUser = loginSharePref.getString("user", null);

        StringBuilder welcomeMessage = new StringBuilder();
        welcomeMessage.append("What kind of dog would you like to see, ").append(checkUser).append("?");

        if (checkUser != null) {
            welcomeUserMessage.setText(welcomeMessage.toString());
        } else {
            backToLoginIntent();
        }
    }

    private void backToLoginIntent() {
        Intent backToLoginIntent = new Intent(BreedActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
    }

    private void getRetrofitForOneRandomBreedImage(final String s) {
        dogService = RetrofitClient.getRetrofit("https://dog.ceo/")
                .create(DogService.class);

        dogService.getOneRandomImage(s).enqueue(new Callback <OneRandomImage>() {
            @Override
            public void onResponse(Call <OneRandomImage> call, Response <OneRandomImage> response) {
                Log.d("onResponse: GETTING DOG", "" + response.body());
                String image = response.body().getMessage();
                loadIntoImageView(image, getImageView(s));
            }

            @Override
            public void onFailure(Call <OneRandomImage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private ImageView getImageView(String s) {
        switch (s) {
            case "terrier":
                return terrierImageView;
            case "poodle":
                return poodleImageView;
            case "spaniel":
                return spanielImageView;
            case "retriever":
                return retrieverImageView;
        }
        return terrierImageView;
    }

    private void loadIntoImageView(String image, ImageView breedImageView) {
        Picasso.with(BreedActivity.this).load(image).fit().into(breedImageView);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.terrier_cardview:
                intentToDogsActivity("terrier");
                break;
            case R.id.poodle_cardview:
                intentToDogsActivity("poodle");
                break;
            case R.id.spaniel_cardview:
                intentToDogsActivity("spaniel");
                break;
            case R.id.retriever_cardview:
                intentToDogsActivity("retriever");
                break;
        }
    }

    private void intentToDogsActivity(String breedname) {
        Intent intent= new Intent(BreedActivity.this, DogsActivity.class);
        intent.putExtra("breed", breedname);
        startActivity(intent);
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
}
