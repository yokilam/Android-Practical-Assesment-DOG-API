package nyc.c4q.final_android_practical_assessment.model;

import java.util.List;

/**
 * Created by yokilam on 2/25/18.
 */

public class DogImages {
    String status;
    List<String> message;

    public DogImages(String status, List <String> message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public List <String> getMessage() {
        return message;
    }
}
