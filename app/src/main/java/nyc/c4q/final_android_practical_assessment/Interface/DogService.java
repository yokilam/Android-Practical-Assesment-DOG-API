package nyc.c4q.final_android_practical_assessment.Interface;

import nyc.c4q.final_android_practical_assessment.model.DogImages;
import nyc.c4q.final_android_practical_assessment.model.OneRandomImage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yokilam on 2/25/18.
 */

public interface DogService {

    @GET("api/breed/{breed-name}/images/random")
    Call<OneRandomImage> getOneRandomImage(@Path("breed-name") String breed);

    @GET("api/breed/{breed-name}/images")
    Call<DogImages> getListOfBreedImage(@Path("breed-name") String breed);
}
