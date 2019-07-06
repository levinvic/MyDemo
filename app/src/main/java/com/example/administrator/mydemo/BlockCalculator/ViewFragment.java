package com.example.administrator.mydemo.BlockCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydemo.R;

public class ViewFragment extends Fragment implements Contract.ViewFragment {
    public static final String TAG = "ccc";

    private Presenter presenter;
    private Activity activity;
    private Button btn_num0;
    private Button btn_num1;
    private Button btn_num2;
    private Button btn_num3;
    private Button btn_num4;
    private Button btn_num5;
    private Button btn_num6;
    private Button btn_num7;
    private Button btn_num8;
    private Button btn_num9;
    private Button btn_add;
    private Button btn_unadd;
    private Button btn_mul;
    private Button btn_unmul;
    private Button btn_clear;
    private Button btn_equal;
    private TextView tbx_display;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_num0 = view.findViewById(R.id.btn_num0);
        btn_num1 = view.findViewById(R.id.btn_num1);
        btn_num2 = view.findViewById(R.id.btn_num2);
        btn_num3 = view.findViewById(R.id.btn_num3);
        btn_num4 = view.findViewById(R.id.btn_num4);
        btn_num5 = view.findViewById(R.id.btn_num5);
        btn_num6 = view.findViewById(R.id.btn_num6);
        btn_num7 = view.findViewById(R.id.btn_num7);
        btn_num8 = view.findViewById(R.id.btn_num8);
        btn_num9 = view.findViewById(R.id.btn_num9);
        btn_add = view.findViewById(R.id.btn_add);
        btn_unadd = view.findViewById(R.id.btn_unadd);
        btn_mul = view.findViewById(R.id.btn_mul);
        btn_unmul = view.findViewById(R.id.btn_unmul);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_equal = view.findViewById(R.id.btn_equal);
        tbx_display = view.findViewById(R.id.tbx_display);

        InitData();
        InitView();
        InitEventListener();
    }

    private void InitData() {
        presenter = new Presenter(new Model(), this);
        presenter.initData();
    }

    private void InitView() {
        presenter.setDisplay("0");
    }

    private void InitEventListener() {
        // 數字
        btn_num0.setOnClickListener(v -> {
            presenter.setNumber("0", false);
        });
        btn_num1.setOnClickListener(v -> {
            presenter.setNumber("1", false);
        });
        btn_num2.setOnClickListener(v -> {
            presenter.setNumber("2", false);
        });
        btn_num3.setOnClickListener(v -> {
            presenter.setNumber("3", false);
        });
        btn_num4.setOnClickListener(v -> {
            presenter.setNumber("4", false);
        });
        btn_num5.setOnClickListener(v -> {
            presenter.setNumber("5", false);
        });
        btn_num6.setOnClickListener(v -> {
            presenter.setNumber("6", false);
        });
        btn_num7.setOnClickListener(v -> {
            presenter.setNumber("7", false);
        });
        btn_num8.setOnClickListener(v -> {
            presenter.setNumber("8", false);
        });
        btn_num9.setOnClickListener(v -> {
            presenter.setNumber("9", false);

        });

        // 運算
        btn_add.setOnClickListener(v -> {
            presenter.setOperation(String.valueOf(tbx_display.getText()), "+");
            presenter.setDisplay("+");
        });
        btn_unadd.setOnClickListener(v -> {
            presenter.setOperation(String.valueOf(tbx_display.getText()), "-");

            presenter.setDisplay("-");

        });
        btn_mul.setOnClickListener(v -> {
            presenter.setOperation(String.valueOf(tbx_display.getText()), "*");
            presenter.setDisplay("*");
        });
        btn_unmul.setOnClickListener(v -> {
            presenter.setOperationUnmul(String.valueOf(tbx_display.getText()), "/");
        });

        // 清除
        btn_clear.setOnClickListener(v -> {
            presenter.setClear(false);

            presenter.setDisplay("0");
        });
        // 加總
        btn_equal.setOnClickListener(v -> {
            presenter.setTotal(String.valueOf(tbx_display.getText()));

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void getDisplay(String s) {
        tbx_display.setText(s);
    }

    @Override
    public void showToast(String str_msg) {
        if (str_msg != null) {
            if (!str_msg.isEmpty()) {
                Toast.makeText(activity, str_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
