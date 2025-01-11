package com.example.room1basic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// 定义一个MyAdapter类，继承自RecyclerView.Adapter，泛型为MyViewHolder
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // 定义一个List类型的变量allWords，用于存储所有的单词
    private List<Word> allWords = new ArrayList<>();
    private boolean userCardView;

    MyAdapter(boolean userCardView) {
        this.userCardView = userCardView;
    }

    // 定义一个方法setAllWords，用于设置allWords的值
    void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    // 重写onCreateViewHolder方法，用于创建ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (userCardView) {
            itemView = layoutInflater.inflate(R.layout.cell_card, parent, false); //加载卡片式的视图
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal, parent, false); //加载列表式的视图
        }
        return new MyViewHolder(itemView);
    }

    // 重写onBindViewHolder方法，用于绑定ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wd = holder.textViewEnglish.getText().toString();
                Uri uri = Uri.parse("https://dict.youdao.com/result?word=" + wd + "&lang=en");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                holder.itemView.getContext().startActivity(intent); //启动浏览器
            }
        });
    }


    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewEnglish, textViewChinese;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
        }
    }
}
