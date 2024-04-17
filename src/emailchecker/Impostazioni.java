package emailchecker;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import sun.audio.*;
import sun.misc.*;

public class Impostazioni extends JFrame{
	public Impostazioni(EmailChecker email){
		this.email = email;
		setTitle("Impostazione EmailChecher");
		setIconImage(new ImageIcon("img/icona.gif").getImage());

		//Crea i contenitori
		Container container = getContentPane();
		JPanel panel_server = new JPanel();
		JPanel panel_notifica = new JPanel();
		JPanel panel_opzioni = new JPanel();

		container.setLayout(new GridBagLayout());
		panel_server.setLayout(new GridBagLayout());
		panel_notifica.setLayout(new GridBagLayout());
		panel_opzioni.setLayout(new GridBagLayout());

		panel_server.setBorder(BorderFactory.createTitledBorder("Email Server"));
		panel_notifica.setBorder(BorderFactory.createTitledBorder("Quando ci sono nuovi messaggi"));
		panel_opzioni.setBorder(BorderFactory.createTitledBorder("Controllo"));


		//Crea i pulsanti
		//JButton salva = new JButton("Salva");
		JButton salva = new ToolbarButton("Salva");
		//annulla = new JButton("Annulla");
		annulla = new ToolbarButton("Annulla");
		JButton sfoglia = new ToolbarButton(new ImageIcon("img/folder.png"));
		sfoglia.setToolTipText("Sfoglia...");
		JButton play = new ToolbarButton(new ImageIcon("img/kcmsound.png"));
		play.setToolTipText("Ascolta");


		//Crea i campi di testo
		porta = new JTextField();
		server = new JTextField();
		time = new JTextField();
		username = new JTextField();

		password = new JPasswordField();

		protocollo = new JComboBox();
		protocollo.addItem("IMAP");
		protocollo.addItem("POP3");

		sound = new JCheckBox("Emetti un suono");
		soundFile = new JTextField();
		popup = new JCheckBox("Mostra finesta popup");


		//Posiziona i componenti
		GridBagConstraints mostra;

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.insets = new Insets(5,5,0,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(new JLabel("Protocollo"), mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.insets = new Insets(5,5,0,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(protocollo, mostra);


		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.insets = new Insets(5,5,0,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(new JLabel("Mail Server"), mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.insets = new Insets(5,5,0,5);
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.weightx = 1;
		panel_server.add(server, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.insets = new Insets(5,5,0,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(new JLabel("Porta"), mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.anchor = GridBagConstraints.WEST;
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.insets = new Insets(5,5,0,5);
		panel_server.add(porta, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.insets = new Insets(5,5,0,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(new JLabel("Username "), mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.insets = new Insets(5,5,0,5);
		panel_server.add(username, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.insets = new Insets(5,5,5,5);
			mostra.anchor = GridBagConstraints.WEST;
		panel_server.add(new JLabel("Password "), mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.insets = new Insets(5,5,5,5);
		panel_server.add(password, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.anchor = GridBagConstraints.WEST;
			mostra.insets = new Insets(0,0,5,0);
		panel_opzioni.add(new JLabel("Controlla ogni "), mostra);

		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout(1,2));
		tmp.add(time);
		tmp.add(new JLabel(" secondi"));

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.anchor = GridBagConstraints.WEST;
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.insets = new Insets(0,0,5,0);
		panel_opzioni.add(tmp, mostra);


		mostra = new GridBagConstraints();
			mostra.gridx = 0;
		panel_notifica.add(sound, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 1;
			mostra.fill = GridBagConstraints.HORIZONTAL;
			mostra.weightx = 1;
			mostra.ipadx = 150;
			mostra.insets = new Insets(5,5,5,0);
		panel_notifica.add(soundFile, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 2;
			mostra.ipady = -5;
			mostra.insets = new Insets(5,5,5,0);
		panel_notifica.add(sfoglia, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 3;
			mostra.ipady = -5;
			mostra.insets = new Insets(5,5,5,5);
		panel_notifica.add(play, mostra);

		mostra = new GridBagConstraints();
			mostra.gridx = 0;
			mostra.gridwidth = 2;
			mostra.weightx = 1;
			mostra.fill = GridBagConstraints.HORIZONTAL;
		panel_notifica.add(popup, mostra);

		JPanel pulsanti = new JPanel();
		pulsanti.add(salva);
		pulsanti.add(annulla);


		mostra = new GridBagConstraints();
			mostra.gridy = 0;
			mostra.weightx = 1;
			mostra.fill = GridBagConstraints.BOTH;
			mostra.insets = new Insets(10,10,0,10);
		container.add(panel_server, mostra);

		mostra = new GridBagConstraints();
			mostra.gridy = 1;
			mostra.fill = GridBagConstraints.BOTH;
			mostra.insets = new Insets(10,10,0,10);
		container.add(panel_opzioni, mostra);


		mostra = new GridBagConstraints();
			mostra.gridy = 2;
			mostra.fill = GridBagConstraints.BOTH;
			mostra.insets = new Insets(10,10,0,10);
		container.add(panel_notifica, mostra);

		mostra = new GridBagConstraints();
			mostra.gridy = 3;
		container.add(pulsanti, mostra);


		//Crea gli ascoltatori
		salva.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(salva()){
					dispose();
					inizializza();
				}
				}
			}
		);

		annulla.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				load();
				dispose();
				}
			}
		);

		sfoglia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				sfoglia();
				}
			}
		);

		sound.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if (!sound.isSelected())return;
				String sf = soundFile.getText();
					if (sf == null || !new File(sf).exists()){
						sfoglia();
					}
				}
			}
		);


		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				play();
				}
			}
		);


		protocollo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JComboBox tmp = (JComboBox)evt.getSource();
				int index = tmp.getSelectedIndex();
				if (index == 0) porta.setText("143");
				if (index == 1) porta.setText("110");
				}
			}
		);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		load();
	}

	public boolean salva(){
		try{
			port = Integer.parseInt(porta.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,	"La porta immessa non è valida","Attenzione", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		try{
			delay = Integer.parseInt(time.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,	"Il numero di secondi immesso non è valido","Attenzione", JOptionPane.WARNING_MESSAGE);
			return false;
		}



		Properties prop = new Properties();
		prop.setProperty("username", username.getText());
		prop.setProperty("server", server.getText());
		prop.setProperty("delay", time.getText());
		prop.setProperty("port", porta.getText());
		prop.setProperty("protocol", ((String)protocollo.getSelectedItem()).toLowerCase());
		prop.setProperty("sound", String.valueOf(sound.isSelected()));
		prop.setProperty("popup", String.valueOf(popup.isSelected()));
		prop.setProperty("soundFile", soundFile.getText());
		prop.setProperty("numeroMessaggi", String.valueOf(numeroMessaggi));

		try{
			//Provo a criptare la password, se fallisco la salvo in chiaro
			Crypto crypt = new Crypto();
			byte[] pw = crypt.encrypt( new String(password.getPassword()).getBytes("ISO-8859-1") );

			BASE64Encoder encoder = new BASE64Encoder();
			String pwb64 = encoder.encode(pw);

			prop.setProperty("password", pwb64);
		}catch(Exception e){
			prop.setProperty("password", new String(password.getPassword()));
		}

		load(prop); //applico le nuove impostazioni

		try{
			FileOutputStream fos = new FileOutputStream(getConfigFile());
			prop.store(fos, null);
			fos.flush();
			fos.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,	"Errore durante il salvataggio delle impostazioni:\n" + e.getMessage(),"Attenzione", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void load(Properties prop){
		System.out.println("load(Prop)");
		try{
			user = prop.getProperty("username");
			srv = prop.getProperty("server");
			protocol = prop.getProperty("protocol", "imap");

			try{
				//Provo a decriptare la password, se fallisco uso quella che carico
				Crypto crypt = new Crypto();
				pwd = prop.getProperty("password");

				BASE64Decoder decoder = new BASE64Decoder();
				byte[]pwCript = decoder.decodeBuffer(pwd);



				byte[] pw = crypt.decrypt( pwCript );
				pwd = new String(pw);
			}catch(Exception e){
				pwd = prop.getProperty("password");
			}


			try{
				delay = Integer.parseInt(prop.getProperty("delay","300"));
			}catch(NumberFormatException e){
				delay = 300;
			}

			try{
				numeroMessaggi = Integer.parseInt(prop.getProperty("numeroMessaggi","0"));
			}catch(NumberFormatException e){
				numeroMessaggi = 0;
			}

			try{
				port = Integer.parseInt(prop.getProperty("port"));
			}catch(NumberFormatException e){
				if (protocol.equals("imap"))port = 143;
				else if (protocol.equals("pop3"))port = 110;
				else{
					protocol = "imap";
					port = 143;
				}
			}


			file = prop.getProperty("soundFile");
			playSound = new Boolean(prop.getProperty("sound")).booleanValue();
			showPopup = new Boolean(prop.getProperty("popup")).booleanValue();

			username.setText(user);
			password.setText(pwd);
			server.setText(srv);
			time.setText(String.valueOf(delay));
			porta.setText(String.valueOf(port));
			sound.setSelected( playSound );
			popup.setSelected( showPopup );
			soundFile.setText(file);

			protocollo.setSelectedItem(protocol.toUpperCase());
			errors = false;
		}catch(Exception e){
			errors = true;
			System.out.println("-->");
			e.printStackTrace();
			System.out.println("<--");
		}

	}

	private void load(){
		System.out.println("load()");
		try{
			FileInputStream fis = new FileInputStream(getConfigFile());
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();
			load(prop);
			errors = false;
		}catch(FileNotFoundException e){
			errors = true;
		}catch(Exception e){
			errors = true;
			e.printStackTrace();
		}
	}

	public void sfoglia(){
		JFileChooser fc = new JFileChooser("sound");
		fc.showOpenDialog(this);
		File f = fc.getSelectedFile();
		if (f != null){
			soundFile.setText(f.toString());
		}

		File sf = new File(soundFile.getText());
		if (f == null && !sf.exists())sound.setSelected(false);
	}

	public void play(){
		try{
			String sf = soundFile.getText();
			if (sf == null)return;
			if (sf.trim().equals(""))return;
			InputStream in = new FileInputStream(sf);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		}catch(Exception e){}
	}

	private File getConfigFile()throws IOException{
		String userHome = System.getProperty("user.home");
		File configFile = new File(userHome, "emailchecker.properties");
		return configFile;
	}

	private void inizializza(){
		email.inizializza();
	}

	public boolean errors(){ return errors; }

	public String getUsername(){ return user; }

	public String getPassword(){ return pwd; }

	public String getHost(){ return srv; }

	public int getPort(){ return port; }

	public int getDelay(){ return delay; }

	public String getProtocol(){ return protocol; }

	public String getSoundFile(){ return file; }

	public boolean playSound(){ return playSound; }

	public boolean showPopup(){ return showPopup; }

	public void show(){
		getRootPane().setDefaultButton(annulla);
		super.show();
	};

	public static void main (String[]args){
		new Impostazioni(null).show();
	}

	public void setNumeroMessaggi(int numeroMessaggi){ this.numeroMessaggi = numeroMessaggi; }

	public int getNumeroMessaggi(){ return numeroMessaggi; }

	private String user, pwd, srv,  protocol, file;
	private JTextField username, server, time, porta, soundFile;
	private JPasswordField password;
	private JComboBox protocollo;
	private JCheckBox sound, popup;
	private boolean playSound, showPopup, errors;
	private int port, delay, numeroMessaggi;
	private EmailChecker email;
	private JButton annulla;
}