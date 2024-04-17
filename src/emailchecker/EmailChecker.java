package emailchecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import javax.mail.*;
import sun.audio.*;
import java.io.*;
import com.jeans.trayicon.*;

public class EmailChecker{// implements ActionListener {
	public EmailChecker() {
		Timer timerGc = new Timer(60000, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					System.runFinalization();
					System.gc();
					}
			});
		timerGc.start();

		try {
			disattivato = new ImageIcon("img/disattivato.png").getImage();
			vecchio = new ImageIcon("img/vecchio.png").getImage();
			nuovo = new ImageIcon("img/nuovo.png").getImage();
			controllo = new ImageIcon("img/controllo.png").getImage();

			WindowsTrayIcon.keepAlive();
			wti = new WindowsTrayIcon(vecchio, 16, 16);
			impostazioni = new Impostazioni(this);
			numeroMessaggiUltimoControllo = impostazioni.getNumeroMessaggi();
			creaMenu();
			wti.setToolTipText("EmailChecker v1.0\nby Piccinini Matteo");
			wti.setVisible(true);
			inizializza();
			controllo();
/*			wti.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (!impostazioni.isVisible()){
						impostazioni.show();
					}
				}
			});
*/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void disattiva() {

		try{
			timer.stop();
			wti.setImage(disattivato, 16, 16);
			attiva.setVisible(true);
			disattiva.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void attiva() {
		timer.start();
		try {
			if (actual == VECCHIO) {
				wti.setImage(vecchio, 16, 16);
			} else {
				wti.setImage(nuovo, 16, 16);
			}
		attiva.setVisible(false);
		disattiva.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void creaMenu() {
		disattiva = new JMenuItem("Disattiva", new ImageIcon("img/disattivato.png"));
		attiva = new JMenuItem("Attiva", new ImageIcon("img/start.png"));
		attiva.setVisible(false);

		JMenuItem configura = new JMenuItem("Configura", new ImageIcon("img/conf.png"));
		JMenuItem esci = new JMenuItem("Esci", new ImageIcon("img/exit.png"));
		JMenuItem controllaAdesso = new JMenuItem("Controlla Adesso", new ImageIcon("img/controllo.png"));

		configura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				impostazioni.show();
			}
		});

		controllaAdesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				controllo();
			}
		});

		esci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				disattiva();
				wti.cleanUp();
				System.exit(0);
			}
		});

		disattiva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				disattiva.setVisible(false);
				attiva.setVisible(true);
				disattiva();
			}
		});

		attiva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				disattiva.setVisible(true);
				attiva.setVisible(false);
				attiva();
			}
		});

		popup = new SwingTrayPopup();
		/*popup.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent evt){
					mouseEntered = true;
			}

			public void mouseExited(MouseEvent evt){
					mouseEntered = false;
			}

			public void mouseReleased(MouseEvent evt){
					if (popup.isVisible() && !mouseEntered)popup.setVisible(false);
			}

		});
*/

		attiva.setToolTipText("Riprende il controllo della posta");
		disattiva.setToolTipText("Sospende il controllo dei messaggi");
		controllaAdesso.setToolTipText("Forza un controllo immediato");
		configura.setToolTipText("Imposta la connessione con il server");
		esci.setToolTipText("Esce dal programma");

		popup.add(attiva);
		popup.add(disattiva);

		popup.addSeparator();
		popup.add(controllaAdesso);
		popup.add(configura);
		popup.add(esci);
		wti.setPopup(null);
		popup.setTrayIcon(wti);
		WindowsTrayIcon.setAlwaysOnTop(popup, true);
	}

	public void inizializza() {
		if (timer != null){
			timer.stop();
			//timer.removeActionListener(this);
			timer = null;
		}

		user = impostazioni.getUsername();
		pwd = impostazioni.getPassword();
		srv = impostazioni.getHost();
		protocol = impostazioni.getProtocol();
		port = impostazioni.getPort();
		sleep = impostazioni.getDelay() * 1000;

		timer = new Timer(sleep, new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					controllo();
				}
			});
		timer.start();
	}

	public void nuoviMessaggi(int nuovi) {
		try{
			wti.setImage(nuovo, 16, 16);

			if (impostazioni.playSound()){
				InputStream in = new FileInputStream(impostazioni.getSoundFile());
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (impostazioni.showPopup()){
			ImageIcon icon = new ImageIcon("img/mail_generic.png");
			if (nuovi == 1){
				Popup pw = new Popup(impostazioni, "Hai 1 nuovo messaggio", "Nuovi Messaggi", icon);
				managePopupWindow( pw );
				//JOptionPane.showMessageDialog(impostazioni,	"Hai 1 nuovo messaggio","Nuovi messaggi", JOptionPane.INFORMATION_MESSAGE, icon);
			}else{
				Popup pw = new Popup(impostazioni, "Hai "+ nuovi+" nuovi messaggi", "Nuovi Messaggi", icon);
				managePopupWindow( pw );
				//JOptionPane.showMessageDialog(impostazioni,	"Hai " + nuovi + " nuovi messaggi","Nuovi messaggi", JOptionPane.INFORMATION_MESSAGE, icon);
			}
		}

	}

/*	public void actionPerformed(ActionEvent evt) {
		controllo();
	}
*/
	public void controllo(){
		//Se ci sono errori nelle impostazioni mostro il pannello di configurazione
		if 	(impostazioni.errors()){
			disattiva();
			impostazioni.show();
			return;
		}


		MailServer ms = new MailServer();
		try {
			wti.setImage(controllo, 16, 16);

			ms.connect(protocol, user, pwd, srv, port);
			int nuovi = ms.getUnreadMessageCount();
			ms.disconnect();

			//if (numeroMessaggiUltimoControllo == -1)numeroMessaggiUltimoControllo = nuovi;

			if (nuovi > numeroMessaggiUltimoControllo){
				wti.setImage(nuovo, 16, 16);
				nuoviMessaggi(nuovi - numeroMessaggiUltimoControllo);
			}else{
				wti.setImage(vecchio, 16, 16);
			}
			numeroMessaggiUltimoControllo = nuovi;
			impostazioni.setNumeroMessaggi(numeroMessaggiUltimoControllo);
			impostazioni.salva();
			wti.setToolTipText("EmailChecker v1.0\nby Piccinini Matteo\n\nHai " + numeroMessaggiUltimoControllo +" messaggi");
		}catch(MessagingException e){
			disattiva();
			e.printStackTrace();
			ImageIcon icon = new ImageIcon("img/error.png");
			JOptionPane.showMessageDialog(impostazioni, e.getLocalizedMessage(), "Errore EmailChecker: ", JOptionPane.ERROR_MESSAGE, icon);
		} catch (Throwable e) {
			disattiva();
			e.printStackTrace();
			ImageIcon icon = new ImageIcon("img/error.png");
			JOptionPane.showMessageDialog(impostazioni, e.getLocalizedMessage(), "Errore Generale EmailChecker: ", JOptionPane.ERROR_MESSAGE, icon);
		}finally{
			ms.disconnect();
		}

	}

	public static void main(String[] args) {
		if (WindowsTrayIcon.isRunning("EmailChecker v1.0\nby Piccinini Matteo")) {
			JOptionPane.showMessageDialog(null, "EmailChecker è già in esecuzione, questa sessione verrà terminata", "Avviso: ", JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
		}
		WindowsTrayIcon.initTrayIcon("EmailChecker v1.0\nby Piccinini Matteo");
		new EmailChecker();
	}

	private void managePopupWindow(Popup newWindow){
		if (popupWindow != null)popupWindow.dispose();
		popupWindow = newWindow;
		popupWindow.show();
	}


	private static int VECCHIO = 0;
	private static int NUOVO = 1;
	private static int DISATTIVATO = 2;
	private int actual = 0;
	private String user, pwd, srv,  protocol;
	private int port, delay;
	private int sleep = 300000; //5 minuti

	boolean enabled, mouseEntered;

	private WindowsTrayIcon wti;
	private Image vecchio, nuovo, disattivato, controllo;
	private static Impostazioni impostazioni;
	private Timer timer;
	//private int numeroMessaggiUltimoControllo = -1;
	private int numeroMessaggiUltimoControllo = 0;
	private SwingTrayPopup popup;
	private Popup popupWindow = null;
	private JMenuItem attiva, disattiva;
}