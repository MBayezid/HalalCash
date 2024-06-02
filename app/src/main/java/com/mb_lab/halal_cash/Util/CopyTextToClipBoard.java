package com.mb_lab.halal_cash.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class CopyTextToClipBoard {
    // Copy the text to the clipboard
    public CopyTextToClipBoard(Context context,String leableString, String textToCopy){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(leableString, textToCopy);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, leableString+" copied to clipboard", Toast.LENGTH_SHORT).show();
    }

}
