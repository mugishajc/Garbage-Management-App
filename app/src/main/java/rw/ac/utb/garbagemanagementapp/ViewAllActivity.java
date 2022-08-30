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

public class ViewAllActivity extends AppCompatActivity implements ViewClientAdapter.OnItemClickListener{
    private RecyclerView recyclerviewClientAll;
    private ViewClientAdapter mAdapter;
    private List<Client> uploads;
    private ProgressBar pbViewClientAll;
    private DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);


        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("View All Clients");

        recyclerviewClientAll=findViewById(R.id.recyclerviewClientAll);
        pbViewClientAll=findViewById(R.id.pbViewClientAll);


        recyclerviewClientAll.setHasFixedSize(true);
        recyclerviewClientAll.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();



        //creating adapter
        mAdapter = new ViewClientAdapter(ViewAllActivity.this, uploads);
        recyclerviewClientAll.setAdapter(mAdapter);


        reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("Client");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    pbViewClientAll.setVisibility(View.GONE);

                    uploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Client upload = postSnapshot.getValue(Client.class);
                        uploads.add(upload);

                        mAdapter.setOnItemClickListener(ViewAllActivity.this);
                        mAdapter.notifyDataSetChanged();
                        //adding adapter to recyclerview
                    }

                } else {
                    pbViewClientAll.setVisibility(View.GONE);
                    Toast.makeText(ViewAllActivity.this, "No Clients records is found the database", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAllActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pbViewClientAll.setVisibility(View.GONE);
            }
        });




    }

    @Override
    public void onItemClick(int position) {
        String ClickedNames = uploads.get(position).getNames();
        String ClickedPhone = uploads.get(position).getPhone();
        String ClickedNid = uploads.get(position).getNid();

        String ClickedMainLocation = uploads.get(position).getMainLocation();
        String ClickedSubLocation = uploads.get(position).getSubLocation();
        String ClickedStreertCode=uploads.get(position).getStreetCode();

        AlertDialog.Builder builder=new AlertDialog.Builder(ViewAllActivity.this);
        builder.setCancelable(false);
        builder.setTitle(" Client Information");
        builder.setMessage("Names: "+ClickedNames +"\n"+"Phone: "+ClickedPhone+"\n"+" Nid or Passport: "+ClickedNid+"\n"+"Main Zone Location: "+ClickedMainLocation
                +"\n"+"Sub Zone Location: "+ClickedSubLocation +"\n"+" Street code: "+ClickedStreertCode);
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                reference.child(ClickedPhone).removeValue();
                Toast.makeText(ViewAllActivity.this, "Successfully deleted  Client "+ClickedNames , Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ViewAllActivity.this, "Cancelled well / Discarded", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}