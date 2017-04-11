package com.gammagamma.lib.infiniteviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;


public class InfiniteViewPager extends ViewPager {
    
    private static final String TAG = InfiniteViewPager.class.getSimpleName();
    
    WeakReference<Context> weakContext;
    private static PageChangeListener pageChangeListener = null;
    
    public InfiniteViewPager( Context context ) {
        super( context );
        weakContext = new WeakReference<>( context );
        pageChangeListener = new PageChangeListener( this );
        addOnPageChangeListener( pageChangeListener );
    }
    
    public InfiniteViewPager( Context context, AttributeSet attrs ) {
        super( context, attrs );
        weakContext = new WeakReference<>( context );
        pageChangeListener = new PageChangeListener( this );
        addOnPageChangeListener( pageChangeListener );
    }
    
    
    
    private static class PageChangeListener implements OnPageChangeListener {
    
        private final InfiniteViewPager viewPager;
    
        public PageChangeListener( final InfiniteViewPager viewPager ) {
            this.viewPager = viewPager;
        }
    
        @Override
        public void onPageSelected( int position ) {
        
            Log.d( TAG, "onPageSelected *********** " + position );
            
            /*InfiniteViewPagerAdapter<?> adapter = (InfiniteViewPagerAdapter) viewPager.getAdapter();
            ArrayList<Serializable> items = adapter.getItems();
        
            if ( position == 1 ) {
    
                Toast.makeText( viewPager.weakContext.get(), "Moving last item to 0, curpos "
                    + position, Toast.LENGTH_SHORT ).show();
    
                items.add( 0, items.remove( items.size() - 1 ) );
                adapter.setItems( items );
                viewPager.setCurrentItem( position + 1, true );
    
            }*/
    
        }
    
        @Override
        public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {
            Log.d( TAG, "onPageScrolled *********** " + position );
        }
    
        @Override
        public void onPageScrollStateChanged( int state ) {
            
        }
        
    }
    
}