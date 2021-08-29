package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetPhotoActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imageView;
    private int TAKE_PICTURE_REQUEST = 1;
    private String mCurrentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);

        imageView = findViewById(R.id.imageViewPhoto);
    }

    // Щелчок кнопки
    public void onbuttonPhotoClick(View v) throws IOException {
        //getPreviewImage();
        saveFullImage();
    }

    public void onbuttonSaveClick(View v) throws IOException {
        Toast.makeText(this,
                SavePicture(imageView, createImageFile())
                , Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap thumbnailBitmap = data.getParcelableExtra("data");
                    // Какие-то действия с миниатюрой
                    imageView.setImageBitmap(thumbnailBitmap);
                }
            } else {
                // Какие-то действия с полноценным изображением,
                // сохранённым по адресу

                File file = new File(mCurrentPhotoPath);
                Uri outputFileUri = Uri.fromFile(file);
                imageView.setImageURI(outputFileUri);
            }
        }
    }

    private void getPreviewImage(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    private void saveFullImage() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = createImageFile();

        Uri outputFileUri = FileProvider.getUriForFile(
                GetPhotoActivity.this,
                "com.example.myapplication.provider", //(use your app signature + ".provider" )
                file);


        if (outputFileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            try {
                startActivityForResult(intent, TAKE_PICTURE_REQUEST);
            }
            catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("111",ex.getMessage());
            }
        }

    }

    private File createImageFile() throws IOException {
        // создание файла с уникальным именем
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File storageDir = new File(getApplicationInfo().dataDir);

        File image = File.createTempFile(imageFileName,".jpeg",storageDir);

        // сохраняем пусть для использования с интентом ACTION_VIEW
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private String SavePicture(ImageView iv, File file)
    {
        OutputStream fOut = null;

        try {
            fOut = new FileOutputStream(file);

            BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
            Bitmap bitmap;
            if(bitmapDrawable==null){
                imageView.buildDrawingCache();
                bitmap = imageView.getDrawingCache();
                imageView.buildDrawingCache(false);
            }else
            {
                bitmap = bitmapDrawable .getBitmap();
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // сохранять картинку в jpeg-формате с 85% сжатия.
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName()); // регистрация в фотоальбоме
        }
        catch (Exception e) // здесь необходим блок отслеживания реальных ошибок и исключений, общий Exception приведен в качестве примера
        {
            return e.getMessage();
        }
        return "Изображение сохранено.";
    }
}