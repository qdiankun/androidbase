package com.me.diankun.andoridbase.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.me.diankun.andoridbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/3/3.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsAdapter adapter;
    private boolean isTwoPane;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsList = getNews(); // 初始化新闻数据
        adapter = new NewsAdapter(getContext(), R.layout.item_news, newsList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; // 可以找到news_content_layout布局时，为双页模式
        } else {
            isTwoPane = false; // 找不到news_content_layout布局时，为单页模式
        }
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("Succeed in College as a Learning Disabled Student");
        news1.setContent("College freshmen will soon learn to live with a roommate, adjust to a new social scene and survive less-than-stellar dining hall food. Students with learning disabilities will face these transitions while also grappling with a few more hurdles.");
        newsList.add(news1);
        News news2 = new News();
        news2.setTitle("Google Android exec poached by China's Xiaomi");
        news2.setContent("China's Xiaomi has poached a key Google executive involved in the tech giant's Android phones, in a move seen as a coup for the rapidly growing Chinese smartphone maker.");
        newsList.add(news2);
        return newsList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        News news = newsList.get(position);
        if (isTwoPane) {
            // 如果是双页模式，则刷新NewsContentFragment中的内容
            NewsContentFragment newsContentFragment = (NewsContentFragment)
                    getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refreshView(news.getTitle(), news.getContent());
        } else {
            // 如果是单页模式，则直接启动NewsContentActivity
            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
        }
    }
}
