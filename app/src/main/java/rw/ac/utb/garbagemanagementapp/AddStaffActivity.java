package rw.ac.utb.garbagemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvgobackAddstaff;
    private Button btnAddStaff;
    private EditText etNames, etNID, etPhone, etMainZone, etSubZone, etPassword, etPlatenumber, etLicenseNumber;

    private FirebaseDatabase database;
    private DatabaseReference table_Staff, table_StaffLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        // calling this activity's function to
        // use ActionBar utility methods
        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("Add New Staff User");

        tvgobackAddstaff = findViewById(R.id.tvgobackAddstaff);
        tvgobackAddstaff.setOnClickListener(this);

        btnAddStaff = findViewById(R.id.btnAddStaff);
        btnAddStaff.setOnClickListener(this);

        etNames = findViewById(R.id.etNames);
        etNID = findViewById(R.id.etNID);
        etPhone = findViewById(R.id.etPhone);
        etMainZone = findViewById(R.id.etMainZone);
        etSubZone = findViewById(R.id.etSubZone);
        etPassword = findViewById(R.id.etPassword);
        etPlatenumber = findViewById(R.id.etPlatenumber);
        etLicenseNumber = findViewById(R.id.etLicenseNumber);

        //Init Firebase
        database = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/");
        table_Staff = database.getReference("GarbageManagement").child("Staff");
        table_StaffLocation = database.getReference("GarbageManagement").child("StaffLocation");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvgobackAddstaff:
                finish();
                break;

            case R.id.btnAddStaff:
                if (etNames.getText().toString().trim().isEmpty() ||
                        etNID.getText().toString().trim().isEmpty() ||
                        etPhone.getText().toString().trim().isEmpty() ||
                        etMainZone.getText().toString().trim().isEmpty() ||
                        etSubZone.getText().toString().trim().isEmpty() ||
                        etPassword.getText().toString().trim().isEmpty() ||
                        etLicenseNumber.getText().toString().trim().isEmpty() ||
                        etPlatenumber.getText().toString().trim().isEmpty()


                ) {

                    Toast.makeText(this, "All inputs fileds are necessary,Please", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, "All Fields are required", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {

                    Staff staff = new Staff(
                            etNames.getText().toString().trim(),
                            etNID.getText().toString().trim(),
                            etPhone.getText().toString().trim(),
                            etMainZone.getText().toString().trim(),
                            etSubZone.getText().toString().trim(),
                            etPassword.getText().toString().trim(),
                            etLicenseNumber.getText().toString().trim(),
                            etPlatenumber.getText().toString().trim()
                    );


                    table_Staff.child(etPhone.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(AddStaffActivity.this, "Sorry This user already exists in our systems", Toast.LENGTH_SHORT).show();
                            } else {
                                table_Staff.child(etPhone.getText().toString().trim()).setValue(staff);
                                table_StaffLocation.child(etMainZone.getText().toString().trim()).child(etPhone.getText().toString().trim()).setValue(staff);
                                Toast.makeText(getApplicationContext(), "New Staff has been saved Successully", Toast.LENGTH_LONG).show();
                                Snackbar snackbar = Snackbar.make(view, "Inserted well", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddStaffActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                break;
        }
    }
}