package com.example.administrator.sendemail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;

import mail.SendMailUtil;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Uri uri;
    private String path;
    private ArrayList<File>fileArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.toAddEt);
    }


    public void senTextMail(View view) {
        SendMailUtil.send(editText.getText().toString());
    }

    public void sendFileMail(View view) {
        //获取文件URI
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent,1);

        getFiles("/storage/emulated/0/emailtest");
//        File file = new File(path);
        //动态获取文件权限操作
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
        SendMailUtil.send(fileArrayList,editText.getText().toString());
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
//                uri = data.getData();
//                Toast.makeText(this, "文件路径："+uri.getPath().toString(), Toast.LENGTH_LONG).show();
//                path =UriToPathUtil.getImageAbsolutePath(this,uri);
//                if (path==null) {
//                    path = UriToPathUtil.getRealFilePath(this,uri);
//                }
//                File file = new File(path);
//
//                //动态获取文件权限操作
//                int REQUEST_EXTERNAL_STORAGE = 1;
//                String[] PERMISSIONS_STORAGE = {
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                };
//                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//                }
//                SendMailUtil.send(file,editText.getText().toString());
//            }
//        }
//    }

    /**
     * 获取指定文件夹中的文件
     * @param path
     * @return
     */
    private void getFiles(String path) {
        fileArrayList = new ArrayList();
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            File file = allFiles[i];
            if (file.isFile()) {
                fileArrayList.add(file);
            } else if (!file.getAbsolutePath().contains(".thumnail")) {
                getFiles(file.getAbsolutePath());
            }
        }
    }

}
