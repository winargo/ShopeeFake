package com.fake.shopee.shopeefake.Main_pages;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.fragment.fragment_batal;
import com.fake.shopee.shopeefake.fragment.fragment_belumbayar;
import com.fake.shopee.shopeefake.fragment.fragment_dikemas;
import com.fake.shopee.shopeefake.fragment.fragment_dikirm;
import com.fake.shopee.shopeefake.fragment.fragment_pengembalian;
import com.fake.shopee.shopeefake.fragment.fragment_profile;
import com.fake.shopee.shopeefake.fragment.fragment_profile_sell;
import com.fake.shopee.shopeefake.fragment.fragment_selesai;
import com.fake.shopee.shopeefake.session_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class main_belanjaanku extends FragmentActivity {

    TabLayout tabLayout;
    ImageButton backarrow;
    private FirebaseAuth mAuth;
    private static final int NUM_PAGES = 6;
    session_class session;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser==null){}
        else {
            session.setusename(currentUser.getEmail());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_belanjaanku);

        mAuth = FirebaseAuth.getInstance();
        session = new session_class(this);
        backarrow = (ImageButton) findViewById(R.id.backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initpager();
    }

    private void initpager(){
        mPager = (ViewPager) findViewById(R.id.pagerbelanjaanku);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setOffscreenPageLimit(6);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(Integer.parseInt(session.currentactivity));

        tabLayout = (TabLayout) findViewById(R.id.tabbelanja);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("Belum bayar"));
        tabLayout.addTab(tabLayout.newTab().setText("Dikemas"));
        tabLayout.addTab(tabLayout.newTab().setText("Dikirim"));
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"));
        tabLayout.addTab(tabLayout.newTab().setText("Batal"));
        tabLayout.addTab(tabLayout.newTab().setText("Pengembalian"));
        tabLayout.setupWithViewPager(mPager);

    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        private final List<String> mfragmenttitlelsit = new ArrayList<String>();
        private final List<Fragment> mfragment = new ArrayList<Fragment>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addfragment(Fragment fragment){
            mfragment.add(fragment);
           // mfragmenttitlelsit.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            String title = null;
            if (position == 0)
            {
                title = "Belum Bayar";
            }
            else if (position == 1)
            {
                title = "Dikemas";
            }
            else if (position == 2)
            {
                title = "Dikirim";
            }
            else if (position == 3)
            {
                title = "Selesai";
            }
            else if (position == 4)
            {
                title = "Batal";
            }
            else if (position == 5)
            {
                title = "Pengembalian";
            }


            return title;
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
            {
                fragment =  new fragment_belumbayar();
            }
            else if (position == 1)
            {
                fragment = new fragment_dikemas();
            }
            else if (position == 2)
            {
                fragment = new fragment_dikirm();
            }
            else if (position == 3)
            {
                fragment = new fragment_selesai();
            }
            else if (position == 4)
            {
                fragment = new fragment_batal();
            }
            else if (position == 5)
            {
                fragment = new fragment_pengembalian();
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public long getItemId(int position){
            return System.currentTimeMillis();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
}
