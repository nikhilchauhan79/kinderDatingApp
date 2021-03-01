package com.example.kinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinder.Model.Chat;

import java.util.List;

public class ChatTextAdapter extends RecyclerView.Adapter<ChatTextAdapter.ViewHolder>{
    private List<Chat> chatList;
    private Context context;

    public ChatTextAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageViewUser;
        public TextView textViewMessage;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textViewMessage =itemView.findViewById(R.id.text_view_message);
            imageViewUser=itemView.findViewById(R.id.profile_image);
        }
    }

    @NonNull
    @Override
    public ChatTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View chatCard = inflater.inflate(R.layout.chat_item_left, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(chatCard);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat=chatList.get(position);
        chat.getSender();
        Log.d("message", "onBindViewHolder: "+chat.getSender()+"\n"+chat.getReceiver()+"\n"+chat.getMessage());

        // Set item views based on your views and data model
        TextView textView = holder.textViewMessage;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
