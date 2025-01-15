package com.example.words;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private WordViewModel wordViewModel;

    MyAdapter(boolean userCardView, WordViewModel wordViewModel) {
        this.userCardView = userCardView;
        this.wordViewModel = wordViewModel;

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
            itemView = layoutInflater.inflate(R.layout.cell_card_2, parent, false); //加载卡片式的视图
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent, false); //加载列表式的视图
        }
//        return new MyViewHolder(itemView);

        //  放在这里节省资源 ，因为ViewHolder是复用的
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wd = holder.textViewEnglish.getText().toString();
                Uri uri = Uri.parse("https://dict.youdao.com/result?word=" + wd + "&lang=en");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                holder.itemView.getContext().startActivity(intent); //启动浏览器
            }
        });
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Word word = (Word) holder.itemView.getTag(R.id.word_for_view_holder);//获取当前单词
            if (isChecked) {
                holder.textViewChinese.setVisibility(View.GONE);
                word.setChineseInvisible(true);
                wordViewModel.updateWords(word); //在数据库中更新
            } else {
                holder.textViewChinese.setVisibility(View.VISIBLE);
                word.setChineseInvisible(false);
                wordViewModel.updateWords(word);
            }
        });
        return holder;


    }

    // 重写onBindViewHolder方法，用于绑定ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
        holder.itemView.setTag(R.id.word_for_view_holder,word); //将当前单词绑定到ViewHolder上

        holder.textViewNumber.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());

        //aSwitchChineseInvisible 显示逻辑
        if (word.isChineseInvisible()) {
            holder.textViewChinese.setVisibility(View.GONE); //gone:不显示 不占位置 INVISIBLE:不显示 占位置
            holder.aSwitchChineseInvisible.setChecked(true);
            //
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);//visible:显示 占位置
            holder.aSwitchChineseInvisible.setChecked(false);
        }

    }


    @Override
    public int getItemCount() {
        return allWords.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewEnglish, textViewChinese;
        Switch aSwitchChineseInvisible;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
