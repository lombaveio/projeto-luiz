package br.edu.ifms.apis;

//importa��o das classes necess�rias para o funcionamento do aplicativo
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.AlarmManager;
import android.os.IBinder;
import android.widget.Toast;

import android.util.Log;


public class MainActivity extends Activity {
	
	//vari�veis que usaremos durante o processo
    private EditText edTime;
    private Button btLocalizar;
    private MediaPlayer mediaPlayer;
    private Location locationAtual;
    
    private Button btComendo;
    private Button btAndando;
    private Button btDeitado;
    private Button btEmPe;
    private Button btRuminandoEmPe;
    private Button btRuminandoDeitado;
    private Button btCalibrar;    
    
    private Button btAmarelo;
    private Button btAzul;
    private Button btVerde;
    private Button btVermelho;
    
    Context contextNot;
    
    public static final String ARQUIVO = "dadosApis.txt";
    
	@Override // M�todo onCreate, iniciada quando o aplicativo � aberto
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);     		 
		setupElements();        
		contextNot = this; // contexto para a notificação
                
        //Thread para ativar a notificação
		Runnable runnable = new Runnable() {
   		 @Override
   		 
   		 	public void run() {
   			 	notificacao();
   		 	}
   		};   	
   		Thread thread = new Thread(runnable);
   		thread.start(); 	
   	
	}
		 @Override
		 public boolean onCreateOptionsMenu(Menu menu) {
		  getMenuInflater().inflate(R.menu.main, menu);
		  return true;
		 }	 

	private void notificacao(){				
		//Temporizador da notificacao
		long total = 0;
		long start = System.currentTimeMillis();
		
		while(total < 300000){ // Espera 5 minutos (60*5*1000) 
			long finish = System.currentTimeMillis();   
			total = finish - start;
		}
		//Fim do Temporizador da notificacao
		
		//Notificacao
		    String extra = "" + (SystemClock.uptimeMillis() / 1000);                		    
            String tickerText = "Anotar comportamento";
            CharSequence titulo = "Atenção";
            CharSequence mensagem = "5 minutos se passaram, anote os comportamentos";                                  
            criarNotificacao(contextNot, tickerText, titulo, mensagem, ExecutaNotificacao.class, extra);        	
          //Fim da Notificacao
            
          //Thread de chamada de uma nova Notificacao
    		Runnable runnable = new Runnable() {
    			@Override
    		 	public void run() {
    			 	notificacao();
    		 	}
    		};
    		Thread thread = new Thread(runnable);
    		thread.start();
    	 }
	
	@Override  
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig);
    } 
			
	private void criarNotificacao(Context context, CharSequence mensagemBarraStatus,CharSequence titulo, CharSequence mensagem,Class<?> activity, String extra)
	{
		Intent i = new Intent(this, activity);
		i.putExtra("extraString", extra);
		PendingIntent p = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

		Notification n = new Notification(R.drawable.ic_launcher, mensagemBarraStatus, System.currentTimeMillis());
		n.setLatestEventInfo(context, titulo, mensagem, p);

		//n.vibrate = new long[] { 100, 250, 100, 500 };

		n.flags = Notification.FLAG_INSISTENT
				| Notification.FLAG_AUTO_CANCEL;

		n.defaults = Notification.DEFAULT_SOUND
				   | Notification.DEFAULT_LIGHTS
				   | Notification.DEFAULT_VIBRATE;
		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(R.string.app_name, n);
			
	}

	//Habilita os botões de comportamento e de identificação dos animais
	public void setBtn(int i){
		if (i == 0){
			btComendo.setEnabled(true);
			btComendo.setVisibility(View.VISIBLE);
			btAndando.setEnabled(true);
			btAndando.setVisibility(View.VISIBLE);
			btDeitado.setEnabled(true);
			btDeitado.setVisibility(View.VISIBLE);
			btEmPe.setEnabled(true);               	
			btEmPe.setVisibility(View.VISIBLE);
			btRuminandoEmPe.setEnabled(true);
			btRuminandoEmPe.setVisibility(View.VISIBLE);
			btRuminandoDeitado.setEnabled(true);
			btRuminandoDeitado.setVisibility(View.VISIBLE);
			
	       	btAzul.setEnabled(false);
	       	btAzul.setVisibility(View.INVISIBLE);
	       	btAmarelo.setEnabled(false);
	       	btAmarelo.setVisibility(View.INVISIBLE);
	       	btVerde.setEnabled(false);
	       	btVerde.setVisibility(View.INVISIBLE);
	       	btVermelho.setEnabled(false);
	       	btVermelho.setVisibility(View.INVISIBLE);
	       	btCalibrar.setEnabled(false);
	       	btCalibrar.setVisibility(View.INVISIBLE);
		}
		else {
			btComendo.setEnabled(false);
			btComendo.setVisibility(View.INVISIBLE);
			btAndando.setEnabled(false);
			btAndando.setVisibility(View.INVISIBLE);
	    	btDeitado.setEnabled(false);
	    	btDeitado.setVisibility(View.INVISIBLE);
	    	btEmPe.setEnabled(false);               
	    	btEmPe.setVisibility(View.INVISIBLE);
	       	btRuminandoEmPe.setEnabled(false);
	       	btRuminandoEmPe.setVisibility(View.INVISIBLE);
	       	btRuminandoDeitado.setEnabled(false);
	       	btRuminandoDeitado.setVisibility(View.INVISIBLE);
	       	       	
	       	btAzul.setEnabled(true);
	       	btAzul.setVisibility(View.VISIBLE);
	       	btAmarelo.setEnabled(true);
	       	btAmarelo.setVisibility(View.VISIBLE);
	       	btVerde.setEnabled(true);
	       	btVerde.setVisibility(View.VISIBLE);
	       	btVermelho.setEnabled(true);
	       	btVermelho.setVisibility(View.VISIBLE);
	       	btCalibrar.setEnabled(true);
	       	btCalibrar.setVisibility(View.VISIBLE);
	       	
	       	estBtn=0;
		}					
	}

	String id;
	int estBtn = 0;
	
	// M�todo usado para importar os elementos da classe R
    public void setupElements(){
    	
        edTime = (EditText) findViewById(R.id.edTime);
        
        mediaPlayer = MediaPlayer.create(this, R.raw.clicksom);
        mediaPlayer.start();
                               
        btLocalizar = (Button) findViewById(R.id.btLocalizar);
        startGPS();
        btLocalizar.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v){
                startGPS();
        }});
        
        btAzul = (Button) findViewById(R.id.btAzul);     	
       	btAzul.setEnabled(false);
        btAzul.setOnClickListener(new Button.OnClickListener() {       	        	
        	public void onClick(View v){   	        		      		
        		if (estBtn==0){
            		id = "Azul";
            		mediaPlayer.start();                        		
            		setBtn(0);
            		estBtn=1;
            	}
            	else{
            		setBtn(1);
            		estBtn=0;
            	}
        }});        
        
        btAmarelo = (Button) findViewById(R.id.btAmarelo);
        btAmarelo.setEnabled(false);
        btAmarelo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	if (estBtn==0){
            		id = "Amarelo";
            		mediaPlayer.start();            	
            		setBtn(0);
            		estBtn=1;
            	}
            	else{
            		setBtn(1);
            		estBtn=0;
            	}
        }});        
        
        btVermelho = (Button) findViewById(R.id.btVermelho);
        btVermelho.setEnabled(false);
        btVermelho.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	if (estBtn==0){
            		id = "Vermelho";
            		mediaPlayer.start();
            		setBtn(0);
            		estBtn=1;
            	}
            	else{
            		setBtn(1);
            		estBtn=0;
            	}
        }});        
        
        btVerde = (Button) findViewById(R.id.btVerde);
        btVerde.setEnabled(false);
        btVerde.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	if (estBtn==0){
            		id = "Verde";
            		mediaPlayer.start();
            		setBtn(0);
            		estBtn=1;
            	}
            	else{
            		setBtn(1);
            		estBtn=0;
            	}
        }});    
        
        
        btComendo = (Button) findViewById(R.id.btComendo);
        btComendo.setVisibility(View.INVISIBLE);
        btComendo.setEnabled(false);
        btComendo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Comendo");
            	mediaPlayer.start();
                setBtn(1);            	
        }});
        
        btAndando = (Button) findViewById(R.id.btAndando);
        btAndando.setVisibility(View.INVISIBLE);
        btAndando.setEnabled(false);
        btAndando.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Andando");
            	mediaPlayer.start();
            	setBtn(1);
        }});
        
        btDeitado = (Button) findViewById(R.id.btDeitado);
        btDeitado.setVisibility(View.INVISIBLE);
        btDeitado.setEnabled(false);
        btDeitado.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Deitado");
            	mediaPlayer.start();
            	setBtn(1);
        }});
        
        btEmPe = (Button) findViewById(R.id.btEmPe);
        btEmPe.setVisibility(View.INVISIBLE);
        btEmPe.setEnabled(false);
        btEmPe.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("EmPe");
            	mediaPlayer.start();
            	setBtn(1);
        }});
        
        btRuminandoEmPe = (Button) findViewById(R.id.btRuminandoEmPe);
        btRuminandoEmPe.setVisibility(View.INVISIBLE);
        btRuminandoEmPe.setEnabled(false);
        btRuminandoEmPe.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("RuminandoEmPe");
            	mediaPlayer.start();
            	setBtn(1);
        }});
        
        btRuminandoDeitado = (Button) findViewById(R.id.btRuminandoDeitado);
        btRuminandoDeitado.setVisibility(View.INVISIBLE);
        btRuminandoDeitado.setEnabled(false);
        btRuminandoDeitado.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("RuminandoDeitado");
            	mediaPlayer.start();
            	setBtn(1);
        }});
        
        btCalibrar = (Button) findViewById(R.id.btCalibrar);     	
        btCalibrar.setVisibility(View.INVISIBLE);
        btCalibrar.setEnabled(false);
        btCalibrar.setOnClickListener(new Button.OnClickListener() {       	        	
        	public void onClick(View v){   	        		      		
        			gravarDados("Calibração");
        			mediaPlayer.start();
        }});        
        
    }
    
    //M�todo que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(){
        LocationManager lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
            	if(locationAtual == null) {
            		setBtn(1);
            		btCalibrar.setEnabled(true);
            	}
            	locationAtual = locat;
            	
                updateView(locat);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
    }
 
    //  M�todo que faz a atualiza��o da tela para o usu�rio.
    public void updateView(Location locat){        
        Long time = locat.getTime();        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zZ");
        edTime.setText(format1.format(new Date(time)));
    }
    
    
    public void gravarDados(String acao){
        
    	String conteudo= "";
        
        Long time = locationAtual.getTime();        
        conteudo += time.toString() +";";                
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zZ");
        conteudo += format1.format(new Date(time)) +";";
        
        conteudo += id + ";"+acao +";";
        		
        gravarArquivo(conteudo);
    }
    
    private void gravarArquivo(String conteudo) {
        try {
            try {
                File f = new File(Environment.getExternalStorageDirectory()+"/"+ARQUIVO);
                if(!f.exists()){
        			f.createNewFile();
        		}
                FileOutputStream out = new FileOutputStream(f, true);
                out.write(conteudo.getBytes());
                out.write('\n');
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }  
}