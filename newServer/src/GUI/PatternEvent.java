package GUI;

import internalmemory.InternalMemory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

//Kalesma tou event eisagetai mia nea IP sto textfield
//h nea IP pernietai sthn eswterikh mnhmh tou server
public class PatternEvent implements ActionListener {

		private JTextField textfield;
		private InternalMemory Memory;
		private JLabel label;
		
		public PatternEvent(JTextField t, JLabel l, InternalMemory m){
			this.textfield = t;
			this.Memory = m;
			this.label = l;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = textfield.getText();
			Memory.insertFromGuiPatterns(input);
			label.setText("Inserted: " + input);
			textfield.setText("");

		}

	}