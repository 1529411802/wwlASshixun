package cn.itcast.weather;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvPm;
    private ImageView ivIcon;
    private String temp,weather,name,pm,wind;
    private Map<String,String> map;
    private List<Map<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try {
            InputStream is = this.getResources().openRawResource(R.raw.weather1);
            List<WeatherInfo> weatherInfos = WeatherService.getInfosFromXML(is);
            list=new ArrayList<Map<String, String>>();
            for (WeatherInfo info : weatherInfos) {
                map =new HashMap<String, String>();
                map.put("temp",info.getTemp());
                map.put("weather",info.getWeather());
                map.put("name",info.getName());
                map.put("pm",info.getPm());
                map.put("wind",info.getWind());
                list.add(map);

            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"解析信息失败",Toast.LENGTH_SHORT).show();
        }
        getMap(1,R.drawable.qing);
    }
    private void initView() {
        tvCity=(TextView) findViewById(R.id.tv_city);
        tvWeather=(TextView) findViewById(R.id.tv_weather);
        tvTemp=(TextView)findViewById(R.id.tv_temp);
        tvWind=(TextView) findViewById(R.id.tv_wind);
        tvPm=(TextView) findViewById(R.id.tv_pm);
        ivIcon=(ImageView) findViewById(R.id.iv_icon);
        findViewById(R.id.btn_cs).setOnClickListener(this);
        findViewById(R.id.btn_ld).setOnClickListener(this);
        findViewById(R.id.btn_xt).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cs:
                getMap(0, R.drawable.dyzq);
                break;
            case R.id.btn_ld:
                getMap(1,R.drawable.qing);
                break;
            case R.id.btn_xt:
                getMap(2,R.drawable.dy);
                break;
        }
    }

    private void getMap(int number, int iconNunber) {
        Map<String, String> cityMap = list.get(number);
         temp = cityMap.get("temp");
         weather = cityMap.get("weather");
         name = cityMap.get("name");
         pm = cityMap.get("pm");
         wind = cityMap.get("wind");
        tvCity.setText(name);
        tvWeather.setText(weather);
        tvTemp.setText(""+temp);
        tvWind.setText("风力:"+wind);
        tvPm.setText("pm:"+pm);
        ivIcon.setImageResource(iconNunber);
    }
}