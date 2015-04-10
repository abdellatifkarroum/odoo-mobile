package com.openerp.addons.sifa.services;

import android.accounts.Account;
import android.app.Service;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.openerp.addons.sifa.ContratDB;
import com.openerp.orm.OEHelper;
import com.openerp.support.service.OEService;

/**
 * Created by abenbouchta on 09/04/15.
 */
public class ContratService extends OEService{
    @Override
    public Service getService() {
        return this;
    }

    @Override
    public void performSync(Context context, Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        ContratDB contrat = new ContratDB(context);
        OEHelper oe = contrat.getOEInstance();
        if(oe!=null){
            if(oe.syncWithServer()){
                System.out.println("goooooooooooooooooood");
            }
        }
    }
}
