package nyc.c4q.final_android_practical_assessment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by yokilam on 2/25/18.
 */

public class DogViewHolder extends RecyclerView.ViewHolder {

    private ImageView dogImageView;

    public DogViewHolder(View itemView) {
        super(itemView);
        dogImageView= itemView.findViewById(R.id.dog_imageview);
    }

    public void bind(final String s) {
        Picasso.with(itemView.getContext()).load(s).fit().into(dogImageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(itemView.getContext(), PhotoActivity.class);
                intent.putExtra("url", s);
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
