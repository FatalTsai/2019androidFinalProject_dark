package com.example.three_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    int no,page;
    String str;
    TextView text2;

    SQLiteDatabase db;
    static final String db_name ="testDB";
    static  final  String tb_name="test";

    private RecyclerView recycler_view;
    private MyAdapter adapter;
    private Button btnremove;
    private ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text2 = findViewById(R.id.text2);
        no =0;
        page =2;
        Intent it=getIntent();
        no = it.getIntExtra("page",0);
        text2.setText(no+"→ "+page);


        final String[] lunch = {"undefined", "色情片", "避孕用品", "口服避用藥", "性病治療費"};
        db  = openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        loadtext();



        // 準備資料，塞到ArrayList裡
        Cursor c=db.rawQuery("SELECT * FROM "+tb_name,null);
        if(c.moveToFirst()) {

            do {
                str ="";
                str += "id:" + c.getString(0) + "\n";
                str += "name:" + c.getString(1) + "\n";
                str += "price:" + c.getString(2) + "\n";
                str += "class:" + lunch[Integer.valueOf(c.getString(3))] + "\n";
                mData.add(" "+str);
            } while (c.moveToNext());

        }


        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // 設置RecyclerView為列表型態
        //recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setLayoutManager(new GridLayoutManager(   this, 1));

        // 設置格線
        //recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        // 將資料交給adapter
        adapter = new MyAdapter(mData);
        // 設置adapter給recycler_view
        recycler_view.setAdapter(adapter);
    }

    public void twotoone(View view) {
        Intent it = new Intent(this,MainActivity.class);
        it.putExtra("page",2);
        startActivityForResult(it,page);
    }

    public void twotothree(View view) {

        Intent it = new Intent(this,Main3Activity.class);
        it.putExtra("page",2);
        startActivityForResult(it,page);

    }

    public  void loadtext()
    {
        Cursor c=db.rawQuery("SELECT * FROM "+tb_name,null);

        Log.d("getcount",c.getCount()+"");
        /*
        if(c.getCount() == 0)
        {
            addData(":NTI20946.","上原亜衣MOODYZ出演全30タイトル12時間コンプリートBEST\t",
                    "8787");

            //addData("PCDIY Magazine","02-23214355",
            //  "service@pcdiy.com.tw");
        }*/
        if(c.moveToFirst()) {
            str = "There is " + c.getCount() + "datas\n";
            str += "-------------\n";

            do {
                str += "name:" + c.getString(0) + "\n";
                str += "price:" + c.getString(1) + "\n";
                str += "class:" + c.getString(2) + "\n";
            } while (c.moveToNext());

            Cursor c2=db.rawQuery("SELECT COUNT(*) FROM "+tb_name+" WHERE classname = 2",null);
            if(c2.moveToFirst()) {
                str = c2.getString(0);
            }

            TextView txv = (TextView) findViewById(R.id.txv);
            txv.setText(str);
            txv.setMovementMethod(ScrollingMovementMethod.getInstance());

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mData;

        MyAdapter(List<String> data) {
            mData = data;
        }

        // 建立ViewHolder
        class ViewHolder extends RecyclerView.ViewHolder{
            // 宣告元件
            private TextView txtItem;

            ViewHolder(View itemView) {
                super(itemView);
                txtItem = (TextView) itemView.findViewById(R.id.txtItem);
                btnremove= (Button) itemView.findViewById(R.id.btnRemove);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        Toast.makeText(view.getContext(),
                                "click " +getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        // 新增一個項目
                        adapter.addItem("New Item");
                        return false;
                    }
                });


                btnremove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 移除項目，getAdapterPosition為點擊的項目位置
                        removeItem(getAdapterPosition());
                    }
                });



            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 連結項目布局檔list_item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 設置txtItem要顯示的內容
            holder.txtItem.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


        // 未更動的程式省略..

        // 新增項目
        public void addItem(String text) {
            // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
            mData.add(3,text);
            Log.d("insert","insert");
            notifyItemInserted(3);
        }

        // 刪除項目
        public void removeItem(int position){
            db.delete(tb_name ,"id  = "+ (position+1), null);
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }
}
