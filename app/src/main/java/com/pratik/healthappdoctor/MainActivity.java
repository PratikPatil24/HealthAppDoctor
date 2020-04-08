package com.pratik.healthappdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextInputEditText PhoneNumberTextInput, OTPTextInput;
    MaterialButton GetOTPButton, ResendOTPButton, LoginButton, CreateAccButton;

    String phoneno, otp;

    //Firebase Firestore
    private FirebaseFirestore db;

    String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    //Firebase Auth
    private FirebaseAuth mAuth;
    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                OTPTextInput.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Firestore
        db = FirebaseFirestore.getInstance();

        PhoneNumberTextInput = findViewById(R.id.textInputEditTextPhoneNumber);
        OTPTextInput = findViewById(R.id.textInputEditTextOTP);
        GetOTPButton = findViewById(R.id.btnGetOTP);
        ResendOTPButton = findViewById(R.id.btnResendOTP);
        LoginButton = findViewById(R.id.btnLogin);
        CreateAccButton = findViewById(R.id.btnCreateAcc);

        GetOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                phoneno = "+91" + PhoneNumberTextInput.getText().toString().trim();

                //Validating Phone Number
                if(phoneno.equals(null) || phoneno.length() != 13){
                    PhoneNumberTextInput.setError("Enter Valid Phone Number!");
                    PhoneNumberTextInput.requestFocus();
                    return;
                }

                DocumentReference docRef = db.collection("doctors").document(phoneno);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("UserFetch", "DocumentSnapshot data: " + document.getData());
                                //Toast.makeText(MainActivity.this, "User Found!", Toast.LENGTH_SHORT).show();
                                    //Getting OTP
                                Snackbar.make(v, "Getting OTP...", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Getting OTP...", Toast.LENGTH_SHORT).show();
                                sendVerificationCode(phoneno);
                            } else {
                                Log.d("UserFetch", "No such document");
                                //Toast.makeText(MainActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v, "User Not Found!", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("UserFetch", "get failed with ", task.getException());
                            //Toast.makeText(MainActivity.this, "Fetch Failed!", Toast.LENGTH_SHORT).show();
                            Snackbar.make(v, "User Fetch Failed!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ResendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno = "+91" + PhoneNumberTextInput.getText().toString().trim();

                //Validating Phone Number
                if (phoneno.equals(null) || phoneno.length() != 13) {
                    PhoneNumberTextInput.setError("Enter Valid Phone Number!");
                    PhoneNumberTextInput.requestFocus();
                    return;
                }
                //Toast.makeText(MainActivity.this, "Resending OTP...", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Resending OTP...", Snackbar.LENGTH_SHORT).show();
                resendVerificationCode(phoneno, mResendToken);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = OTPTextInput.getText().toString().trim();

                if(otp.equals(null)){
                    OTPTextInput.setError("Enter OTP!");
                    OTPTextInput.requestFocus();
                    return;
                }
                verifyVerificationCode(otp);
            }
        });

        CreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //[START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        Toast.makeText(this, "Credential: " + credential.toString(), Toast.LENGTH_SHORT).show();
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(MainActivity.this, DashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                String message = "Invalid code entered...";
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getBaseContext(), DashActivity.class));
            finish();
        }
    }
}
