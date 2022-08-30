package rw.ac.utb.garbagemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener, ViewFeedbackAdapter.OnItemClickListener {

    private TextView salitation, tvshowstafflocation,Tvroleadmin,tvplatenumber,tvloggedinnames,tvsenderphone,tvsendernames;
    private ImageView imageView;
    private MaterialCardView LinAddUser, linViewStaff, LinAddClient, linViewClient,LinViewComplain,linAdminViewComplain,linAdminViewFeedback,linViewAllClient;
    private Button BtnRequestComplain, BtnPayMonthlySubscription,BtnSendFeeback;
    private LinearLayout linClientMain;

    private FirebaseDatabase database;
    private DatabaseReference table_Complain,table_Allcomplains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        tvsenderphone=findViewById(R.id.tvsenderphone);
        tvsendernames=findViewById(R.id.tvsendernames);

        tvplatenumber=findViewById(R.id.tvplatenumber);

        tvshowstafflocation = findViewById(R.id.tvshowstafflocation);

        linClientMain = findViewById(R.id.linClientMain);

        tvloggedinnames=findViewById(R.id.tvloggedinnames);


        linAdminViewComplain=findViewById(R.id.linAdminViewComplain);
        linViewAllClient=findViewById(R.id.linViewAllClient);
        linViewAllClient.setOnClickListener(this);

        BtnRequestComplain = findViewById(R.id.BtnRequestComplain);
        BtnRequestComplain.setOnClickListener(this);

        BtnSendFeeback=findViewById(R.id.BtnSendFeeback);
        BtnSendFeeback.setOnClickListener(this);

        BtnPayMonthlySubscription = findViewById(R.id.BtnPayMonthlySubscription);
        BtnPayMonthlySubscription.setOnClickListener(this);

        linViewClient = findViewById(R.id.linViewClient);
        linViewClient.setOnClickListener(this);

        linAdminViewFeedback=findViewById(R.id.linAdminViewFeedback);
        linAdminViewFeedback.setOnClickListener(this);

        LinAddClient = findViewById(R.id.LinAddClient);
        LinAddClient.setOnClickListener(this);
        Tvroleadmin=findViewById(R.id.Tvroleadmin);

        LinAddUser = findViewById(R.id.LinAddUser);
        linViewStaff = findViewById(R.id.linViewStaff);
        linViewStaff.setOnClickListener(this);
        LinAddUser.setOnClickListener(this);

        LinViewComplain=findViewById(R.id.LinViewComplain);
        LinViewComplain.setOnClickListener(this);

        salitation = findViewById(R.id.salitation);
        imageView = findViewById(R.id.imageView);

        linAdminViewComplain.setOnClickListener(this);

        tvshowstafflocation.setText(getIntent().getStringExtra("staffLocation"));

        if (getIntent().getStringExtra("role").equals("Admin")) {
            linViewStaff.setVisibility(View.VISIBLE);
            LinAddUser.setVisibility(View.VISIBLE);
            linViewAllClient.setVisibility(View.VISIBLE);
            linAdminViewFeedback.setVisibility(View.VISIBLE);
            Tvroleadmin.setText(getIntent().getStringExtra("role"));
            linAdminViewComplain.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra("role").equals("Staff")) {
            linViewClient.setVisibility(View.VISIBLE);
            LinAddClient.setVisibility(View.VISIBLE);
            LinViewComplain.setVisibility(View.VISIBLE);
            tvplatenumber.setVisibility(View.VISIBLE);
            tvloggedinnames.setText(getIntent().getStringExtra("LoggedInNames"));
            tvplatenumber.setText("Plate Number:  "+getIntent().getStringExtra("LoggedInPlateNumber")+"\n"+"License Number: "+getIntent().getStringExtra("LoggedInLicenseNumber"));
        } else if (getIntent().getStringExtra("role").equals("Client")) {
            linClientMain.setVisibility(View.VISIBLE);
            tvsenderphone.setText(getIntent().getStringExtra("LoggedInPhone"));
            tvsendernames.setText(getIntent().getStringExtra("LoggedInNames"));
        }

        // salitation
        Calendar calendar = Calendar.getInstance();
        int dayTime = calendar.get(Calendar.HOUR_OF_DAY);

        if (dayTime >= 04 && dayTime < 12) {
            // morning
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun));

            salitation.setText("Morning " + getIntent().getStringExtra("LoggedInNames") + "\n" + getIntent().getStringExtra("LoggedInLocation") + " [" + getIntent().getStringExtra("role") + "]");


            salitation.setTextColor(getResources().getColor(R.color.colorGreen));

        } else if (dayTime >= 12 && dayTime < 17) {
            // afternoon
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun));

            salitation.setText("Morning " + getIntent().getStringExtra("LoggedInNames") + "\n" + getIntent().getStringExtra("LoggedInLocation") + " [" + getIntent().getStringExtra("role") + "]");


            salitation.setTextColor(getResources().getColor(R.color.colorGreen));

        } else if (dayTime >= 17 && dayTime < 21) {
            // evening
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mon));

            salitation.setText("Morning " + getIntent().getStringExtra("LoggedInNames") + "\n" + getIntent().getStringExtra("LoggedInLocation") + "  [" + getIntent().getStringExtra("role") + "]");

            salitation.setTextColor(getResources().getColor(R.color.colorGray));
        } else if (dayTime >= 21 && dayTime < 03) {
            // night
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mon));
            salitation.setText("Morning " + getIntent().getStringExtra("LoggedInNames") + "\n" + getIntent().getStringExtra("LoggedInLocation") + "  [" + getIntent().getStringExtra("role") + "]");


            salitation.setTextColor(getResources().getColor(R.color.colorGray));

        }

//Init Firebase
        database = FirebaseDatabase.getInstance("https://garbage-management-app---titus-default-rtdb.firebaseio.com/");
        table_Complain = database.getReference("GarbageManagement").child("Complain");
        table_Allcomplains=database.getReference("GarbageManagement").child("AllComplains");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.LinAddUser:
                startActivity(new Intent(this, AddStaffActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.linViewStaff:
                startActivity(new Intent(this, ViewAllStaffActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;

            case R.id.linViewClient:

                Intent intent = new Intent(new Intent(this, ViewAllClientActivity.class));
                intent.putExtra("Location", tvshowstafflocation.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;

            case R.id.LinAddClient:
                startActivity(new Intent(this, AddClientActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.LinViewComplain:
                Intent intent1 = new Intent(new Intent(this, ClientComplainActivity.class));
                intent1.putExtra("Location", tvshowstafflocation.getText().toString());
                intent1.putExtra("loggedindrivernames",tvloggedinnames.getText().toString());
                startActivity(intent1);
                break;

            case R.id.linAdminViewComplain:

                Intent intentad = new Intent(new Intent(this, AdminViewComplainActivity.class));
                intentad.putExtra("Roles", Tvroleadmin.getText().toString());
                startActivity(intentad);

                break;
            case R.id.linAdminViewFeedback:

                startActivity(new Intent(MainScreenActivity.this,AdminViewAllFeedbackActivity.class));
                break;


            case R.id.BtnSendFeeback:

                Intent intentFeebcak = new Intent(new Intent(this, FeedbackActivity.class));
                intentFeebcak.putExtra("SenderNames", tvsendernames.getText().toString());
                intentFeebcak.putExtra("SenderPhone", tvsenderphone.getText().toString());
                startActivity(intentFeebcak);
                break;
            case R.id.BtnRequestComplain:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScreenActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Important Comfirmation");
                builder.setMessage("Are yo usure you want to send a complain so that they can come to collect your bin");

                builder.setPositiveButton("REQUEST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                        String datetime = dateformat.format(c.getTime());

                        Complain complain = new Complain(
                                getIntent().getStringExtra("LoggedInNames"),
                                getIntent().getStringExtra("LoggedInLocation"),
                                getIntent().getStringExtra("LoggedInPhone"),
                                datetime,
                                "pending"
                        );

//                        getIntent().getStringExtra("LoggedInPhone")
                        table_Allcomplains.child(datetime).setValue(complain);

                        table_Complain.child(getIntent().getStringExtra("LoggedInLocation")).child(datetime).setValue(complain);

                        Toast.makeText(MainScreenActivity.this, "Your Complain is sent successfully", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(view, "Saved well", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                });
                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainScreenActivity.this, "Cancelled THE Complain well / Discarded", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;

            case R.id.linViewAllClient:
                startActivity(new Intent(MainScreenActivity.this, ViewAllActivity.class));
                break;

            case R.id.BtnPayMonthlySubscription:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainScreenActivity.this);
                builder1.setCancelable(false);
                builder1.setTitle("Important Comfirmation");
                builder1.setMessage("Are yo usure you want to pay ? if yes please press on button pay monthly subscription");
                builder1.setPositiveButton("REQUEST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainScreenActivity.this, "PAYMENT IS IN PROGRESS PLEASE...", Toast.LENGTH_SHORT).show();


         payment();


                        new RaveUiManager(MainScreenActivity.this).setAmount(100)
                                .setCurrency("RWF")
                                .setCountry("RW")
                                .setEmail("mihigojeanpele@gmail.com")
                                .setfName( getIntent().getStringExtra("LoggedInNames"))
                                .setlName("Titus")
                                .setNarration("PAY FOR Garbage fees")
                                .setPublicKey("FLWPUBK-72b1cddaa14d3497296ecc503b4dfd58-X")
                                .setEncryptionKey("e65716b8e64695fc9c915c0d")
                                .setTxRef(System.currentTimeMillis()+" Ref")
                                .setPhoneNumber("+250788503968", true)
                                .acceptAccountPayments(true)
                                .acceptCardPayments(true)
                                .acceptMpesaPayments(true)
                                .acceptAchPayments(false)
                                .acceptGHMobileMoneyPayments(true)
                                .acceptUgMobileMoneyPayments(true)
                                .acceptZmMobileMoneyPayments(true)
                                .acceptRwfMobileMoneyPayments(true)
                                .acceptSaBankPayments(true)
                                .acceptUkPayments(true)
                                .acceptBankTransferPayments(true)
                                .acceptUssdPayments(true)
                                .acceptBarterPayments(false)
                                .acceptFrancMobileMoneyPayments(false,"France")
                                .allowSaveCardFeature(false)
                                .onStagingEnv(false)
                                .shouldDisplayFee(true)
                                .showStagingLabel(true)
                                .withTheme(R.style.MyCustomTheme)
                                .initialize();


                    }
                });
                builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainScreenActivity.this, "Cancelled The payment Processing   / Discarded", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.show();
                break;

        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(this, "Visit our website at www.utb.ac.rw", Toast.LENGTH_LONG).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "Call us on on +250788647092", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                Intent intent = new Intent(getApplicationContext(), categoryBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            payment();
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                payment();
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                payment();
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                payment();
                Toast.makeText(this, "CANCELLED well " + message, Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onItemClick(int position) {

        Toast.makeText(this, "clicked " , Toast.LENGTH_SHORT).show();
    }

    private  void payment(){

        String ussdCode = "*182*1*1*0788647092*100"+ Uri.encode("#");
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
    }


}