package com.opencraft.gcherubini.typeyourpin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class TypeYourPinLayout extends LinearLayout {

    List<View> pins = new ArrayList<>();
    private TypeYourPinInterface pinInterface;
    private EditText invisiblePinEditText;
    private int pinLenght;

    public TypeYourPinLayout(Context context) {
        super(context);
        init();
    }

    public TypeYourPinLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypeYourPinLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTypeYourPinInterface(TypeYourPinInterface pinInterface) {
        this.pinInterface = pinInterface;
    }

    private void init() {
        pinLenght = getResources().getInteger(R.integer.type_your_pin_lenght);
        int inputType = getResources().getInteger(R.integer.type_your_pin_input_type);
        int marginSize = getResources().getDimensionPixelSize(R.dimen.type_your_pin_margins);
        int height = getResources().getDimensionPixelSize(R.dimen.type_your_pin_size);
        int width = getResources().getDimensionPixelSize(R.dimen.type_your_pin_size);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setSize(this, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setupOnClickListener();
        setFocusableInTouchMode(false);

        setInvisibleEditText(pinLenght, inputType);

        for (int i = 1; i <= pinLenght; i++) {
            View pin = new View(getContext());
            setSize(pin, width, height);
            unfillPin(pin);
            setMarginLeft(pin, marginSize);

            if (isLastPin(i, pinLenght)) {
                setMarginRight(pin, marginSize);
            }

            pins.add(pin);
            addView(pin);
        }
    }

    private void setupOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPinEditTextFocus();
            }
        });
    }

    private void requestPinEditTextFocus() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && invisiblePinEditText != null) {
            imm.showSoftInput(invisiblePinEditText, 0);
            invisiblePinEditText.requestFocus();
        }
    }

    private void setInvisibleEditText(int pinLenght, int inputType) {
        invisiblePinEditText = new EditText(getContext());
        setSize(invisiblePinEditText, 0, 0);
        invisiblePinEditText.setInputType(inputType);
        invisiblePinEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pinLenght)});
        addView(invisiblePinEditText);
        setupEditTextPinListener(invisiblePinEditText);
        requestPinEditTextFocus();
    }

    private void setSize(View v, int width, int height) {
        v.setLayoutParams(new LayoutParams(width, height));
    }

    public static void setMarginLeft(View v, int left) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            setMargins(v, left, p.topMargin, p.rightMargin, p.bottomMargin);
        }
    }

    public static void setMarginRight(View v, int right) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            setMargins(v, p.leftMargin, p.topMargin, right, p.bottomMargin);
        }
    }

    public static void setMargins(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    private boolean isLastPin(int i, int pinSize) {
        return i == pinSize;
    }


    private void setupEditTextPinListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            int lastTextLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastTextLenght = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int textLenghtAfter = charSequence.length();
                if (hasNewPin(lastTextLenght, textLenghtAfter)) {
                    fillPin(pins.get(textLenghtAfter - 1));
                    if (pinInterface != null && hasFinishedTyping(textLenghtAfter)) {
                        pinInterface.onPinTyped(charSequence.toString());

                    }
                } else {
                    unfillPin(pins.get(textLenghtAfter));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean hasFinishedTyping(int textLenghtAfter) {
        return textLenghtAfter == pinLenght;
    }

    private boolean hasNewPin(int textSizeBefore, int size) {
        return textSizeBefore < size;
    }

    private void fillPin(View pin) {
        pin.setBackground(getResources().getDrawable(R.drawable.type_your_pin_rounded_shape_filled));
    }

    private void unfillPin(View pin) {
        pin.setBackground(getResources().getDrawable(R.drawable.type_your_pin_rounded_shape_transparent));
    }

    /**
     *  <EditText
     android:id="@+id/password_invisible_edit_text"
     android:layout_width="0dp"
     android:layout_height="0dp"
     android:inputType="number"
     android:maxLength="4" />

     * <LinearLayout
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="30dp"
     android:gravity="center"
     android:onClick="onLayoutPinBtnClick"
     android:orientation="horizontal">

     <View
     android:id="@+id/password_pin_1"
     android:layout_width="30dp"
     android:layout_height="30dp"
     android:background="@drawable/type_your_pin_rounded_shape_transparent" />

     <View
     android:id="@+id/password_pin_2"
     android:layout_width="30dp"
     android:layout_height="30dp"
     android:background="@drawable/type_your_pin_rounded_shape_transparent" />

     <View
     android:id="@+id/password_pin_3"
     android:layout_width="30dp"
     android:layout_height="30dp"
     android:background="@drawable/type_your_pin_rounded_shape_transparent" />

     <View
     android:id="@+id/password_pin_4"
     android:layout_width="30dp"
     android:layout_height="30dp"
     android:background="@drawable/type_your_pin_rounded_shape_transparent" />

     </LinearLayout>*/
}
