package dev.mirror.library.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import dev.mirror.library.android.R;


@SuppressWarnings("unused")
public class CircleImageView extends ImageView
{
	private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 1;
	private static final int DEFAULT_BORDER_WIDTH = 0;
	private static final int DEFAULT_BORDER_COLOR = -16777216;
	private final RectF mDrawableRect = new RectF();
	private final RectF mBorderRect = new RectF();

	private final Matrix mShaderMatrix = new Matrix();
	private final Paint mBitmapPaint = new Paint();
	private final Paint mBorderPaint = new Paint();

	private int mBorderColor = -16777216;
	private int mBorderWidth = 0;
	private Bitmap mBitmap;
	private BitmapShader mBitmapShader;
	private int mBitmapWidth;
	private int mBitmapHeight;
	private float mDrawableRadius;
	private float mBorderRadius;
	private boolean mReady;
	private boolean mSetupPending;

	public CircleImageView(Context context)
	{
		super(context);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setScaleType(SCALE_TYPE);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleImageView, defStyle, 0);

		this.mBorderWidth = a.getDimensionPixelSize(
				R.styleable.CircleImageView_border_width, 0);
//		this.mBorderColor = a.getColor(R.styleable.CircleImageView_border_color,R.color.red);
		this.mBorderColor = a.getColor(R.styleable.CircleImageView_border_color,-16777216);

		a.recycle();

		this.mReady = true;

		if (this.mSetupPending) {
			setup();
			this.mSetupPending = false;
		}
	}

	public ScaleType getScaleType()
	{
		return SCALE_TYPE;
	}

	public void setScaleType(ScaleType scaleType)
	{
		if (scaleType != SCALE_TYPE)
			throw new IllegalArgumentException(String.format(
					"ScaleType %s not supported.", new Object[] { scaleType }));
	}

	protected void onDraw(Canvas canvas)
	{
		if (getDrawable() == null) {
			return;
		}

		canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.mDrawableRadius,
				this.mBitmapPaint);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.mBorderRadius,
				this.mBorderPaint);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		setup();
	}

	public int getBorderColor() {
		return this.mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		if (borderColor == this.mBorderColor) {
			return;
		}

		this.mBorderColor = borderColor;
		this.mBorderPaint.setColor(this.mBorderColor);
		invalidate();
	}

	public int getBorderWidth() {
		return this.mBorderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		if (borderWidth == this.mBorderWidth) {
			return;
		}

		this.mBorderWidth = borderWidth;
		setup();
	}

	public void setImageBitmap(Bitmap bm)
	{
		super.setImageBitmap(bm);
		this.mBitmap = bm;
		setup();
	}

	public void setImageDrawable(Drawable drawable)
	{
		super.setImageDrawable(drawable);
		this.mBitmap = getBitmapFromDrawable(drawable);
		setup();
	}

	public void setImageResource(int resId)
	{
		super.setImageResource(resId);
		this.mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}

	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable == null) {
			return null;
		}

		if ((drawable instanceof BitmapDrawable)) {
			return ((BitmapDrawable)drawable).getBitmap();
		}
		try
		{
			Bitmap bitmap = null;

			if ((drawable instanceof ColorDrawable)) {
				bitmap = Bitmap.createBitmap(1,
						1, BITMAP_CONFIG);
			} else if ((drawable instanceof LayerDrawable)) {
				TransitionDrawable drawable2 = (TransitionDrawable)drawable;
				Drawable drawable3 = drawable2.getDrawable(drawable2
						.getNumberOfLayers() - 1);
				setImageDrawable(drawable3);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}
			if (bitmap == null) {
				return null;
			}
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap; } catch (OutOfMemoryError e) {
			}
		return null;
	}

	private void setup()
	{
		if (!this.mReady) {
			this.mSetupPending = true;
			return;
		}

		if (this.mBitmap == null) {
			return;
		}

		this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);

		this.mBitmapPaint.setAntiAlias(true);
		this.mBitmapPaint.setShader(this.mBitmapShader);

		this.mBorderPaint.setStyle(Paint.Style.STROKE);
		this.mBorderPaint.setAntiAlias(true);
		this.mBorderPaint.setColor(this.mBorderColor);
		this.mBorderPaint.setStrokeWidth(this.mBorderWidth);

		this.mBitmapHeight = this.mBitmap.getHeight();
		this.mBitmapWidth = this.mBitmap.getWidth();

		this.mBorderRect.set(0.0F, 0.0F, getWidth(), getHeight());
		this.mBorderRadius = Math.min((this.mBorderRect.height() - this.mBorderWidth) / 2.0F,
				(this.mBorderRect.width() - this.mBorderWidth) / 2.0F);

		this.mDrawableRect.set(this.mBorderWidth, this.mBorderWidth, this.mBorderRect.width() -
				this.mBorderWidth, this.mBorderRect.height() - this.mBorderWidth);
		this.mDrawableRadius = Math.min(this.mDrawableRect.height() / 2.0F,
				this.mDrawableRect.width() / 2.0F);

		updateShaderMatrix();
		invalidate();
	}

	private void updateShaderMatrix()
	{
		float dx = 0.0F;
		float dy = 0.0F;

		this.mShaderMatrix.set(null);
		float scale;
		if (this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect.width() *
				this.mBitmapHeight) {
			scale = this.mDrawableRect.height() / this.mBitmapHeight;
			dx = (this.mDrawableRect.width() - this.mBitmapWidth * scale) * 0.5F;
		} else {
			scale = this.mDrawableRect.width() / this.mBitmapWidth;
			dy = (this.mDrawableRect.height() - this.mBitmapHeight * scale) * 0.5F;
		}

		this.mShaderMatrix.setScale(scale, scale);
		this.mShaderMatrix.postTranslate((int)(dx + 0.5F) + this.mBorderWidth,
				(int)(dy + 0.5F) + this.mBorderWidth);

		this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
	}
}