package com.example.juda.chat;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Test extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    TextView file_name;

    EditText etEmail, etPW, etCheckPW, etName, etPhoneNumber;
    Button btnRegister, select_file;
    RadioButton btnSeller, btnBuyer;
    RadioGroup radioGroup;
    String[] user_type = new String[1];

    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        layout = findViewById(R.id.registerActivity);

        select_file = findViewById(R.id.select_file);
        file_name = findViewById(R.id.file_name);
        radioGroup = findViewById(R.id.radioGroup);
        RadioGroup.OnCheckedChangeListener radiogroupListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.seller){
                    user_type[0] = "판매자";
                    file_name.setVisibility(View.VISIBLE);
                    select_file.setVisibility(View.VISIBLE);
                }else if(i == R.id.buyer){
                    user_type[0] = "구매자";
                    file_name.setVisibility(View.INVISIBLE);
                    select_file.setVisibility(View.INVISIBLE);
                }
            }
        };

        radioGroup.setOnCheckedChangeListener(radiogroupListener);

        try{
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getInstance().getReference();
        }catch (Exception e){
            Log.e(TAG, "파이어베이스 연동 오류", e);
        }

        btnSeller = findViewById(R.id.seller);
        btnBuyer = findViewById(R.id.buyer);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etPW = findViewById(R.id.etPW);
        etCheckPW = findViewById(R.id.etCheckPW);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String pwd = etPW.getText().toString().trim();
                String pwdCheck = etCheckPW.getText().toString().trim();


                if(pwd.equals(pwdCheck)){

                    Log.d(TAG, "등록버튼" + email + " , " + pwd);

                    final ProgressDialog mDialog = new ProgressDialog(Test.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();


                    try{
                        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Test.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //여기까지 들어오는데 5분 정도 걸리는데 그 이유 못 찾음...

                                    mDialog.dismiss();


                                    //firebase의 realtime database에 데이터 전송 안 됨.
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserInfo2 userInfo = new UserInfo2();

                                    String name = etName.getText().toString();
                                    String email = etEmail.getText().toString();
                                    String phone = etPhoneNumber.getText().toString();
                                    String pw = etPW.getText().toString();

                                    Log.d(TAG,"user_type[0] :: " + user_type[0]);


                                    userInfo.setUid(user.getUid());
                                    Log.d(TAG, user.getUid());
                                    userInfo.setEmail(user.getEmail());
                                    Log.d(TAG, user.getEmail());
                                    userInfo.setPassword(pw);
                                    userInfo.setName(name);
                                    userInfo.setPhone(phone);

//                                if(database != null){
//                                    String sql = "insert into userAccount(user_type, name, phoneNum, email, password) values(?, ?, ?, ?, ?)";
//                                    Object[] params = {user_type[0], name, phone, email, pw};
//                                    database.execSQL(sql, params);//이런식으로 두번쨰 파라미터로 이런식으로 객체를 전달하면 sql문의 ?를 이 params에 있는 데이터를 물음표를 대체해준다.
//                                    println("데이터 추가함");
//
//                                }else {
//                                    println("데이터베이스를 먼저 오픈하시오");
//                                }

                                    mReference.child("UserAccount").child(user.getUid()).setValue(userInfo);
                                    onBackPressed();

                                    Toast.makeText(Test.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                }else{
                                    mDialog.dismiss();
                                    Toast.makeText(Test.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }catch(Exception e){
                        mDialog.dismiss();
                        Toast.makeText(Test.this, "정보를 입력하세요", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "정보 미입력");
                    }

                }else{
                    Toast.makeText(Test.this, "비밀번호가 틀렸습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}