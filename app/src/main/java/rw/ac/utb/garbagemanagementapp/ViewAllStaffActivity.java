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

public class ViewAllStaffActivity extends AppCompatActivity implements ViewStaffAdapter.OnItemClickListener {

    private RecyclerView recyclerviewStaff;
    private ViewStaffAdapter mAdapter;
    private List<Staff> uploads;
    private ProgressBar pbViewStaff;
    private DatabaseReference reference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_staff);

        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("View All Staffs");

        pbViewStaff=findViewById(R.id.pbViewStaff);

        recyclerviewStaff=findViewById(R.id.recyclerviewStaff);

        recyclerviewStaff.setHasFixedSize(true);
        recyclerviewStaff.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();



        //creating adapter
        mAdapter = new ViewStaffAdapter(ViewAllStaffActivity.this, uploads);
        recyclerviewStaff.setAdapter(mAdapter);


         reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("Staff");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    pbViewStaff.setVisibility(View.GONE);

                    uploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Staff upload = postSnapshot.getValue(Staff.class);
                        uploads.add(upload);

                        mAdapter.setOnItemClickListener(ViewAllStaffActivity.this);
                        mAdapter.notifyDataSetChanged();
                        //adding adapter to recyclerview
                    }

                } else {
                    pbViewStaff.setVisibility(View.GONE);
                    Toast.makeText(ViewAllStaffActivity.this, "No Staff records is found the database", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAllStaffActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pbViewStaff.setVisibility(View.GONE);
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

        AlertDialog.Builder builder=new AlertDialog.Builder(ViewAllStaffActivity.this);
        builder.setCancelable(false);
        builder.setTitle(" Staff Information");
        builder.setMessage("Names "+ClickedNames +"\n"+"Phone "+ClickedPhone+"\n"+" Nid or Passport"+ClickedNid+"\n"+"Main Zone Location "+ClickedMainLocation +"\n"+"Sub Zone Location "+ClickedSubLocation);
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                 reference.child(ClickedPhone).removeValue();
                Toast.makeText(ViewAllStaffActivity.this, "Successfully deleted  Staff "+ClickedNames , Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ViewAllStaffActivity.this, "Cancelled well / Discarded", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();


    }
}