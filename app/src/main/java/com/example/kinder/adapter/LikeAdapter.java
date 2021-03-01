package com.example.kinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kinder.Model.Profile;
import com.example.kinder.R;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>{
    private List<Profile> profileList;
    private Context context;
    private OnItemClickListener mListener;



    public LikeAdapter(List<Profile> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }


    @NonNull
    @Override
    public LikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View chatCard = inflater.inflate(R.layout.like_card, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder =new LikeAdapter.ViewHolder(chatCard);

        return viewHolder;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile=profileList.get(position);
        holder.nameAndAge.setText(profile.getName());
        holder.description.setText(profile.getDescription());
        String imageUrl=profile.getImageUrl();



        Glide
                .with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageViewProfile);


        // Set item views based on your views and data model

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageViewProfile;
        public TextView nameAndAge,description;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageViewProfile=itemView.findViewById(R.id.image_view_like);
            nameAndAge=itemView.findViewById(R.id.nameAndAge);
            description=itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });


        }
    }

}

