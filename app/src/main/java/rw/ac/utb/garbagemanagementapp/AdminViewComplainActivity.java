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
import java.util.List;

public class AdminViewComplainActivity extends AppCompatActivity implements ViewComplainAdapter.OnItemClickListener {

    private RecyclerView recyclerviewViewAdminAllComplains;
    private ViewComplainAdapter mAdapter;
    private List<Complain> uploads;
    private ProgressBar pbViewComplain;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_complain);

        ActionBar actionBar = getSupportActionBar();


        pbViewComplain=findViewById(R.id.pbViewComplainadmin);

        // providing title for the ActionBar
        actionBar.setTitle("All Complains");

        recyclerviewViewAdminAllComplains=findViewById(R.id.recyclerviewViewAdminAllComplains);
        recyclerviewViewAdminAllComplains.setHasFixedSize(true);
        recyclerviewViewAdminAllComplains.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();

        //creating adapter
        mAdapter = new ViewComplainAdapter(AdminViewComplainActivity.this, uploads);
        recyclerviewViewAdminAllComplains.setAdapter(mAdapter);
        reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("AllComplains");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    pbViewComplain.setVisibility(View.GONE);

                    uploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Complain upload = postSnapshot.getValue(Complain.class);
                        uploads.add(upload);

                        mAdapter.setOnItemClickListener(AdminViewComplainActivity.this);
                        mAdapter.notifyDataSetChanged();
                        //adding adapter to recyclerview
                    }

                } else {
                    pbViewComplain.setVisibility(View.GONE);
                    Toast.makeText(AdminViewComplainActivity.this, "No Complains records is found the database", Toast.LENGTH_LONG).show();

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

//        Toast.makeText(this, "Clicked "+uploads.get(position).getClientPhone(), Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder=new AlertDialog.Builder(AdminViewComplainActivity.this);
        builder.setCancelable(false);
        builder.setTitle(" Client Information");
        builder.setMessage("Names: "+uploads.get(position).getClientNames() +"\n"+"Phone: "+uploads.get(position).getClientPhone()+"\n"+" date: "+uploads.get(position).getDate());
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminViewComplainActivity.this, "Thank you titus for te development for this project", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Delete Complain", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reference.child(uploads.get(position).getDate()).removeValue();
//                Toast.makeText(AdminViewComplainActivity.this, "clicked \n"+uploads.get(position).getDate(), Toast.LENGTH_SHORT).show();

            }
        });

        builder.show();
    }


}