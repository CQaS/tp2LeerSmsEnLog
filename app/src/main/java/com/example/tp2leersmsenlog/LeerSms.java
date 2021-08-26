package com.example.tp2leersmsenlog;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class LeerSms extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final Uri sms = Telephony.Sms.CONTENT_URI;
        final ContentResolver conRe = getContentResolver();

        Runnable ver = new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    Cursor cr = conRe.query(sms, null, null, null, null);

                    if(cr.getCount() > 0)
                    {
                        int i = 0;
                        while (cr.moveToNext() && i<5)
                        {
                            String num = cr.getString(cr.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                            Date date = new Date(Long.parseLong(cr.getString(cr.getColumnIndex(Telephony.Sms.Inbox.DATE))));
                            String lectura = cr.getString(cr.getColumnIndex(Telephony.Sms.Inbox.BODY));

                            Log.d("salida", "Nro: " +num+ ". Fecha: " +date+ ". SMS: "+lectura);
                        }
                        try
                        {
                            Thread.sleep(9000);
                        }
                        catch(InterruptedException e)
                        {
                            Log.e("Error",e.getMessage());

                        }

                    }
                }

            }
        };

        Thread leerMensajes = new Thread(ver);
        leerMensajes.start();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
