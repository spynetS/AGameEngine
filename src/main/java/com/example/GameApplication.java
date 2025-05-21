package com.example;

import javax.swing.JFrame;

public class GameApplication extends JFrame{

	private Scene selectedScene;

	public Scene getSelectedScene() {return selectedScene;}


    public GameApplication(){
		this.setSize(840,520);
		this.setVisible(true);
		System.setProperty("sun.java2d.opengl", "true");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSelectedScene(new Scene(this));
	}

	public void setSelectedScene(Scene newScene){
		selectedScene = newScene;
		add(newScene, java.awt.BorderLayout.CENTER);
		newScene.start();
	}

}
