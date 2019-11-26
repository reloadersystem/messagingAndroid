package ar.reloadersystem.messagingandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static Bitmap getBitMapFromAsset(Context context, String filePath) {

        AssetManager asset = context.getAssets();

        InputStream is;
        Bitmap bitMap = null;

        try {
            is = asset.open(filePath);
            bitMap = BitmapFactory.decodeStream(is);

        } catch (IOException ex) {
        }
        return bitMap;
    }
}
