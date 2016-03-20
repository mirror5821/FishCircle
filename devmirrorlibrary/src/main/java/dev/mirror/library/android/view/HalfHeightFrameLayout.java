package dev.mirror.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class HalfHeightFrameLayout extends FrameLayout
{
	public HalfHeightFrameLayout(Context context)
	{
		super(context);
	}

	public HalfHeightFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HalfHeightFrameLayout(Context context, AttributeSet attrs, int defStyle)
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