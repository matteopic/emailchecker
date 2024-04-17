package emailchecker;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ToolbarButton extends JButton
{

    public ToolbarButton(String text){
		this(text, null);
	}

    public ToolbarButton(Icon icon)
    {
        this(null, icon);
    }

    public ToolbarButton(String text, Icon icon)
    {
        super(text, icon);
        setModel(new EnabledButtonModel());
        setMargin(new Insets(2, 1, 0, 1));
        setBorderPainted(false);
        setRolloverEnabled(true);
    }

    public void processMouseEvent(MouseEvent e)
    {
        if(e.getID() == 504)
            setBorderPainted(true);
        if(e.getID() == 505)
            setBorderPainted(false);
        super.processMouseEvent(e);
    }

    public Icon getDisabledIcon()
    {
        if(!disabled)
        {
            setDisabledIcon(createDisabledIcon(getIcon()));
            disabled = true;
        }
        return super.getDisabledIcon();
    }

    public boolean isFocusTraversable()
    {
        return super.isFocusable();
    }

    static Icon createDisabledIcon(Icon icon)
    {
        if(!(icon instanceof ImageIcon))
            return null;
        ImageIcon imgIcon = (ImageIcon)icon;
        java.awt.Image img = imgIcon.getImage();
        if(!(img instanceof BufferedImage))
            return null;
        BufferedImage srcImg = (BufferedImage)img;
        int w = srcImg.getWidth();
        int h = srcImg.getHeight();
        int wh = w * h;
        int rgbArray[] = srcImg.getRGB(0, 0, w, h, new int[wh], 0, w);
        GrayFilter grayFilter = new GrayFilter(true, 50);
        for(int i = 0; i < wh; i++)
            rgbArray[i] = grayFilter.filterRGB(0, 0, rgbArray[i]);

        BufferedImage destImg = new BufferedImage(w, h, 2);
        destImg.setRGB(0, 0, w, h, rgbArray, 0, w);
        return new ImageIcon(destImg);
    }

    static final long serialVersionUID = 0x5b198e78243c9fceL;
    boolean disabled;

}