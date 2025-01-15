package com.example.words;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao  //Database access object
public interface WordDao {
    @Insert
    void insertWords(Word... words);

    @Update
    int updateWords(Word... words);

    @Delete
    int deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID ASC")
    LiveData<List<Word>> getAllWordsLive();

    @Query("SELECT * from WORD WHERE english_word LIKE :wordPattern ORDER BY ID DESC")
    LiveData<List<Word>> findWordsWithPattern(String wordPattern);

}
