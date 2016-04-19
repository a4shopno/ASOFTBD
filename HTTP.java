package com.shojib.asoftbd.adust;

import android.util.Log;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.net.URI;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;

import org.apache.http.params.HttpConnectionParams;

import org.apache.http.params.HttpParams;

import org.apache.http.util.EntityUtils;


/**
 * Created by Shopno_Shumo on 21-03-16.
 */

public class HTTP {


    public static String executeHttpGet(String url) throws Throwable {


        String page = "";

        Throwable th;

        BufferedReader in = null;

        try {

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient client = new DefaultHttpClient(httpParameters);

            HttpGet request = new HttpGet();

            String s = url;

            URI uri = new URI(s.replace(" ", "%20"));

            Log.d("hai", "cadeaux url" + s.replace(" ", "%20"));

            request.setURI(uri);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(client.execute(request).getEntity().getContent()));

            try {

                StringBuffer sb = new StringBuffer("");

                String line = "";

                String NL = System.getProperty("line.separator");

                while (true) {

                    line = in2.readLine();

                    if (line == null) {

                        break;

                    }

                    sb.append(new StringBuilder(String.valueOf(line)).append(NL).toString());

                }

                in2.close();

                page = sb.toString();

                System.out.println("page=" + page);

                if (in2 != null) {

                    try {

                        in2.close();

                        in = in2;

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                    System.out.println("page=" + page);

                    return page;

                }

                in = in2;

            } catch (Exception e2) {

                in = in2;

            } catch (Throwable th2) {

                th = th2;

                in = in2;

            }

        } catch (Exception e3) {


            try {

                page = "";

                if (in != null) {

                    try {

                        in.close();

                    } catch (IOException e4) {

                        e4.printStackTrace();

                    }

                }

                System.out.println("page=" + page);

                return page;

            } catch (Throwable th3) {

                th = th3;

                if (in != null) {

                    try {

                        in.close();

                    } catch (IOException e42) {

                        e42.printStackTrace();

                    }

                }

                throw th;

            }

        }

        System.out.println("page=" + page);

        return page;

    }


    public static String initiateRequest(String url, String postdata) {

        try {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            String str = "";

            HttpPost post = new HttpPost(url);

            post.setEntity(new StringEntity(postdata, "UTF-8"));

            post.setHeader("Accept", "application/json");

            post.setHeader("Content-type", "application/x-www-form-urlencoded");

            String responseText = EntityUtils.toString(new DefaultHttpClient(httpParameters).execute(post).getEntity());

            System.out.println("haihttpjfhgkhfhjhgkjhsfdhgdshkghfdsjgh");

            return responseText;

        } catch (Exception e) {

            return "malfunction";

        }

    }
}

