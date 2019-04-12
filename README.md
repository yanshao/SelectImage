# SelectImage
选择图片

此项目依赖YanImageload加载图片框架 

selectimagelib依赖https://github.com/yanshao/YanImageload 加载图片框架，selectimagelib主要实现本地sd卡图片的展示及选中

app为示例demo依赖selectimagelib

# 使用
                Intent intent = new Intent(MainActivity.this, YanSelectImageActivity.class);
                intent.putExtra(YanSelectImageActivity.SELECT_SIZE, 1);
                startActivityForResult(intent, YanSelectImageActivity.STAR_CODE);
                
  在需要的地方调用以上代码进行跳转   SELECT_SIZE表示最多选择多少张图片  默认为1张   
  
  注：startAcityForResult 跳转requestCode 必传 否则在onActivityResult方法没法正确判断是否时选择照片返回
  
  onActivityResult回调：
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YanSelectImageActivity.STAR_CODE && resultCode == YanSelectImageActivity.OK_CODE) {
            imageList = data.getStringArrayListExtra(YanSelectImageActivity.SELECT_IMAGE_LIST);
            Log.e("yy", "im==" + imageList.size());

            YanImageLoad.getInstance(MainActivity.this).disPlay(photo_image, imageList.get(0),         com.yanshao.selectimagelib.R.drawable.ic_yan_photo, 1);
        }
    }

回调回来根据requestCode和resultCode进行判断  获取传回来的 ArrayList<String> 数组
