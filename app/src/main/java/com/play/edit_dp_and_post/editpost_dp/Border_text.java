package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class Border_text extends AppCompatTextView
{
    private static final int DEFAULT_STROKE_WIDTH = 0;

    // fields
    private int isC_fill,isGrd_fill,isPic_fill;
    private int fgradClr;
    private  int sgradClr;
    private int _strokeColor;
    private float _strokeWidth;

    public void setIsPic_fill(int isPic_fill) {
        this.isPic_fill = isPic_fill;
    }

    public Border_text(@NonNull Context context) {
        this(context, null, 0);
    }

    public Border_text(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Border_text(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokedTextAttrs);
            _strokeColor = a.getColor(R.styleable.StrokedTextAttrs_textStrokeColor,
                    getCurrentTextColor());
            _strokeWidth = a.getFloat(R.styleable.StrokedTextAttrs_textStrokeWidth,
                    DEFAULT_STROKE_WIDTH);

            a.recycle();
        } else {
            _strokeColor = getCurrentTextColor();
            _strokeWidth = DEFAULT_STROKE_WIDTH;
        }
        //convert values specified in dp in XML layout to
        //px, otherwise stroke width would appear different
        //on different screens
        _strokeWidth = dpToPx(context, _strokeWidth);
    }//////fun///close
    /////////////for c////////////////

    public void setIsC_fill(int isC_fill)
    {
        this.isC_fill = isC_fill;
    }

    public void brd_clr_C_fill(int bclr)
    {  setIsGrd_fill(0);
        setIsC_fill(1);
        setStrokeColor(bclr);
    }
    public void brd_width_C_fill(int widt)
    {
        setIsC_fill(1);
        setStrokeWidth(widt);
    }
    public void removeC_fill()
    {
        setIsC_fill(0);
    }

    ////////////////// for Grd /////


    public void setFgradClr(int fgradClr) {
        this.fgradClr = fgradClr;
    }

    public void setSgradClr(int sgradClr) {
        this.sgradClr = sgradClr;
    }

    public void setIsGrd_fill(int isGrd_fill)
    {
        this.isGrd_fill = isGrd_fill;
    }
   public void brd_clr_G_fill(int bgclr,int fclr,int sclr)
   {
       setIsGrd_fill(1);
       setIsC_fill(0);
       setFgradClr(fclr);
       setSgradClr(sclr);
       setStrokeColor(bgclr);
   }
    public void brd_width_G_fill(int widt,int wf_clr,int ws_clr)
    {
        setIsGrd_fill(1);
        setIsC_fill(0);
        setFgradClr(wf_clr);
        setSgradClr(ws_clr);
        setStrokeWidth(widt);
    }
    //////////////////
    public void remove_eff()
    {
        setIsGrd_fill(0);
        setIsC_fill(0);
    }

    ////////////
    public void setStrokeColor(int color) {
        _strokeColor = color;
    }

    public void setStrokeWidth(int width) {
        _strokeWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isC_fill > 0) {
            //set paint to fill mode
            Paint p = getPaint();
            p.setStyle(Paint.Style.FILL);
            //draw the fill part of text
            super.onDraw(canvas);

            int currentTextColor = getCurrentTextColor();
            //set paint to stroke mode and specify
            //stroke color and width
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(_strokeWidth);
            setTextColor(_strokeColor);
            //draw text stroke
            super.onDraw(canvas);
            //revert the color back to the one
            //initially specified
            setTextColor(currentTextColor);
            //removeC_fill();
        }else  if (isGrd_fill>0)
        {
           // getPaint().clearShadowLayer();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(_strokeWidth);
            getPaint().setShader(new LinearGradient(0, 0, 0, getHeight(),_strokeColor ,_strokeColor, Shader.TileMode.CLAMP ));
            super.onDraw(canvas);

// draw the gradient filled text
            getPaint().setStyle(Paint.Style.FILL);
            getPaint().setShader(new LinearGradient(0, 0, 0, getHeight(), fgradClr, sgradClr, Shader.TileMode.CLAMP ));
//getPaint().setStrokeWidth(32);
            super.onDraw(canvas);
            //remove_G_fill();


        }else if (isPic_fill>1)
        {
            Shader shader =getPaint().getShader();
            getPaint().setShader(null);
            getPaint().setShadowLayer(20,10,10, Color.BLACK);
            super.onDraw(canvas);
            getPaint().clearShadowLayer();
            getPaint().setShader(shader);
            super.onDraw(canvas);


        }

        else {
            super.onDraw(canvas);
        }
    }//on draw close
    public static int dpToPx(Context context, float dp)
    {
        final float scale= context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}//custom close
