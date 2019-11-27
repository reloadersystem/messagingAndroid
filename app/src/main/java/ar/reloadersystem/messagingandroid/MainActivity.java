package ar.reloadersystem.messagingandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_notificar;
    int id = 0;
    private NotificationManagerCompat notificationManager;
    int count = 0;
    private MediaSessionCompat mediaSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        mediaSession = new MediaSessionCompat(this, "tag");

        btn_notificar = findViewById(R.id.btn_notificar);
        btn_notificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Prueben cada metodo y descomenten el que desean ver...

        // createSimpleNotification();
        //createSimpleColorIconNotification();
        //createPictureStyleNotification();
        //notificationOpenActivity();
        //notificationCountSound();
        //setBadge(MainActivity.this, 10);
        //notificationDownloadIndeterminate();
        //notificationDownloadPercentage();
        //notificationToast();
        musicNotificationPlay();


        //necesariamente  no es usar un botón, sino un servicio que esta latente en este activity, o en segundo plano corriendo...
        //el servidor manda un mensaje y traes la data del servicio, y  podrias  validar  a que grupo de personas  enviar, y ejecutas tu metodo....
    }


    private void musicNotificationPlay() {

        Notification notification = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_security)
                .setContentTitle("Bob Dylan")
                .setContentText("Mantenimiento de Servidor 12:15 am")
                .setLargeIcon(Util.getBitMapFromAsset(this, "discography.jpg"))
                .addAction(R.drawable.like, "like", null)
                .addAction(R.drawable.ic_skip_previous, "anterior", null)
                .addAction(R.drawable.ic_pause, "pausa", null)
                .addAction(R.drawable.ic_navigate_next, "siguiente", null)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setMediaSession(mediaSession.getSessionToken())
                )
                .setSubText("GoDaddy Service")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.GRAY)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(id, notification);


    }

    private void largeMessage() {

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.dev_android);

        Notification notification = new NotificationCompat.Builder(this, "")
                .setContentTitle("Reloader Maintenance")
                .setContentText("Mantenimiento de Servidor 12:15 am")
                .setSmallIcon(R.drawable.ic_security)
                .setLargeIcon(picture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(id, notification);
    }

    private void notificationToast() {
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadacastIntent = new Intent(this, NotificationReceiver.class);
        broadacastIntent.putExtra("toastMessage", "Conexiones");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadacastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_file_download)
                .setContentTitle("Tramite en Linea")
                .setContentText("Elija la opción")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();
        notificationManager.notify(id, notification);


    }

    private void notificationDownloadPercentage() {

        final int progressMax = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_file_download)
                .setContentTitle("Descarga")
                .setContentText("Descarga en progreso")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, false);

        notificationManager.notify(id, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(2000);

                for (int progress = 0; progress <= progressMax; progress += 10) {
                    notification.setProgress(progressMax, progress, false);
                    notificationManager.notify(id, notification.build());
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Descarga Completada")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                notificationManager.notify(id, notification.build());
            }
        }).start();
    }

    private void notificationDownloadIndeterminate() {

        final int progressMax = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_file_download)
                .setContentTitle("Descarga")
                .setContentText("Descarga en progreso")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, true);

        notificationManager.notify(id, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(2000);

                for (int progress = 0; progress <= progressMax; progress += 20) {
                    //notification.setProgress(progressMax, progress, false);
                    //notificationManager.notify(2, notification.build());
                    SystemClock.sleep(1000);
                }
                notification.setContentText("Descarga Completada")
                        .setProgress(0, 0, false)
                        .setOngoing(false);
                notificationManager.notify(id, notification.build());
            }
        }).start();

    }


    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    private void notificationCountSound() {
        count = count + 1;
        Notification notification = new NotificationCompat.Builder(MainActivity.this, "")
                .setContentTitle("Aerolineas Argentinas")
                .setContentText("Salida Destino Cordoba 12:15 am - Gate 4")
                .setSmallIcon(R.drawable.ic_flight_black)
                .setNumber(count)
                .setVibrate(new long[]{0, 250, 100, 250})
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        NotificationManagerCompat.from(this).notify(id, notification);
        id++; //abre el mensaje en un nuevo slot
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
