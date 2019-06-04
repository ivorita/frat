package com.antelope.android.frat.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.antelope.android.frat.R;
import com.antelope.android.frat.utils.CmdUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;

public class ControlFragment extends Fragment {

    boolean door_state = false;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.camera)
    Button mCamera;
    @BindView(R.id.temp_min)
    TextInputEditText mTempMin;
    @BindView(R.id.temp_max)
    TextInputEditText mTempMax;
    @BindView(R.id.hum_min)
    TextInputEditText mHumMin;
    @BindView(R.id.hum_max)
    TextInputEditText mHumMax;
    @BindView(R.id.door_ctrl)
    Button doorCtrl;
    private CmdUtils mCmdUtils;
    private Unbinder unbinder;

    public static ControlFragment newInstance() {
        ControlFragment fragment = new ControlFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCmdUtils = new CmdUtils();
        return view;
    }

    @OnClick({R.id.confirm, R.id.camera, R.id.door_ctrl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (!mTempMin.getText().toString().equals("")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("Temp_Min:" + mTempMin.getText().toString());
                        }
                    }, 1000);

                }

                if (!mTempMax.getText().toString().equals("")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("Temp_Max:" + mTempMax.getText().toString());
                        }
                    }, 2000);

                }
                if (!mHumMin.getText().toString().equals("")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("Hum_Min:" + mHumMin.getText().toString());
                        }
                    }, 3000);

                }
                if (!mHumMax.getText().toString().equals("")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("Hum_Max:" + mHumMax.getText().toString());
                        }
                    }, 4000);

                }

                /*mCmdUtils.cmd("Temp_Max:" + mTempMax.getText().toString());
                mCmdUtils.cmd("Temp_Min:" + mTempMin.getText().toString());
                mCmdUtils.cmd("Hum_Min:" + mHumMin.getText().toString());
                mCmdUtils.cmd("Hum_Max:" + mHumMax.getText().toString());*/

                if (!mTempMin.getText().toString().equals("") | !mTempMax.getText().toString().equals("") |
                        !mHumMin.getText().toString().equals("") | !mHumMax.getText().toString().equals("")) {
                    Snackbar.make(view, "正在修改，请稍等。", Snackbar.LENGTH_SHORT).show();
                } else if (
                        mTempMin.getText().toString().equals("") & mTempMax.getText().toString().equals("") &
                                mHumMin.getText().toString().equals("") & mHumMax.getText().toString().equals("")) {
                    Snackbar.make(view, "请输入数值！", Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.camera:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mCmdUtils.cmd("Fire:1");
                    }
                }).start();
                Snackbar.make(view, "正在拍照，请稍后刷新数据界面", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.door_ctrl:
                Log.d(TAG, "doorCtrl: " + doorCtrl.getText().toString());
                if (doorCtrl.getText().toString().equals("远程开门")) {
                    doorCtrl.setText("远程关门");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("lock:1");
                        }
                    }).start();
                    Snackbar.make(view, "开门", Snackbar.LENGTH_SHORT).show();
                } else if (doorCtrl.getText().toString().equals("远程关门")) {
                    doorCtrl.setText("远程开门");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mCmdUtils.cmd("lock:0");
                        }
                    }).start();
                    Snackbar.make(view, "关门", Snackbar.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
