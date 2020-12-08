package standardclasses;

import android.graphics.drawable.Drawable;

public class SnowtamDecodeObject {

    private String text;
    private Drawable drawable;

    public SnowtamDecodeObject(String text, Drawable drawable) {
        this.text = text;
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}

