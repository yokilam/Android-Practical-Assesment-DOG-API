package nyc.c4q.final_android_practical_assessment;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yokilam on 2/25/18.
 */

public class DogsAdapter extends RecyclerView.Adapter<DogViewHolder> {

    private List<String> listOfDogs;

    public DogsAdapter(List <String> listOfDogs) {
        this.listOfDogs = listOfDogs;
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item_view, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        holder.bind(getListOfDogs().get(position));
    }

    @Override
    public int getItemCount() {
        return getListOfDogs().size();
    }

    public List <String> getListOfDogs() {
        return listOfDogs;
    }


}
