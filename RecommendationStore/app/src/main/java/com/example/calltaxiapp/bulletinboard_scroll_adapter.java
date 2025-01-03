package com.example.calltaxiapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bulletinboard_scroll_adapter extends RecyclerView.Adapter<bulletinboard_scroll_adapter.CustomViewHolder> {

    private ArrayList<bulletinboard_scroll_data> arraylist;
    private Context context;

    public bulletinboard_scroll_adapter(ArrayList<bulletinboard_scroll_data> arraylist, Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public bulletinboard_scroll_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulletinboard_scroll_itme_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bulletinboard_scroll_adapter.CustomViewHolder holder, int position) {

        bulletinboard_scroll_data currentItem = arraylist.get(position);

        holder.title_text.setText(arraylist.get(position).getTitle_text());
        holder.content_text.setText(arraylist.get(position).getContent_text());
        holder.time_text.setText(arraylist.get(position).getTime_text());
        holder.nickname_text.setText(arraylist.get(position).getNickname_text());
        holder.count_likeicon_text.setText(arraylist.get(position).getCount_likeicon_text());
        holder.count_commenticon_text.setText(arraylist.get(position).getCount_commenticon_text());

        holder.likeicon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 좋아요 수 증가 로직
                int likeCount = Integer.parseInt(holder.count_likeicon_text.getText().toString());
                likeCount++;
                holder.count_likeicon_text.setText(String.valueOf(likeCount));
                currentItem.setCount_likeicon_text(String.valueOf(likeCount)); // MainData 객체의 값도 업데이트
            }
        });

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.nickname_text.getText().toString();
                Log.e("434342", holder.title_text.getText().toString());

                Intent intent1 = new Intent(context, publishing_details_Activity.class);
                intent1.putExtra("title", holder.title_text.getText().toString());
                intent1.putExtra("content", holder.content_text.getText().toString());
                intent1.putExtra("time", holder.time_text.getText().toString());
                intent1.putExtra("nickname", holder.nickname_text.getText().toString());
                intent1.putExtra("Start", "1");
                context.startActivity(intent1);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return false;
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

        protected TextView title_text;
        protected TextView content_text;
        protected TextView time_text;
        protected TextView nickname_text;
        protected TextView count_likeicon_text;
        protected TextView count_commenticon_text;
        protected ImageView likeicon_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Title);
            this.content_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Content);
            this.time_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Time);
            this.nickname_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Nickname);
            this.count_likeicon_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Count_likeicon);
            this.count_commenticon_text = (TextView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Textview_Count_commenticon);
            this.likeicon_image = (ImageView) itemView.findViewById(R.id.bulletinboard_scroll_itme_list_Imageview_Likeicon);
        }
    }
}