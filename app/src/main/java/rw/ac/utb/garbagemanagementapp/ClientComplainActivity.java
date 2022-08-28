package rw.ac.utb.garbagemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientComplainActivity extends AppCompatActivity implements ViewComplainAdapter.OnItemClickListener {
    private RecyclerView recyclerviewComplainView;

    private ViewComplainAdapter mAdapter;
    private List<Complain> uploads;
    private ProgressBar pbViewComplain;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_complain);

        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("View " + getIntent().getStringExtra("Location") + " Complains");

        pbViewComplain = findViewById(R.id.pbViewComplain);
        recyclerviewComplainView = findViewById(R.id.recyclerviewComplainView);

        recyclerviewComplainView.setHasFixedSize(true);
        recyclerviewComplainView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();


        //creating adapter
        mAdapter = new ViewComplainAdapter(ClientComplainActivity.this, uploads);
        recyclerviewComplainView.setAdapter(mAdapter);
        reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("Complain").child(getIntent().getStringExtra("Location"));


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    pbViewComplain.setVisibility(View.GONE);

                    uploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Complain upload = postSnapshot.getValue(Complain.class);
                        uploads.add(upload);

                        mAdapter.setOnItemClickListener(ClientComplainActivity.this);
                        mAdapter.notifyDataSetChanged();
                        //adding adapter to recyclerview
                    }

                } else {
                    pbViewComplain.setVisibility(View.GONE);
                    Toast.makeText(ClientComplainActivity.this, "No Complains records is found the database", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                pbViewComplain.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onItemClick(int position) {

//        Toast.makeText(this,   uploads.get(position).getClientNames(), Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder=new AlertDialog.Builder(ClientComplainActivity.this);
        builder.setCancelable(false);
        builder.setTitle(" Client Information");
        builder.setMessage("Names: "+uploads.get(position).getClientNames() +"\n"+"Phone: "+uploads.get(position).getClientPhone()+"\n"+" date: "+uploads.get(position).getDate());
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ClientComplainActivity.this, "Thank you titus for te development for this project", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNeutralButton("COMPLETE JOB", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                HashMap<String, Object> result = new HashMap<>();
                result.put("status", "Completed by "+getIntent().getStringExtra("loggedindrivernames"));

                reference.child(uploads.get(position).getDate()).updateChildren(result);
                Toast.makeText(ClientComplainActivity.this, uploads.get(position).getClientNames()+" Complain has been completed successfuly", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}