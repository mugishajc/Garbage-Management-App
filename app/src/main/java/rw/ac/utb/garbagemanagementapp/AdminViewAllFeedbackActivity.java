package rw.ac.utb.garbagemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AdminViewAllFeedbackActivity extends AppCompatActivity implements ViewFeedbackAdapter.OnItemClickListener {
    private ProgressBar pbviewfeedack;

    private RecyclerView recyclerviewViewFeedcak;
    private ViewFeedbackAdapter mAdapter;
    private List<Feedback> uploads;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_feedback);

        recyclerviewViewFeedcak = findViewById(R.id.recyclerviewViewFeedcak);

        pbviewfeedack = findViewById(R.id.pbviewfeedack);


        pbviewfeedack=findViewById(R.id.pbviewfeedack);
        recyclerviewViewFeedcak=findViewById(R.id.recyclerviewViewFeedcak);

        reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("ClientFeedback");

        recyclerviewViewFeedcak.setHasFixedSize(true);
        recyclerviewViewFeedcak.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();

        //creating adapter
        mAdapter = new ViewFeedbackAdapter(AdminViewAllFeedbackActivity.this, uploads);
        recyclerviewViewFeedcak.setAdapter(mAdapter);





        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    pbviewfeedack.setVisibility(View.GONE);

                    uploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Feedback upload = postSnapshot.getValue(Feedback.class);
                        uploads.add(upload);

                        mAdapter.setOnItemClickListener(AdminViewAllFeedbackActivity.this);


                        mAdapter.notifyDataSetChanged();
                        //adding adapter to recyclerview
                    }

                } else {
                    pbviewfeedack.setVisibility(View.GONE);
                    Toast.makeText(AdminViewAllFeedbackActivity.this, "No Clients feedbacks is found the database", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminViewAllFeedbackActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pbviewfeedack.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onItemClick(int position) {

     //   Toast.makeText(this, "clicked "+uploads.get(position).getMessage(), Toast.LENGTH_LONG).show();

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminViewAllFeedbackActivity.this);
        builder.setTitle("Feedack from "+uploads.get(position).getSenderName());
        builder.setMessage("Hello Admin here is the message from "+uploads.get(position).getSenderPhone()+"\n"
        +" Date: "+uploads.get(position).getDate()+"\n Message\n"+uploads.get(position).getMessage());
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AdminViewAllFeedbackActivity.this, "Thanks for using Titus -UTB Mobile Application", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);

        builder.show();

    }
}