package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bomba, doblebomba, carro, camino, lineasamarillas, lineasseparadoras;
	float[] linea = {178.5f, 332.5f, 486.8f, 640.7f, 795.1f}; //Cada uno de los cinco carriles
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		carro = new Texture("Carro.png");
		bomba = new Texture("Bomba.png");
		doblebomba = new Texture("Doble_Bomba.png");
		camino = new Texture("Camino.png");
		lineasamarillas = new Texture("LineasAmarillas.png");
		lineasseparadoras = new Texture("LineasSeparadoras.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(camino, 0, 0);
		batch.draw(lineasseparadoras, 0, 0);
		batch.draw(lineasamarillas, 0 ,0);
		batch.draw(carro, linea[2], 100);
		batch.draw(bomba, linea[1], 500);
        batch.draw(doblebomba, linea[3], 800);
		batch.end();
	}
}
