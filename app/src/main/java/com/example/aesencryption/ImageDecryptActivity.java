package com.example.aesencryption;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import Utils.MyEncrypter;

public class ImageDecryptActivity extends AppCompatActivity {

    private static final String FILE_NAME_ENCRYPT = "felix_encrypted";
    private static final String FILE_NAME_DECRYPT = "felix_decrypt.png";
    ImageView imageView;
    Button mChooseBtn, btnpick;
    Button btnimgdec;
    String AES = "AES";
    File myDir;

    String my_key = "NV9VRBum0kWZfDFu"; //16 character = 128 bit
    String my_spec_key = "XQqhySth7hqEcGPc";
    byte[] key, iv;
    TextView txt_pathShow;
    Intent myFileIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_decrypt);

        btnimgdec = (Button) findViewById(R.id.btnimgdec);
        mChooseBtn = findViewById(R.id.choose_image_btn);
        imageView  = findViewById(R.id.image_view);

        txt_pathShow = (TextView) findViewById(R.id.txt_path);
        btnpick = (Button) findViewById(R.id.btnpick);

        //initial path
        myDir = new File(Environment.getExternalStorageDirectory().toString() + "/saved_images");
        Dexter.withActivity(this)
                .withPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                })
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        btnimgdec.setEnabled(true);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        Toast.makeText(ImageDecryptActivity.this, "You must enable permission", Toast.LENGTH_SHORT).show();

                    }
                }).check();

        btnimgdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    File outputFileDec = new File(myDir, FILE_NAME_DECRYPT);
                    File encFile = new File(myDir, FILE_NAME_ENCRYPT);
                    try {
                        MyEncrypter.decryptToFile(my_key, my_spec_key, new FileInputStream(encFile), new FileOutputStream(outputFileDec));

                        //set image view
                        imageView.setImageURI(Uri.fromFile(outputFileDec));

                        //delete file after decrypt
                        outputFileDec.delete();
                        Toast.makeText(ImageDecryptActivity.this, "Image Decrypt", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    }
                }
        });

        btnpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);

            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {

                    String path = data.getData().getPath();
                    txt_pathShow.setText(path);
                }
                break;
        }
    }
}
