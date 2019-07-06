package com.example.administrator.mydemo.BlockImageLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.mydemo.BuildConfig;
import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.Utils_Tools.FileSaveUtil;

import pub.devrel.easypermissions.EasyPermissions;

import static com.example.administrator.mydemo.Constants.IMAGE_STORE;

public class ViewFragment extends Fragment implements Contract.ViewFragment {
    public static final String TAG = "ccc";

    private Presenter presenter;
    private Activity activity;
    private Button btn_loadImage;
    private Button btn_clearImage;
    private ImageView img_move;
    private int mode;
    private float moveX = 0;
    private float moveY = 0;
    private float startDistance;
    private float startRotate;
    private Matrix firstMatrix;
    private Matrix startMatrix;
    private Matrix lastMatrix;
    private PointF centerPoint;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_manipulation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_loadImage = view.findViewById(R.id.btn_loadImage);
        btn_clearImage = view.findViewById(R.id.btn_clearImage);
        img_move = view.findViewById(R.id.img_move);

        InitData();
        InitView();
        InitEventListener();
    }

    private void InitData() {
        mode = Config.MODE_LEAVE;
        presenter = new Presenter(new Model(), this);
        startMatrix = new Matrix();
        lastMatrix = new Matrix();
        firstMatrix = new Matrix();
        centerPoint = new PointF();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitEventListener() {
        // 讀取檔案
        btn_loadImage.setOnClickListener(v -> {
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};

//          Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//          intent.setType("image/*");
//          intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
//          this.startActivityForResult(intent,IMAGE_STORE);

//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction("android.intent.action.GET_CONTENT");
//            this.startActivityForResult(Intent.createChooser(intent, "選擇圖片"), IMAGE_STORE);

            if (!EasyPermissions.hasPermissions(getContext(), perms)) {
                if (BuildConfig.DEBUG) Log.d(TAG, "沒有權限");
                Toast.makeText(activity, "權限不足", Toast.LENGTH_SHORT).show();
                EasyPermissions.requestPermissions(this, "需要權限", 100, perms);
            } else {
                if (BuildConfig.DEBUG) Log.d(TAG, "進到這裡");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(intent, IMAGE_STORE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(intent, IMAGE_STORE);
                }
            }

        });
        // 清除檔案
        btn_clearImage.setOnClickListener(v -> {
            img_move.setImageMatrix(firstMatrix);
            img_move.setImageBitmap(null);
        });
        // 圖片手勢
        img_move.setOnTouchListener((v, event) -> {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = Config.MODE_DRAG;
                    startMatrix.set(img_move.getImageMatrix());
                    centerPoint.set(event.getX(), event.getY()); // 記錄初始位置
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == Config.MODE_DRAG) {
                        moveX = event.getX() - centerPoint.x;
                        moveY = event.getY() - centerPoint.y;
                        lastMatrix.set(startMatrix);
                        lastMatrix.postTranslate(moveX, moveY);
                    } else if (mode == Config.MODE_ZOOM_ROTATE) {
                        // 計算結束距離
                        float lastDistance = calDistance(event);
                        // 計算旋轉後的角荾
                        float lastRotate = calRotate(event);
                        // 算出要旋轉多少
                        float newRotation = lastRotate - startRotate;
                        // 防誤觸
                        if (lastDistance >= 15f || lastDistance <= -15) {
                            float scale = lastDistance / startDistance; // 放大倍數
                            lastMatrix.set(startMatrix);
                            lastMatrix.postScale(scale, scale, centerPoint.x, centerPoint.y);
                            lastMatrix.postRotate(newRotation, centerPoint.x, centerPoint.y);
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = Config.MODE_ZOOM_ROTATE;
                    // 計算開始距離
                    startDistance = calDistance(event);
                    // 計算開始旋轉角度
                    startRotate = calRotate(event);
                    // 防誤觸
                    if (startDistance > 15f) {
                        float dx = event.getX(1) + event.getX(0);
                        float dy = event.getY(1) + event.getY(0);
                        centerPoint = new PointF(dx / 2, dy / 2); // 重新設置中心點
                        startMatrix.set(img_move.getImageMatrix());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mode = Config.MODE_LEAVE;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mode = Config.MODE_LEAVE;
                    break;
            }
            img_move.setImageMatrix(lastMatrix);
            return true;
        });
    }

    @interface Config {
        int MODE_LEAVE = 0;
        int MODE_DRAG = 1;
        int MODE_ZOOM_ROTATE = 2;
    }


    private float calDistance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private float calRotate(MotionEvent event) {
        float rx = event.getX(0) - event.getX(1);
        float ry = event.getY(0) - event.getY(1);
        float radius = (float) Math.atan2(ry, rx);
        return (float) Math.toDegrees(radius);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_STORE:
                if (data != null) {
                    String path = FileSaveUtil.with().getPath(getContext(), data.getData());
                    if (BuildConfig.DEBUG) Log.d(TAG, path);
                    if (null != path) {
                        Bitmap pointer = BitmapFactory.decodeFile(path).copy(Bitmap.Config.ARGB_4444, true);
                        img_move.setScaleType(ImageView.ScaleType.MATRIX);
                        img_move.setImageBitmap(pointer);
                        firstMatrix.set(img_move.getImageMatrix());
//                    img_move.setImageDrawable(new BitmapDrawable(getResources(), pointer));
//                    Glide.with(this).load(pointer).into(img_move);
                    }
                }
                break;
        }
    }

    private void InitView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
