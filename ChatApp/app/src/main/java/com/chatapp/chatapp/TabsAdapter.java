package com.chatapp.chatapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nathalye Quaresma on 01/10/2016.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String[] titletab;

    public TabsAdapter(Context context, FragmentManager fm, String[] titletab){
        super(fm);
        this.context = context;
        this.titletab = titletab;
    }


    @Override
    public int getCount(){
        return this.titletab.length;
    }


    @Override
    public  CharSequence getPageTitle(int position){
        if(position == 0){
        return context.getString(R.string.conversas);
        }else if(position == 1){
            return context.getString(R.string.favoritos);
        }
        return context.getString(R.string.configuracoes);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if(position == 0){
            return  new Main_Fragments();

        }else if(position == 1){
            return new FavoritoFragment();
        }
        return new ConfgFragment();
    }

}
