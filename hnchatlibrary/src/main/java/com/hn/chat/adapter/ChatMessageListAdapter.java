package com.hn.chat.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hn.chat.R;
import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;
import com.hn.chat.model.IModel;
import com.hn.chat.util.LogUtils;
import com.hn.chat.util.ScreenUtil;
import com.hn.chat.util.TimeUtil;
import com.hn.chat.widget.emoji.MoonUtil;

import java.util.List;

/**
 * 聊天消息列表适配器
 * Created by lenovo on 2017/7/3.
 */

public class ChatMessageListAdapter<T  extends IModel> extends RecyclerView.Adapter {

    private String TAG="ChatMessageListAdapter";
    private List<T> list;
    private Context  context;
    private ChatMessageClickLListener  listener;

    public ChatMessageListAdapter(List<T> data, Context context) {
        this.list = data;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
         if(MessageObject.leftType==list.get(position).getMessageObj()){
             return  0;
         }else {
              return 1;
         }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){//创建左边viewholder
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left_layout,parent,false);
            return    new ChatMessageListLeftViewHolder(view);
        }else {//创建右边viewholder
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right_layout,parent,false);
            return    new ChatMessageListRightViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         T  result=list.get(position);
        if(result!=null&&result instanceof  IModel){

            //显示时间
            long lastTime = 0;
            long currentTime = 0;
            if (position >= 1) {
                lastTime = list.get(position - 1).getShowTime();
                currentTime = list.get(position).getShowTime();
            }
            long  time=result.getShowTime();
            String showTime = TimeUtil.getTimeShowString(time, false);
            //用户头像
            String headerLogo=result.getHeaderIcon();
            //数据类型
            MsgType msgType =result.getMessageType();
            //文本 emoji 图片
            String  content=result.getTextData();
            if(holder  instanceof  ChatMessageListLeftViewHolder){
                ChatMessageListLeftViewHolder vh= (ChatMessageListLeftViewHolder) holder;
                updateLeftView(vh,position,showTime,lastTime,currentTime,headerLogo,msgType,content);
            }else if(holder instanceof  ChatMessageListRightViewHolder){
                ChatMessageListRightViewHolder vh= (ChatMessageListRightViewHolder) holder;
                updateRightView(vh,position,showTime,lastTime,currentTime,headerLogo,msgType,content);
            }
        }
    }

    /**
     * 更新左边视图
     * @param vh            viewholder
     * @param showTime      时间
     * @param msgType       消息类型
     * @param content       消息类容   文本 emoji 图片
     */
    private void updateLeftView(ChatMessageListLeftViewHolder vh, final int pos, String showTime, long lastTime, long currentTime, String headerLogoUrl, MsgType msgType, String content) {
        if(TextUtils.isEmpty(showTime)){
            vh.getTvLeftTime().setVisibility(View.GONE);
        }else{
            vh.getTvLeftTime().setVisibility(View.VISIBLE);
            vh.getTvLeftTime().setText(showTime);
        }
        //间隔5分钟内不显示时间
        if(lastTime!=0&&currentTime!=0) {
            if (Math.abs(currentTime - lastTime) < 5 * 60 * 1000) {
                vh.getTvLeftTime().setVisibility(View.GONE);
            } else {
                vh.getTvLeftTime().setVisibility(View.VISIBLE);
            }
        }
        //用户头像
        if(!TextUtils.isEmpty(headerLogoUrl)) {
            vh.getIvLeftHeaderIcon().setImageURI(Uri.parse(headerLogoUrl));
        }else{
            LogUtils.i(TAG,"头像为空"+pos);
        }
        //判断消息类型
        if(MsgType.TEXT==msgType){//文本
            vh.getIvLeftLogo().setVisibility(View.GONE);
            vh.getIvLeftFlAudio().setVisibility(View.GONE);
            vh.getTvLeftContent().setVisibility(View.VISIBLE);
            MoonUtil.identifyFaceExpression(context, vh.getTvLeftContent(), content, ImageSpan.ALIGN_BOTTOM);
            vh.getTvLeftContent().setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(10), ScreenUtil.dip2px(8));
            LogUtils.i(TAG,"内容："+content+"--->"+showTime);
        }else if(MsgType.PICTURE==msgType){//图片
            vh.getTvLeftContent().setVisibility(View.GONE);
            vh.getIvLeftFlAudio().setVisibility(View.GONE);
            vh.getIvLeftLogo().setVisibility(View.VISIBLE);
            LogUtils.i(TAG,"图片地址:"+content);
            if(!TextUtils.isEmpty(content)) {
                vh.getIvLeftLogo().setImageURI(content);
            }
        }else if(MsgType.RECORD==msgType){//录音

        }else if(MsgType.VIDEO==msgType){//视频

        }else if(MsgType.LOCATION==msgType){//位置分享

        }
        //点击头像
        vh.getIvLeftLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClickPicture(pos,list);
                }
            }
        });
        //点击图片
        vh.getIvLeftHeaderIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClickHeaderLogo(pos,list);
                }
            }
        });

    }

    /**
     * 更新右边视图
     * @param vh            viewholder
     * @param showTime      时间
     * @param msgType       消息类型
     * @param content       消息类容   文本 emoji 图片
     */
    private void updateRightView(ChatMessageListRightViewHolder vh, final int pos, String showTime, long lastTime, long currentTime, String headerLogoUrl, MsgType msgType, String content) {
        //显示时间
        if(TextUtils.isEmpty(showTime)){
            vh.getTvRightTime().setVisibility(View.GONE);
        }else{
            vh.getTvRightTime().setVisibility(View.VISIBLE);
            vh.getTvRightTime().setText(showTime);
        }
        //间隔5分钟内不显示时间
        if(lastTime!=0&&currentTime!=0) {
            if (Math.abs(currentTime - lastTime) < 5 * 60 * 1000) {
                vh.getTvRightTime().setVisibility(View.GONE);
            } else {
                vh.getTvRightTime().setVisibility(View.VISIBLE);
            }
        }
        //用户头像
        if(!TextUtils.isEmpty(headerLogoUrl)) {
            vh.getIvRightHeaderIcon().setImageURI(Uri.parse(headerLogoUrl));
        }else{
              LogUtils.i(TAG,"头像为空"+pos);
         }
        if(MsgType.TEXT==msgType){//文本
            vh.getIvRightLogo().setVisibility(View.GONE);
            vh.getFlRightAudio().setVisibility(View.GONE);
            vh.getTvRightContent().setVisibility(View.VISIBLE);
            MoonUtil.identifyFaceExpression(context, vh.getTvRightContent(), content, ImageSpan.ALIGN_BOTTOM);
            vh.getTvRightContent().setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(10), ScreenUtil.dip2px(8));
            LogUtils.i(TAG,"内容："+content+"--->"+showTime);
        }else if(MsgType.PICTURE==msgType){//图片
            vh.getTvRightContent().setVisibility(View.GONE);
            vh.getFlRightAudio().setVisibility(View.GONE);
            vh.getIvRightLogo().setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(content)) {
                vh.getIvRightLogo().setImageURI(content);
            }
            LogUtils.i(TAG,"图片地址:"+content);
        }else if(MsgType.RECORD==msgType){//录音

        }else if(MsgType.VIDEO==msgType){//视频

        }else if(MsgType.LOCATION==msgType){//位置分享

        }

        //点击头像
        vh.getIvRightLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClickPicture(pos,list);
                }
            }
        });
        //点击图片
        vh.getIvRightHeaderIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClickHeaderLogo(pos,list);
                }
            }
        });
    }

    public   interface   ChatMessageClickLListener<T extends IModel>{

        void  onClickHeaderLogo(int pos,List<T>  list);//点击头像

        void  onClickPicture(int pos,List<T>  list);//点击图片  可进行图片预览

    }
    public   void   setListener(ChatMessageClickLListener  listener){
          this.listener=listener;
    }


}
