package ar.reloadersystem.messagingandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_notificar;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_notificar = findViewById(R.id.btn_notificar);

        btn_notificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // createSimpleNotification();
        //createSimpleColorIconNotification();
        //createPictureStyleNotification();
        notificationOpenActivity();

        //necesariamente  no es usar un botón, sino un servicio que esta latente en este activity, o en segundo plano corriendo...
        //el servidor manda un mensaje y traes la data del servicio, y  podrias  validar  a que grupo de personas  enviar, y ejecutas tu metodo....
    }

    private void notificationOpenActivity() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "");
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setColor(getResources().getColor(R.color.colorAccent));
        builder.setLargeIcon(Util.getBitMapFromAsset(this, "reloadericon.png"));
        builder.setContentTitle("Reloader");
        final String texto = "Inicio 7 pm Developers Teams";
        builder.setContentText(texto);

        Intent intent = new Intent(this, QrActivityData.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //ahora toca  cerrar  la notificacion una vez que abrimos un Activity
        builder.setAutoCancel(true); // puedes cerrar la  App, y  usa las  notificación para entrar a otra activity..
        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(id, notification);
    }

    private void createPictureStyleNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "");
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setColor(getResources().getColor(R.color.colorAccent));
        builder.setLargeIcon(Util.getBitMapFromAsset(this, "reloadericon.png"));
        builder.setContentTitle("Reloader");
        Bitmap bitmap = Util.getBitMapFromAsset(this, "androidmessages.jpg");
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle().bigPicture(bitmap);
        style.setSummaryText("Developers");
        builder.setStyle(style);

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(id, notification);
    }

    private void createSimpleColorIconNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setColor(getResources().getColor(R.color.colorAccent));
        builder.setLargeIcon(Util.getBitMapFromAsset(this, "reloadericon.png"));
        builder.setContentTitle("Reloader");

        final String textBig = "Inicio 7 pm Developers Teams";
        final String title = "Solo para Developers";
        //builder.setContentText(textBig);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle().bigText(title);
        style.setSummaryText(textBig);
        builder.setStyle(style);
        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(id, notification);
        id++;
    }

    private void createSimpleNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Reloader");
        String textContent = "Reunión Developers 31/11/2019";
        builder.setContentText(textContent);

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(id, notification);
        id++; // lanza  una  notificación como uno nuevo...
    }
}
