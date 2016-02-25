package com.me.diankun.recyclerviewdemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/2/25.
 */
public class ContactActivity extends ToolbarActivity {

    @Bind(R.id.rvContacts)
    RecyclerView mContacts;
    @Bind(R.id.swiperefersh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ContactAdapter mAdapter;
    private List<Contact> mDatas;
    private boolean mIsRequestDataRefresh = false;

    private int mPage = -1;

    @Override

    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupSwipeRefershLayout();

        setupRecyclerView();

    }

    private void setupSwipeRefershLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirst();
            }
        });
    }

    /**
     * 加载第一页数据
     */
    private void loadFirst() {
        if (mPage = 1) {
            mPage = 1;
        }


        loadData();
    }

    private void loadNextPage(int page) {

        mPage = 1;

        loadData();

    }

    private void loadData() {
        mPage++;
        //mDatas.clear();
        mDatas.addAll(Contact.createContactList(15, true));
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRequestDataRefresh = false;

        //模拟请求网络数据
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //更新刷新状态
//                mSwipeRefreshLayout.setRefreshing(false);
//                mIsRequestDataRefresh = false;
//            }
//        }, 3000);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        });

//        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
//        mSwipeRefreshLayout.setRefreshing(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupRecyclerView() {
        // Create adapter passing in the sample user data
        mDatas = Contact.createContactList(20, false);
        mAdapter = new ContactAdapter(mDatas);
        // Attach the adapter to the recyclerview to populate items
        mContacts.setAdapter(mAdapter);
        // Set layout manager to position the items
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mContacts.setLayoutManager(manager);

//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mContacts.setLayoutManager(staggeredGridLayoutManager);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        mContacts.setLayoutManager(gridLayoutManager);

        //all item views are of the same height and width for significantly smoother scrolling:
        mContacts.setHasFixedSize(true);
        //set Decorations
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mContacts.addItemDecoration(itemDecoration);
        //set animator
        mContacts.setItemAnimator(new DefaultItemAnimator());

        mContacts.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsRequestDataRefresh) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        mContacts.addOnScrollListener(new AutoScrollListener());

    }

    @OnClick(R.id.btn_add_bottom_item)
    void addBottomItem() {
        mDatas.add(mDatas.size(), new Contact("New", false));
        mAdapter.notifyItemInserted(mDatas.size() - 1);
        mContacts.smoothScrollToPosition(mDatas.size() - 1);
    }

    @Override
    public void onToolbarClick() {
        mDatas.add(0, new Contact("Student", true));
        mAdapter.notifyItemInserted(0);
        mContacts.smoothScrollToPosition(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                hideOrShowToolbar();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class AutoScrollListener extends RecyclerView.OnScrollListener {

        private int lastPosition;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 最新可见元素的位置
            lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int totalItemIndex = recyclerView.getAdapter().getItemCount();
            if (!mIsRequestDataRefresh && newState == RecyclerView.SCROLL_STATE_IDLE
                    && (lastPosition + 1) >= (totalItemIndex - 1)) {
//                mSwipeRefreshLayout.setRefreshing(true);
//                mPage++;
//                loadData();
//                setRequestDataRefresh(true);
                mIsRequestDataRefresh = true;
                mSwipeRefreshLayout.setRefreshing(true);
                loadNextPage(1);
            }
        }
    }

}

