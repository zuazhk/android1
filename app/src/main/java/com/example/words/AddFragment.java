package com.example.words;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.TokenWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class AddFragment extends Fragment {
    private Button buttonSubmit;
    private EditText editTextEnglish, editTextChinese;
    private WordViewModel wordViewModel;


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        editTextEnglish = activity.findViewById(R.id.editTextEnglish);
        editTextChinese = activity.findViewById(R.id.editTextChinese);
        buttonSubmit = activity.findViewById(R.id.buttonSubmit);
        buttonSubmit.setEnabled(false);//一开始不可用

        // 输入框 键盘
        editTextEnglish.requestFocus();//让editTextEnglish获取焦点
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);//获取输入法管理器
        imm.showSoftInput(editTextEnglish, 0);//让editTextEnglish弹出输入法

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String english = editTextEnglish.getText().toString().trim();//获取输入框的内容+去空格
                String chinese = editTextChinese.getText().toString().trim();
                buttonSubmit.setEnabled(!english.isEmpty() && !chinese.isEmpty());//当两个输入框都不为空时，按钮可用
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editTextEnglish.addTextChangedListener(textWatcher);
        editTextChinese.addTextChangedListener(textWatcher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String english = editTextEnglish.getText().toString().trim();//获取输入框的内容+去空格
                String chinese = editTextChinese.getText().toString().trim();
                Word word = new Word(english, chinese);
                wordViewModel.insertWords(word);
                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();//返回上一级
            }
        });
    }
}