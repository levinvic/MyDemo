package com.example.administrator.mydemo.BlockCanvas;


public class Presenter {
    private static final String TAG = Presenter.class.getSimpleName();
    private Model model;
    private Contract.View view;
    private Contract.ViewDialogFragment viewDialogFragment;
    private Contract.ViewFragment viewFragment;

    Presenter(Model model, Contract.ViewFragment viewFragment) {
        this.model = model;
        this.viewFragment = viewFragment;
    }

    Presenter(Model model, Contract.ViewDialogFragment viewDialogFragment) {
        this.model = model;
        this.viewDialogFragment = viewDialogFragment;
    }

    Presenter(Model model, Contract.View view) {
        this.model = model;
        this.view = view;
    }


    //////////////////////////新增方法//////////////////////////


}
