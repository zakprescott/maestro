import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.awt.BorderLayout;

public class Maestro {
	JFrame frame;

	JButton choirButton;
	JButton trumpetButton;
	JButton pianoButton;
	JButton xylophoneButton;

	public static void main(String[] args) {
		Maestro gui = new Maestro();
		gui.go();
	}

	public void go() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		choirButton = new JButton("Choir");
		trumpetButton = new JButton("Trumpet");
		pianoButton = new JButton("Piano");
		xylophoneButton = new JButton("Xylophone");

		choirButton.addActionListener(new ChoirListener());
		trumpetButton.addActionListener(new TrumpetListener());
		pianoButton.addActionListener(new PianoListener());
		xylophoneButton.addActionListener(new XylophoneListener());

		frame.getContentPane().add(choirButton);
		frame.getContentPane().add(pianoButton);
		frame.getContentPane().add(trumpetButton);
		frame.getContentPane().add(xylophoneButton);

		frame.getContentPane().add(BorderLayout.CENTER, choirButton);
		frame.getContentPane().add(BorderLayout.EAST, trumpetButton);
		frame.getContentPane().add(BorderLayout.WEST, pianoButton);
		frame.getContentPane().add(BorderLayout.SOUTH, xylophoneButton);

		frame.setSize(3000, 3000);
		frame.setVisible(true);
	}

	void play(int instrument) {
		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();

			Sequence seq = new Sequence(Sequence.PPQ, 4);

			Track track = seq.createTrack();

			ShortMessage first = new ShortMessage();
			first.setMessage(192, 1, instrument, 0);
			MidiEvent changeInstrument = new MidiEvent(first, 1);
			track.add(changeInstrument);

			ShortMessage a = new ShortMessage();
			a.setMessage(144, 1, 56, 100);
			MidiEvent noteOn = new MidiEvent(a, 1);
			track.add(noteOn);

			ShortMessage b = new ShortMessage();
			b.setMessage(128, 1, 56, 100);
			MidiEvent noteOff = new MidiEvent(b, 16);
			track.add(noteOff);

			player.setSequence(seq);
			player.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	class ChoirListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Maestro.this.play(53);
		}
	}

	class TrumpetListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Maestro.this.play(57);
		}
	}

	class PianoListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Maestro.this.play(1);
		}
	}

	class XylophoneListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Maestro.this.play(14);
		}
	}
}