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

public class AddClientActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvgobackAddClient;
    private EditText etPasswordClient,etStreetCodeClient,etSubZoneClient,etMainZoneClient,etPhoneClient,etNIDClient,etNamesClient;
    private FirebaseDatabase database;
    private DatabaseReference table_Client,table_ClientLocation,tableaddclntAdm;
    private Button btnAddClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);


        // calling this activity's function to
        // use ActionBar utility methods
        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("Client Registration");

        //Init Firebase
        database = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/");
        table_Client = database.getReference("GarbageManagement").child("Client");
        tableaddclntAdm = database.getReference("GarbageManagement").child("AllClients");
        table_ClientLocation=database.getReference("GarbageManagement").child("ClientLocation");

        btnAddClient=findViewById(R.id.btnAddClient);
        btnAddClient.setOnClickListener(this);

        etPasswordClient=findViewById(R.id.etPasswordClient);
        etStreetCodeClient=findViewById(R.id.etStreetCodeClient);
        etSubZoneClient=findViewById(R.id.etSubZoneClient);
        etMainZoneClient=findViewById(R.id.etMainZoneClient);
        etPhoneClient=findViewById(R.id.etPhoneClient);
        etNIDClient=findViewById(R.id.etNIDClient);
        etNamesClient=findViewById(R.id.etNamesClient);

        tvgobackAddClient=findViewById(R.id.tvgobackAddClient);
        tvgobackAddClient.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvgobackAddClient:
                finish();
                break;

            case R.id.btnAddClient:

                if (etNamesClient.getText().toString().trim().isEmpty() ||
                        etNIDClient.getText().toString().trim().isEmpty() ||
                        etPhoneClient.getText().toString().trim().isEmpty() ||
                        etMainZoneClient.getText().toString().trim().isEmpty() ||
                        etSubZoneClient.getText().toString().trim().isEmpty() ||
                        etStreetCodeClient.getText().toString().trim().isEmpty() ||
                        etPasswordClient.getText().toString().trim().isEmpty()) {

                    Toast.makeText(this, "All inputs fileds are necessary,Please", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, "All Fields are required", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else {

                    Client client = new Client(
                            etNamesClient.getText().toString().trim(),
                            etNIDClient.getText().toString().trim(),
                            etPhoneClient.getText().toString().trim(),
                            etMainZoneClient.getText().toString().trim(),
                            etSubZoneClient.getText().toString().trim(),
                            etStreetCodeClient.getText().toString().trim(),
                            etPasswordClient.getText().toString().trim());


                    table_Client.child(etPhoneClient.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(AddClientActivity.this, "Sorry This Client already exists in our database", Toast.LENGTH_SHORT).show();
                            } else {
//                                tableaddclntAdm.setValue(client);
                                table_Client.child(etPhoneClient.getText().toString().trim()).setValue(client);
                                table_ClientLocation.child(etMainZoneClient.getText().toString().trim()).child(etPhoneClient.getText().toString().trim()).setValue(client);
                                Toast.makeText(getApplicationContext(), "New Client / Customer has been saved Successully", Toast.LENGTH_LONG).show();
                                Snackbar snackbar = Snackbar.make(view, "Inserted well", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddClientActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                break;
        }
    }
}