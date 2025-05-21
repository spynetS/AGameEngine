package com.example;

import javax.swing.JFrame;

public class GameApplication extends JFrame{

	private Scene selectedScene;

	public Scene getSelectedScene() {return selectedScene;}


    public GameApplication(){
		this.setSize(540,420);
		this.setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSelectedScene(new Scene(this));
	}

	public void setSelectedScene(Scene newScene){
		selectedScene = newScene;
		add(newScene, java.awt.BorderLayout.CENTER);
		newScene.start();
	}




}
