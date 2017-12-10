package com.example.easywallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easywallet.adapter.ListAdapter;
import com.example.easywallet.db.DbHelper;
import com.example.easywallet.model.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DbHelper mHelper;
    private SQLiteDatabase mDb;
    private Button mAddButton;
    private Button mOutButton;
    private TextView mTextView;

    String sum ="0";
    private ArrayList<ListItem> mItemList = new ArrayList<>();
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mHelper = new DbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new ListAdapter(
                this,
                R.layout.item,
                mItemList
        );

        ListView lv = (ListView) findViewById(R.id.list_view);  //ผูกกับ listview
        lv.setAdapter(mAdapter);

        mAddButton = (Button) findViewById(R.id.add_button);
        mOutButton = (Button) findViewById(R.id.out_button);
        mTextView = (TextView) findViewById(R.id.textView);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                   startActivityForResult(intent,001);

            }
        });

        mOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivityForResult(intent,002);
            }
        });

        //---------------กดค้าง -----------------------------------------------------------------------------------------
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                ListItem item = mItemList.get(position);
                dialog.setTitle("ยืนยันลบรายการ "+item.name+" "+item.money+" บาท");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListItem item = mItemList.get(position);
                        int phoneId=item.id;
                        //String[] args = new String[]{String.valueOf(phoneId)};
                        mDb.delete(
                                DbHelper.TABLE_NAME,
                                DbHelper.COL_ID+"=?",//คอลัมเงือนไขตัวที่จะลบ    _id=? AND picture=?"
                                new String[]{String.valueOf(phoneId)}//?คือphoneId ก็คือargsนั้นหละ  (phoneID,"number0001.jpg")
                        );
                        loadDataFromDb();//โหลดDB
                        mAdapter.notifyDataSetChanged();//บอกให้อแดปเตอรู้
                    }

                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //โค้ดที่ต้องการให้ทำงาน เมือปุ่ม OK ใน dialog ถูกคลิค}
                    }

                });


                dialog.show();
                return true;

            }
        });

//---------------กดค้าง ----

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 001){
            if(resultCode == RESULT_OK){
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();

            }
        }else if(requestCode == 002){
            if(resultCode == RESULT_OK){
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();

            }
        }
    }

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                DbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
        String s = sum;
        mItemList.clear();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.COL_NAME));
            String money = cursor.getString(cursor.getColumnIndex(DbHelper.COL_MONEY));
            String type = cursor.getString(cursor.getColumnIndex(DbHelper.COL_TYPE));
            String picture = cursor.getString(cursor.getColumnIndex(DbHelper.COL_PICTURE));

            ListItem item = new ListItem(id, name, money,type,picture);
            mItemList.add(item);

            if(type.equals("1")) {
                s = String.valueOf((Integer.parseInt(s) + Integer.parseInt(money)));
            }
            if(type.equals("2")) {
                s = String.valueOf((Integer.parseInt(s) - Integer.parseInt(money)));
            }
            mTextView.setText(s);


        }
    }
}
