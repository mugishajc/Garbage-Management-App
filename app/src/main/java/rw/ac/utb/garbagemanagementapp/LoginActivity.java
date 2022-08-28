package rw.ac.utb.garbagemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView LoginGobBack, textuserClicked;
    private Button btnSignIn;
    private EditText etPhone, etPassword;
    private String role,stafflocation;
    private FirebaseDatabase database;
    private DatabaseReference table_Admin,table_Staff,table_Client;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Init Firebase
        database = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/");
        table_Admin = database.getReference("GarbageManagement").child("Admin");
        table_Staff = database.getReference("GarbageManagement").child("Staff");
        table_Client = database.getReference("GarbageManagement").child("Client");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCanceledOnTouchOutside(false);

        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getSupportActionBar().hide();
        btnSignIn = findViewById(R.id.btnSignIn);
        LoginGobBack = findViewById(R.id.LoginGobBack);
        LoginGobBack.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        textuserClicked = findViewById(R.id.textuserClicked);
        Intent UserType = getIntent();
        textuserClicked.setText(UserType.getStringExtra("user"));
        role = UserType.getStringExtra("user");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.LoginGobBack:
                startActivity(new Intent(LoginActivity.this, categoryBoardActivity.class));
                this.finish();
                break;
            case R.id.btnSignIn:

                String telephone = etPhone.getText().toString().trim();
                String pswd = etPassword.getText().toString().trim();
                if (telephone.isEmpty() || pswd.isEmpty()) {
                    Toast.makeText(this, "Phone or password is missing,Please", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, "All Fields are required", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    progressDialog.show();

                    if (role.equals("Admin")) {
                        table_Admin.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(etPhone.getText().toString().trim()).exists()) {
                                    progressDialog.dismiss();

                                     String PasswordDB=dataSnapshot.child(etPhone.getText().toString().trim()).child("Password").getValue()+"";
                                    if (PasswordDB.equals(etPassword.getText().toString().trim())){
                                        Intent intent=new Intent(LoginActivity.this, MainScreenActivity.class);
                                        intent.putExtra("role",textuserClicked.getText().toString());
                                        intent.putExtra("LoggedInNames",dataSnapshot.child(etPhone.getText().toString().trim()).child("names").getValue()+"");
                                        intent.putExtra("LoggedInLocation",dataSnapshot.child(etPhone.getText().toString().trim()).child("mainLocation").getValue()+"");
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }



                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Error " + databaseError, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if (role.equals("Staff")) {
                    table_Staff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(etPhone.getText().toString().trim()).exists()) {
                                    progressDialog.dismiss();

                                    String PasswordDB=dataSnapshot.child(etPhone.getText().toString().trim()).child("password").getValue()+"";
                                    if (PasswordDB.equals(etPassword.getText().toString().trim())){

                                      table_Staff.child(etPhone.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {

                                           if (snapshot.exists()){

                                               stafflocation=snapshot.child("mainLocation").getValue()+"";
                                              // Toast.makeText(LoginActivity.this, "uyu mu staff akorera "+stafflocation, Toast.LENGTH_LONG).show();

                                               Intent intent=new Intent(LoginActivity.this, MainScreenActivity.class);
                                               intent.putExtra("role",textuserClicked.getText().toString());
                                               intent.putExtra("staffLocation",stafflocation);
                                               intent.putExtra("LoggedInNames",dataSnapshot.child(etPhone.getText().toString().trim()).child("names").getValue()+"");
                                               intent.putExtra("LoggedInLocation",dataSnapshot.child(etPhone.getText().toString().trim()).child("mainLocation").getValue()+"");

                                               intent.putExtra("LoggedInPlateNumber",dataSnapshot.child(etPhone.getText().toString().trim()).child("plateNumber").getValue()+"");

                                               intent.putExtra("LoggedInLicenseNumber",dataSnapshot.child(etPhone.getText().toString().trim()).child("licenseNumber").getValue()+"");


                                               startActivity(intent);
                                               finish();
                                           }else {
                                               Toast.makeText(LoginActivity.this, "Sorry System Error occured  ", Toast.LENGTH_SHORT).show();
                                           }

                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {

                                          }
                                      });


                                    }else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }



                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Error " + databaseError, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if (role.equals("Client"))
                    {
                        table_Client.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(etPhone.getText().toString().trim()).exists()) {
                                    progressDialog.dismiss();

                                    String PasswordDB=dataSnapshot.child(etPhone.getText().toString().trim()).child("password").getValue()+"";
                                    if (PasswordDB.equals(etPassword.getText().toString().trim())){
                                        Intent intent=new Intent(LoginActivity.this, MainScreenActivity.class);
                                        intent.putExtra("role",textuserClicked.getText().toString());
                                        intent.putExtra("LoggedInNames",dataSnapshot.child(etPhone.getText().toString().trim()).child("names").getValue()+"");
                                        intent.putExtra("LoggedInLocation",dataSnapshot.child(etPhone.getText().toString().trim()).child("mainLocation").getValue()+"");
                                        intent.putExtra("LoggedInPhone",dataSnapshot.child(etPhone.getText().toString().trim()).child("phone").getValue()+"");

                                        startActivity(intent);
                                        finish();

                                    }else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }



                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Error " + databaseError, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
                break;
        }

    }


}