package com.yanshao.selectimagelib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanshao.selectimagelib.view.ZoomImageView;
import com.yanshao.yanimageload.imageload.YanImageLoad;

public class YanShowImageActivity extends AppCompatActivity {
    ImageView show_image;
    TextView yan_title_text, yan_finish_text, status_text;
    ImageView yan_back_img;
    LinearLayout shaw_title_layout;
    public static boolean isshow = false;
    public static int sTheme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar(YanShowImageActivity.this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (sTheme != 0) {
            //设置主题
            setTheme(sTheme);
        }

        setContentView(R.layout.activity_yan_show_image);

        yan_title_text = findViewById(R.id.yan_title_text);
        yan_finish_text = findViewById(R.id.yan_finish_text);
        yan_back_img = findViewById(R.id.yan_back_img);
        show_image = findViewById(R.id.show_image);
        status_text = findViewById(R.id.status_text);
        shaw_title_layout = findViewById(R.id.shaw_title_layout);
        status_text.setHeight(getStateBar3());
        yan_title_text.setText("查看图片");
        String path = this.getIntent().getStringExtra("image");
        Bitmap bm = BitmapFactory.decodeFile(path);
        show_image.setImageBitmap(bm);
        show_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isshow) {


                    //将选中的主题资源id保存到静态变量中
                 /*   sTheme = android.R.style.Theme_Light_NoTitleBar_Fullscreen;
                    Log.e("yyy", "" + sTheme);
                    recreate();*/
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                    shaw_title_layout.setVisibility(View.GONE);
                    isshow = true;
                } else {
                   /* sTheme = android.R.style.Theme_Light_NoTitleBar;
                    recreate();*/
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//全部显示出来。
                    shaw_title_layout.setVisibility(View.VISIBLE);
                    isshow = false;
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


    /**
     * 设置透明状态栏
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private int getStateBar3() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        //    Log.e("yy","result="+result);
        return result;
    }

}
