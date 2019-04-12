package com.yanshao.selectimagelib;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanshao.selectimagelib.adapter.ImageRecyclerAdapter;
import com.yanshao.selectimagelib.bean.ImageBean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YanSelectImageActivity extends AppCompatActivity {
    RecyclerView image_recycler;
    List<ImageBean> imageBeanList = new ArrayList<>();

    public static String SELECT_IMAGE_LIST = "IMAGE_LIST";
    public static String SELECT_SIZE = "SIZE";
    public static int OK_CODE = 552;
    public static int STAR_CODE = 550;
    private String[] permissions = {
            //文件
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    int select_size = 1;
    ImageRecyclerAdapter imageRecyclerAdapter;
    ArrayList<String> imagelist;
    TextView yan_title_text, yan_finish_text;
    ImageView yan_back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_yan_select_image);
        yan_title_text = findViewById(R.id.yan_title_text);
        yan_finish_text = findViewById(R.id.yan_finish_text);
        yan_back_img = findViewById(R.id.yan_back_img);
        image_recycler = findViewById(R.id.image_recycler);
        yan_finish_text.setVisibility(View.VISIBLE);
        select_size = this.getIntent().getIntExtra(SELECT_SIZE, 1);
        imagelist = new ArrayList<>();
        PermissionsUtils.getInstance().checkPermissions(this, permissions, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissions() {
                getLoaderManager().initLoader(0, null, loaderCallbacks);
            }

            @Override
            public void forbidPermissions() {
                finish();
            }
        });

        yan_finish_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagelist.size() > 0) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(SELECT_IMAGE_LIST, imagelist);
                    setResult(OK_CODE, intent);
                    finish();
                } else {
                    Toast.makeText(YanSelectImageActivity.this, "您还未选择图片！", Toast.LENGTH_LONG).show();
                }
            }
        });
        yan_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    LoaderManager.LoaderCallbacks loaderCallbacks = new LoaderManager.LoaderCallbacks() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(YanSelectImageActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC");
        }

        @Override
        public void onLoadFinished(Loader loader, Object data1) {
            Cursor data = ((Cursor) data1);
            int count = data.getCount();
            if (count > 0) {
                data.moveToFirst();
                do {
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                    int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                    String thumbPath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                    String bucket = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));
                    if (!path.substring(path.length() - 4, path.length()).equals(".gif") && !path.substring(path.length() - 4, path.length()).equals(".GIF")) {
                        ImageBean imageBean = new ImageBean();
                        imageBean.setCkeck(false);
                        imageBean.setPath(path);
                        imageBeanList.add(imageBean);
                    }


                } while (data.moveToNext());

                GridLayoutManager layoutManager = new GridLayoutManager(YanSelectImageActivity.this, 3);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                image_recycler.setLayoutManager(layoutManager);
                image_recycler.setItemAnimator(null);
                imageRecyclerAdapter = new ImageRecyclerAdapter(YanSelectImageActivity.this, imageBeanList);
                image_recycler.setAdapter(imageRecyclerAdapter);
                imageRecyclerAdapter.setOnItemCallClickListener(new ImageRecyclerAdapter.OnItemCallClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(YanSelectImageActivity.this, YanShowImageActivity.class);
                        intent.putExtra("image", imageBeanList.get(position).getPath());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemCheckClick(View view, int position) {
                        if (imageBeanList.get(position).isCkeck()) {
                            imageBeanList.get(position).setCkeck(false);
                            for (int i = 0; i < imagelist.size(); i++) {
                                if (imagelist.get(i) == imageBeanList.get(position).getPath()) {
                                    imagelist.remove(i);
                                }
                            }
                            imageRecyclerAdapter.notifyItemChanged(position);
                        } else {
                            if (imagelist.size() < select_size) {
                                imageBeanList.get(position).setCkeck(true);
                                imagelist.add(imageBeanList.get(position).getPath());
                                imageRecyclerAdapter.notifyItemChanged(position);
                            } else {
                                Toast.makeText(YanSelectImageActivity.this, "已超过最多选择限制！", Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                });
            }

        }


        @Override
        public void onLoaderReset(Loader loader) {

        }
    };
}
