package com.example.administrator.mydemo.BlockClock;

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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new MyClock(this.getContext());
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
