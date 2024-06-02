package com.mb_lab.halal_cash.CustomeDialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;

import com.mb_lab.halal_cash.R;
import com.mb_lab.mbviewlib.spinkit.sprite.Sprite;
import com.mb_lab.mbviewlib.spinkit.style.ThreeBounce;

import java.util.Objects;

public class ViewLoadingAnimation extends Dialog {
    Context context;

    public ViewLoadingAnimation(@NonNull Context context) {
        super(context);
        // Initialize the dialog
        initDialog(context);
    }
    private void initDialog(Context context) {
        // Set dialog properties
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.three_dots_animation);

        // Set up ProgressBar with ThreeBounce animation
        ProgressBar progressBar = findViewById(R.id.progress);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    // Method to show/hide the loading animation
    public void showLoading(boolean visibility) {
        if (visibility) {
            show();
        } else {
            dismiss();
        }
    }
//    public ViewLoadingAnimation(Context context, boolean visibility) {
//        super(context);
//        this.context = context;
//        loadAnim(visibility);
//
//    }
//
//
//    public void loadAnim(boolean visibility) {
//        Dialog dialog = new Dialog(context);
//        dialog.setCancelable(false);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.three_dots_animation);
//
//        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
//        Sprite doubleBounce = new ThreeBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//        dialog.create();
//        if (!visibility) {
//            dialog.setCancelable(true);
//            dialog.dismiss();
//        } else {
//            dialog.show();
//        }
//    }



}
