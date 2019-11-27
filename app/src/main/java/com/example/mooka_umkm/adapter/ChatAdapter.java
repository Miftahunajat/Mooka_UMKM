package com.example.mooka_umkm.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mooka_umkm.R;
import com.example.mooka_umkm.network.model.MessageChat;

import java.util.List;

/**
 * Created by aflah on 10/08/19
 * Email  : aflahtaqiusondha@gmail.com
 * Github : https://github.com/aflahtaqiu
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<MessageChat> chats;
    private int idSender;

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    public ChatAdapter(List<MessageChat> chats, int idSender) {
        this.chats = chats;
        this.idSender = idSender;
    }

    @Override
    @NonNull
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;

        if (viewType == MSG_TYPE_RIGHT) {
            layout = R.layout.item_messages_send;
        } else {
            layout = R.layout.item_messages_recieve;
        }

        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        MessageChat item = chats.get(position);

        holder.tvName.setText(item.getNama_pemilik());
        holder.tvMessage.setText(item.getIsi());
        Log.e("lele", item.getId() + " ");

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvMessage;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text_message_name);
            tvMessage = itemView.findViewById(R.id.text_message_body);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (chats.get(position).getId() == idSender)
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }
}