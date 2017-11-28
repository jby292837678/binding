package com.binding.library.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.binding.library.R;

public class CommonDialog extends Dialog {
//    private TextView contentTxt;
//    private TextView titleTxt;
//    private TextView submitTxt;
//    private TextView cancelTxt;
//    private TextView mainText;
    //    private OnCloseListener onCloseListener;

    private CharSequence description;
    private CharSequence content;
    private CharSequence positiveName;
    private CharSequence negativeName;
    private CharSequence title;
//    private ImageView imageView;
    private int image_layout;

    private View.OnClickListener onPositiveClickListener;
    private View.OnClickListener onNegativeClickListener;

    public CommonDialog(Context context) {
        this(context, R.style.idea_dialog);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CommonDialog setImg(int image_layout){
        this.image_layout = image_layout;
        return this;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommonDialog setPositiveButton(String positiveName, View.OnClickListener onPositiveClickListener) {
        this.positiveName = positiveName;
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    public CommonDialog setNegativeButton(String negativeName, View.OnClickListener onNegativeClickListener) {
        this.negativeName = negativeName;
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }

    public CommonDialog setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommonDialog setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.dialog_idea,null);
        setContentView(rootView);
        setCanceledOnTouchOutside(false);
        initView(inflater);
    }

    private void initView(LayoutInflater inflater) {
        View viewImage = findViewById(R.id.view_image);
        FrameLayout frameLayout = findViewById(R.id.frame_image);
        if(image_layout!=0) {
            frameLayout.setVisibility(View.VISIBLE);
            viewImage.setVisibility(View.VISIBLE);
            inflater.inflate(image_layout,frameLayout,true);
        }else{
            frameLayout.setVisibility(View.GONE);
            viewImage.setVisibility(View.GONE);
        }
        TextView descriptionTxt = findViewById(R.id.descriptionTxt);
        TextView titleTxt = findViewById(R.id.title);
        TextView submitTxt = findViewById(R.id.submit);
        TextView cancelTxt = findViewById(R.id.cancel);
        TextView contentTxt = findViewById(R.id.contentTxt);
        if (onPositiveClickListener != null) submitTxt.setOnClickListener(onPositiveClickListener);
        if (onNegativeClickListener != null) cancelTxt.setOnClickListener(onNegativeClickListener);
        setTextValid(content, contentTxt);
        setTextValid(description, descriptionTxt);
        setTextValid(positiveName, submitTxt);
        setTextValid(negativeName, cancelTxt);
        setTextValid(title, titleTxt);
    }

    private void setTextValid(CharSequence content, TextView textView) {
        textView.setVisibility((TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE));
        if (!TextUtils.isEmpty(content)) {
            textView.setText(content);
        }
    }
}