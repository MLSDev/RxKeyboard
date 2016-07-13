package com.mlsdev.rxkeyboard;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

public class RxKeyboard {

    private ViewTreeObserver.OnGlobalLayoutListener viewTreeObserverListener;
    private WeakReference<View> rootView;
    private PublishSubject<KeyboardState> publishSubject;

    private static RxKeyboard instance;

    public static synchronized RxKeyboard instance() {
        if (instance == null) {
            instance = new RxKeyboard();
        }
        return instance;
    }

    private RxKeyboard() {

    }

    public Observable<KeyboardState> requestKeyboardUpdates(Activity activity) {
        publishSubject = PublishSubject.create();

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        viewTreeObserverListener = new GlobalLayoutListener();
        rootView = new WeakReference<>(activity.findViewById(Window.ID_ANDROID_CONTENT));
        rootView.get().getViewTreeObserver().addOnGlobalLayoutListener(viewTreeObserverListener);

        publishSubject.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                destroy();
            }
        });
        return publishSubject;
    }

    public void destroy() {
        if (rootView.get() != null)
            if (Build.VERSION.SDK_INT >= 16) {
                rootView.get().getViewTreeObserver().removeOnGlobalLayoutListener(viewTreeObserverListener);
            } else {
                rootView.get().getViewTreeObserver().removeGlobalOnLayoutListener(viewTreeObserverListener);
            }
    }

    private class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        int initialValue;
        boolean hasSentInitialAction;
        boolean isKeyboardShown;

        @Override
        public void onGlobalLayout() {
            if (initialValue == 0) {
                initialValue = rootView.get().getHeight();
            } else {
                if (initialValue > rootView.get().getHeight()) {
                    if (!hasSentInitialAction || !isKeyboardShown) {
                        isKeyboardShown = true;
                        publishSubject.onNext(new KeyboardState(true, initialValue - rootView.get().getHeight()));
                    }
                } else {
                    if (!hasSentInitialAction || isKeyboardShown) {
                        isKeyboardShown = false;
                        rootView.get().post(new Runnable() {
                            @Override
                            public void run() {
                                publishSubject.onNext(new KeyboardState(false));
                            }
                        });
                    }
                }
                hasSentInitialAction = true;
            }
        }
    }
}
