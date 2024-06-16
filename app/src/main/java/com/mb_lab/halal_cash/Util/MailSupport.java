package com.mb_lab.halal_cash.Util;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import java.util.List;

public class MailSupport {


    public MailSupport(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]

                {
                        "support@halalcash.mblab.tech"
                });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Halal Cash Support Center");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Explain how we can serve you.......");
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]

                {
//                                    "cc@example.com"
                }); // Optional: CC recipients
        emailIntent.putExtra(Intent.EXTRA_BCC, new String[]

                {
//                                    "bcc@example.com"
                }); // Optional: BCC recipients

        // Check if there's an app that can handle this intent
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(emailIntent, 0);
        if (!resolveInfos.isEmpty()) {
            // Start the activity with the intent
            // Add FLAG_ACTIVITY_NEW_TASK flag
            // Set the package name of the Gmail app
            emailIntent.setPackage("com.google.android.gm");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(emailIntent);
        } else {
            // No email app found
            Toast.makeText(context.getApplicationContext(), "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
    public MailSupport(Context context, String agentName, String agentAccountNo, String paymentMethod, String amount, String transId, String userPayId) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]

                {
                        "support@halalcash.mblab.tech"
                });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Halal Cash Deposit");
        emailIntent.putExtra(Intent.EXTRA_TEXT, String.format(" Hi there halal cash support team.\nMy Pay Id : %s.\nI have deposited %s BDT.\nAgent Name : %s.\nAC NO : %s.\nPayment Method : %s.",userPayId,amount,agentName,agentAccountNo,paymentMethod));
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]

                {
//                                    "cc@example.com"
                }); // Optional: CC recipients
        emailIntent.putExtra(Intent.EXTRA_BCC, new String[]

                {
//                                    "bcc@example.com"
                }); // Optional: BCC recipients

        // Check if there's an app that can handle this intent
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(emailIntent, 0);
        if (!resolveInfos.isEmpty()) {
            // Start the activity with the intent
            // Add FLAG_ACTIVITY_NEW_TASK flag
            // Set the package name of the Gmail app
            emailIntent.setPackage("com.google.android.gm");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(emailIntent);
        } else {
            // No email app found
            Toast.makeText(context.getApplicationContext(), "No email app found", Toast.LENGTH_SHORT).show();
        }
    }


}
