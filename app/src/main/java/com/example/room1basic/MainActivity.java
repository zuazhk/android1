package com.example.room1basic;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    Switch aSwitch;
    MyAdapter myAdapter1, myAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        myAdapter1 = new MyAdapter(false, wordViewModel);
        myAdapter2 = new MyAdapter(true, wordViewModel);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1); //默认为myAdapter1

        aSwitch = findViewById(R.id.switch1); //开关切换显示模式
        aSwitch.setOnCheckedChangeListener((compoundButton, isChanged) -> {
            if (isChanged) {
                recyclerView.setAdapter(myAdapter2);
            } else {
                recyclerView.setAdapter(myAdapter1);
            }
        });

        wordViewModel.getAllWordsLive().observe(this, words -> {
            int temp = myAdapter1.getItemCount();
            myAdapter1.setAllWords(words);
            myAdapter2.setAllWords(words);
            if (temp != words.size()) {
                myAdapter1.notifyDataSetChanged(); //刷新视图
                myAdapter2.notifyDataSetChanged(); //刷新视图
            }


        });

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
//        buttonDelete = findViewById(R.id.buttonDelete);
//        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonInsert.setOnClickListener(view -> {
            String[] english = {
                    "Hello", "World", "Android", "Google",
                    "Studio", "Project", "Database", "Recycler",
                    "View", "string", "Value", "Integer"
            };
            String[] chinese = {
                    "你好", "世界", "安卓系统", "谷歌公司",
                    "工作室", "项目", "数据库", "回收站",
                    "视图", "字符串", "价值", "整数类型"
            };
            for (int i = 0; i < english.length; i++) {
                Word word = new Word(english[i], chinese[i]);
                wordViewModel.insertWords(word); //插入单词
            }
        });


        buttonClear.setOnClickListener(view -> {
            wordViewModel.deleteAllWords(); //删除所有单词
        });

/*        buttonDelete.setOnClickListener(view -> {
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
        });*/
    }


}