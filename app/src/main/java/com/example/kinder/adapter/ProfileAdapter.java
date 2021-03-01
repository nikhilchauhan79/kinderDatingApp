package com.example.kinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kinder.Model.ProfileDetail;
import com.example.kinder.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    private List<ProfileDetail> userPhotosList;
    private Context context;


    public ProfileAdapter(List<ProfileDetail> userPhotosList, Context context) {
        this.userPhotosList = userPhotosList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View photosCard = inflater.inflate(R.layout.card_view_profile, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder =new ProfileAdapter.ViewHolder(photosCard);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileDetail profile =userPhotosList.get(position);

        String name=profile.getName();
        String imageUrl=profile.getImageUrl();

        Glide
                .with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageViewProfileDetail);


    }



    @Override
    public int getItemCount() {
        return userPhotosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageViewProfileDetail;
        public Button buttonSettings,buttonEdit;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageViewProfileDetail=itemView.findViewById(R.id.image_view_profile_card);


        }
    }



}