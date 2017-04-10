package com.gammagamma.lib.infiniteviewpager;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;


public class InfiniteViewPager extends ViewPager {
    
    private static final String TAG = InfiniteViewPager.class.getSimpleName();
    
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
    
    /*private static int cyclePagerFragments( List<String> items, final int position ) {
    
        Log.d( TAG, "cyclePagerFragments before " + TextUtils.join( ", ", items ) );
        
        final int lastPosition = items.size() - 1;
        
        if ( position == lastPosition ) {
            
            items.add( items.remove( 0 ) );
            return -1;
            
        }
        else if ( position == 0 ) {
            
            items.add( 0, items.remove( lastPosition ) );
            return 1;
            
        }
        
        return 0;
        
    }*/
    
    private static Pair<Integer, ArrayList<String>> cyclePagerFragments( ArrayList<String> items, final int position ) {
    
        final int lastPosition = items.size() - 1;
    
        Log.d( TAG, "cyclePagerFragments @" + position + "/" + lastPosition + " before " + TextUtils.join( ", ", items ) );
        
        if ( position == lastPosition - 1 ) {
            Log.d( TAG, "cyclePagerFragments ADD TO END" );
            items.add( items.remove( 0 ) );
            return new Pair<>( -1, items );
            
        }
        else if ( position == 1 ) {
            Log.d( TAG, "cyclePagerFragments ADD TO START" );
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
        
            InfiniteViewPagerAdapter adapter = (InfiniteViewPagerAdapter) viewPager.getAdapter();
            /*List<Fragment> pagerFragments = adapter.getPagerFragments();
            // Ensure that cycling only occurs if there are 3 or more fragments.
            if (pagerFragments.size() > 2) {
                final int cycleResult = cyclePagerFragments(pagerFragments, position);
                if (cycleResult != 0) {
                    adapter.setPagerFragments(pagerFragments);
                    adapter.notifyDataSetChanged();
                
                    // Turn off the actual and external OnPageChangeListeners, so that
                    // this function does not unnecessarily get called again when
                    // setting the current item.
                    viewPager.setOnPageChangeListener(null);
                    viewPager.setActualOnPageChangeListener(null);
                    viewPager.setCurrentItem(position + cycleResult, false);
                    viewPager.setOnPageChangeListener(externalOnPageChangeListener);
                    viewPager.setActualOnPageChangeListener(this);
                }
            }*/
            
            
            
            ArrayList<String> items = adapter.getItems();
            
            if ( items.size() > 2 ) {
                
                final Pair<Integer, ArrayList<String>> cycleResult = cyclePagerFragments( items, position );
    
                Log.d( TAG, "cyclePagerFragments after " + TextUtils.join( ", ", cycleResult.second ) );
                
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
        public void onPageScrollStateChanged(int state) {
            final OnPageChangeListener externalOnPageChangeListener =
                viewPager.getExternalOnPageChangeListener();
            if (externalOnPageChangeListener != null) {
                externalOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }
        
    }
    
}
