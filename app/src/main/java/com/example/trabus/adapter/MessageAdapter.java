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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MSG_TYP_SENT=1;
    public static final int MSG_TYP_RECIEVE=2;
    private  List<ChatModel> mChat;
    FirebaseUser fuser;
    Context  context;

    public MessageAdapter(List<ChatModel> mChat,Context context) {
        this.mChat = mChat;
        this.context=context;
    }
    public void setList(List<ChatModel> mChat,Context context) {
        this.mChat = mChat;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if(viewType==MSG_TYP_SENT)
        {
          return new SendMessageViewHolder(layoutInflater,parent);
        }
        else {
            return new RecieveMessageViewHolder(layoutInflater,parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ChatModel model=mChat.get(position);

        if(holder.getClass()==SendMessageViewHolder.class)
        {
            ((SendMessageViewHolder) holder).SendMessageShow.setText(model.getSendermessage());
        }
        else
        {
            ((RecieveMessageViewHolder)holder).ShowMessage.setText((model.getSendermessage()));;

        }

    }

    @Override
    public int getItemCount() {

            return mChat.size();
        }

    @Override
    public int getItemViewType(int position) {
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        ChatModel model=mChat.get(position);
        if(model.getId().equals(fuser.getUid()))
        {
            return MSG_TYP_RECIEVE;
        }
        else
            return MSG_TYP_SENT;
    }
    public static class SendMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView SendMessageShow;

        public SendMessageViewHolder(LayoutInflater layoutInflater,ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.chatsender,viewGroup,false));
            SendMessageShow=itemView.findViewById(R.id.sendmessage);

        }
    }
    public static class RecieveMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView ShowMessage;

        public RecieveMessageViewHolder(LayoutInflater layoutInflater,ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.chatreciever,viewGroup,false));
            ShowMessage=itemView.findViewById(R.id.recievemessage);
        }
    }

}
