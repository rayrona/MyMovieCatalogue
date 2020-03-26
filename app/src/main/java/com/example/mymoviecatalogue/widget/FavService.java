package com.example.mymoviecatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

//services
public class FavService extends RemoteViewsService{
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavRemote(this.getApplicationContext());
    }
}
