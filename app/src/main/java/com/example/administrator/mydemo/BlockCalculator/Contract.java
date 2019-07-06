package com.example.administrator.mydemo.BlockCalculator;


public class Contract {

    public interface Persenter {
        void getCarList(String userId);
    }

    public interface ViewFragment {

        void getDisplay(String s);

        void showToast(String str_msg);
    }

    public interface ViewDialogFragment{

    }

    public interface View {

    }

    public interface Model {

        void setNumberData(String s, boolean b);

        void setOperation(String display,String operation);

        String checkError();

        void setOperationUnmul(String display, String operation);

        void setClear(boolean b);

        void setTotal(String display);

        String checkDisplay();

        void initData();
    }
}
