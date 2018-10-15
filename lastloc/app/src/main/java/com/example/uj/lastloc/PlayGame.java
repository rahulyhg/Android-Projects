package com.example.uj.lastloc;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class PlayGame extends AppCompatActivity {

    Button btn,btn1;
    TextView mRequestEmail;
    String currentUser, reciverEmail;
    String[] mSplit;
    Intent  myintent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    DatabaseReference myGameRef = database.getReference("Playing");
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        btn = findViewById(R.id.button11);
        btn1 = findViewById(R.id.button12);
        mRequestEmail = findViewById(R.id.requestEmail);
        incomingRequest();
    }

    String beforeAt(String Email){
        String[] nSplit = Email.split("@");
        return nSplit[0];
    }

    public String mIntent(){
        myintent = getIntent();
        currentUser = myintent.getStringExtra("currentUser");
        uid = myintent.getStringExtra("uid");
//        Log.i("Outer  **************",""+currentUser);
        return currentUser;
    }



    public void incomingRequest() {
                String curruseremail = mIntent();
//                Toast.makeText(getApplicationContext(), mIntent(), Toast.LENGTH_SHORT).show();
                myRef.child(beforeAt(curruseremail)).child("Request")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            HashMap<String, Object> hm = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(hm != null){
                                String value;
                                for (String key : hm.keySet()){
                                    value =(String) hm.get(key);
                                    Toast.makeText(PlayGame.this, value, Toast.LENGTH_SHORT).show();
                                    mRequestEmail.setText(value);
                                    changecolor();
                                    myRef.child("User").child(mSplit[0]).child("Request").setValue(uid);
                                    break;
                                }
                            }
                        }catch (Exception e){
                            System.out.print("*********************"+e);}
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }




    public void sendrequest(View view){
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        reciverEmail = mRequestEmail.getText().toString();
//        Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();
        myRef.child(beforeAt(reciverEmail)).child("Request").push().setValue(currentUser);


        RequestNotification requestNotification = new RequestNotification();
        requestNotification.notify(getApplicationContext(), currentUser,5);


        startGame(beforeAt(reciverEmail)+":"+beforeAt(currentUser));

        MySample = "X";
    }




    String playerGame="";


    public void startGame(String playerID){
        playerGame = playerID;
        myGameRef.child(playerID).removeValue();

//        String curruseremail = mIntent();
//        Toast.makeText(getApplicationContext(), mIntent(), Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        myGameRef.child(playerID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            Player1.clear();
                            Player2.clear();
                            ActivePlayer = 2;
                            HashMap<String, Object> hm = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(hm != null){
                                String value;
                                String FirstPlayer=beforeAt(currentUser);
                                for (String key : hm.keySet()){
                                    value =(String) hm.get(key);
                                    if(!value.equals(FirstPlayer))
                                        ActivePlayer = MySample=="X"?1:2;
                                    else{
                                        ActivePlayer = MySample=="X"?2:1;

                                    }
                                    FirstPlayer = value;
                                    String[] splitID = key.split(":");
                                    AutoPlay(Integer.parseInt(splitID[1]));
                                }
                            }
                        }catch (Exception e){
                            Log.i("-------*****-------","Error"+e);}
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

    }

    public void changecolor(){
        mRequestEmail.setBackgroundColor(Color.YELLOW);
    }
    public void acceptRequest(View view){
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        reciverEmail = mRequestEmail.getText().toString();
        myRef.child(beforeAt(reciverEmail)).child("Request").push().setValue(currentUser);
        startGame(beforeAt(currentUser)+":"+beforeAt(reciverEmail));
        MySample = "O";

    }

    public void BuClick(View view) {
        if(playerGame.length()<=0)
            return;

        Button buSelected= (Button) view;
        int CellID=0;
        switch ((buSelected.getId())){

            case R.id.bu1:
                CellID=1;
                break;

            case R.id.bu2:
                CellID=2;
                break;

            case R.id.bu3:
                CellID=3;
                break;

            case R.id.bu4:
                CellID=4;
                break;

            case R.id.bu5:
                CellID=5;
                break;

            case R.id.bu6:
                CellID=6;
                break;

            case R.id.bu7:
                CellID=7;
                break;

            case R.id.bu8:
                CellID=8;
                break;

            case R.id.bu9:
                CellID=9;
                break;
        }
//        Toast.makeText(this, mIntent(), Toast.LENGTH_LONG).show();
        myGameRef.child(playerGame).child("CellID:"+CellID).setValue(beforeAt(mIntent()));
    }

    int ActivePlayer=1; // 1- for first , 2 for second
    ArrayList<Integer> Player1= new ArrayList<>();// hold player 1 data
    ArrayList<Integer> Player2= new ArrayList<>();// hold player 2 data



    String MySample = "X";
    void PlayGam(int CellID,Button buSelected){

        Log.d("Player:",String.valueOf(CellID));

        if (ActivePlayer==1){
            buSelected.setText("X");
            buSelected.setBackgroundColor(Color.GREEN);
            Player1.add(CellID);
            Toast.makeText(this, ""+CellID, Toast.LENGTH_SHORT).show();
            ActivePlayer=2;
        }
        else if (ActivePlayer==2){
            buSelected.setText("O");
            buSelected.setBackgroundColor(Color.BLUE);
            Player2.add(CellID);
            ActivePlayer=1;
        }

        buSelected.setEnabled(false);
        CheckWiner();
    }

    void CheckWiner(){
        int Winer=-1;
        //row 1
        if (Player1.contains(1) && Player1.contains(2)  && Player1.contains(3))  {
            Winer=1 ;
        }
        if (Player2.contains(1) && Player2.contains(2)  && Player2.contains(3))  {
            Winer=2 ;
        }

        //row 2
        if (Player1.contains(4) && Player1.contains(5)  && Player1.contains(6))  {
            Winer=1 ;
        }
        if (Player2.contains(4) && Player2.contains(5)  && Player2.contains(6))  {
            Winer=2 ;
        }

        //row 3
        if (Player1.contains(7) && Player1.contains(8)  && Player1.contains(9))  {
            Winer=1 ;
        }
        if (Player2.contains(7) && Player2.contains(8)  && Player2.contains(9))  {
            Winer=2 ;
        }


        //col 1
        if (Player1.contains(1) && Player1.contains(4)  && Player1.contains(7))  {
            Winer=1 ;
        }
        if (Player2.contains(1) && Player2.contains(4)  && Player2.contains(7))  {
            Winer=2 ;
        }

        //col 2
        if (Player1.contains(2) && Player1.contains(5)  && Player1.contains(8))  {
            Winer=1 ;
        }
        if (Player2.contains(2) && Player2.contains(5)  && Player2.contains(8))  {
            Winer=2 ;
        }


        //col 3
        if (Player1.contains(3) && Player1.contains(6)  && Player1.contains(9))  {
            Winer=1 ;
        }
        if (Player2.contains(3) && Player2.contains(6)  && Player2.contains(9))  {
            Winer=2 ;
        }


        if ( Winer !=-1){
            // We have winer

            if (Winer==1){
                Toast.makeText(this,"Player 1 is winner",Toast.LENGTH_LONG).show();
            }

            if (Winer==2){
                Toast.makeText(this,"Player 2 is winner",Toast.LENGTH_LONG).show();
            }
        }
    }


    void AutoPlay(int CellID){

    Button buSelected;
        switch (CellID){

            case 1 :
                buSelected= findViewById(R.id.bu1);
                break;

            case 2:
                buSelected= findViewById(R.id.bu2);
                break;

            case 3:
                buSelected= findViewById(R.id.bu3);
                break;

            case 4:
                buSelected= findViewById(R.id.bu4);
                break;

            case 5:
                buSelected= findViewById(R.id.bu5);
                break;

            case 6:
                buSelected= findViewById(R.id.bu6);
                break;

            case 7:
                buSelected= findViewById(R.id.bu7);
                break;

            case 8:
                buSelected= findViewById(R.id.bu8);
                break;

            case 9:
                buSelected= findViewById(R.id.bu9);
                break;
            default:
                buSelected= findViewById(R.id.bu1);
                break;

        }
        PlayGam(CellID, buSelected);
    }






    public void signout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}