package com.example.aesencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TextDecryptActivity extends AppCompatActivity {
    EditText InputText2, InputPassword2;
    TextView OutputText2;
    Button btndec;
    String outputString2;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_decrypt);
        InputText2 = (EditText) findViewById(R.id.InputText2);
        InputPassword2 = (EditText) findViewById(R.id.password2);
        OutputText2 = (TextView) findViewById(R.id.OutputText2);

        btndec = (Button) findViewById(R.id.btndec);

        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InputText2.getText().toString().matches("") || InputPassword2.getText().toString().matches("") ) {

                    Toast.makeText(TextDecryptActivity.this, "Enter Text and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    try {
                        outputString2 = decrypt(InputText2.getText().toString(), InputPassword2.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    OutputText2.setText(outputString2);
                    Toast.makeText(TextDecryptActivity.this, "Text Decrypt", Toast.LENGTH_SHORT).show();
                }
            }
            });

    }


            public String decrypt(String outputString, String password) throws Exception {
            SecretKeySpec key = generateKey(password);
            Cipher c = Cipher.getInstance(AES);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeValue = Base64.decode(outputString, Base64.DEFAULT);
            byte[] decValue = c.doFinal(decodeValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;

            }

            public SecretKeySpec generateKey(String password) throws Exception {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            return secretKeySpec;
            }

    }