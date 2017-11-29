package com.binding.model.util;

import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by pc on 2017/9/28.
 */

public class Text {
    private final String text;
    private final String color;
    private final int big;
    private final boolean line;

    public Text(String text,boolean line, int color, int big) {
        this.color = color == 0?"":String.format("%1x",color&0x00FFFFFF);
        this.text = text;
        this.line = line;
        this.big = big;
    }

    public Text(String text,boolean line, String color, int big) {
//        this.color = color == 0?"":String.format("%1x",color&0x00FFFFFF);
        this.color = color;
        this.text = text;
        this.line = line;
        this.big = big;
    }

    public Text(String text, boolean line,int color) {
        this(text,line, color, 0);
    }

    public Text(String text, int color) {
        this(text,false,  color);
    }

    public Text(String text, boolean line) {
        this(text,line,0);
    }

    public Text(String text) {
        this(text,0);
    }

    @Override
    public String toString() {
        String html;
        if (TextUtils.isEmpty(color) && big == 0) html = text;
        else if (big == 0) {
            html = String.format(Locale.getDefault(), "<font color = '#%1s'>%2s</font>", color, text);
        } else if (TextUtils.isEmpty(color)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < big; i++) builder.append("<big>");
            builder.append("%1s");
            for (int i = 0; i < big; i++) builder.append("</big>");
            html = String.format(Locale.getDefault(), builder.toString(), text);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("<font color = '#%1s'>");
            for (int i = 0; i < big; i++) builder.append("<big>");
            builder.append("%2s");
            for (int i = 0; i < big; i++) builder.append("</big>");
            builder.append("</font>");
            html = String.format(Locale.getDefault(), builder.toString(), color, text);
        }
//        Timber.i("html:%1s",html);
        return line?html+"<br>":html;
    }
}
