package tu_darmstadt.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

import tu_darmstadt.sudoku.game.GameCell;

/**
 * Created by TMZ_LToP on 10.11.2015.
 */
public class SudokuCellView extends View {

    GameCell mGameCell;
    int mWidth;
    int mSectionHeight;
    int mSectionWidth;
    int mRow;
    int mCol;
    boolean touched;
    boolean selected;
    public boolean[] notes;


    public SudokuCellView(Context context) {
        super(context);
    }

    public SudokuCellView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setSelected(boolean b) {
        this.selected = b;
    }

    public void setValues (int width, int sectionHeight, int sectionWidth, GameCell gameCell) {
        mSectionHeight = sectionHeight;
        mSectionWidth = sectionWidth;
        mGameCell = gameCell;
        mWidth = width;
        mRow = gameCell.getRow();
        mCol = gameCell.getCol();
        notes = gameCell.getNotes();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(mGameCell == null) return false;

        touched = true;

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWidth, mWidth);
        params.topMargin = mRow*mWidth;
        params.leftMargin = mCol*mWidth;
        this.setLayoutParams(params);

        if(mGameCell == null) {
            return;
        }
        drawBackground(canvas);
        drawValue(canvas);
    }

    public void drawBackground(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        RectF rect = new RectF(3, 3, mWidth-3, mWidth-3);
        canvas.drawRect(rect, p);

        if(touched) {
            p.setColor(Color.GREEN);
            RectF rectTouched = new RectF(3, 3, mWidth-3, mWidth-3);
            canvas.drawRect(rectTouched, p);
        }
    }

    public void drawValue(Canvas canvas) {
        Paint p = new Paint();
        int j=3;
        int k = 3;
        if(mGameCell.getValue() == 0) {
            for (int i = 0; i < mGameCell.getNotes().length; i++) {
                if (mGameCell.getNotes()[i]) {
                    p.setTypeface(Typeface.SANS_SERIF);
                    p.setTextSize(mWidth * 3 / 12);
                    p.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(i+1),(mWidth*1/12)*k,(mWidth*1/12)*j,p);
                    k+=4;
                    if (k > 11) {
                        k = 3;
                        j +=4;
                    }
                    /*canvas.drawText(String.valueOf(1), (mWidth * 1 / 12)*3, (mWidth* 1 / 12)*3, p);
                    canvas.drawText(String.valueOf(2),(mWidth*1/12)*7, (mWidth* 1 / 12)*7,p );
                    canvas.drawText(String.valueOf(3),(mWidth*1/12)*11, (mWidth* 1 / 12)*11,p );*/
                }
            }
            return;
        }


        if (mGameCell.isFixed()) {
            p.setTypeface(Typeface.DEFAULT_BOLD);
        }
        p.setAntiAlias(true);
        p.setTextSize(Math.min(mWidth * 3 / 4, mWidth * 3 / 4));
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(mGameCell.getValue()), mWidth / 2, mWidth / 2 + mWidth / 4, p);
    }

    public int getColumn() {
        return mCol;
    }

    public int getRow() {
        return mRow;
    }


}
