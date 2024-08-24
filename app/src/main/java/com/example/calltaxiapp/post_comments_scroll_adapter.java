package com.example.calltaxiapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class post_comments_scroll_adapter extends RecyclerView.Adapter<post_comments_scroll_adapter.CustomViewHolder> {

    private ArrayList<post_comments_scroll_data> arraylist;

    public post_comments_scroll_adapter(ArrayList<post_comments_scroll_data> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_comments_scroll_itme_lits, parent, false);
        post_comments_scroll_adapter.CustomViewHolder holder = new post_comments_scroll_adapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        post_comments_scroll_data currentItem = arraylist.get(position);

        holder.imageview_anonymous_image.setImageResource(arraylist.get(position).getImageview_anonymous_image());
        holder.textview_nickname.setText(arraylist.get(position).getTextview_nickname());
        holder.textview_content.setText(arraylist.get(position).getTextview_content());
        holder.textview_time.setText(arraylist.get(position).getTextview_time());

        holder.imageview_options_replyicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!", "댓글 onClick");
            }
        });

        holder.imageview_options_likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 좋아요 수 증가 로직
                int likeCount = Integer.parseInt(holder.textview_likeicon_count.getText().toString());
                likeCount++;
                holder.textview_likeicon_count.setText(String.valueOf(likeCount));
            }
        });

        holder.imageview_options_likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!", "좋아요 onClick");
            }
        });

        holder.imageview_options_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!", "설정 onClick");
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }

    public void remove(int position) {
        try {
            arraylist.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageview_anonymous_image;
        protected TextView textview_nickname;
        protected TextView textview_content;
        protected TextView textview_time;
        protected TextView textview_likeicon_count;

        protected ImageView imageview_options_replyicon;
        protected ImageView imageview_options_likeicon;
        protected ImageView imageview_options_setting;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageview_anonymous_image = (ImageView) itemView.findViewById(R.id.Imageview_Anonymous_image);
            this.textview_nickname = (TextView) itemView.findViewById(R.id.Textview_Nickname);
            this.textview_content = (TextView) itemView.findViewById(R.id.Textview_content);
            this.textview_time = (TextView) itemView.findViewById(R.id.Textview_Time);
            this.textview_likeicon_count = (TextView) itemView.findViewById(R.id.Textview_Likeicon_count);

            this.imageview_options_replyicon = (ImageView) itemView.findViewById(R.id.Imageview_Options_replyicon);
            this.imageview_options_likeicon = (ImageView) itemView.findViewById(R.id.Imageview_Options_likeicon);
            this.imageview_options_setting = (ImageView) itemView.findViewById(R.id.Imageview_Options_setting);
        }
    }
}
