package com.example.administrator.mydemo.BlockCanvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewFragment extends Fragment implements Contract.ViewFragment {

    private Presenter presenter;
    private Activity activity;
    public static final String TAG = "ccc";
    private MyCanvas myCanvas;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_canvas, container, false);
        myCanvas = new MyCanvas(getContext());
        myCanvas.setOnTouchListener((v, event) -> {
            myCanvas.onTouch(v, event);
            return true;
        });
        return myCanvas;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitData();
        InitEventListener();
    }

    private void InitEventListener() {

    }


    private void InitData() {
        presenter = new Presenter(new Model(), this);
    }

    private void InitView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
