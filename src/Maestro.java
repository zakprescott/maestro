import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;

public class Maestro implements ActionListener {
	JButton button;

	public static void main(String[] args) {
		Maestro gui = new Maestro();
		gui.go();
	}

	public void go() {
		JFrame frame = new JFrame();
		button = new JButton("Trumpet");

		button.addActionListener(this);

		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();

			Sequence seq = new Sequence(Sequence.PPQ, 4);

			Track track = seq.createTrack();

			MidiEvent event = null;

			ShortMessage first = new ShortMessage();
			first.setMessage(192, 1, 60, 0);
			MidiEvent changeInstrument = new MidiEvent(first, 1);
			track.add(changeInstrument);

			ShortMessage a = new ShortMessage();
			a.setMessage(144, 1, 62, 100);
			MidiEvent noteOn = new MidiEvent(a, 1);
			track.add(noteOn);

			ShortMessage b = new ShortMessage();
			b.setMessage(128, 1, 62, 100);
			MidiEvent noteOff = new MidiEvent(b, 16);
			track.add(noteOff);
			player.setSequence(seq);
			player.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}