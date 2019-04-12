package com.yanshao.selectimagelib.adapter;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanshao.selectimagelib.R;
import com.yanshao.selectimagelib.ScreenUtils;
import com.yanshao.selectimagelib.bean.ImageBean;
import com.yanshao.yanimageload.imageload.YanImageLoad;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {
    private List<ImageBean> imageBeanList;
    private Activity mactivity;
    private OnItemCallClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_item,check_item;
        ConstraintLayout layout_item;

        public ViewHolder(View itemView) {
            super(itemView);


            layout_item = itemView.findViewById(R.id.layout);
            image_item = itemView.findViewById(R.id.image_item);
            check_item= itemView.findViewById(R.id.check_item);
        }
    }

    public ImageRecyclerAdapter(Activity activity, List<ImageBean> imageBeans) {
        this.imageBeanList = imageBeans;
        mactivity = activity;
    }

    @Override
    public int getItemCount() {

        return imageBeanList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yan_image_recycler_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.layout_item.getLayoutParams();
        int screenWidth = ScreenUtils.getScreenWidth(mactivity);
        int marginLeft = (int) ScreenUtils.dp2px(mactivity, 7);
        layoutParams.height = (screenWidth - marginLeft * 2) / 3;
        layoutParams.width = (screenWidth - marginLeft * 2) / 3;
        holder.layout_item.setLayoutParams(layoutParams);
        ImageBean imageBean = imageBeanList.get(position);
        YanImageLoad.getInstance(mactivity).disPlay(holder.image_item, imageBean.getPath(),R.drawable.ic_yan_photo, 3);

        if (imageBean.isCkeck()==true) {
            holder.check_item.setImageResource(R.drawable.ic_yan_check);
        }else{
            holder.check_item.setImageResource(R.drawable.ic_yan_check_nor);
        }
        holder.check_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemCheckClick(v,position);
                }
            }
        });
        holder.image_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });
    }


    public void setOnItemCallClickListener(OnItemCallClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemCallClickListener {
        void onItemClick(View view, int position);
        void onItemCheckClick(View view, int position);
    }


}
