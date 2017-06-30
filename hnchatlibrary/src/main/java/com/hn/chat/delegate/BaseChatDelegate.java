package com.hn.chat.delegate;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hn.chat.R;
import com.hn.chat.impl.ChatRoomListener;
import com.hn.chat.impl.MsgType;
import com.hn.chat.model.MsgModel;
import com.hn.chat.util.DimenUtil;
import com.hn.chat.util.LogUtils;
import com.hn.chat.util.StringUtil;
import com.hn.chat.util.Utils;
import com.hn.chat.view.AppDeleagte;
import com.hn.chat.widget.emoji.EmoticonPickerView;
import com.hn.chat.widget.emoji.IEmoticonSelectedListener;
import com.hn.chat.widget.emoji.MoonUtil;

import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;
import cn.dreamtobe.kpswitch.widget.KPSwitchRootLinearLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：聊天消息界面视图代理基类
 * 创建人：mj
 * 创建时间：2017/6/27 18:16
 * 修改人：Administrator
 * 修改时间：2017/6/27 18:16
 * 修改备注：
 * Version:  1.0.0
 */
public abstract  class BaseChatDelegate extends AppDeleagte    implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,IEmoticonSelectedListener  {

     private String  TAG="ChatRoomDelegate";
     //初始化头部视图       返回 标题  子标题                布局文件:chat_room_title_layout
     private Toolbar                mToolbar;//toolbar
     private AppCompatImageButton   btnBack;//返回按钮
     private TextView               tvTitle;//标题
     private TextView               tvSubTitle;//子标题
     //初始化消息列表视图   消息列表  刷新                    布局文件：chat_room_message_list_layout
     private RecyclerView           mRecyclerView;
     private SwipeRefreshLayout     mSwipeRefreshLayout;
     //初始化底部视图     文字，音频，表情，图片等数据的发送    布局文件:chat_room_bottom_layout
     private FrameLayout            flSwitch;//
     private ImageView              ivVoice;//语音控件
     private ImageView              ivVoiceToText;//语音切换到文本控件
     private FrameLayout            flVoice;
     private Button                 btnSendVoice;//发送语音
     private EditText               etText;//文本编辑框
     private ImageView              ivEmoji;//表情
     private FrameLayout            flSend;//发送
     private ImageView              ivAddMore;//更多
     private TextView               tvSend;//发送
     private EmoticonPickerView     mEmoticonPickerView;//emoji图片选择器
     //初始化录音视图                                       布局文件：chat_record_layout
//     private FrameLayout            flRecordBg;//录音视图父控件
//     private Chronometer            mChronometer;//实现显示器

    private KPSwitchRootLinearLayout   mKPSwitchRootLinearLayout;//聊天父容器
    private KPSwitchPanelLinearLayout  mKPSwitchPanelLinearLayout;//底部更多菜单栏父容器
    private View                       mEmojiMenuBg;//emoji父容器
    private View                       mMoreMenuBg;//更多父布局



     private boolean                 isTitleBarShow;//是否显示标题栏
     private ChatRoomListener        listener;//聊天消息界面监听





    /**
     * 获取视图
     * @return
     */
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initView();

    }
    /**
     * 初始化组件
     */
    public void initView() {
        //初始化头部视图
        initHeaderView();
        //初始化消息列表
        initMessageListView();
        //初始化底部视图
        initFootView();
        //初始化录音视图
        initRecordView();
        KeyboardDeal();
        setChatShowTitleBar(true);//显示标题栏
        setChatShowBack(true);//显示返回按钮
        setChatShowSubTitle(false);//不显示子标题
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setListener();
        initData();
    }




    /**
     * 初始化数据
     */
    public abstract void initData();


    /*****************************视图初始化********************************************************************************
    /**
     * 初始化头部视图  标题    子标题  返回等组件
     */
    private void initHeaderView() {
        mToolbar=get(R.id.toolbar);
        btnBack=get(R.id.toolbar_back);
        tvTitle=get(R.id.toolbar_title);
        tvSubTitle=get(R.id.toolbar_subtitle);
    }

    /**
     * 初始化消息列表    消息列表显示，刷新组件
     */
    private void initMessageListView() {
        mSwipeRefreshLayout=get(R.id.mSwipeRefreshLayout);
        mRecyclerView=get(R.id.mRecyclerView);
    }
    /**
     * 初始化底部视图   文字，音频，表情，图片等数据的发送组件
     */
    private void initFootView() {
        flSwitch=get(R.id.switchLayout);
        ivVoice=get(R.id.buttonAudioMessage);
        ivVoice.setOnClickListener(this);
        ivVoiceToText=get(R.id.buttonTextMessage);
        ivVoiceToText.setOnClickListener(this);
        flVoice=get(R.id.audioTextSwitchLayout);
        btnSendVoice=get(R.id.audioRecord);
        etText=get(R.id.editTextMessage);
        etText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        etText.setOnClickListener(this);
        ivEmoji=get(R.id.emoji_button);
        ivEmoji.setOnClickListener(this);
        flSend=get(R.id.sendLayout);
        ivAddMore=get(R.id.buttonMoreFuntionInText);
        tvSend=get(R.id.buttonSendMessage);
        mEmoticonPickerView=get(R.id.emoticon_picker_view);
        mEmoticonPickerView.setVisibility(View.VISIBLE);
        mEmoticonPickerView.show(BaseChatDelegate.this);
        mKPSwitchRootLinearLayout=get(R.id.activity_chat);
        mKPSwitchPanelLinearLayout=get(R.id.panel_root);
        mEmojiMenuBg=get(R.id.sub_panel_emoji);
        mMoreMenuBg=get(R.id.sub_panel_more);
    }

    /**
     * 初始化录音视图
     */
    private   void initRecordView(){
//        flRecordBg=get(R.id.layoutPlayAudio);
//        mChronometer=get(R.id.timer);
    }

    /**
     * 键盘冲突处理
     */
    private void KeyboardDeal() {
        mKPSwitchPanelLinearLayout.setIgnoreRecommendHeight(false);
        KeyboardUtil.attach(this.getActivity(), mKPSwitchPanelLinearLayout,
                // Add keyboard showing state callback, do like this when you want to listen in the
                // keyboard's show/hide change.
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        Log.d(TAG, String.format("Keyboard is %s", isShowing ? "showing" : "hiding"));
                    }
                });
        // If there are several sub-panels in this activity ( e.p. function-panel, emoji-panel).
        KPSwitchConflictUtil.attach(mKPSwitchPanelLinearLayout, etText,
                new KPSwitchConflictUtil.SwitchClickListener() {
                    @Override
                    public void onClickSwitch(boolean switchToPanel) {
                        if (switchToPanel) {
                            etText.clearFocus();
                        } else {
                            etText.requestFocus();
                        }
                        if(etText.getVisibility()==View.GONE){
                            ivVoice.setVisibility(View.VISIBLE);
                            ivVoiceToText.setVisibility(View.INVISIBLE);
                            flVoice.setVisibility(View.INVISIBLE);
                            btnSendVoice.setVisibility(View.INVISIBLE);
                            etText.setVisibility(View.VISIBLE);
                        }
                    }
                },
           new KPSwitchConflictUtil.SubPanelAndTrigger(mEmojiMenuBg, ivEmoji),new KPSwitchConflictUtil.SubPanelAndTrigger(mEmojiMenuBg, ivEmoji),
         new KPSwitchConflictUtil.SubPanelAndTrigger(mMoreMenuBg, ivAddMore));

    }

    /**
     * 事件监听
     */
    private void setListener() {
        etText.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                MoonUtil.replaceEmoticons(getActivity(), s, start, count);
                int editEnd = etText.getSelectionEnd();
                etText.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                etText.setSelection(editEnd);
                etText.addTextChangedListener(this);
                //根据输入框的输入的数据长度，切换发送按钮和更过按钮
                String result=s.toString();
                if(result.length()>0){
                     hideAddMoreView();
                }else{
                    showAddMoreView();
                }


            }
        });

        btnSendVoice.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {


                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {


                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {


                }

                return false;
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    KPSwitchConflictUtil.hidePanelAndKeyboard(mKPSwitchPanelLinearLayout);
                }

                return false;
            }
        });
    }




    /****************************方法设置************************************************************************

    /**
     * 设置标题
     * @param data
     */
    public  void  setChatTitleText(String  data){
        if(TextUtils.isEmpty(data)){
            return;
        }
        if(tvTitle!=null){
            tvTitle.setText(data);
        }
    }

    /**
     * 设置标题内容
     * @param titleId
     */
    public void setTitle(@StringRes int titleId) {
        if (!isTitleBarShow) return;
        tvTitle.setText(titleId);
    }
    /**
     * 设置子标题
     * @param data
     */
    public  void  setChatSubTitleText(String data){
        if(TextUtils.isEmpty(data)){
            return;
        }
        if (!isTitleBarShow) return;
        if(tvSubTitle!=null){
            tvSubTitle.setText(data);
        }
    }


    /**
     * 设置标题栏显示或者隐藏
     *
     * @param isShow true则显示,false为不显示
     */
    public  void setChatShowTitleBar(boolean isShow) {
        if (mToolbar != null) {
            isTitleBarShow=isShow;
            mToolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 是否显示子标题
     *
     * @param isShow true则显示,false为不显示
     */
    public void setChatShowSubTitle(boolean isShow) {
        if (!isTitleBarShow) return;
        if (tvSubTitle != null) {
            tvSubTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 是否显示返回按钮
     *
     * @param isShow true则显示,false为不显示
     */
    public void setChatShowBack(boolean isShow) {
        if (!isTitleBarShow) return;
        if (mToolbar != null) {
            btnBack.setVisibility(isShow ?View.VISIBLE:View.GONE);
            btnBack.setOnClickListener(isShow ? new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onBack();
                    }
                    Utils.cleanFouce(getActivity(),etText);
                    getActivity().finish();
                }
            } : null);
            btnBack.setPadding(isShow?0: DimenUtil.dp2px(getActivity(),16), 0, 0, 0);
        }
    }

    /**
     * 更换返回键图片资源
     * @param resId
     */
    public void setCahtBackRes(@DrawableRes int resId){
        btnBack.setImageResource(resId);
    }

    /**
     * 获取toolbar
     * @return
     */
    public   Toolbar  getToolBar(){
        return   mToolbar;
    }

    /**
     * 设置监听器
     * @param listener
     */
    public  void  setChatRoomListener(ChatRoomListener   listener){
        this.listener=listener;
    }


    /**
     * 监听下拉刷新
     */
    @Override
    public void onRefresh() {
        if(listener!=null){
            listener.onRefreshListeer();
        }
    }

    /**
     * 是否禁止或打开刷新
     * @param enable   true  打开刷新   false  禁止刷新
     */
    public  void  isEnableRefresh(boolean  enable){
        if(mSwipeRefreshLayout!=null){
             mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    /**
     * 设置刷新控件的背景色
     * @param colors
     */
    public  void  setRefreshColor(@ColorInt int... colors){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setColorSchemeColors(colors);
        }
    }

    /**
     * 获取mSwipeRefreshLayout刷新控件
     * @return
     */
    public  SwipeRefreshLayout   getSwipeRefreshLayout(){
        return   mSwipeRefreshLayout;
    }

    /**
     * 获取recyclerview
     * @return
     */
    public  RecyclerView   getRecyclerView(){
        return mRecyclerView;
    }

    /**
     * 设置recyclerview  LayoutManager
     * @param manager
     */
    public  void      setRecyclerviewLayoutManager(RecyclerView.LayoutManager manager){
        if(mRecyclerView!=null){
            mRecyclerView.setLayoutManager(manager);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.buttonAudioMessage) {//点击语音控件 隐藏键盘，显示语音，进行语音录制
            ivVoice.setVisibility(View.INVISIBLE);
            ivVoiceToText.setVisibility(View.VISIBLE);
            flVoice.setVisibility(View.VISIBLE);
            btnSendVoice.setVisibility(View.VISIBLE);
            etText.setVisibility(View.GONE);
            showAddMoreView();
            KPSwitchConflictUtil.hidePanelAndKeyboard(mKPSwitchPanelLinearLayout);
        }else if(i==R.id.buttonTextMessage){//点击键盘控件  隐藏按住语音，弹起键盘输入，进行文本输入
            ivVoice.setVisibility(View.VISIBLE);
            ivVoiceToText.setVisibility(View.INVISIBLE);
            flVoice.setVisibility(View.INVISIBLE);
            btnSendVoice.setVisibility(View.INVISIBLE);
            etText.setVisibility(View.VISIBLE);
            KPSwitchConflictUtil.showKeyboard(mKPSwitchPanelLinearLayout,etText);
        }else if(i==R.id.buttonSendMessage){//发送
            String result=etText.getText().toString();
            if(!TextUtils.isEmpty(result)) {
                MsgModel model = new MsgModel();
                model.setTime(Utils.getNowDate());//获取当前毫秒值
                model.setMsgData(result);//数据
                model.setMsgType(MsgType.TEXT);
            }

        }
    }

    /**
     * 隐藏添加更多按钮
     */
    private void hideAddMoreView() {
        tvSend.setVisibility(View.VISIBLE);
        ivAddMore.setVisibility(View.GONE);
    }

    /**
     * 显示添加更多按钮
     */
    private void showAddMoreView() {
        tvSend.setVisibility(View.GONE);
        ivAddMore.setVisibility(View.VISIBLE);
    }

    /**
     * emoji表情选择
     * @param key
     */
    @Override
    public void onEmojiSelected(String key) {
        LogUtils.i(TAG,"选择的表情:"+key);
        Editable mEditable = etText.getText();
        if (key.equals("/DEL")) {
            etText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = etText.getSelectionStart();
            int end = etText.getSelectionEnd();
            start = (start < 0 ? 0 : start);
            end = (start < 0 ? 0 : end);
            mEditable.replace(start, end, key);

        }
    }
    @Override
    public void onStickerSelected(String category, String item) {
        LogUtils.i("InputPanel", "onStickerSelected, category =" + category + ", sticker =" + item);
    }

}
