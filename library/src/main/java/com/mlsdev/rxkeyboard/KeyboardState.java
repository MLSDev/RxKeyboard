package com.mlsdev.rxkeyboard;

public class KeyboardState {

    public final boolean isShow;
    public final int keyboardHeight;

    public KeyboardState(boolean isShow) {
        this(isShow, 0);
    }

    public KeyboardState(boolean isShow, int keyboardHeight) {
        this.isShow = isShow;
        this.keyboardHeight = keyboardHeight;
    }

}
