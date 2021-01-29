package com.example.aesencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TextEncryptActivity extends AppCompatActivity {

    EditText InputText, InputPassword;
    TextView OutputText;
    Button btnenc;
    Button btncopy;
    String outputString;
    String AES = "AES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_encrypt);


        InputText = (EditText) findViewById(R.id.InputText);
        InputPassword = (EditText) findViewById(R.id.password);
        OutputText = (TextView) findViewById(R.id.OutputText);

        btnenc = (Button) findViewById(R.id.btnenc);
        btncopy = (Button) findViewById(R.id.btncopy);

        btncopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", OutputText.getText().toString());
                clipboard.setPrimaryClip(clip);


                Toast.makeText(TextEncryptActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        btnenc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(InputText.getText().toString().matches("") || InputPassword.getText().toString().matches("") ){

                    Toast.makeText(TextEncryptActivity.this, "Enter Text and Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        outputString = encrypt(InputText.getText().toString(), InputPassword.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    OutputText.setText(outputString);
                    Toast.makeText(TextEncryptActivity.this, "Text Encrypt", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    public String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes() );
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT );
        return encryptedValue;
    }

    public SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;

    }
}
