package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginScreen extends AppCompatActivity {

    private TextInputLayout usernameTextBox;
    private TextInputLayout passwordTextBox;
    private String username;
    private String password;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private TextView forgetpassbtn;
    private TextView forgetusernamebtn;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        usernameTextBox = findViewById(R.id.username);
        passwordTextBox = findViewById(R.id.password);

        forgetpassbtn = findViewById(R.id.forgotpass);
        forgetpassbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        forgetusernamebtn = findViewById(R.id.forgotid);
        forgetusernamebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ForgetUsername.class);
                startActivity(intent);
            }
        });
    }

    public void login(View v) {

        if(!validateUsername() | !validatePassword()) {

            return;
        }

        new SignInAsync(this, usernameTextBox, passwordTextBox).execute(username, password);
    }

    private boolean validateUsername() {

        username = usernameTextBox.getEditText().getText().toString().trim();
        username = username.toLowerCase();

        boolean result = Validation.validateEmpty(username, usernameTextBox);

        //if not empty
        if (result) {

            usernameTextBox.setError(null);
        }

        return result;
    }

    private boolean validatePassword() {

        password = passwordTextBox.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(password, passwordTextBox);

        //if not empty
        if (result) {

            passwordTextBox.setError(null);
        }

        return result;
    }

    public void fingerprintLogin(View v) {

        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        generateKey();

        if (cipherInit()) {

            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerprintHandler helper = new FingerprintHandler(this);
            helper.startAuth(fingerprintManager, cryptoObject);
        }
    }

    private void generateKey() {

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {

            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cipherInit() {

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                                        + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {

            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        }
        catch (KeyPermanentlyInvalidatedException e) {

            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException
               | InvalidKeyException e) {

            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("DigiBank Alert");
        builder.setMessage("Do you want to exit the application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                System.exit(0);
            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog quitDialog = builder.create();
        quitDialog.show();
    }
}
