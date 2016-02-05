package tk.azertyfun.dasmtc;

import tk.azertyfun.dasmtc.emulator.GenericKeyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LemKeyboard extends JFrame implements KeyListener {

	private GenericKeyboard keyboard;

	public LemKeyboard(GenericKeyboard keyboard) {
		this.keyboard = keyboard;

		this.setTitle("DCPU Emulator Keyboard for techcompliant");
		this.setSize(new Dimension(178, 100));
		this.setResizable(false);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addKeyListener(this);

		this.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				keyboard.pressedKeyCode(0x10);
				break;
			case KeyEvent.VK_ENTER:
				keyboard.pressedKeyCode(0x11);
				break;
			case KeyEvent.VK_INSERT:
				keyboard.pressedKeyCode(0x12);
				break;
			case KeyEvent.VK_DELETE:
				keyboard.pressedKeyCode(0x13);
				break;
			case KeyEvent.VK_UP:
				keyboard.pressedKeyCode(0x80);
				break;
			case KeyEvent.VK_DOWN:
				keyboard.pressedKeyCode(0x81);
				break;
			case KeyEvent.VK_LEFT:
				keyboard.pressedKeyCode(0x82);
				break;
			case KeyEvent.VK_RIGHT:
				keyboard.pressedKeyCode(0x83);
				break;
			case KeyEvent.VK_SHIFT:
				keyboard.pressedKeyCode(0x90);
				break;
			case KeyEvent.VK_CONTROL:
				keyboard.pressedKeyCode(0x91);
				break;
			default:
				keyboard.pressedKey(e.getKeyChar());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void close() {
		setVisible(false);
		dispose();
	}
}
