package com.example.trabus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabus.R;
import com.example.trabus.models.ChatModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MSG_TYP_SENT=1;
    public static final int MSG_TYP_RECIEVE=2;
    private final List<ChatModel> mChat;
    private final String senderId;
    Context  context;

    public MessageAdapter(List<ChatModel> mChat,String senderId,Context context) {
        this.mChat = mChat;
        this.senderId=senderId;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if(viewType==MSG_TYP_SENT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatsender,parent,false);
            viewHolder = new SendMessageViewHolder(view);


        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatreciever, parent, false);
            viewHolder = new RecieveMessageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==MSG_TYP_SENT)
        {
            ((SendMessageViewHolder)holder).sendData(mChat.get(position));
        }
        else
        {
            ((RecieveMessageViewHolder)holder).setData((mChat.get(position)));;

        }

    }

    @Override
    public int getItemCount() {
        if(mChat!=null) {
            return mChat.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mChat.get(position).equals(senderId))
        {
            return MSG_TYP_SENT;
        }
        else
            return MSG_TYP_RECIEVE;
    }
    public static class SendMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView SendMessageShow;

        public SendMessageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            SendMessageShow=itemView.findViewById(R.id.sendmessage);
        }
        void sendData(ChatModel chatModel)
        {
            SendMessageShow.setText(chatModel.getSendermessage());
        }
    }
    public static class RecieveMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView ShowMessage;

        public RecieveMessageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ShowMessage=itemView.findViewById(R.id.recievemessage);
        }
        void setData(ChatModel chatModel)
        {
            ShowMessage.setText(chatModel.getSendermessage());
        }
    }

}
