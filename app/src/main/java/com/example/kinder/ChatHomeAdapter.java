package com.example.kinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kinder.Model.ChatHome;

import java.util.List;

public class ChatHomeAdapter extends RecyclerView.Adapter<ChatHomeAdapter.ViewHolder>{
    Context context;
    List<ChatHome> chatsHome;

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    public ChatHomeAdapter(Context context, List<ChatHome> chatsHome) {
        this.context = context;
        this.chatsHome = chatsHome;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textViewUsername,textViewChatMessage,textViewReceiver;
        ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            textViewUsername=itemView.findViewById(R.id.text_view_user_name);
            textViewChatMessage=itemView.findViewById(R.id.text_view_message);
            imageView=itemView.findViewById(R.id.image_view_chat_home);
            textViewReceiver=itemView.findViewById(R.id.text_view_receiver);

        }
    }

    @NonNull
    @Override
    public ChatHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.chat_home_card, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatHome chat=chatsHome.get(position);
        String sender=chat.getUserName();
        String message=chat.getMessage();
        String receiver=chat.getReceiver();
        String imageUrl=chat.getImageUrl();

        holder.textViewChatMessage.setText(message);
        holder.textViewUsername.setText(sender);
        holder.textViewReceiver.setText(receiver);

        Glide
                .with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageView);

        // Set item views based on your views and data model
    }

    @Override
    public int getItemCount() {
        return chatsHome.size();
    }
}
