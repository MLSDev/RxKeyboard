# RxKeyboard

RxKeyboard is a software keyboard watcher based on JavaRx.

## Setup

To use this library your ` minSdkVersion` must be >= 15.

In your build.gradle :

```gradle
dependencies {
    compile 'com.mlsdev.rxkeyboard:library:1.1.1'
    compile 'io.reactivex:rxjava:1.0.14'
}
```
## Example

```java
RxKeyboard.instance().requestKeyboardUpdates(this).subscribe(new Action1<KeyboardState>() {
            @Override
            public void call(KeyboardState keyboardState) {
                if (keyboardState.isShow) {
                    textView.setText(String.format(getString(R.string.show_keyboard_height), keyboardState.keyboardHeight));
                } else {
                    textView.setText(getString(R.string.hidden));
                }
            }
        });
```

## Sample App

<img src="https://cloud.githubusercontent.com/assets/1778155/16866498/ed5e03aa-4a74-11e6-8039-e80ec9bbc7af.png" width="400">

## Authors
* [Sergey Glebov](mailto:glebov@mlsdev.com) ([frederikos][github-frederikos]), MLSDev 

## License
RxKeyboard is released under the MIT license. See LICENSE for details.

## About MLSDev

[<img src="https://cloud.githubusercontent.com/assets/1778155/11761239/ccfddf60-a0c2-11e5-8f2a-8573029ab09d.png" alt="MLSDev.com">][mlsdev]

RxKeyboard is maintained by MLSDev, Inc. We specialize in providing all-in-one solution in mobile and web development. Our team follows Lean principles and works according to agile methodologies to deliver the best results reducing the budget for development and its timeline. 

Find out more [here][mlsdev] and don't hesitate to [contact us][contact]!

[mlsdev]: http://mlsdev.com
[contact]: http://mlsdev.com/contact_us
[github-frederikos]: https://github.com/frederikos
