package dev.mirror.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HalfHeightRelativeLayout extends RelativeLayout
{
	public HalfHeightRelativeLayout(Context context)
	{
		super(context);
	}

	public HalfHeightRelativeLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public HalfHeightRelativeLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = width / 2;
		int w_mode = MeasureSpec.getMode(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec,
				MeasureSpec.makeMeasureSpec(height, w_mode));
		setMeasuredDimension(width, height);
	}
}