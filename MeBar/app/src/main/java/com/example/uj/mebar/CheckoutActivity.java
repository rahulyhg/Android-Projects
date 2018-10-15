package com.example.uj.mebar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckoutActivity extends AppCompatActivity {

    private TextView myAddress;
    private TextView myPhone;
    private Button myPayment;
    private Spinner spinner;
    private String mop;
    private String uid;
    ArrayAdapter<String> adapter ;

    private DatabaseReference mDatabase,myUse,myCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myAddress = findViewById(R.id.address);
        myPhone = findViewById(R.id.phone1);
        myPayment = findViewById(R.id.payment);
        spinner = findViewById(R.id.spinner1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("MOP");
        uid = user.getPhoneNumber();
        myPhone.setText(uid);

        String[] modeofpayment = new String[]{"Select Mode Of Payment","Cash On Delivery","Pay using Paytm","Pay using Card"};
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, modeofpayment);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mop = (String)adapterView.getItemAtPosition(i);
                if(mop.equals("Select Mode Of Payment")) {
                    myPayment.setVisibility(View.INVISIBLE);
                }
                else {
                    myCart = mDatabase.child(uid);
                    myCart.child("MOP").setValue(adapterView.getItemAtPosition(i));
                    myCart.child("Address").setValue(myAddress.getText().toString());
                    update();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void update() {

        if(mop.equals("Cash On Delivery")){
            myPayment.setText("Confirm Order");
            myPayment.setVisibility(View.VISIBLE);
            myPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    sendMessage();
                    myCart.child("Status").setValue("Order Confirmed");
                    startActivity(new Intent(getApplicationContext(),OrderConfirmed.class));
                    finish();
                }
            });


        }
        else if (mop.equals("Pay using Paytm")){
            myPayment.setText("Proceed");
            myPayment.setVisibility(View.VISIBLE);
            myPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCart.child("Status").setValue("Order Pending");
                   startActivity(new Intent(getApplicationContext(),paymentMethod.class));
                    finish();
                }
            });

        }
        else if(mop.equals("Pay using Card")){
            myPayment.setText("Proceed");
            myPayment.setVisibility(View.VISIBLE);
            myPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myCart.child("Status").setValue("Order Pending");
                    startActivity(new Intent(getApplicationContext(),paymentMethod.class));
                    finish();

                }
            });
        }
    }

}
