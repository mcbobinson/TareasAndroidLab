package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


import static com.mygdx.game.GameInputProcessor.pos;
import static com.mygdx.game.GameInputProcessor.linea;
import static com.mygdx.game.GameInputProcessor.y;

public class MainGame implements ApplicationListener {
    static int velocidadX = 0; //Velocidad X del carro
    static float carroX = linea[pos]; // Carril en el que el carro estara
    static boolean moverIzquierda = false, moverDerecha = false, moviendo = false; //Si el carro se esta moviendo
    static State state = State.CORRIENDO; //Estado del juego, empieza corriendo
    static int amarilloY1 = 0, amarilloY2 = 1920, velocidad = 20, bombaY, doblebombaY; //Amarillo es la linea amarilla del medio, se hacen 2 de ellas.
    //Velocidad es la velocidad en la que to do se mueve en Y.
    //BombaY y doblebombaY es la posicion en Y de la bomba y doblebomba
    static float bombaX = 5000, doblebombaX = 5000; //Posicion en X de la bomba y doble bomba

    private OrthographicCamera camera; //Camara ortografica

    SpriteBatch batch;
    Texture bomba, doblebomba, carro, camino, lineasamarillas, lineasseparadoras, juegoperdido; //Nombres de las texturas
    boolean bombaOff = false, doblebombaOff = false; //Si la bomba se sale de la pantalla, esto se vuelve True
    Rectangle carroRect,bombaRect, doblebombaRect; //Rectangulos para revisar colisiones
    static Music musica;  //Inicializa la musica

    private int randomInt(int min, int max){ //Funcion (sacada de internet) que retorna un numero al azar entre el minimo y maximo dado como parametro
        Random generator = new Random();
        return generator.nextInt((max-min)+1)+min;
    }

    public static void reset(){ //Reinica to do a sus valores iniciales
        amarilloY1 = 0;
        amarilloY2 = 1920;
        velocidad = 20;
        velocidadX = 0;
        bombaX = 5000;
        doblebombaX = 5000;
        moverIzquierda = false;
        moverDerecha = false;
        moviendo = false;
        pos = 2;
        carroX = linea[pos];
        musica.play(); //Se reinicia la musica
        musica.setLooping(true); //Hace que siempre este sonando
    }

    @Override
    public void create() { //Se llama cuando se empieza el juego
        camera = new OrthographicCamera(1080, 1920); //Se define la resolucion de 1080x1920, mas apropiada para celular
        camera.update(); //Se actualiza la camara ya con las nuevas resoluciones
        musica = Gdx.audio.newMusic(Gdx.files.internal("data/musica.mp3")); //Inicializa la musica
        batch = new SpriteBatch();
        carro = new Texture("Carro.png"); //Se inicializan las texturas, osea los dibujos
        bomba = new Texture("Bomba.png");
        doblebomba = new Texture("Doble_Bomba.png");
        camino = new Texture("Camino.png");
        lineasamarillas = new Texture("LineasAmarillas.png");
        lineasseparadoras = new Texture("LineasSeparadoras.png");
        juegoperdido = new Texture("JuegoPerdido.png");

        musica.play();//Empieza la musica
        musica.setLooping(true); //Hace que la musica siempre siga sonando.
        GameInputProcessor inputProcessor = new com.mygdx.game.GameInputProcessor(); //Se define el InputProcessor
        Gdx.input.setInputProcessor(inputProcessor); //Con esto, todos los comandos se hacen en la clase "GameInputProcessor"
    }

    @Override
    public void resize(int width, int height) {// Se llama si la pantalla cambia de tamaño
        camera.position.set(1080/2, 1920/2,0);
    }

    @Override
    public void render() { //Se llama continuamente mientras el juego este corriendo.
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1); //El fondo es color blanco
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined); //Se usa para definir como funcionara la camara
        switch(state){ //Revisa el estado del juego. Si esta corriendo o si se acabo
            case CORRIENDO: //Si esta corriendo hace esto

                if(bombaOff){ //Si la bomba esta fuera del mapa, crea una nueva
                    bombaY = randomInt(3000, 5000);
                    bombaX = linea[randomInt(0,4)];
                    bombaOff = false;
                }

                if(doblebombaOff){ //Si la dobleBomba esta fuera del mapa, crea una nueva
                    doblebombaY = randomInt(4000, 6000);
                    doblebombaX = linea[randomInt(0,3)];
                    doblebombaOff = false;
                }
                carroRect = new Rectangle(carroX, y, carro.getWidth(), carro.getHeight()); //Se pone un rectangulo sobre el carro para revisar colisiones
                bombaRect = new Rectangle(bombaX, bombaY, bomba.getWidth(), bomba.getHeight()); //Se pone un rectangulo sobre la bomba para revisar colisiones
                doblebombaRect = new Rectangle(doblebombaX, doblebombaY, doblebomba.getWidth(), doblebomba.getHeight()); //Se pone un rectangulo sobre la doblebomba para revisar colisiones
                batch.begin(); //Se empieza a dibujar
                batch.draw(camino, 0f, 0f);
                batch.draw(lineasseparadoras, 0, 0);
                batch.draw(lineasamarillas, 0, amarilloY1);
                batch.draw(lineasamarillas, 0, amarilloY2);
                batch.draw(bomba, bombaX, bombaY);
                batch.draw(doblebomba, doblebombaX, doblebombaY);
                batch.draw(carro, carroX, y);
                batch.end(); //Se termina de dibujar

                amarilloY1 -= velocidad; //Todas las cosas bajan a la velocidad definida
                amarilloY2 -= velocidad;
                bombaY -= velocidad;
                doblebombaY -= velocidad;
                carroX += velocidadX;

                if (amarilloY1 < -1920) { //Si las lineas amarillas se salen de la pantalla se reinician
                    amarilloY1 = 0;
                }
                if (amarilloY2 < 0) { //Lo mismo de arriba
                    amarilloY2 = 1920;
                }
                if(bombaY < -bomba.getHeight()){ //Si la bomba se sale de la pantalla, se reinicia
                    bombaOff = true;
                }
                if(doblebombaY < -doblebomba.getHeight()){ //Si la bomba se sale de la pantalla, se reinicia
                    doblebombaOff = true;
                }
                if(carroRect.overlaps(bombaRect)){ //Si el carro toca la bomba, se acaba
                    state = State.ACABO;
                    reset();
                }
                if(carroRect.overlaps(doblebombaRect)){//Si el carro toca la doblebomba, se acaba
                    state = State.ACABO;
                    reset();
                }
                if(bombaRect.overlaps(doblebombaRect)){ //Si la bomba toca la doblebomba, la doblebomba se mueve para arriba
                    doblebombaY += 400;
                }

                if(moverDerecha){ //El carro se mueve a la derecha hasta que llegue a su posicion
                    moviendo = true;
                    if(carroX <= linea[pos]) {
                        velocidadX = 30;
                    }else {
                        carroX = linea[pos];
                        velocidadX = 0;
                        moverDerecha = false;
                        moviendo = false;
                    }
                }
                if(moverIzquierda) { //El carro se mueve a la izquierda hasta que llegue a su posicion
                    moviendo = true;
                    if (carroX >= linea[pos]) {
                        velocidadX = -30;
                    } else {
                        carroX = linea[pos];
                        velocidadX = 0;
                        moverIzquierda = false;
                        moviendo = false;
                    }
                }
                break;

            case ACABO: //Si se acabo el juego muestra la pantalla de acabado
                musica.dispose();//Se elimina la musica si se pierde
                batch.begin();
                batch.draw(juegoperdido, 0, 0);
                batch.end();
                break;
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
