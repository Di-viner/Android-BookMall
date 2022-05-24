package com.example.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DBOpenHelper extends SQLiteOpenHelper {
    //public static final String name = "db_test.db";
    public static final String CREATE_USER = "CREATE TABLE user("
            + "username TEXT PRIMARY KEY,"
            + "password TEXT)";
    public static final String CREATE_BOOK = "CREATE TABLE book("
            + "name TEXT PRIMARY KEY,"
            + "pic INTEGER,"
            + "intro TEXT,"
            + "price TEXT,"
            + "ID TEXT,"
            + "author TEXT,"
            + "content TEXT)";
    public static final String CREATE_CART = "CREATE TABLE cart("
            + "username TEXT,"
            + "ID TEXT,"
            + "pic INTEGER,"
            + "name TEXT,"
            + "price TEXT,"
            + "num TEXT)";

    public static final String CREATE_HISTORY = "CREATE TABLE history("
            + "username TEXT,"
            + "orderID TEXT,"
            + "name TEXT,"
            + "num TEXT,"
            + "totalPrice TEXT,"
            + "time TEXT,"
            + "pic INTEGER)";


    private SQLiteDatabase db;
    private Context myContext;
    public DBOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CART);
        sqLiteDatabase.execSQL(CREATE_HISTORY);
        //初始化书库
        initLibrary(sqLiteDatabase);

        Toast.makeText(myContext, "Welcome you!", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }


    private void initLibrary(SQLiteDatabase sqLiteDatabase) {


        ///0
        ContentValues values = new ContentValues();
        values.put("name", "不能承受的生命之轻");
        values.put("intro", "哲理");
        values.put("price", "28");
        values.put("pic", R.drawable.a);
        values.put("ID","2000");
        values.put("author","米兰昆德拉");
        values.put("content", "该小说描写了托马斯与特丽莎、萨丽娜之间的感情生活。它是一部哲理小说，" +
                "小说从“永恒轮回”的讨论开始，把读者带入了对一系列问题的思考中，比如轻与重、灵与肉。");
        sqLiteDatabase.insert("book", null, values);

        ///1
        values = new ContentValues();
        values.put("name", "追忆似水年华");
        values.put("intro", "哲理");
        values.put("price", "27");
        values.put("pic", R.drawable.i);
        values.put("ID","2001");
        values.put("author","普鲁斯特");
        values.put("content", "全书以叙述者“我”为主体，" +
                "将其所见所闻所思所感融合一体，既有对社会生活，人情世态的真实描写，又是一份作者自我追求，自我认识的内心经历的记录。");
        sqLiteDatabase.insert("book", null, values);
        ///2
        values = new ContentValues();
        values.put("name", "活着");
        values.put("intro", "哲理");
        values.put("price", "28");
        values.put("pic", R.drawable.e);
        values.put("ID","2002");
        values.put("author","余华");
        values.put("content", "随着内战、三反五反、大跃进、文化大革命等社会变革，徐福贵的人生和家庭不断经受着苦难，" +
                "到了最后所有亲人都先后离他而去，仅剩下年老的他和一头老牛相依为命。");
        sqLiteDatabase.insert("book", null, values);

        ///3
        values = new ContentValues();
        values.put("name", "约翰·克利斯朵夫");
        values.put("intro", "哲理");
        values.put("price", "55");
        values.put("pic", R.drawable.c);
        values.put("ID","2003");
        values.put("author","罗曼罗兰");
        values.put("content", "该小说描写了主人公奋斗的一生，从儿时音乐才能的觉醒、到青年时代对权贵的蔑视和反抗、" +
                        "再到成年后在事业上的追求和成功、最后达到精神宁静的崇高境界。");
        sqLiteDatabase.insert("book", null, values);
        ///4
        values = new ContentValues();
        values.put("name", "自在独行");
        values.put("intro", "散文");
        values.put("price", "20");
        values.put("pic", R.drawable.d);
        values.put("ID","2004");
        values.put("author","贾平凹");
        values.put("content", "这本书的目的是写给生命的行者，愿他们能懂得孤独的真义，在生活里多一些从容潇洒。");
        sqLiteDatabase.insert("book", null, values);

        ///5
        values = new ContentValues();
        values.put("name", "自由在高处");
        values.put("intro", "散文");
        values.put("price", "32");
        values.put("pic", R.drawable.f);
        values.put("ID","2005");
        values.put("author","熊培云");
        values.put("content", "《自由在高处》是作者对《重新发现社会》一书的补充。" +
                "旨在从个体的角度探讨身处转型期的人们如何超越逆境，盘活自由，拓展创造，积极生活。");
        sqLiteDatabase.insert("book", null, values);
        ///6
        values = new ContentValues();
        values.put("name", "无比芜杂的心绪");
        values.put("intro", "散文");
        values.put("price", "20");
        values.put("pic", R.drawable.g);
        values.put("ID","2006");
        values.put("author","村上春树");
        values.put("content", "村上春树自选三十五年来的精彩随笔结集而成，都是未以单行本发表过的文字，" +
                "并在每篇前附短文记述写作时的心绪。");
        sqLiteDatabase.insert("book", null, values);

        ///7
        values = new ContentValues();
        values.put("name", "给青年的十二封信");
        values.put("intro", "青春");
        values.put("price", "16");
        values.put("pic", R.drawable.h);
        values.put("ID","2007");
        values.put("author","朱光潜");
        values.put("content", "自朱光潜先生笔下汩汩流淌的，是如长者劝导似的语重心长，如老友交谈般的诚恳真挚，" +
                "读来亲切自然，受益颇多。");
        sqLiteDatabase.insert("book", null, values);


        ///8
        values = new ContentValues();
        values.put("name", "挪威的森林");
        values.put("intro", "青春");
        values.put("price", "22");
        values.put("pic", R.drawable.b);
        values.put("ID","2008");
        values.put("author","村上春树");
        values.put("content", "《挪威的森林》是日本作家村上春树的一部长篇爱情小说" +
                "故事讲述主角纠缠在情绪不稳定且患有精神疾病的直子和开朗活泼的小林绿子之间，展开了自我成长的旅程。");
        sqLiteDatabase.insert("book", null, values);

        ///9
        values = new ContentValues();
        values.put("name", "解忧杂货店");
        values.put("intro", "青春");
        values.put("price", "30");
        values.put("pic", R.drawable.j);
        values.put("ID","2009");
        values.put("author","东野圭吾");
        values.put("content", "本书的作者将生命之钥藏在了五个故事中，" +
                "故事里人们将困惑写成信投进杂货店，奇妙的事情随即不断发生。");
        sqLiteDatabase.insert("book", null, values);

    }


}
