package com.hn.chat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hn.chat.impl.MessageObject;
import com.hn.chat.impl.MsgType;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：ChatFremwork
 * 类描述：消息数据
 * 创建人：mj
 * 创建时间：2017/6/30 9:12
 * 修改人：Administrator
 * 修改时间：2017/6/30 9:12
 * 修改备注：
 * Version:  1.0.0
 */
public class MsgModel  implements   IModel ,Parcelable{


    private  MessageObject mMessageObject;
    private  long    time;//时间
    private  MsgType msgType;//消息类型
    private  String  msgData;//文本 emoji  图片地址
    private  String  voice_videoLocUrl;//音/视频本地地址
    private  String  voice_videoNetUrl;//音/视频网络地址
    private  long  voice_videoTime;//音视频的时间长
    private  String   header;
    private  String   nick;
    private  long  voice_videoSize; //音视频的大小

    public MsgModel() {
    }

    protected MsgModel(Parcel in) {
        time = in.readLong();
        msgData = in.readString();
        voice_videoLocUrl = in.readString();
        voice_videoNetUrl = in.readString();
        header = in.readString();
        nick = in.readString();
        voice_videoTime = in.readLong();
        voice_videoSize = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(voice_videoTime);
        dest.writeLong(voice_videoSize);
        dest.writeLong(time);
        dest.writeString(msgData);
        dest.writeString(header);
        dest.writeString(nick);
        dest.writeString(voice_videoLocUrl);
        dest.writeString(voice_videoNetUrl);
        dest.writeLong(voice_videoTime);
        dest.writeLong(voice_videoSize);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MsgModel> CREATOR = new Creator<MsgModel>() {
        @Override
        public MsgModel createFromParcel(Parcel in) {
            return new MsgModel(in);
        }

        @Override
        public MsgModel[] newArray(int size) {
            return new MsgModel[size];
        }
    };

    public MessageObject getmMessageObject() {
        return mMessageObject;
    }

    public void setmMessageObject(MessageObject mMessageObject) {
        this.mMessageObject = mMessageObject;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getMsgData() {
        return msgData;
    }

    public void setMsgData(String msgData) {
        this.msgData = msgData;
    }

    public String getVoice_videoLocUrl() {
        return voice_videoLocUrl;
    }

    public void setVoice_videoLocUrl(String voice_videoLocUrl) {
        this.voice_videoLocUrl = voice_videoLocUrl;
    }

    public String getVoice_videoNetUrl() {
        return voice_videoNetUrl;
    }

    public void setVoice_videoNetUrl(String voice_videoNetUrl) {
        this.voice_videoNetUrl = voice_videoNetUrl;
    }

    public long getVoice_videoTime() {
        return voice_videoTime;
    }

    public void setVoice_videoTime(long voice_videoTime) {
        this.voice_videoTime = voice_videoTime;
    }

    public long getVoice_videoSize() {
        return voice_videoSize;
    }

    public void setVoice_videoSize(long voice_videoSize) {
        this.voice_videoSize = voice_videoSize;
    }

    @Override
    public MessageObject getMessageObj() {
        return mMessageObject;
    }

    @Override
    public MsgType getMessageType() {
        return msgType;
    }

    @Override
    public long getShowTime() {
        return time;
    }

    @Override
    public String getHeaderIcon() {
        return header;
    }

    @Override
    public String getUserNick() {
        return nick;
    }

    @Override
    public String getTextData() {
        return msgData;
    }

    @Override
    public String getVoiceVideoLocUrl() {
        return voice_videoLocUrl;
    }

    @Override
    public String getVoiceVideoNetUrl() {
        return voice_videoNetUrl;
    }

    @Override
    public long getVoiceVideoTime() {
        return voice_videoTime;
    }

    @Override
    public long getVoiceVideoSize() {
        return voice_videoSize;
    }
}
