package com.mlsdev.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mlsdev.rxkeyboard.KeyboardState;
import com.mlsdev.rxkeyboard.RxKeyboard;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Subscription keyBoardStateSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.tv_status);
        keyBoardStateSubscription = RxKeyboard.instance().requestKeyboardUpdates(this).subscribe(new Action1<KeyboardState>() {
            @Override
            public void call(KeyboardState keyboardState) {
                if (keyboardState.isShow) {
                    textView.setText(String.format(getString(R.string.show_keyboard_height), keyboardState.keyboardHeight));
                } else {
                    textView.setText(getString(R.string.hidden));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        keyBoardStateSubscription.unsubscribe();
        super.onDestroy();
    }
}
