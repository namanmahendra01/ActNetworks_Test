package com.naman.actnetworkstest.models;

import org.json.JSONException;
import org.json.JSONObject;

public class CounttryDetailModel {
    String name,code,states,img;

    public CounttryDetailModel() {

    }
    public static CounttryDetailModel fromJson(JSONObject jsonObject) {
        CounttryDetailModel b = new CounttryDetailModel();
        try {
            b.name = jsonObject.getString("name");
            b.code = jsonObject.getString("code");
            b.states = jsonObject.getString("states");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public CounttryDetailModel(String name, String code, String states,String img) {
        this.name = name;
        this.code = code;
        this.states = states;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "CountryDetailModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", states='" + states + '\'' +
                ", img='" + img + '\'' +

                '}';
    }
}


