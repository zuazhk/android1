package com.example.words;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton  单例模式
@Database(entities = {Word.class}, version = 5, exportSchema = false)//当数据库结构更新时，需改动version的值
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;

    static WordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
//                    .fallbackToDestructiveMigration()//如果数据库版本升级，会先删除原数据库，再创建新数据库
//                    .addMigrations(MIGRATION_3_4) // 指定升级数据库的迁移策略
                    .addMigrations(MIGRATION_4_5) // 指定升级数据库的迁移策略
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();

    static final Migration MIGRATION_2_3 = new Migration(2, 3) { //数据库版本从2升级到3
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) { //数据库版本从3升级到4 数据库删除不要的字段
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //创建一个新的表
            database.execSQL("CREATE TABLE word_temp(id INTEGER PRIMARY KEY NOT NULL,english_word TEXT,chinese_meaning TEXT)");
            //复制旧表数据到新表
            database.execSQL("INSERT INTO word_temp(id,english_word,chinese_meaning) SELECT id,english_word,chinese_meaning FROM word");
            //删除旧表
            database.execSQL("DROP TABLE word");
            //重命名新表
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) { //数据库版本从4升级到5 添加字段
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0");
        }
    };

}
