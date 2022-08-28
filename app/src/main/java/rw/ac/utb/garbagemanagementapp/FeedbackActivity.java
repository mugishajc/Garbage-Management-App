package rw.ac.utb.garbagemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnsendfeeback;
    private EditText etClientfeedback;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);



        etClientfeedback=findViewById(R.id.etClientfeedback);


        ActionBar actionBar = getSupportActionBar();

        // providing title for the ActionBar
        actionBar.setTitle("Send Feeback");

        btnsendfeeback=findViewById(R.id.btnsendfeeback);
        btnsendfeeback.setOnClickListener(this);


        reference = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/")
                .getReference("GarbageManagement").child("ClientFeedback");


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.btnsendfeeback:

                if (etClientfeedback.getText().toString().trim().isEmpty()){
                    Toast.makeText(this, "Please enter your client feeback to send, it's necessary", Toast.LENGTH_LONG).show();
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
//                dateFormat.format(date)

                Feedback feedback=new Feedback( etClientfeedback.getEditableText().toString().trim(),getIntent().getStringExtra("SenderNames"), getIntent().getStringExtra("SenderPhone"),dateFormat.format(date));

                reference.child(getIntent().getStringExtra("SenderPhone")).setValue(feedback);

                etClientfeedback.getText().clear();

                Toast.makeText(this, "Your feedback is sent successfully", Toast.LENGTH_LONG).show();


                break;

        }
    }
}