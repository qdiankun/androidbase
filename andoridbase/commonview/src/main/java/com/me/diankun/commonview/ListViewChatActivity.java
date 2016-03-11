package com.me.diankun.commonview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.me.diankun.commonview.adapter.ChatAdapter;
import com.me.diankun.commonview.bean.ChatMessage;
import com.me.diankun.commonview.utils.HttpUtils;
import com.me.diankun.commonview.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListViewChatActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    ListView mListView;
    private ChatAdapter mAdapter;
    private List<ChatMessage> mDatas;

    @Bind(R.id.et_input_msg)
    EditText mInputMsg;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //接受线程返回的机器人的数据,更新对话框
            ChatMessage chatMessage = (ChatMessage) msg.obj;
            mDatas.add(chatMessage);
            mAdapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_chat);
        ButterKnife.bind(this);

        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("小敏", "你好,很高兴和你对话", ChatMessage.Type.GET_MSG, new Date()));
        //mDatas.add(new ChatMessage("小Q", "非常好", Type.SEND_MSG, new Date()));

        mAdapter = new ChatAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_send_msg)
    void sendMessage(View view) {
        final String message = mInputMsg.getText().toString();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.showShort("输入信息不能为空!");
            return;
        }

        //清空输入框
        mInputMsg.setText("");

        // 将发送的消息加入到listview中展示
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setDate(new Date());
        chatMessage.setMsg(message);
        chatMessage.setName("小Q");
        chatMessage.setType(ChatMessage.Type.SEND_MSG);
        mDatas.add(chatMessage);
        mAdapter.notifyDataSetChanged();

        // 请求机器人返回的数据
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                ChatMessage sendMessage = httpUtils
                        .sendMessage(message);
                Message message = mHandler.obtainMessage();
                message.obj = sendMessage;
                mHandler.sendMessage(message);
            }
        }) {
        }.start();
    }

}
