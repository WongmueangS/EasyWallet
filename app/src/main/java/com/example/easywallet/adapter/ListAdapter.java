package com.example.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easywallet.R;
import com.example.easywallet.model.ListItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ADMIN on 10/12/2560.
 */

public class ListAdapter extends ArrayAdapter<ListItem>{
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<ListItem> mItemList;

    public ListAdapter(@NonNull Context context, int layoutResId, ArrayList<ListItem> ItemList) {
        super(context, layoutResId, ItemList);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mItemList = ItemList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId, null);

        ListItem item = mItemList.get(position);

        ImageView ImageView = (ImageView) itemLayout.findViewById(R.id.image_view);
        TextView nameTextView = (TextView) itemLayout.findViewById(R.id.name_text_view);
        TextView moneyTextView = (TextView) itemLayout.findViewById(R.id.money_text_view);

        nameTextView.setText(item.name);
        moneyTextView.setText(item.money);

        String pictureFileName = item.picture;

        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName);
            Drawable drawable = Drawable.createFromStream(stream, null);
            ImageView.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();

            File pictureFile = new File(mContext.getFilesDir(), pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            ImageView.setImageDrawable(drawable);
        }

        return itemLayout;
    }
    
}

