package com.rfo.e920;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.jecelyin.util.FileUtil;
import android.net.Uri;
import android.content.Intent;

public class Help
{

    public static void showChangesLog(Context mContext)
    {
        popupWindow(mContext, R.string.changelog, "CHANGES");
    }
    
    public static void showHelp(Context mContext)
    {
        //popupWindow(mContext, R.string.help, "HELP");
		Uri uri = Uri.parse("http://rfobasic.freeforums.org/post20081.html#p20081");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		mContext.startActivity(intent);
    }

    private static void popupWindow(final Context mContext, int title, String file)
    {
        String text;
        try
        {
            text = FileUtil.readFileAsString(mContext.getAssets().open(file), "utf-8");
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setIcon(0).setTitle(title).setMessage(text).setPositiveButton(android.R.string.ok, null);

            builder.show();
        }catch (Exception e)
        {
            return;
        }
    }
}
