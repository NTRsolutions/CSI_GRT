package com.growatt.shinephone.view;

/**
 * Created by dg on 2017/6/21.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.m30.wifi.InputWifi;
import com.example.m30.wifi.WifiConnector;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.AhToolActivity;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.T;

import java.util.HashMap;
import java.util.List;

public class GetWifiListNew extends Dialog {
    private HashMap<String, String> ssidmap;
    private List<HashMap<String, String>> wifilists;
    private EditText et;
    private Context mContext;
    public GetWifiListNew(Context context) {
        super(context,R.style.dialog1);
        mContext = context;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.inputwifiwindow, null);
        Button bt1=(Button)view.findViewById(R.id.button1);
        Button bt2=(Button)view.findViewById(R.id.button2);
        et=(EditText)view.findViewById(R.id.editText_pwd);
        et.setText("12345678");
        final ListView listView=(ListView)view.findViewById(R.id.listView1);
        InputWifi.getwifi(mContext, listView, new InputWifi.InputListener() {

            @Override
            public void Error() {
                T.make(R.string.dataloggers_inquire_no,mContext);
            }
            @Override
            public void Right(List<HashMap<String, String>> WiFiList) {
                wifilists = WiFiList;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                GetWifiListNew.this.dismiss();
                ssidmap=wifilists.get(position);
                Mydialog.Show(mContext,"");
                new WifiConnector(mContext, new WifiConnector.WifiConnectListener() {

                    @Override
                    public void OnWifiConnectCompleted(boolean isConnected) {
                        if(isConnected==true){
                            Intent intent=new Intent(mContext,AhToolActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("SSID",ssidmap.get("SSID").toString());
                            bundle.putString("WifiPwd","12345678");
                            bundle.putString("et",ssidmap.get("SSID").toString());
                            intent.putExtras(bundle);
                            ((Activity)mContext).startActivity(intent);
                        }else{
                            T.make(R.string.all_error,mContext);
                        }
                    }
                }).connect(ssidmap.get("SSID"),et.getText().toString(), WifiConnector.SecurityMode.WPA);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                InputWifi.getwifi(mContext, listView, new InputWifi.InputListener() {

                    @Override
                    public void Error() {
                        T.make(R.string.dataloggers_inquire_no,mContext);
                    }

                    @Override
                    public void Right(List<HashMap<String, String>> WiFiList) {
                        wifilists=WiFiList;
                    }
                });
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GetWifiListNew.this.dismiss();
            }
        });
        setContentView(view);
    }
}
