package com.thelittlefireman.appkillermanager.utils;

import com.thelittlefireman.appkillermanager.managers.ResponseWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Instructions {
    String name = "";
    JSONArray manufacturer= new JSONArray();
    int award=0;
    int position=0;
    String explanation="";
    String user_solution="";
    String developer_solution="";

    boolean hasException = false;
    Exception exception  = null;


    public Instructions(ResponseWrapper responseWrapper) {
        String body = null;
        try {
            body = responseWrapper.getResponseBodyAsString();
            LogUtils.i("instructions", "27");
            JSONObject json = new JSONObject(body);

            if ( ! json.isNull("name")){
                name            = json.getString("name");
            }
            if ( ! json.isNull("manufacturer")){
                manufacturer    = json.getJSONArray("manufacturer");
            }
            if ( ! json.isNull("award")){
                award           = json.getInt("award");
            }
            if ( ! json.isNull("position")){
                position        = json.getInt("position");
            }
            if ( ! json.isNull("explanation")){
                explanation     = json.getString("explanation");
            }
            if ( ! json.isNull("user_solution")){
                user_solution   = json.getString("user_solution");
            }
            if ( ! json.isNull("developer_solution")){
                developer_solution = json.getString("developer_solution");
            }


            hasException       = false;
        } catch (IOException e) {
            e.printStackTrace();
            hasException = true;
            exception = e;
        } catch (JSONException e) {
            e.printStackTrace();
            hasException = true;
            exception = e;
        }
    }

    public String getName() {
        return name;
    }

    public JSONArray getManufacturer() {
        return manufacturer;
    }

    public int getAward() {
        return award;
    }

    public int getPosition() {
        return position;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUser_solution() {
        return user_solution;
    }

    public String getDeveloper_solution() {
        return developer_solution;
    }

    public boolean isHasException() {
        return hasException;
    }

    public Exception getException() {
        return exception;
    }
}
