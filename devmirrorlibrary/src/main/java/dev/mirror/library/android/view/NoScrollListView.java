package dev.mirror.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoScrollListView extends ListView
{
	public NoScrollListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public NoScrollListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NoScrollListView(Context context)
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