package GUI;

import internalmemory.InternalMemory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import databasesystem.Database;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;

	public Gui(InternalMemory Memory, Database Database) {
		CreateGui(Memory, Database);
	}

	public void CreateGui(InternalMemory Memory, final Database Database) {
		JPanel panel;
		GridBagConstraints cb;
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JMenuItem menuItem2;
		JMenuItem menuItem3;
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		menuBar.add(menu);
		menuItem = new JMenuItem("View Database");
		menuItem2 = new JMenuItem("View Internal Memory");
		menuItem3 = new JMenuItem("Insert in Internal Memory");
		menu.add(menuItem);
		menu.add(menuItem2);
		menu.add(menuItem3);

		panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setBackground(Color.WHITE);
		cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);

		cb.gridx = 0;
		cb.gridy = 0;
		cb.gridwidth = 4;

		setJMenuBar(menuBar);

		JLabel intro = new JLabel("Java Adder Service Application");
		intro.setFont(new Font("Serif", Font.PLAIN, 40));

		panel.add(intro, cb);

		BufferedImage myPicture = null;
		try {
			File currentDir = new File("");
			myPicture = ImageIO.read(new File(currentDir.getAbsolutePath()+"/icon.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			cb.gridx = 0;
			cb.gridy = 1;
			panel.add(picLabel, cb);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		cb.gridheight = 1;
		cb.gridwidth = 1;
		ViewMemory event2 = new ViewMemory(this, panel, Memory);
		menuItem2.addActionListener(event2);
		InsertMemory event3 = new InsertMemory(this, panel, Memory);
		menuItem3.addActionListener(event3);
		ViewDatabase event1 = new ViewDatabase(this, panel, Database);
		menuItem.addActionListener(event1);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame) e.getSource();

				int result = JOptionPane.showConfirmDialog(frame,
						"Are you sure you want to exit the application?",
						"Exit Application", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION)
				{
					Database.drop();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
				}
			}
		});
	}
}
