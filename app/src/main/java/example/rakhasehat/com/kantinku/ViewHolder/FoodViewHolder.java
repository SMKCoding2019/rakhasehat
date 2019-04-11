package example.rakhasehat.com.kantinku.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.rakhasehat.com.kantinku.Interface.ItemClickListener;
import example.rakhasehat.com.kantinku.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView food_image;
    public TextView food_name;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        food_image = (ImageView) itemView.findViewById(R.id.food_image);
        food_name = (TextView) itemView.findViewById(R.id.txtFoodName);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
