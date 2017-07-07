package com.hn.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hn.chat.R;
import com.hn.chat.widget.imageview.FrescoImageView;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：DiWei
 * 类描述：聊天消息之右边适配器Viderholder
 * 创建人：mj
 * 创建时间：2017/6/7 19:36
 * 修改人：Administrator
 * 修改时间：2017/6/7 19:36
 * 修改备注：
 * Version:  1.0.0
 */
public class ChatMessageListRightViewHolder extends RecyclerView.ViewHolder {

    private TextView tvRightTime;//时间
    private FrescoImageView ivRightHeaderIcon;//头像
    private TextView tvRightContent;//内容
    private FrescoImageView ivRightLogo;//图片
    private FrameLayout flRightAudio;//音频父布局
    private RelativeLayout rlRightContent;//内容父布局
    private ImageView ivRightAudio;//音频动画控件
    private TextView tvRightAudioTime;//音频时间
    private ProgressBar pbRightProgress;//进度条

    public ChatMessageListRightViewHolder(View itemView) {
        super(itemView);
        tvRightTime = (TextView) itemView.findViewById(R.id.talk_post_right_tv_time);
        ivRightHeaderIcon = (FrescoImageView) itemView.findViewById(R.id.talk_post_right_user_header_img);
        tvRightContent = (TextView) itemView.findViewById(R.id.talk_post_right_tv_content);
        ivRightLogo = (FrescoImageView) itemView.findViewById(R.id.talk_post_right_message_item_thumb_thumbnail);
        flRightAudio = (FrameLayout) itemView.findViewById(R.id.talk_post_right_message_item_audio_container);
        ivRightAudio = (ImageView) itemView.findViewById(R.id.talk_post_right_message_item_audio_playing_animation);
        tvRightAudioTime = (TextView) itemView.findViewById(R.id.talk_post_right_message_item_audio_duration);
        rlRightContent = (RelativeLayout) itemView.findViewById(R.id.talk_post_right_content);
        pbRightProgress = (ProgressBar) itemView.findViewById(R.id.talk_post_right_message_item_progressBar);
    }

    public RelativeLayout getRlRightContent() {
        return rlRightContent;
    }

    public void setRlRightContent(RelativeLayout rlRightContent) {
        this.rlRightContent = rlRightContent;
    }

    public ProgressBar getPbRightProgress() {
        return pbRightProgress;
    }

    public void setPbRightProgress(ProgressBar pbRightProgress) {
        this.pbRightProgress = pbRightProgress;
    }

    public TextView getTvRightTime() {
        return tvRightTime;
    }

    public void setTvRightTime(TextView tvRightTime) {
        this.tvRightTime = tvRightTime;
    }

    public FrescoImageView getIvRightHeaderIcon() {
        return ivRightHeaderIcon;
    }

    public void setIvRightHeaderIcon(FrescoImageView ivRightHeaderIcon) {
        this.ivRightHeaderIcon = ivRightHeaderIcon;
    }

    public TextView getTvRightContent() {
        return tvRightContent;
    }

    public void setTvRightContent(TextView tvRightContent) {
        this.tvRightContent = tvRightContent;
    }

    public FrescoImageView getIvRightLogo() {
        return ivRightLogo;
    }

    public void setIvRightLogo(FrescoImageView ivRightLogo) {
        this.ivRightLogo = ivRightLogo;
    }

    public FrameLayout getFlRightAudio() {
        return flRightAudio;
    }

    public void setFlRightAudio(FrameLayout flRightAudio) {
        this.flRightAudio = flRightAudio;
    }

    public ImageView getIvRightAudio() {
        return ivRightAudio;
    }

    public void setIvRightAudio(ImageView ivRightAudio) {
        this.ivRightAudio = ivRightAudio;
    }

    public TextView getTvRightAudioTime() {
        return tvRightAudioTime;
    }

    public void setTvRightAudioTime(TextView tvRightAudioTime) {
        this.tvRightAudioTime = tvRightAudioTime;
    }
}
