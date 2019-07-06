package com.example.administrator.mydemo.BlockCalculator;


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


    public void setDisplay(String s) {
        viewFragment.getDisplay(s);
    }

    public void setNumber(String inputNumber, boolean boo_equal) {
        model.setNumberData(inputNumber, boo_equal);
        viewFragment.getDisplay(model.checkDisplay());
    }


    public void setOperation(String display, String operation) {
        model.setOperation(display, operation);
        // 顯示異常訊息
        if (!"".equals(model.checkError())) {
            viewFragment.showToast(model.checkError());
        }
    }

    public void setOperationUnmul(String display, String operation) {
        model.setOperationUnmul(display, operation);
        // 螢幕顯示 "/"
        viewFragment.getDisplay(model.checkDisplay());
        // 顯示異常訊息
        if (!"".equals(model.checkError())) {
            viewFragment.showToast(model.checkError());
        }
    }

    public void setClear(boolean b) {
        model.setClear(b);
    }

    public void setTotal(String display) {
        model.setTotal(display);
        // 螢幕顯示 計算結果
        viewFragment.getDisplay(model.checkDisplay());
        // 顯示異常訊息
        if (!"".equals(model.checkError())) {
            viewFragment.showToast(model.checkError());
        }
    }

    public void initData() {
        model.initData();
    }
}
