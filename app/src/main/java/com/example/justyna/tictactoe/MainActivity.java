package com.example.justyna.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES="usernames";
    private static final String USER1_TEXT="userText1";
    private static final String USER2_TEXT="userText2";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadContent();
    }


    private void loadContent() {

        Button startButton = (Button) findViewById(R.id.start_button);

       /* final EditText firstUserNameEditText = (EditText) findViewById(R.id.firstUsername_editText);
        final EditText secondUserNameEditText = (EditText) findViewById(R.id.secondUsername_editText);
        Button resetButton = (Button) findViewById(R.id.resetNames_button);

        if(!TextUtils.isEmpty(firstUserNameEditText.getText().toString()) || !TextUtils.isEmpty(secondUserNameEditText.getText().toString())) {
            resetButton.setVisibility(View.VISIBLE);
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firstUserNameEditText.setText("");
                    secondUserNameEditText.setText("");
                }
            });
        }

   */

        if (startButton != null) {
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EditText firstUserNameEditText = (EditText) findViewById(R.id.firstUsername_editText);
                    EditText secondUserNameEditText = (EditText) findViewById(R.id.secondUsername_editText);

                    String firstUserName = firstUserNameEditText.getText().toString();
                    String secondUserName = secondUserNameEditText.getText().toString();

                    sharedPreferences= getSharedPreferences(SHARED_PREFERENCES, 0);

                    SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                    sharedEditor.putString(USER1_TEXT, firstUserName).apply();
                    sharedEditor.putString(USER2_TEXT, secondUserName).apply();
                    sharedEditor.commit();

                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    // intent.putExtra("username2", user2_name);

                    if(!TextUtils.isEmpty(firstUserName) && !TextUtils.isEmpty(secondUserName)) {
                        Toast.makeText(MainActivity.this, R.string.whose_first, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    else Toast.makeText(MainActivity.this, R.string.lackOfnamesAllert, Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}

