package com.example;

import javax.swing.JFrame;

public class GameApplication extends JFrame{

	private Scene selectedScene;

	public Scene getSelectedScene() {return selectedScene;}


    public GameApplication(){
		this.setSize(1920/2,1080/2);
		this.setVisible(true);
		System.setProperty("sun.java2d.opengl", "true");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setSelectedScene(Scene newScene){
		selectedScene = newScene;
		add(newScene, java.awt.BorderLayout.CENTER);
		newScene.start();
	}

}
