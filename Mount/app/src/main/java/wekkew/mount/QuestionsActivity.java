package wekkew.mount;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.MenuPopupWindow;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionsActivity extends AppCompatActivity {

    private boolean done;
    private int questionNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
        findViewById(R.id.correctornot).setVisibility(View.INVISIBLE);
        findViewById(R.id.nextbutton).setVisibility(View.INVISIBLE);
        String[] questions = getResources().getStringArray(R.array.Questions);

        TextView t = (TextView) findViewById(R.id.question);
        t.setText(questions[questionNo]);
        createAlert();
    }

    public void onHintClick(View view) {
        String[] hints = getResources().getStringArray(R.array.Hints);
        Toast toasty = Toast.makeText(getApplicationContext(), hints[questionNo], Toast.LENGTH_SHORT);
        toasty.show();
    }

    public void onAnswerClick(View view) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        if (done == false) {
            String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
            String[] answers = getResources().getStringArray(R.array.Answers);
            if (answer.length() == 0) {
                Toast toasty = Toast.makeText(getApplicationContext(), "Enter something, please", Toast.LENGTH_SHORT);
                toasty.show();
                return;
            }
            String correctanswer = answers[questionNo];
            //gets the answer and correct answer from the edit text and strings.xml respectively
            correctanswer = correctanswer.toUpperCase();
            answer = answer.toUpperCase();

            TextView t = (TextView) findViewById(R.id.correctornot);
            ImageView i = (ImageView) findViewById(R.id.tickcross);
            if (answer.equals(correctanswer)) {
                t.setText("CORRECT!");
                i.setImageDrawable(getDrawable(R.drawable.weirdtick));
            } else {
                t.setText("CORRECT ANSWER: " + correctanswer);
                i.setImageDrawable(getDrawable(R.drawable.weirdcross));
            }
            answerSubmitted();
            done = true;
        }
    }

    public void answerSubmitted() {
        findViewById(R.id.tickcross).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0,0,2000,0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.correctornot).setVisibility(View.VISIBLE);
                findViewById(R.id.nextbutton).setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        findViewById(R.id.tickcross).startAnimation(animation);
    }

    public void onNextClick(View view) {
        if (done) {
            String[] questions = getResources().getStringArray(R.array.Questions);
            TextView t = (TextView) findViewById(R.id.question);

            if (questionNo < (questions.length - 1)) {
                questionNo++;
            } else {
                //createAlert();
                questionNo = 0;
            }
            t.setText(questions[questionNo]);
            findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
            findViewById(R.id.correctornot).setVisibility(View.INVISIBLE);
            findViewById(R.id.nextbutton).setVisibility(View.INVISIBLE);
            EditText et = (EditText) findViewById(R.id.answer);
            et.setText("");

            done = false;
        }
    }

    public void createAlert() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("Alert message");
        ad.setTitle("Title");
        ad.setNeutralButton(R.string.alertNeutral,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        ad.setPositiveButton(R.string.alertOK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
        });
        ad.setNegativeButton(R.string.alertCancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
        });
        ad.show();
    }
}
