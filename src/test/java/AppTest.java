package com.engine;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.JFrame;

import com.engine.GameApplication;

public class AppTest {

	@Test
    public void testApp() {
		JFrame frame = new JFrame();
		frame.setSize(540,250);
		frame.setVisible(true);
    }
}
