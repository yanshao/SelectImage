package com.yanshao.selectimage;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yanshao.selectimagelib.YanSelectImageActivity;
import com.yanshao.yanimageload.imageload.YanImageLoad;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btn;
    ArrayList<String> imageList;
    ImageView photo_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yan_selectimage_activity);
        photo_image = findViewById(R.id.photo_image);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YanSelectImageActivity.class);
                intent.putExtra(YanSelectImageActivity.SELECT_SIZE, 1);
                startActivityForResult(intent, YanSelectImageActivity.STAR_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YanSelectImageActivity.STAR_CODE && resultCode == YanSelectImageActivity.OK_CODE) {
            imageList = data.getStringArrayListExtra(YanSelectImageActivity.SELECT_IMAGE_LIST);
            Log.e("yy", "im==" + imageList.size());

            YanImageLoad.getInstance(MainActivity.this).disPlay(photo_image, imageList.get(0), com.yanshao.selectimagelib.R.drawable.ic_yan_photo, 1);
        }
    }
}
