package com.example.atilagapps.hellixdatamanager.Reciept;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.R;

import java.io.File;

public class Common {

    public static String getAppPath(Context context) {

        File dir=new File(context.getExternalFilesDir(null)+File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);

        if (!dir.exists())
            dir.mkdirs();


        return dir.getAbsolutePath() + File.separator;
    }
}
