package GUI;
import internalmemory.InternalMemory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

//Kalesma tou event otan patietai to koumpi "Print Malicious IPs" gia 
//thn emfanish twn Malicious IP pou exei orisei o admin sthn eswterikh mnhmh tou server
public class PrintIPEvent implements ActionListener {

		private InternalMemory Memory;
		private JLabel print;
		
		public PrintIPEvent(InternalMemory m, JLabel l){
			this.print = l;
			this.Memory = m;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = "<html><body>";

			for (String temp : Memory.getMalList().getMalIPs()) {
				input = input + temp + "<br>";
			}
			input = input + "</html></body>";
			print.setText(input);
		}

	}