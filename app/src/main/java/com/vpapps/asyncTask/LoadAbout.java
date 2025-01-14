package com.vpapps.asyncTask;

import android.os.AsyncTask;

import com.vpapps.interfaces.AboutListener;
import com.vpapps.items.ItemAbout;
import com.vpapps.utils.Constant;
import com.vpapps.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoadAbout extends AsyncTask<String, String, String> {

    private AboutListener aboutListener;

    public LoadAbout(AboutListener aboutListener) {
        this.aboutListener = aboutListener;
    }

    @Override
    protected void onPreExecute() {
        aboutListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String json = JsonUtils.okhttpGET(url);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.TAG_ROOT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                String appname = c.getString("app_name");
                String applogo = c.getString("app_logo");
                String desc = c.getString("app_description");
                String appversion = c.getString("app_version");
                String appauthor = c.getString("app_author");
                String appcontact = c.getString("app_contact");
                String email = c.getString("app_email");
                String website = c.getString("app_website");
                String privacy = c.getString("app_privacy_policy");
                String developedby = c.getString("app_developed_by");

                Constant.ad_banner_id = c.getString("banner_ad_id");
                Constant.ad_inter_id = c.getString("interstital_ad_id");
                Constant.isBannerAd = Boolean.parseBoolean(c.getString("banner_ad"));
                Constant.isInterAd = Boolean.parseBoolean(c.getString("interstital_ad"));
                Constant.ad_publisher_id = c.getString("publisher_id");
                Constant.adShow = Integer.parseInt(c.getString("interstital_ad_click"));

                Constant.itemAbout = new ItemAbout(appname, applogo, desc, appversion, appauthor, appcontact, email, website, privacy, developedby);
            }
            return "1";
        } catch (Exception ee) {
            ee.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        aboutListener.onEnd(s);
        super.onPostExecute(s);
    }
}
