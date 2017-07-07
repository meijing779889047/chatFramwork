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
 * 类描述：聊天消息之左边适配器Viderholder
 * 创建人：mj
 * 创建时间：2017/6/7 19:36
 * 修改人：Administrator
 * 修改时间：2017/6/7 19:36
 * 修改备注：
 * Version:  1.0.0
 */
public class ChatMessageListLeftViewHolder extends RecyclerView.ViewHolder {


    private TextView tvLeftTime;//时间
    private FrescoImageView ivLeftHeaderIcon;//头像
    private TextView tvLeftContent;//内容
    private FrescoImageView ivLeftLogo;//图片
    private FrameLayout ivLeftFlAudio;//音频父布局
    private RelativeLayout rlLeftContent;//内容父布局
    private ImageView ivLeftAudio;//音频动画控件
    private TextView tvLeftAudioTime;//音频时间
    private ProgressBar pbLeftProgress;//进度条


    public ChatMessageListLeftViewHolder(View itemView) {
        super(itemView);
        tvLeftTime = (TextView) itemView.findViewById(R.id.talk_post_left_tv_time);
        ivLeftHeaderIcon = (FrescoImageView) itemView.findViewById(R.id.talk_post_left_user_header_imgs);
        tvLeftContent = (TextView) itemView.findViewById(R.id.talk_post_left_tv_content);
        ivLeftLogo = (FrescoImageView) itemView.findViewById(R.id.talk_post_left_message_item_thumb_thumbnail);
        ivLeftFlAudio = (FrameLayout) itemView.findViewById(R.id.talk_post_left_message_item_audio_container);
        ivLeftAudio = (ImageView) itemView.findViewById(R.id.talk_post_left_message_item_audio_playing_animation);
        tvLeftAudioTime = (TextView) itemView.findViewById(R.id.talk_post_left_message_item_audio_duration);
        rlLeftContent = (RelativeLayout) itemView.findViewById(R.id.talk_post_left_content);
        pbLeftProgress = (ProgressBar) itemView.findViewById(R.id.talk_post_left_message_item_progressBar);
    }

    public RelativeLayout getRlLeftContent() {
        return rlLeftContent;
    }

    public void setRlLeftContent(RelativeLayout rlLeftContent) {
        this.rlLeftContent = rlLeftContent;
    }

    public ProgressBar getPbLeftProgress() {
        return pbLeftProgress;
    }

    public void setPbLeftProgress(ProgressBar pbLeftProgress) {
        this.pbLeftProgress = pbLeftProgress;
    }

    public TextView getTvLeftTime() {
        return tvLeftTime;
    }

    public void setTvLeftTime(TextView tvLeftTime) {
        this.tvLeftTime = tvLeftTime;
    }

    public FrescoImageView getIvLeftHeaderIcon() {
        return ivLeftHeaderIcon;
    }

    public void setIvLeftHeaderIcon(FrescoImageView ivLeftHeaderIcon) {
        this.ivLeftHeaderIcon = ivLeftHeaderIcon;
    }

    public TextView getTvLeftContent() {
        return tvLeftContent;
    }

    public void setTvLeftContent(TextView tvLeftContent) {
        this.tvLeftContent = tvLeftContent;
    }

    public FrescoImageView getIvLeftLogo() {
        return ivLeftLogo;
    }

    public void setIvLeftLogo(FrescoImageView ivLeftLogo) {
        this.ivLeftLogo = ivLeftLogo;
    }

    public FrameLayout getIvLeftFlAudio() {
        return ivLeftFlAudio;
    }

    public void setIvLeftFlAudio(FrameLayout ivLeftFlAudio) {
        this.ivLeftFlAudio = ivLeftFlAudio;
    }

    public ImageView getIvLeftAudio() {
        return ivLeftAudio;
    }

    public void setIvLeftAudio(ImageView ivLeftAudio) {
        this.ivLeftAudio = ivLeftAudio;
    }

    public TextView getTvLeftAudioTime() {
        return tvLeftAudioTime;
    }

    public void setTvLeftAudioTime(TextView tvLeftAudioTime) {
        this.tvLeftAudioTime = tvLeftAudioTime;
    }
}
