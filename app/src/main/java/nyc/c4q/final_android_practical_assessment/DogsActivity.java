package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.final_android_practical_assessment.Interface.DogService;
import nyc.c4q.final_android_practical_assessment.model.DogImages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogsActivity extends AppCompatActivity{

    private TextView dogBreed;
    private DogService dogService;
    private List <String> listOfDogImages = new ArrayList <>();
    private RecyclerView dogRecyclerView;
    private ProgressBar progressBar;
    private SharedPreferences loginSharePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);

        dogBreed = findViewById(R.id.breed_name);
        dogRecyclerView = findViewById(R.id.dog_recyclerview);
        progressBar = findViewById(R.id.dog_list_progress_bar);

        Intent intent = getIntent();
        loginSharePref = getApplicationContext().getSharedPreferences(intent.getStringExtra("loginPrefKey"), MODE_PRIVATE);
        String breedName = intent.getStringExtra("breed");
        dogBreed.setText(breedName);


        getRetrofitCall(breedName);
        if (DogsActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            dogRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            dogRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

    }

    private void getRetrofitCall(String breedName) {
        progressBar.setVisibility(View.VISIBLE);
        dogService = RetrofitClient.getRetrofit("https://dog.ceo/")
                .create(DogService.class);

        dogService.getListOfBreedImage(breedName).enqueue(new Callback <DogImages>() {
            @Override
            public void onResponse(Call <DogImages> call, Response <DogImages> response) {
                listOfDogImages = response.body().getMessage();
                Log.d("onResponse: gettinglist", "" + response.body().getMessage());
                dogRecyclerView.setAdapter(new DogsAdapter(listOfDogImages));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call <DogImages> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        Intent backToLoginIntent = new Intent(DogsActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
    }

}
