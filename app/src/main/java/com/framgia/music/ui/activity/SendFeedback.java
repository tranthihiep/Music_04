package com.framgia.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.framgia.music.R;

public class SendFeedback extends AppCompatActivity {

    private EditText mEdtfeedback;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        mEdtfeedback = (EditText) findViewById(R.id.edtfeedback);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { "tranthihiep2597@gmail.com" });
        email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        email.putExtra(Intent.EXTRA_TEXT, mEdtfeedback.getText().toString());
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Send mail..."));
    }
}
