package com.example.asyn_download;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private  static  int RESULT_CODE = 100;

   /// public  static final String imageUrl = "https://www.tutorialspoint.com/images/tp-logo-diamond.png";

    String imageName = "demoimage.png";
    Button button;
    ProgressDialog p;

  public  static final String imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazy-loading-fa843.appspot.com/o/What%20is%20Physics_.mp4?alt=media&token=ba2cca42-4d6d-4cd9-9e7e-d017995c2ece";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Downloader downloader  = new Downloader();
                downloader.execute();

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_CODE);

            }else {
                Toast.makeText(this, "pls enable it", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class Downloader extends AsyncTask <String, String, String> {

        Context context;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(SecondActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();



        }


        @Override
        protected String doInBackground(String... strings) {


            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));

                request.setTitle(imageName);
                request.setDescription("Downloading ");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "outFileName");
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);

            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            p.hide();
        }

    }


}