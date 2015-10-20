package GUI;

import internalmemory.InternalMemory;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Event pou kaleitai otan kapoios epileksei thn epilogh Insert in Memory apto menu
public class InsertMemory implements ActionListener {

	private JPanel panel;
	private Gui gui;

	private InternalMemory Memory;

	private GridBagConstraints cb;

	public InsertMemory(Gui gui, JPanel panel, InternalMemory Memory) {
		this.gui = gui;

		this.Memory = Memory;
		this.panel = panel;

		cb = new GridBagConstraints();
		cb.insets = new Insets(5, 5, 5, 5);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.updateUI();
		gui.revalidate();
		gui.repaint();

		startActionsInsertInMemory();

	}

	public void startActionsInsertInMemory() {
		JLabel label, label2, label3, label4, print, print2;
		JButton button, button2, button3, button4;
		JTextField textfield, textfield2;
		JLabel title = new JLabel(
				"Insert malicious ips/patterns manually in Internal Memory");
		cb.gridx = 0;
		cb.gridy = 0;
		cb.gridwidth = 4;
		panel.add(title, cb);
		cb.gridwidth = 1;

		label = new JLabel("Insert Malicious IPs");
		cb.gridx = 0;
		cb.gridy = 2;
		panel.add(label, cb);

		textfield = new JTextField(30);
		cb.gridx = 0;
		cb.gridy = 3;
		panel.add(textfield, cb);

		button = new JButton("OK");
		cb.gridx = 0;
		cb.gridy = 4;
		panel.add(button, cb);

		label2 = new JLabel("");
		cb.gridx = 0;
		cb.gridy = 5;
		panel.add(label2, cb);

		label3 = new JLabel("Insert Malicious Patterns");
		cb.gridx = 1;
		cb.gridy = 2;
		panel.add(label3, cb);

		textfield2 = new JTextField(30);
		cb.gridx = 1;
		cb.gridy = 3;
		panel.add(textfield2, cb);

		button2 = new JButton("OK");
		cb.gridx = 1;
		cb.gridy = 4;
		panel.add(button2, cb);

		label4 = new JLabel("");
		cb.gridx = 1;
		cb.gridy = 5;
		panel.add(label4, cb);

		button3 = new JButton("Print Admin's Malicious IPs");
		cb.gridx = 0;
		cb.gridy = 10;
		panel.add(button3, cb);

		print = new JLabel("");

		cb.gridx = 0;
		cb.gridy = 11;
		panel.add(print, cb);

		button4 = new JButton("Print Admin's Malicious Patterns");
		cb.gridx = 1;
		cb.gridy = 10;
		panel.add(button4, cb);

		print2 = new JLabel("");
		cb.gridx = 1;
		cb.gridy = 11;
		panel.add(print2, cb);
		gui.validate();
		gui.repaint();

		IPEvent event = new IPEvent(textfield, Memory, label2);
		button.addActionListener(event);
		PatternEvent event2 = new PatternEvent(textfield2, label4, Memory);
		button2.addActionListener(event2);
		PrintIPEvent event3 = new PrintIPEvent(Memory, print);
		button3.addActionListener(event3);
		PrintPatternEvent event4 = new PrintPatternEvent(Memory, print2);
		button4.addActionListener(event4);

	}
}