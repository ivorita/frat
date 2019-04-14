package com.antelope.android.frat.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.antelope.android.frat.MainActivity;
import com.antelope.android.frat.R;
import com.antelope.android.frat.data.Datapoints;
import com.antelope.android.frat.data.Datastreams;
import com.antelope.android.frat.utils.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Delayed;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    @BindView(R.id.spinner_data)
    Spinner spinnerData;
    Unbinder unbinder;

    Datastreams datastreams;

    String queryTmp;

    boolean isPress = false;

    String url = "http://api.heclouds.com/devices/30964714/datapoints?";

    String[] data = new String[]{"温度", "湿度", "门状态", "火情"};
    @BindView(R.id.year)
    TextInputEditText year;
    @BindView(R.id.month)
    TextInputEditText month;
    @BindView(R.id.day)
    TextInputEditText day;
    @BindView(R.id.query_data)
    Button queryData;
    @BindView(R.id.history_layout)
    LinearLayout historyLayout;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        unbinder = ButterKnife.bind(this, view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, data);
        spinnerData.setAdapter(adapter);

        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    queryTmp = "Temperature";
                }
                if (position == 1) {
                    queryTmp = "Humidity";
                }
                if (position == 2) {
                    queryTmp = "door:";
                }
                if (position == 3) {
                    queryTmp = "Fire";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.query_data)
    public void onViewClicked() {
        requestInfo();
    }

    private void requestInfo() {
        HttpUtil.sendOkHttpRequest(url, queryTmp, year.getText().toString(), month.getText().toString(), day.getText().toString(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("his", "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseJson = response.body().string();
                Log.d("his", "onResponse: " + responseJson);
                datastreams = handleResponse(responseJson);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showInfo(datastreams);
                    }
                });
            }
        });
    }

    private void showInfo(Datastreams datastreams) {

        historyLayout.removeAllViews();

        for (Datapoints datapoints : datastreams.datapoints) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.history_item, historyLayout, false);
            TextView mDateText = view.findViewById(R.id.date_text);
            TextView mInfoText = view.findViewById(R.id.info_text);


            mDateText.setText(datapoints.at);
            mInfoText.setText(Integer.toString(datapoints.value));

            historyLayout.addView(view);
        }
    }

    public static Datastreams handleResponse(String response) {

        try {
            //使用JSONArray、Gson、JSONObject,返回一个charJson类对象
            JSONObject jsonObject = new JSONObject(response);//创建一个JSONObject对象 填入response
            String object = jsonObject.toString();
            Log.d(TAG, "handleResponse: " + object);
            JSONObject data = jsonObject.getJSONObject("data");
            Log.d(TAG, "data: " + data);
            JSONArray datastreams = data.getJSONArray("datastreams");
            Log.d(TAG, "datastreams: " + datastreams);

            JSONObject obj_ds = datastreams.getJSONObject(0);
            Log.d(TAG, "obj_ds: " + obj_ds);

            JSONArray datapoints = obj_ds.getJSONArray("datapoints");
            Log.d(TAG, "datapoints: " + datapoints);

            String Strobj_ds = obj_ds.toString();

            return new Gson().fromJson(Strobj_ds, Datastreams.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
