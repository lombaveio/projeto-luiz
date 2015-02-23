package br.edu.ifms.apis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ExecutaNotificacao extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Necessario apenas se Notification.FLAG_AUTO_CANCEL nao foi usada
		// Necessario saber o id da notificacao a ser removida
		//NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//nm.cancel(R.string.app_name);
		
		String extra = this.getIntent().getStringExtra("extraString");
		
		TextView text = new TextView(this);
		text.setText("Usuario selecionou a notificacao. A string extra é '" + extra + "'");
		setContentView(text);
	}

}