package com.gammagamma.lib.infiniteviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;


public class InfiniteViewPagerAdapter<C> extends FragmentStatePagerAdapter {
    
    private final Class<C> fragmentClass;
    private final String bundleKey;
    private ArrayList<Serializable> items;
    
    public InfiniteViewPagerAdapter( FragmentManager fm, Class<C> fragmentClass, final String bundleKey ) {
        super( fm );
        this.fragmentClass = fragmentClass;
        this.bundleKey = bundleKey;
    }
    
    public ArrayList<Serializable> getItems() { return items; }
    
    public void setItems( final ArrayList<Serializable> items ) {
        this.items = items;
        notifyDataSetChanged();
    }
    
    @Override
    public Fragment getItem( int position) {
        
        if ( items == null || items.size() < 1 ) return new Fragment();
        
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable( bundleKey, items.get( position ) );
            fragment.setArguments( bundle );
            return fragment;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
        
    }
    
    @Override
    public int getCount() {
        return ( items != null) ? items.size() : 0;
    }
    
    @Override
    public int getItemPosition( Object object ) {
        return POSITION_NONE;
    }
    
}
