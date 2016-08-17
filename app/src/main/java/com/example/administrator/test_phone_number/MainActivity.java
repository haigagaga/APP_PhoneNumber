package com.example.administrator.test_phone_number;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.test_phone_number.bean.NumInfo;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    /**
     * jialezhushi
     * */
    private Button mButton;
    private EditText mEditText;
    private Context mContext;
    private TextView textView;
    private NumInfo numInfo;
    private static final int SEND_SUCCEDD = 1;
    String province, city, code,company;

//    private NumInfo.ResultBean resultBean;
//    private ServiceInter serviceNum = new ServiceImpl();

//    private List<Map<Object,String>>

    private void init() {
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editTxt_num);
        textView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mContext = this;
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Thread mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestQueue queue = Volley.newRequestQueue(mContext);
                        String num = mEditText.getText().toString();
                        String url = "http://apis.juhe.cn/mobile/get?phone=" + num + "&key=7db320dc0b22daddaea37b445f5103d4";
                        queue.add(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG",response.toString());
//                        Toast.makeText(mContext,response.toString() , Toast.LENGTH_SHORT).show();
                                /**
                                 * resultcode : 200
                                 * reason : Return Successd!
                                 * result : {"province":"湖南","city":"长沙","areacode":"0731","zip":"410000","company":"中国联通","card":"未知"}
                                 * error_code : 0
                                 */
                                try {

//                            String mJSON = response.toString();
                                    String resultcode = response.getString("resultcode");

                                    if (resultcode.equals("200")) {
                                        JSONObject result = response.getJSONObject("result");
                                        province = result.getString("province");
                                        city = result.getString("city");
                                        code = result.getString("areacode");
                                        company = result.getString("company");
                                        Message msg = handler.obtainMessage();
                                        msg.what = 1;
                                        handler.sendEmptyMessage(1);
                                    }else {
                                        Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                    }
//                           NumInfo.ResultBean resultBean=numInfo.getResult();
//                            String province = resultBean.getProvince();
//                            String city = resultBean.getCity();

//                            numInfo = JSON.parseObject(mJSON,NumInfo.class);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Log.e("TAG", error.getMessage(), error);
                                if (mEditText.getText() !=null) {
                                    Toast.makeText(mContext, "请尝试重新连接网络", Toast.LENGTH_SHORT).show();
                                }else {
                            }}
                        }
                        ));
                    }
                });
                mThread.start();

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            msg = handler.obtainMessage();

            switch (msg.what) {
                case 1:
//                    Toast.makeText(mContext, province + "------" + city, Toast.LENGTH_SHORT).show();
                    textView.setText("省：" + province + "\n" + "市：" + city + "\n" + "区号：" + code+"\n"+"通信商："+company);
                    break;
            }
            super.handleMessage(msg);

        }
    };

}


