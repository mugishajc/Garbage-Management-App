package rw.ac.utb.garbagemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class categoryBoardActivity extends AppCompatActivity implements View.OnClickListener {


    private Button BtnAdmin;
    private LinearLayout lnlStaff,lnlclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_board);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        BtnAdmin=findViewById(R.id.BtnAdmin);
        lnlStaff=findViewById(R.id.lnlStaff);
        lnlclient=findViewById(R.id.lnlclient);

        lnlStaff.setOnClickListener(this);
        BtnAdmin.setOnClickListener(this);
        lnlclient.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.BtnAdmin:

                Intent intentAdmin=new Intent(this,LoginActivity.class);
                intentAdmin.putExtra("user","Admin");
                startActivity(intentAdmin);

                break;

            case R.id.lnlStaff:

                Intent intentStaff=new Intent(this,LoginActivity.class);
                intentStaff.putExtra("user","Staff");
                startActivity(intentStaff);

                break;
            case R.id.lnlclient:
                Intent intentClient=new Intent(this,LoginActivity.class);
                intentClient.putExtra("user","Client");
                startActivity(intentClient);
                break;


        }
    }
}