package com.openerp.addons.sifa.providers.contrat;


import com.openerp.support.provider.OEContentProvider;

/**
 * Created by abenbouchta on 09/04/15.
 */
public class ContratProvider extends OEContentProvider{
    public static String CONTENTURI = "com.openerp.addons.sifa.providers.contrat.ContratProvider";

    public static String AUTHORITY = "com.openerp.addons.sifa.providers.contrat";


    @Override
    public String authority() {
        return AUTHORITY;
    }

    @Override
    public String contentUri() {
        return CONTENTURI;
    }
}
