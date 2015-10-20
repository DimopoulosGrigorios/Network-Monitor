package GUI;

import internalmemory.InternalMemory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

//Kalesma tou event otan patietai to koumpi "Print Malicious Patterns" gia 
//thn emfanish twn Malicious Patterns pou exei orisei o admin sthn eswterikh mnhmh tou server
public class PrintPatternEvent implements ActionListener {
		private InternalMemory Memory;
		private JLabel print2;
		
		public PrintPatternEvent(InternalMemory m, JLabel l){
			this.Memory = m;
			this.print2 = l;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = "<html><body>";

			for (String temp : Memory.getMalList().getMalPatterns()) {
				input = input + temp + "<br>";
			}
			input = input + "</html></body>";
			print2.setText(input);

		}

	}