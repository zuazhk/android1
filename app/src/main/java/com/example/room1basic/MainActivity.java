package com.example.room1basic;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);


        textView = findViewById(R.id.textView);

        wordViewModel.getAllWordsLive().observe(this, words -> {
            //当数据库中的数据发生变化时，更新UI
            StringBuilder text = new StringBuilder();
            for (Word word : words) {
                text.append(word.getId()).append(":").append(word.getWord()).append(" = ").append(word.getChineseMeaning()).append("\n");
            }
            textView.setText(text.toString());
        });

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonInsert.setOnClickListener(view -> {
            Word word1 = new Word("hello", "你好");
            Word word2 = new Word("world", "世界");
            wordViewModel.insertWords(word1, word2); //插入两个单词
        });


        buttonClear.setOnClickListener(view -> {
            wordViewModel.deleteAllWords(); //删除所有单词
        });


        buttonDelete.setOnClickListener(view -> {
            Word word = new Word("hello", "你好");
            word.setId(101);
//            new DeleteAsyncTask(wordDao).execute(word); //删除id为31的单词
            wordViewModel.deleteWords(word); //删除id为31的单词
        });


        buttonUpdate.setOnClickListener(view -> {
            Word word = new Word("Hi", "你好吖!");
            word.setId(100);
//            wordDao.updateWords(word);
//            new UpdateAsyncTask(wordDao).execute(word);
            wordViewModel.updateWords(word);
        });

    }


}