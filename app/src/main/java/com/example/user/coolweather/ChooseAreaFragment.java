package com.example.user.coolweather;

import android.app.ProgressDialog;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coolweather.db.City;
import com.example.user.coolweather.db.County;
import com.example.user.coolweather.db.Province;
import com.example.user.coolweather.util.HttpUtil;
import com.example.user.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 遍历省市县数据
 * @author
 */
public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView titletext;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();//数据列表,用于listView展示
    private List<Province> provinceList;//省列表
    private List<City> cityList;//市列表
    private List<County> countyList;//区县列表
    private Province selectedProvince;//选中的省份
    private City selectedCity;
    private int currentLevel;//当前选中的级别


    /**
     * 创建碎片视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titletext=(TextView)view.findViewById(R.id.title_text);
        backButton=(Button)view.findViewById(R.id.back_button);
        listView=(ListView)view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    /**
     * 添加listView和button的点击事件
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }
                else if (currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }
            }
        });
        //back键
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有再到服务器查询
     */
    private void queryProvinces(){
        titletext.setText("中国");
        backButton.setVisibility(View.GONE);//不可见
        provinceList= DataSupport.findAll(Province.class);//数据库查询
        if (provinceList.size()>0){
            //更新数据
            dataList.clear();
            for (Province province:provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();//提示数据更新
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";//数据地址
            queryFromServer(address,"province");
        }

    }

    /**
     * 查询选中省的所有市数据，优先从数据库查询
     */
    private void queryCities(){
        titletext.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList=DataSupport.where("provinceId=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询选中市的所有区县数据，优先从数据库查询
     */
    private void queryCounties(){
        titletext.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityId=?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceId=selectedProvince.getProvinceCode();
            int cityId=selectedCity.getCityCode();
            String address="http://guolin.tech/api/china/"+provinceId+"/"+cityId;
            queryFromServer(address,"county");
        }
    }

    /**
     * 根据地址和类型从服务器查询省市县数据
     * @param address 地址
     * @param type 数据类型(省市县)
     */
    private void queryFromServer(String address, final String type){

        showProgressDialog();
        //向服务器请求数据,异步处理
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"服务器数据加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText=response.body().string();
                boolean result=false;
                //解析和处理数据
                if ("province".equals(type))
                    result= Utility.handleProvinceResponse(responseText);
                if ("city".equals(type))
                    result=Utility.handleCityResponse(responseText,selectedProvince.getId());
                if ("county".equals(type))
                    result=Utility.handleCountyResponse(responseText,selectedCity.getId());
                //如果返回数据成功，调用返回的数据进行UI更新，保证在主线程中
                if (result)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type))
                                queryProvinces();
                            else if ("city".equals(type))
                                queryCities();
                            else if ("county".equals(type))
                                queryCounties();
                        }
                    });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);//不可点击其他地方退出
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if (progressDialog!=null)
            progressDialog.dismiss();
    }
}
