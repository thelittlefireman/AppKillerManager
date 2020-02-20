package com.thelittlefireman.appkillermanager.managers;

import androidx.annotation.NonNull;

import com.thelittlefireman.appkillermanager.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseWrapper {

    private ResponseBody responsebody;
    private okhttp3.Response response;

    public ResponseWrapper(okhttp3.Response response) {
        this.response = response;
        if (response != null){
            responsebody = response.body();
        }
    }

    public Response getResponse() {
        return response;
    }


    public String getResponseBodyAsString() throws IOException {
        String res = new String("");
        if (response == null){
            return res;
        }
        //ResponseBody body = response.body();//.byteStream();
        if ( responsebody != null) {
            return responsebody.string();
        }else{
            return res;
        }
    }

    public InputStream getResponseBodyAsStream() {
        InputStream res = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        if (response == null){
            return res;
        }

        //ResponseBody body = response.body();//.byteStream();
        if ( responsebody != null) {
            return responsebody.byteStream();
        }else{
            return res;
        }
    }


    public Reader getResponseBodyAsChar() {
        Reader res = new Reader() {

            @Override
            public int read(@NonNull char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        };
        if (response == null){
            return res;
        }

        //ResponseBody body = response.body();//.byteStream();
        if ( responsebody != null) {
            return responsebody.charStream();
        }else{
            return res;
        }
    }

    public JSONObject getResponseAsJson(){
        if (response == null){
            return  null;
        }
        if ( responsebody == null){
            return null;
        }

        String body_text ;
        try {
            body_text = responsebody.string();
        } catch (IOException e) {
            LogUtils.e("", "105"+e.getMessage());
            return null;
        }

        try {
            return new JSONObject(body_text);
        } catch (JSONException e) {

            LogUtils.e("", "113"+ e.getMessage());
            return null;
        }
    }

    public boolean isSuccessful(){
        return response.isSuccessful();
    }

    public boolean isRedirect(){
        return response.isRedirect();
    }

    public String getMessage(){
        return response.message();
    }

    public int getCode(){
        return response.code();
    }
}
