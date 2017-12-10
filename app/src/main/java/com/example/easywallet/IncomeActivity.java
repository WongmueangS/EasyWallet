package com.example.easywallet;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easywallet.db.DbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {

    private DbHelper mHelper;
    private SQLiteDatabase mDb;
    Cursor mCursor;

    private static final String TAG = IncomeActivity.class.getName();

    private EditText mNameEditText, mMoneyEditText;
    private ImageView mImageView;
    private Button mSaveButton;

    private File mSelectedPictureFile;

    private String id;
    private String name;
    private String money;
    private String type;
    private String filePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Intent intent = getIntent();
         mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mMoneyEditText = (EditText) findViewById(R.id.money_edit_text);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mSaveButton = (Button) findViewById(R.id.save_button);

        mSaveButton.setOnClickListener(this);




    }

    private void saveDataToDb() {
        String name = mNameEditText.getText().toString();
        String money = mMoneyEditText.getText().toString();


        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COL_NAME, name);
        cv.put(DbHelper.COL_MONEY, money);
        cv.put(DbHelper.COL_TYPE, "1");
        cv.put(DbHelper.COL_PICTURE, "ic_income.png");

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(DbHelper.TABLE_NAME, null, cv);
        if (result == -1) {
            //
        }

        Toast.makeText(getApplicationContext(), "บันทึกข้อมูลเรียบร้อยแล้ว"
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId == R.id.save_button){
            saveDataToDb();
            setResult(RESULT_OK);
            finish();

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Log.e(TAG, "Error choosing picture file: " + e.getMessage());
            }

            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                mSelectedPictureFile = imagesFiles.get(0);
                Drawable drawable = Drawable.createFromPath(mSelectedPictureFile.getAbsolutePath());
                mImageView.setImageDrawable(drawable);

                Log.i(TAG, mSelectedPictureFile.getAbsolutePath());
            }
        });

    }
    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dst);
        byte[] buffer = new byte[1024];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }



}
