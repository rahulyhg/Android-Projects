package utkarshjain.mindtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static utkarshjain.mindtrainer.R.id.resultview;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer>answers = new ArrayList<Integer>() ;
    int positionofanswer;
    TextView resultview;
    TextView scoreview;
    int score=0;
    int tques=0;
    TextView questionview;
    Button button4;
    Button button3;
    Button button2;
    Button button1;


    public void generateques(){

        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        questionview.setText(Integer.toString(a)+ "+" + Integer.toString(b));
        positionofanswer = rand.nextInt(4);
        int incorrect;
        answers.clear();
        for(int i= 0; i<4; i++){

            if(i == positionofanswer){
                answers.add(a+b);

            }else{

                incorrect = rand.nextInt(41);
                while(incorrect == a+b){
                    incorrect = rand.nextInt(41);
                }
                answers.add(incorrect);
            }

        }
        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(positionofanswer))){
            score++;
            resultview.setText("Correct");

        }
        else{
            resultview.setText("InCorrect");
        }
        tques++;
        scoreview.setText(Integer.toString(score)+"/"+Integer.toString(tques));
        generateques();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionview = (TextView) findViewById(R.id.questionview);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        scoreview = (TextView)findViewById(R.id.scoreview);
        resultview = (TextView)findViewById(R.id.resultview);

        generateques();


    }
}
