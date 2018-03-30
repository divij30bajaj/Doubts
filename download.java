package com.bajaj.divij.chatbox;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
public class download extends AppCompatActivity {
    Button button;
    ImageView img;
String url = "https://192.168.43.60/image.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        button = (Button) findViewById(R.id.download);
        img = (ImageView) findViewById(R.id.img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Downloadtask downloadtask = new Downloadtask();
                 downloadtask.execute(url);
            }
        });
    }
    class Downloadtask extends AsyncTask<String,Integer,String>
    {
        ProgressDialog progressDialog = new ProgressDialog(download.this);
        TextView t = (TextView) findViewById(R.id.Error);
        String A = "Error A";
        String B = "Error B";
        @Override
        protected String doInBackground(String... params) {


            int file_length=0;
              String path = params[0];
            try {
                URL url = new URL(path);

                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();
                File new_folder = new File("Internal storage/DCIM/Camera/album");
                if(new_folder.equals(null)){
                    throw new RuntimeException("error message");
                }
                if(!new_folder.exists()){
                    new_folder.mkdir();
                }
                File file = new File(new_folder, "image.jpg");
                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total=0;
                int count=0;
                OutputStream outputStream = new FileOutputStream(file);
                while((count=inputStream.read(data))!=-1){
                    total+=count;
                    outputStream.write(data,0,count);
                    int progress = (int) total*100/file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();



            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download Complete!";
        }

        private void displayExceptionMessage(String msg) {
            t.setText(msg);
        }

        @Override
        protected void onPreExecute() {

            progressDialog.setTitle("Downloading...");
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String  result)
        {
            progressDialog.hide();
            Toast.makeText( getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            String path = "Internal storage/DCIM/Camera/image.jpg";
            img.setImageDrawable(Drawable.createFromPath(path));

        }
    }
}
