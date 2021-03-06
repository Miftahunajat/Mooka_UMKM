package com.example.mooka_umkm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mooka_umkm.R;
import com.example.mooka_umkm.network.Repository;
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
    private Context context;
    private boolean isAdmin;

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    public ChatAdapter(List<MessageChat> chats, int idSender, Context context, boolean isAdmin) {
        this.chats = chats;
        this.idSender = idSender;
        this.context = context;
        this.isAdmin = isAdmin;
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
        final MessageChat item = chats.get(position);

        holder.tvName.setText(item.getNama_pemilik() + " ("+ item.getNama_toko() + ")");
        holder.tvMessage.setText(item.getIsi());

        if (isAdmin) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Penambahan Poin")
                            .setMessage("Anda yakin menambahkan 1 poin untuk IKM ini?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Repository.INSTANCE.addPoint((int) item.getId());
                                    Toast.makeText(context, "Anda telah menambahkan 1 poin untuk toko " + item.getNama_toko(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setCancelable(true)
                            .create();

                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();
                }
            });
        }
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