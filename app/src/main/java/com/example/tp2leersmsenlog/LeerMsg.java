package com.example.tp2leersmsenlog;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

public class LeerMsg extends Service
{
    public LeerMsg()
    { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Runnable Leer = new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    //Cursor cr = conRe.query(sms, null, null, null, null);
                    Cursor cr = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);

                    if(cr.getCount() > 0)
                    {
                        while (cr.moveToNext() && cr.getPosition() < 5)
                        {
                            String num = cr.getString(cr.getColumnIndex(Telephony.Sms.ADDRESS));
                            Date date = new Date(Long.parseLong(cr.getString(cr.getColumnIndex(Telephony.Sms.DATE))));
                            String lectura = cr.getString(cr.getColumnIndex(Telephony.Sms.BODY));

                            Log.d("salida", "Nro: " +num+ ". Fecha: " +date+ ". SMS: "+lectura);
                        }

                        cr.close();

                        try
                        {
                            Thread.sleep(9000);
                        }
                        catch(InterruptedException e)
                        {
                            Log.e("Error", e.getMessage());
                            break;
                        }

                    }
                    else
                    {
                        Log.d("salida", "No hay mensajes");
                    }
                }
            }
        };

        Thread trabajador= new Thread(Leer);
        trabajador.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}