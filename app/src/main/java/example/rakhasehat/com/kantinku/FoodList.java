package example.rakhasehat.com.kantinku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import example.rakhasehat.com.kantinku.Interface.ItemClickListener;
import example.rakhasehat.com.kantinku.Model.Food;
import example.rakhasehat.com.kantinku.ViewHolder.FoodViewHolder;
import example.rakhasehat.com.kantinku.ViewHolder.MenuViewHolder;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference foodlist;

    String categoryId="";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Init Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        foodlist = firebaseDatabase.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Intentnya
        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
            if(!categoryId.isEmpty() && categoryId != null){
                loadlistFood(categoryId);
            }
        }
    }

    private void loadlistFood(String categoryId) {
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foodlist, Food.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options){
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.food_item, viewGroup, false);
                return new FoodViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage())
                        .into(holder.food_image);
                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new activity
                        Intent i = new Intent(FoodList.this, FoodDetail.class);
                        i.putExtra("FoodId", adapter.getRef(position).getKey()); //Send FoodId to new Activity
                        startActivity(i);
                    }
                });
            }
        };
        //Set Adapter
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
