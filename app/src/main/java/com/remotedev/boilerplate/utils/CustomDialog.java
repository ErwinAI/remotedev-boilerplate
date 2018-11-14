package com.remotedev.boilerplate.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.remotedev.boilerplate.R;

/**
 * CustomDialog
 * <p>
 * Created by Erwin on 12-03-2018.
 */

public class CustomDialog extends Dialog {

    /**
     * Constructor
     *
     * @param context context
     */
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Method to display dialog
     */
    public static CustomDialog showCustomDialog(Context context, String action, String title, String content) {
        final CustomDialog mDialog = new CustomDialog(context);

        // use custom background for the dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.custom_dialog);

        // get title and content views
        TextView titleTextView = mDialog.findViewById(R.id.dialog_title);
        TextView contentTextView = mDialog.findViewById(R.id.dialog_content);
        TextView actionTextVIew = mDialog.findViewById(R.id.dialog_action);

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            titleTextView.setText(title);
            contentTextView.setText(content);
            actionTextVIew.setText(action);
        }
        FrameLayout tryAgainButton = mDialog.findViewById(R.id.dialog_try_again_container);

        // dismiss dialog
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
        return mDialog;
    }
}
