import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayWAV {
   JFrame frame;
   JTextField txtName;
   JButton btnPlay, btnStop; 
   JButton btnSelectFile;
   JButton btnInitCache, btnPlayCache;

   PlayInterface play;

   public PlayWAV(String playClass) {
      try {
         play = (PlayInterface)Class.forName(playClass).newInstance();
      } catch (Exception ex) {
          IllegalArgumentException iae = new IllegalArgumentException(ex.getMessage());
          iae.initCause(ex);
          throw iae;
      }

      JLabel lblName = new JLabel("File");
      txtName = new JTextField("", 20);
      //txtName.setMaximumSize(txtName.getPreferredSize());

      btnSelectFile = new JButton("...");
      btnSelectFile.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           btnSelectFileClicked(e);
         }
      });
      btnSelectFile.setPreferredSize(new Dimension(18, 18));

      btnPlay = new JButton("Play");
      btnPlay.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           btnPlayClicked(e);
         }
      });

      btnStop = new JButton("Stop");
      btnStop.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           btnStopClicked(e);
         }
      });

      btnInitCache = new JButton("InitCache");
      btnInitCache.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           btnInitCacheClicked(e);
         }
      });
      btnPlayCache = new JButton("PlayCache");
      btnPlayCache.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           btnPlayCacheClicked(e);
         }
      });


      //JFrame.setDefaultLookAndFeelDecorated(true);
      frame = new JFrame("PlayAudio");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      frame.addWindowListener(new WindowListener() {
         public void windowActivated(WindowEvent e) { }
         public void windowClosed(WindowEvent e) { 
            dispose();
         }
         public void windowClosing(WindowEvent e) { }
         public void windowDeactivated(WindowEvent e) { }
         public void windowDeiconified(WindowEvent e) { }
         public void windowIconified(WindowEvent e) { }
         public void windowOpened(WindowEvent e) { }
      });

      Container cp = frame.getContentPane();
      SpringLayout layout = new SpringLayout();
      cp.setLayout(layout);
      cp.add(lblName);
      cp.add(txtName);
      cp.add(btnSelectFile);
      cp.add(btnPlay);
      cp.add(btnStop);
      cp.add(btnInitCache);
      cp.add(btnPlayCache);

      layout.putConstraint(SpringLayout.WEST, lblName, 5, SpringLayout.WEST, cp);
      layout.putConstraint(SpringLayout.NORTH, lblName, 5, SpringLayout.NORTH, cp);
      layout.putConstraint(SpringLayout.WEST, txtName, 5, SpringLayout.EAST, lblName);
      layout.putConstraint(SpringLayout.NORTH, txtName, 5, SpringLayout.NORTH, cp);
      layout.putConstraint(SpringLayout.WEST, btnSelectFile, 3, SpringLayout.EAST, txtName);
      layout.putConstraint(SpringLayout.NORTH, btnSelectFile, 5, SpringLayout.NORTH, cp);

      layout.putConstraint(SpringLayout.WEST, btnPlay, 5, SpringLayout.WEST, cp);
      layout.putConstraint(SpringLayout.NORTH, btnPlay, 5, SpringLayout.SOUTH, lblName);
      layout.putConstraint(SpringLayout.WEST, btnStop, 5, SpringLayout.EAST, btnPlay);
      layout.putConstraint(SpringLayout.NORTH, btnStop, 5, SpringLayout.SOUTH, lblName);

      layout.putConstraint(SpringLayout.WEST, btnInitCache, 5, SpringLayout.WEST, cp);
      layout.putConstraint(SpringLayout.NORTH, btnInitCache, 5, SpringLayout.SOUTH, btnPlay);
      layout.putConstraint(SpringLayout.WEST, btnPlayCache, 5, SpringLayout.EAST, btnInitCache);
      layout.putConstraint(SpringLayout.NORTH, btnPlayCache, 5, SpringLayout.SOUTH, btnPlay);

      // size contentpane itselef +5/+5 relative to embedded components
      //layout.putConstraint(SpringLayout.EAST, cp, 5, SpringLayout.EAST, txtName);
      //layout.putConstraint(SpringLayout.SOUTH, cp, 5, SpringLayout.SOUTH, btnPlay);
      
      frame.pack();
      frame.setSize(300,300);
      frame.setLocationRelativeTo(null); // open to desktop center
      frame.setVisible(true);
   }

  
   public void btnPlayClicked(ActionEvent e) {
      try {
         //AppletAudioPlay.play(frame, txtName.getText());
         //JOALAudioPlay.play(frame, txtName.getText());
         //AudioAPIPlay.play(frame, txtName.getText());
         //MP3Play.play(frame, txtName.getText());
         play.play(frame, txtName.getText());
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public void btnStopClicked(ActionEvent e) {
      try {
         //AppletAudioPlay.stop();
         //JOALAudioPlay.stop();
         //AudioAPIPlay.stop();
         //MP3Play.stop();
         play.stop();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private void dispose() {
       //AppletAudioPlay.dispose();
       //JOALAudioPlay.dispose();
       //AudioAPIPlay.dispose();
       //MP3Play.dispose();
       play.dispose();
   }

   public void btnInitCacheClicked(ActionEvent e) {
      try {
         play.init(this, txtName.getText());
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public void btnPlayCacheClicked(ActionEvent e) {
      try {
         play.play();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public void btnSelectFileClicked(ActionEvent e) {
      FileDialog fd = new FileDialog(frame, "Select audio file", FileDialog.LOAD);
      fd.setVisible(true);
      String file = fd.getFile();
      if (file == null) 
         return;
      file = fd.getDirectory() + file;
      txtName.setText(file);      
   }

   public static void main(String[] args) throws Exception {
     PlayWAV pw = new PlayWAV(args[0]);
   }

}
