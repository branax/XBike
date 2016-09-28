package player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class KrmilnaEnota {

    private final JFrame frame;
    public final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    public static void main(final String[] args) {
    	
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	KrmilnaEnota pp;
                pp = new KrmilnaEnota(args);
                
                try {
					Client_niti cc = new Client_niti(pp);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
    }

    public KrmilnaEnota(String[] args) {
        frame = new JFrame("Video predvajalnik");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setVisible(true);

        mediaPlayerComponent.getMediaPlayer().playMedia("file:///URL do video//.avi");
        mediaPlayerComponent.getMediaPlayer().skip(1000);
        
    }
}