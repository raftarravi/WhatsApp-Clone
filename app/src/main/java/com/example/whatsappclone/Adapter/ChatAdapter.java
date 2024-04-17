package com.example.whatsappclone.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> message;
    Context context;

    String recId;


    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> message, Context context) {
        this.message = message;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> message, Context context, String recId) {
        this.message = message;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHilder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return  new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(message.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else{
            return RECEIVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = message.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("are you sure you want to delete this ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() +recId;
                                database.getReference().child("Chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        if(holder.getClass() == SenderViewHilder.class){

            ((SenderViewHilder)holder).senderMessage.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimeStamp());
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            String strDate = dateFormat.format(date);
            ((SenderViewHilder)holder).senderTime.setText(strDate);


        }else {
            ((ReceiverViewHolder)holder).receiverMessage.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimeStamp());
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            String strDate = dateFormat.format(date);
            ((ReceiverViewHolder)holder).receiverTime.setText(strDate);

        }

    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMessage;
        TextView receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMessage =itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);

        }
    }

    public class SenderViewHilder extends RecyclerView.ViewHolder{

        TextView senderMessage;
        TextView senderTime;

        public SenderViewHilder(@NonNull View itemView) {
            super(itemView);

            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);

        }
    }
}
