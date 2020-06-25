package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mTitleText;
    private EditText mBodyText;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleText = (EditText) findViewById(R.id.et_titleInput);
        mBodyText = (EditText) findViewById(R.id.et_noteInput);
        save = (Button) findViewById(R.id.btn_save);

        SharedPreferences prefs = getSharedPreferences("information", MODE_PRIVATE);
        String title = prefs.getString("title", "");
        String body = prefs.getString("body", "");

        mTitleText.setText(title);
        mBodyText.setText(body);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveState();
            }
        });

    }

    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences("information", MODE_PRIVATE).edit();
        editor.putString("title", title);
        editor.putString("body", body);

        editor.commit();

        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show();
    }

    public static class LineEditText extends androidx.appcompat.widget.AppCompatEditText {
        public LineEditText(Context context, AttributeSet attr) {
            super(context, attr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int height = this.getHeight();
            int lineHeight = this.getLineHeight();

            int lineCount = height / lineHeight;
            if (this.getLineCount() > lineCount) {
                lineCount = this.getLineCount();
            }

            Rect r = new Rect();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.BLUE);

            int baseline = getLineBounds(0, r);

            for (int i = 0; i < lineCount; i++) {

                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
                baseline += getLineHeight();

                super.onDraw(canvas);
            }

            Paint paint2 = new Paint();
            paint2.setStyle(Paint.Style.FILL_AND_STROKE);
            paint2.setColor(Color.RED);
            for (int i = 0; i < 2; i++) {
                canvas.drawLine(r.left, (float) i * 10f, r.right, (float) i * 10f, paint2);
                super.onDraw(canvas);
            }
        }
    }

}