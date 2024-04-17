package emailchecker;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Popup extends JDialog{

	public Popup(Frame owner, String message, String titolo, ImageIcon icona){
		super(owner, titolo, true);
		Container cont = getContentPane();

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					dispose();
				}
			}
		);
		ok.setMnemonic('O');
		getRootPane().setDefaultButton(ok);

		JLabel immagine = new JLabel(icona);
		JLabel messaggio = new JLabel(message);

		cont.setLayout(new GridBagLayout());

		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(10,10,10,10);
		cont.add(immagine, constr);

		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 0;
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.insets = new Insets(10,10,10,20);
		constr.ipadx = 50;
		cont.add(messaggio, constr);

		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridwidth = 2;
		constr.insets = new Insets(0,10,10,10);
		cont.add(ok, constr);

		pack();
		setLocationRelativeTo(null);
	}
}