package com.example.jung_kiyun.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsParse extends AppCompatActivity {
    private String url = "http://www.yonhapnews.co.kr";
    private TextView htmldoc;
    private String htmlContent="";

    int cnt=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_news);

        htmldoc = (TextView)findViewById(R.id.textView);
        htmldoc.setMovementMethod(new ScrollingMovementMethod());

        Button titleButton = (Button)findViewById(R.id.button);
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("result: ", "doc= "+ cnt + "번째 파싱 : " +  url);
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(url).get();

                Log.d("doc: ", doc.toString());

                //테스트1
                Elements titles= doc.select("div.news-con h1.tit-news");

                Log.d("titles: ", titles.toString());

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    System.out.println("title: " + e.text());
                    htmlContent += e.text().trim() + "\n";
                }

                //테스트2
                titles= doc.select("div.news-con h2.tit-news");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    System.out.println("title: " + e.text());
                    htmlContent += e.text().trim() + "\n";
                }

                //테스트3
                titles= doc.select("li.section02 div.con h2.news-tl");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    System.out.println("title: " + e.text());
                    htmlContent += e.text().trim() + "\n";
                }
                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                Log.d("result: ", "에러가 발생하였습니다");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            htmldoc.setText(htmlContent);
        }
    }
}
