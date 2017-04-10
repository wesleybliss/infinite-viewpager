package com.gammagamma.lib.infiniteviewpager;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.io.Serializable;
import java.util.ArrayList;


public class InfiniteViewPager extends ViewPager {
    
    private OnPageChangeListener externalOnPageChangeListener = null;
    private static PageChangeListener pageChangeListener = null;
    
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
    
    private static Pair<Integer, ArrayList<Serializable>> cyclePagerFragments(
        ArrayList<Serializable> items, final int position ) {
    
        final int lastPosition = items.size() - 1;
        
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
            
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            
            if ( externalOnPageChangeListener != null ) {
                externalOnPageChangeListener.onPageScrolled(
                    position, positionOffset, positionOffsetPixels );
            }
            
        }
    
        @Override
        public void onPageSelected( int position ) {
            
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            
            if ( externalOnPageChangeListener != null ) {
                externalOnPageChangeListener.onPageSelected( position );
            }
        
            InfiniteViewPagerAdapter<?> adapter = (InfiniteViewPagerAdapter) viewPager.getAdapter();
            ArrayList<Serializable> items = adapter.getItems();
            
            if ( items.size() > 2 ) {
                
                final Pair<Integer, ArrayList<Serializable>> cycleResult = cyclePagerFragments( items, position );
                
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
