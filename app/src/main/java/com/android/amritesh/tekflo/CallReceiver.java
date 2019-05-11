package com.android.amritesh.tekflo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.DisconnectCause;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static String savedNumber;
    private static boolean isIncoming;
    private static Date callStartTime;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");


        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);   //to get state
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER); //to fetch number on incoming calls
            String cause = null;
            int state = 0;//to store the state
            //we need to check for the current state of the call
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            //to get to know the call connection status
            onCallStateChanged(context, state, number);

        }

    }

    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            return;

        }
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming=true;
                callStartTime=new Date();
                savedNumber=number;
                Toast.makeText(context, "Incoming Call RINGING", Toast.LENGTH_SHORT ).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (lastState!=TelephonyManager.CALL_STATE_RINGING){
                    isIncoming=false;
                    callStartTime= new Date();
                    Toast.makeText(context, "OUTGOING CALL STARTED", Toast.LENGTH_SHORT).show();

                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState==TelephonyManager.CALL_STATE_RINGING){
                    Toast.makeText(context, "Ringing but no pickup" + savedNumber + " Call time " + callStartTime +" Date " + new Date(), Toast.LENGTH_SHORT).show();

                }
                else if (isIncoming){
                    Toast.makeText(context, "Incoming " + savedNumber + " Call time " + callStartTime, Toast.LENGTH_SHORT ).show();
                }
                else{
                    if (DisconnectCause.LOCAL==2){
                        //BUSY
                        Toast.makeText(context, "busy", Toast.LENGTH_SHORT).show();


                    }
                    else if (DisconnectCause.CONNECTION_MANAGER_NOT_SUPPORTED==10){
                        //switch-off
                        Toast.makeText(context, "switch-off", Toast.LENGTH_SHORT).show();

                    }

                    else if (DisconnectCause.BUSY==7){
                        //LINE BUSY
                        Toast.makeText(context, "line-busy", Toast.LENGTH_SHORT).show();

                    }
                    else if (DisconnectCause.RESTRICTED==8){
                        //incorrect-number
                        Toast.makeText(context, "incorrect", Toast.LENGTH_SHORT).show();

                    }
                    else if (DisconnectCause.REMOTE==3){
                        //operator service low balance or offers etc
                        Toast.makeText(context, "Noti", Toast.LENGTH_SHORT).show();
                    }
                    else if (DisconnectCause.ERROR==1){
                        // OUT OF COVERAGE
                        Toast.makeText(context, "outside coverage", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(context, "outgoing " + savedNumber + " Call time " + callStartTime + " Date " + new Date(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
        lastState=state;

    }

}
