package com.alice.teampang.retrofit;

import android.content.Context;
import android.util.Log;

import com.alice.teampang.model.retrofit.TeampangResponseBody;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 인터페이스 통신 후 결과값 처리 클래스
 */

public abstract class CustomResponse<T> implements CustomResponseListener<T> {

        private Context context;

//    private ContextCompat contextCompat;
//    boolean shouldShowErrorDialog;

    public CustomResponse(Context context) {
        this.context = context;
//        this.shouldShowErrorDialog = true;
    }

    public CustomResponse(Context context, boolean shouldShowErrorDialog) {
        this.context = context;
//        this.shouldShowErrorDialog = shouldShowErrorDialog;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
//        if (shouldShowErrorDialog) {
//        if (context != null && context instanceof BaseFrag && !((BaseFrag) context).isFinishing()) {
//            ((BaseFrag) context).progressHide();
//        }
        Log.e("teampang", "interface 연동실패 :: ", t);
        t.getStackTrace();

        if (t instanceof IOException) {
            //no internet connection, or no host
//            ((BaseFrag) context).errorToast(context.getString(R.string.error_timeout));
            return;
        }
        if (context != null) {
//            ((BaseFrag) context).errorToast(context.getString(R.string.error_common));
        }
    }
//    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
//        if (context != null && context instanceof BaseFrag && !((BaseFrag) context).isFinishing()) {
//            ((BaseFrag) context).progressHide();
//        }

        if (response.body() instanceof TeampangResponseBody) {
            if (((TeampangResponseBody) response.body()).getCode().equals("500")) { //코드가 500이라면 실패, 아니면 성공
                onCustomFailed(call, response);
            } else {
                onCustomSuccess(call, response);
            }
            return;
        }

        if (response.code() == 500) { //코드가 500이라면 실패, 아니면 성공
            onCustomFailed(call, response);
        } else {
            onCustomSuccess(call, response);
        }
    }

    @Override
    public void onCustomFailed(final Call<T> call, final Response<T> response) {
    }

    @Override
    public void onCustomSuccess(Call<T> call, Response<T> response) {

    }
}

