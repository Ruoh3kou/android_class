package com.cbj.storage123022016027;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class weather extends AppCompatActivity {

    private Button btn_beijing;
    private Button btn_shanghai;
    private Button btn_fuzhou;

    private ImageView img_weather;
    private TextView t_windpower;
    private TextView t_temperature;
    private TextView t_pm;
    private TextView t_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // 绑定控件
        btn_beijing = (Button) findViewById(R.id.btn_beijing);
        btn_shanghai = (Button) findViewById(R.id.btn_shanghai);
        btn_fuzhou = (Button) findViewById(R.id.btn_fuzhou);
        img_weather = (ImageView) findViewById(R.id.img_weather);
        t_windpower = (TextView) findViewById(R.id.t_windpower);
        t_temperature = (TextView) findViewById(R.id.t_temperature);
        t_pm = (TextView) findViewById(R.id.t_pm);
        t_location = (TextView) findViewById(R.id.t_location);

        btn_beijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject weatherObj = getWeather("北京");
                    if (weatherObj != null) {
                        setWeather(weatherObj.optString("temp"),
                                weatherObj.optString("weather"),
                                weatherObj.optString("name"),
                                weatherObj.optString("pm"),
                                weatherObj.optString("wind"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btn_shanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject weatherObj = getWeather("上海");
                    if (weatherObj != null) {
                        setWeather(weatherObj.optString("temp"),
                                weatherObj.optString("weather"),
                                weatherObj.optString("name"),
                                weatherObj.optString("pm"),
                                weatherObj.optString("wind"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btn_fuzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject weatherObj = getWeather("福州");
                    if (weatherObj != null) {
                        setWeather(weatherObj.optString("temp"),
                                weatherObj.optString("weather"),
                                weatherObj.optString("name"),
                                weatherObj.optString("pm"),
                                weatherObj.optString("wind"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // 获取天气信息
    private JSONObject getWeather(String location) throws IOException, JSONException {
        // 读取天气json文件
        InputStream is = this.getResources().openRawResource(R.raw.weather);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        String json = new String(buffer, "utf-8");

        // 解析JSON
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if (jsonObj.optString("name").equals(location))
                return jsonObj;
        }
        Toast.makeText(this, "No this place.", Toast.LENGTH_SHORT).show();
        return null;
    }

    // 设置控件信息
    private void setWeather(String s_temp, String s_img_weather, String s_name, String s_pm, String s_wind) {
        t_temperature.setText(s_temp);
        t_pm.setText(s_pm);
        t_windpower.setText(s_wind);
        t_location.setText(s_name);
        // 图片
        switch (s_img_weather) {
            case "晴转多云":
                img_weather.setImageResource(R.drawable.cloud_sun);
                break;
            case "晴":
                img_weather.setImageResource(R.drawable.sun);
                break;
            case "多云":
                img_weather.setImageResource(R.drawable.clouds);
                break;
            default:
                break;
        }
    }
}
