package dev.mirror.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScrollGridView extends GridView
{
	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public NoScrollGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NoScrollGridView(Context context)
	{
		super(context);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(536870911,
				-2147483648);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}