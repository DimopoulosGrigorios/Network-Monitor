package GUI;

import internalmemory.InternalMemory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;


//Kalesma tou event eisagetai mia nea IP sto textfield
//h nea IP pernietai sthn eswterikh mnhmh tou server
public class IPEvent implements ActionListener {
		private JTextField textfield;
		private InternalMemory Memory;
		private JLabel label2;
		
		public IPEvent(JTextField t, InternalMemory m, JLabel l){
			this.textfield=t;
			this.Memory=m;
			this.label2=l;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = textfield.getText();
			Memory.insertFromGuiIPs(input);
			label2.setText("Inserted: " + input);
			textfield.setText("");

		}

	}