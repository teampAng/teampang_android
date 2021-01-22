package com.cau.teampang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final  String TAG = getClass().getSimpleName();

    // server의 url을 적어준다 => 달라질 때 유념해야한다. // 근데 이 주소 일회용인가? 그걸 모르겠네
    private final String BASE_URL = "https://fa40710d4273.ngrok.io";  // 수정해야함
    private MyAPI mMyAPI;

    private TextView mListTv;

    private Button mGetButton;
    private Button mPostButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListTv = findViewById(R.id.result1);

        mGetButton = findViewById(R.id.button1);
        mGetButton.setOnClickListener(this);
        mPostButton = findViewById(R.id.button2);
        mPostButton.setOnClickListener(this);
//        mDeleteButton = findViewById(R.id.button4);
//        mDeleteButton.setOnClickListener(this);

        initMyAPI(BASE_URL);
    }
    private void initMyAPI(String baseUrl){

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }

    @Override
    public void onClick(View v) {
        if( v == mGetButton){
            Log.d(TAG,"GET");
            Call<List<LoginData>> getCall = mMyAPI.get_logins();
            getCall.enqueue(new Callback<List<LoginData>>() {
                @Override
                public void onResponse(Call<List<LoginData>> call, Response<List<LoginData>> response) {
                    if( response.isSuccessful()){
                        List<LoginData> mList = response.body();
                        String result ="";
                        for( LoginData item : mList){
                            result += "title : " + item.getUsername() + " text: " + item.getPassword() + "\n";
                        }
                        mListTv.setText(result);
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<LoginData>> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }else if(v == mPostButton){
            Log.d(TAG,"POST");


            LoginData item = new LoginData();
            item.setUsername("아이디"); //수정 요망!!!!!!!!!!!!!!!!!!!!
            item.setPassword("비밀번호");
            Call<LoginData> postCall = mMyAPI.post_logins(item);
            postCall.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"등록 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.d(TAG,response.errorBody().toString());
                        Log.d(TAG,call.request().body().toString());
                    }
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }
    }


}