package br.com.pingpongmadness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class GameSplash extends Activity {
	// tempo para esperar durante a tela de aprensetação
    private static final int SPLASH_SCREEN_DELAY = 3000;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash); 
        
        new Thread() {
            @Override
            public void run() {
                try {
                    // fazer as inicializacões aqui

                    // espere alguns segundos antes de ir para a próxima tela
                    sleep(SPLASH_SCREEN_DELAY);
                }
                catch (InterruptedException e) {

                }
                catch (Exception e) {

                }
                finally {
                	// #FEATURE Mode: se tiver mais de um tem o GameSelect, se for só um já vai direto a tela do Mode 
                    // inicie a tela de selecão de level
                    Intent intentSelect = new Intent(GameSplash.this, GameSelect.class);
                    System.out.println("startActivity(intentSelect)...");
                    startActivity(intentSelect);
                }
            }
        }.start();

    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // ignore qualquer tecla pressionada na tela de apresentacao
        return true;
    }
    
}
