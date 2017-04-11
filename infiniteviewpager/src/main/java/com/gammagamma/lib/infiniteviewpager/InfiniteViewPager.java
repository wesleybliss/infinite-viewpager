package com.gammagamma.lib.infiniteviewpager;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


public class InfiniteViewPager extends ViewPager {
    
    private static final String TAG = InfiniteViewPager.class.getSimpleName();
    
    private static PageChangeListener pageChangeListener = null;
    
    private OnPageChangeListener externalOnPageChangeListener = null;
    
    public InfiniteViewPager( Context context ) {
        super( context );
        pageChangeListener = new PageChangeListener( this );
        setActualOnPageChangeListener( pageChangeListener );
    }
    
    public InfiniteViewPager( Context context, AttributeSet attrs ) {
        super( context, attrs );
        pageChangeListener = new PageChangeListener( this );
        setActualOnPageChangeListener( pageChangeListener );
    }
    
    @Override
    public void setAdapter( PagerAdapter adapter ) {
        super.setAdapter( adapter );
    }
    
    private void setActualOnPageChangeListener( OnPageChangeListener listener ) {
        super.addOnPageChangeListener( listener );
    }
    
    @Override
    public void addOnPageChangeListener( OnPageChangeListener listener ) {
        this.externalOnPageChangeListener = listener;
    }
    
    private OnPageChangeListener getExternalOnPageChangeListener() {
        return this.externalOnPageChangeListener;
    }
    
    private static Pair<Integer, ArrayList<Serializable>> cyclePagerItems(
        ArrayList<Serializable> items, final int position ) {
    
        final int lastPosition = items.size() - 1;
        Log.d( TAG, "cyclePagerItems POSITION ------- " + position );
        if ( position == lastPosition - 1 ) {
            items.add( items.remove( 0 ) );
            return new Pair<>( -1, items );
            
        }
        else if ( position == 1 ) {
            items.add( 0, items.remove( lastPosition ) );
            return new Pair<>( 1, items );
            
        }
        
        return new Pair<>( 0, items );
        
    }
    
    private static class PageChangeListener implements OnPageChangeListener {
    
        private final InfiniteViewPager viewPager;
    
        public PageChangeListener( final InfiniteViewPager viewPager ) {
            this.viewPager = viewPager;
        }
    
        @Override
        public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {
    
            Log.d( TAG, "onPageScrolled position " + position );
            
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            
            if ( externalOnPageChangeListener != null ) {
                externalOnPageChangeListener.onPageScrolled(
                    position, positionOffset, positionOffsetPixels );
            }
            
        }
    
        @Override
        public void onPageSelected( int position ) {
    
            Log.d( TAG, "onPageSelected position " + position );
            
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            
            if ( externalOnPageChangeListener != null ) {
                externalOnPageChangeListener.onPageSelected( position );
            }
        
            InfiniteViewPagerAdapter<?> adapter = (InfiniteViewPagerAdapter) viewPager.getAdapter();
            ArrayList<Serializable> items = adapter.getItems();
            
            if ( items.size() > 2 ) {
                
                //final Pair<Integer, ArrayList<Serializable>> cycleResult = cyclePagerFragments( items, position );
                final Pair<Integer, ArrayList<Serializable>> cycleResult = cyclePagerItems( items, position );
                
                if ( cycleResult.first != 0 ) {
                    
                    adapter.setItems( cycleResult.second );
                    
                    viewPager.removeOnPageChangeListener( pageChangeListener );
                    viewPager.setActualOnPageChangeListener( null );
                    viewPager.setCurrentItem( position + cycleResult.first, false );
                    viewPager.addOnPageChangeListener( externalOnPageChangeListener );
                    viewPager.setActualOnPageChangeListener( this );
                    
                }
                
            }
            
        }
    
        @Override
        public void onPageScrollStateChanged( int state ) {
            
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            
            if ( externalOnPageChangeListener != null ) {
                externalOnPageChangeListener.onPageScrollStateChanged( state );
            }
            
        }
        
    }
    
}
