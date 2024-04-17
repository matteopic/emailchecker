package emailchecker;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.Serializable;
import javax.swing.DefaultButtonModel;

class EnabledButtonModel extends DefaultButtonModel
    implements Serializable
{

    public EnabledButtonModel()
    {
    }

    public void setArmed(boolean b)
    {
        if(isArmed() != b)
        {
            if(b)
                stateMask |= 1;
            else
                stateMask &= -2;
            if(isEnabled())
                fireStateChanged();
        }
    }

    public void setSelected(boolean b)
    {
        if(isSelected() != b)
        {
            if(b)
                stateMask |= 2;
            else
                stateMask &= -3;
            if(isEnabled())
            {
                fireItemStateChanged(new ItemEvent(this, 701, this, b ? 1 : 2));
                fireStateChanged();
            }
        }
    }

    public void setPressed(boolean b)
    {
        if(isPressed() != b)
        {
            if(b)
                stateMask |= 4;
            else
                stateMask &= -5;
            if(isEnabled())
            {
                if(!isPressed() && isArmed())
                    fireActionPerformed(new ActionEvent(this, 1001, getActionCommand()));
                fireStateChanged();
            }
        }
    }

    public void setRollover(boolean b)
    {
        if(isRollover() != b)
        {
            if(b)
                stateMask |= 0x10;
            else
                stateMask &= 0xffffffef;
            if(isEnabled())
                fireStateChanged();
        }
    }

    static final long serialVersionUID = 0xe35a29a6be0a395fL;

}