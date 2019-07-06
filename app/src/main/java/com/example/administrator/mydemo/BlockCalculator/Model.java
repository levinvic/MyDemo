package com.example.administrator.mydemo.BlockCalculator;


import java.util.ArrayList;
import java.util.List;

public class Model implements Contract.Model {

    private StringBuilder stringBuilder;
    private boolean boo_equal;
    private String str_displayData;
    private List<Double> lst_temp;
    private List<String> lst_operation;
    private String str_errorMsg;
    private Double db_total;
    private String str_outputWord;

    @Override
    public void setNumberData(String s, boolean b) {
        stringBuilder.append(s);
        boo_equal = b;
        str_outputWord = String.valueOf(stringBuilder);
    }

    /**
     * @param display 畫面顯示的字
     * @param operation 計算符號
     */
    @Override
    public void setOperation(String display, String operation) {
        str_displayData = display;
        // 連按加減乘除
        if ("+".equals(str_displayData) || "-".equals(str_displayData) || "*".equals(str_displayData) || "/".equals(str_displayData)) {
            str_errorMsg = "符號不可連續計算";
        }
        if (boo_equal || "0".equals(str_displayData)) {
            stringBuilder.append(str_displayData);
        }
        try {
            lst_temp.add(Double.parseDouble(String.valueOf(stringBuilder)));
        } catch (NumberFormatException e) {
            str_errorMsg = e.getMessage();
        }
        lst_operation.add(operation);
        stringBuilder = new StringBuilder();
        str_errorMsg = "";
    }

    @Override
    public String checkError() {
        return str_errorMsg;
    }

    /**
     * @param display 畫面顯示的字
     * @param operation 計算符號
     */
    @Override
    public void setOperationUnmul(String display, String operation) {
        str_displayData = display;
        if ("+".equals(str_displayData) || "-".equals(str_displayData) || "*".equals(str_displayData) || "/".equals(str_displayData)) {
            str_errorMsg = "符號不可連續計算";
        }
        if (!"0".equals(str_displayData)) {
            if (boo_equal) {
                stringBuilder.append(str_displayData);
            }
            try {
                lst_temp.add(Double.parseDouble(String.valueOf(stringBuilder)));
            } catch (NumberFormatException e) {
                str_errorMsg = e.getMessage();
            }
            str_outputWord = "/";
            lst_operation.add(operation);
            stringBuilder = new StringBuilder();
        } else {
            str_errorMsg = "0不能作為除數";
            resetNum();
        }
    }

    @Override
    public void setClear(boolean b) {
        boo_equal = b;
        resetNum();
    }

    @Override
    public void setTotal(String display) {
        str_displayData = display;

        if (boo_equal || "0".equals(str_displayData)) {
            return;
        } else {
            boo_equal = true;
        }
        try {
            lst_temp.add(Double.parseDouble(String.valueOf(stringBuilder)));
        } catch (NumberFormatException e) {
            str_errorMsg = e.getMessage();
//            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // 0分母
        for (int i = 0; i < (lst_temp != null ? lst_temp.size() : 0); i++) {
            if (i > 0) {
                if ("+".equals(lst_operation.get(i - 1))) {
                    db_total += lst_temp.get(i);
                } else if ("-".equals(lst_operation.get(i - 1))) {
                    db_total -= lst_temp.get(i);
                } else if ("*".equals(lst_operation.get(i - 1))) {
                    db_total *= lst_temp.get(i);
                } else if ("/".equals(lst_operation.get(i - 1))) {
                    db_total /= lst_temp.get(i);
                }
            } else {
                db_total = lst_temp.get(0);
            }
        }

        // 小於0
        if (db_total < 0) {
            str_errorMsg = "無法計算負數";
            db_total = 0.0;
            str_outputWord = "0";
            resetNum();
        } else {
            str_outputWord = String.valueOf(db_total).replace(".0", "");
            resetNum();
        }
    }

    @Override
    public String checkDisplay() {
        return str_outputWord;
    }

    @Override
    public void initData() {
        stringBuilder = new StringBuilder();
        db_total = 0.0;
        lst_temp = new ArrayList<>();
        lst_operation = new ArrayList<>();
        boo_equal = false;
    }

    // 重置
    public void resetNum() {
        stringBuilder = new StringBuilder();
        lst_operation = new ArrayList<>();
        lst_temp = new ArrayList<>();
        db_total = 0.0;
    }
}
