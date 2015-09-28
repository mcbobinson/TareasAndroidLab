package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import static com.mygdx.game.MainGame.moverIzquierda;
import static com.mygdx.game.MainGame.moverDerecha;
import static com.mygdx.game.MainGame.moviendo;
import static com.mygdx.game.MainGame.state;


public class GameInputProcessor implements InputProcessor{
    static int y = 180; //Posicion en y del carro
    public static int pos = 2; //Posicion inicial del carro
    public static float[] linea = {178.5f, 332.5f, 486.8f, 640.7f, 795.1f}; //Cada uno de los cinco carriles
    boolean toca = true;

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(Gdx.input.getX() + " - " + Gdx.input.getY());
        if(toca) { //Si no hay nada tocando la pantalla
            toca = false;

            switch(state){//Se revisa el estado
                case CORRIENDO: //Si el juego esta corriendo
                        if (Gdx.input.getX() > Gdx.graphics.getWidth()/2 && pos < 4) { //El carro se mueve a la derecha
                            if(!moverIzquierda && !moviendo) {
                                pos += 1;
                                moverDerecha = true;
                            }

                        } else if (Gdx.input.getX() < Gdx.graphics.getWidth()/2 && pos > 0) { //El carro se mueve a la izquierda
                            if(!moverDerecha && !moviendo){
                                pos-=1;
                                moverIzquierda = true;
                            }
                        }
                    break;

                case ACABO: //Si el juego se acabo
                    if(Gdx.input.getX() > 114 && Gdx.input.getX() < 686
                            && Gdx.input.getY() > 454 && Gdx.input.getY() < 584){ //Si apreta en reiniciar
                        state = State.CORRIENDO;
                        MainGame.reset();
                    }else if(Gdx.input.getX() > 114 && Gdx.input.getX() < 686
                            && Gdx.input.getY() > 706 && Gdx.input.getY() < 832){ //Si apreta en terminar
                            System.exit(0);
                    }

             }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        toca = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
