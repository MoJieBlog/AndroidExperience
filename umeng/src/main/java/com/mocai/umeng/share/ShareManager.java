package com.mocai.umeng.share;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

/**
 * @author Li Xiaopeng
 * @date 2018/12/21
 */
public class ShareManager {

    private static ShareManager INSTANCE;

    private Activity activity;
    private ShareType shareType;
    private ShareContentBuilder contentBuilder;

    public static ShareManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ShareManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShareManager();
                }
            }
        }
        return INSTANCE;
    }

    public ShareManager() {
    }

    /**
     * QQ与新浪分享需要在使用QQ分享或者授权的Activity中添加该方法
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化
     *
     * @param application
     * @param isDebug
     * @param umAppKey
     * @param channel
     * @param wxAppId
     * @param wxAppSecret
     * @param qqAppId
     * @param qqAppKey
     * @param wbAppId
     * @param wbAppKey
     */
    public void init(Application application, boolean isDebug, String umAppKey, String channel,
                     String wxAppId, String wxAppSecret, String qqAppId,
                     String qqAppKey, String wbAppId, String wbAppKey) {
        // 友盟登录分享初始化
        UMConfigure.setLogEnabled(isDebug);
        UMConfigure.init(application, umAppKey, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
        //友盟平台需要
        PlatformConfig.setWeixin(wxAppId, wxAppSecret);
        PlatformConfig.setQQZone(qqAppId, qqAppKey);
        PlatformConfig.setSinaWeibo(wbAppId, wbAppKey, "http://sns.whalecloud.com");
    }


    public ShareManager withActivity(Activity activity) {
        this.activity = activity;
        return INSTANCE;
    }


    public ShareContentBuilder shareType(ShareType shareType) {
        this.shareType = shareType;
        if (activity == null) {
            return null;
        }
        contentBuilder = new ShareContentBuilder(activity, shareType);
        return contentBuilder;
    }

    public void share() {
        if (shareType == null) {
            return;
        }
        if (activity == null) {
            return;
        }
        if (contentBuilder == null) {
            return;
        }
        contentBuilder.share();
    }

    public void release() {
        activity = null;
        shareType = null;
        contentBuilder.release();
        contentBuilder = null;
    }


    public static class ShareContentBuilder implements IShareMediaType {

        private Activity activity;
        private ShareType shareType;

        SharePlatForm sharePlatForm;
        int pictureRes;
        String picUrl;
        String title;
        String content;
        String webUrl;
        String audioUrl, audioTurnToUrl;
        String videoUrl;
        private String wxUrl, wxPath, userName;

        UMShareListener listener;
        private ShareAction shareAction;


        public ShareContentBuilder(Activity activity, ShareType shareType) {
            this.activity = activity;
            this.shareType = shareType;
        }

        void share() {
            shareAction.share();
        }

        public void release() {
            activity = null;
            shareType = null;
            sharePlatForm = null;
            pictureRes = 0;
            picUrl = null;
            title = null;
            content = null;
            webUrl = null;
            audioUrl = null;
            audioTurnToUrl = null;
            videoUrl = null;
            wxUrl = null;
            wxPath = null;
            userName = null;
            listener = null;
            shareAction = null;
        }

        public ShareManager build() {
            try {


                if (shareType == null) {
                    throw new IllegalAccessException("shareType can not be null.");
                } else if (activity == null) {
                    throw new IllegalAccessException("activity can not be null.");
                } else if (sharePlatForm == null) {
                    throw new IllegalAccessException("sharePlatForm can not be null.");
                }
                shareAction = new ShareAction(activity);
                shareAction.setPlatform(getPlatForm(sharePlatForm));
                if (shareType == ShareType.TEXT) {//分享文字
                    if (TextUtils.isEmpty(content)) {
                        throw new IllegalAccessException("share content can not be null.");
                    } else {
                        shareAction.withText(content);
                    }
                } else if (shareType == ShareType.PICTURE) {
                    if (pictureRes == 0 && TextUtils.isEmpty(picUrl)) {
                        throw new IllegalAccessException("share pic can not be null.");
                    } else {
                        if (!TextUtils.isEmpty(content)) {
                            shareAction.withText(content);
                        }
                        UMImage image = getSharePic();
                        shareAction.withMedia(image);
                    }
                } else if (shareType == ShareType.WEB) {
                    if (TextUtils.isEmpty(webUrl)) {
                        throw new IllegalAccessException("webUrl can not be null.");
                    } else {
                        UMWeb web = new UMWeb(webUrl);
                        web.setTitle(title);//标题
                        web.setThumb(getSharePic());  //缩略图
                        web.setDescription(content);//描述
                        shareAction.withMedia(web);
                    }
                } else if (shareType == ShareType.AUDIO) {
                    if (TextUtils.isEmpty(audioUrl)) {
                        throw new IllegalAccessException("audioUrl can not be null.");
                    } else {
                        UMusic music = new UMusic(audioUrl);//音乐的播放链接
                        music.setTitle(title);//音乐的标题
                        music.setThumb(getSharePic());//音乐的缩略图
                        music.setDescription(content);//音乐的描述
                        music.setmTargetUrl(audioTurnToUrl);//音乐的跳转链接
                        shareAction.withMedia(music);
                    }
                } else if (shareType == ShareType.VIDEO) {
                    if (TextUtils.isEmpty(videoUrl)) {
                        throw new IllegalAccessException("videoUrl can not be null.");
                    } else {
                        UMVideo video = new UMVideo(videoUrl);
                        video.setTitle(title);//视频的标题
                        video.setThumb(getSharePic());//视频的缩略图
                        video.setDescription(content);//视频的描述
                        shareAction.withMedia(video);
                    }
                } else if (shareType == ShareType.WXEXE) {
                    if (TextUtils.isEmpty(wxUrl)) {
                        throw new IllegalAccessException("wxUrl can not be null.");
                    } else if (TextUtils.isEmpty(wxPath)) {
                        throw new IllegalAccessException("wxPath can not be null.");
                    } else if (TextUtils.isEmpty(userName)) {
                        throw new IllegalAccessException("userName can not be null.");
                    } else {
                        UMMin umMin = new UMMin(wxUrl);
                        umMin.setThumb(getSharePic());
                        umMin.setTitle(title);
                        umMin.setDescription(content);
                        umMin.setPath(wxPath);
                        umMin.setUserName(userName);// 小程序原始id,在微信平台查询
                        shareAction.withMedia(umMin);
                    }

                }
                if (listener != null) {
                    shareAction.setCallback(listener);
                }
            } catch (Exception e) {
            }
            return INSTANCE;
        }

        public UMImage getSharePic() {
            UMImage image = null;
            if (!TextUtils.isEmpty(picUrl)) {
                image = new UMImage(activity, picUrl);
                image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图

            } else if (pictureRes != 0) {
                image = new UMImage(activity, pictureRes);
                image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
            }
            return image;
        }

        @Override
        public ShareContentBuilder withPlatForm(SharePlatForm sharePlatForm) {
            this.sharePlatForm = sharePlatForm;
            return this;
        }

        @Override
        public ShareContentBuilder withPictureRes(int pictureRes) {
            this.pictureRes = pictureRes;
            return this;
        }

        @Override
        public ShareContentBuilder withNetPicture(String picUrl) {
            this.picUrl = picUrl;
            return this;
        }

        @Override
        public ShareContentBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public ShareContentBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public ShareContentBuilder withWeb(String webUrl) {
            this.webUrl = webUrl;
            return this;
        }

        @Override
        public ShareContentBuilder withAudio(String audioUrl, String audioTurnToUrl) {
            this.audioUrl = audioUrl;
            this.audioTurnToUrl = audioTurnToUrl;
            return this;
        }

        @Override
        public ShareContentBuilder withVideo(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        @Override
        public ShareContentBuilder withWxExe(String url, String path, String userName) {
            this.userName = userName;
            this.wxUrl = url;
            this.wxPath = path;
            return this;
        }

        @Override
        public ShareContentBuilder withCallback(UMShareListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 获取对应的平台
         *
         * @param sharePlatForm
         * @return
         */
        @Nullable
        private SHARE_MEDIA getPlatForm(SharePlatForm sharePlatForm) {
            SHARE_MEDIA platform = null;
            if (sharePlatForm == SharePlatForm.SHARE_WX_CHART) {
                platform = SHARE_MEDIA.WEIXIN;
            } else if (sharePlatForm == SharePlatForm.SHARE_WX_CIRCLE) {
                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
            } else if (sharePlatForm == SharePlatForm.SHARE_WX_COLLECTION) {
                platform = SHARE_MEDIA.WEIXIN_FAVORITE;
            } else if (sharePlatForm == SharePlatForm.SHARE_WB) {
                platform = SHARE_MEDIA.SINA;
            } else if (sharePlatForm == SharePlatForm.SHARE_QQ_CHART) {
                platform = SHARE_MEDIA.QQ;
            } else if (sharePlatForm == SharePlatForm.SHARE_QZONE) {
                platform = SHARE_MEDIA.QZONE;
            } else if (sharePlatForm == SharePlatForm.SHARE_EMAIL) {
                platform = SHARE_MEDIA.EMAIL;
            } else if (sharePlatForm == SharePlatForm.SHARE_SMS) {
                platform = SHARE_MEDIA.SMS;
            }
            return platform;
        }
    }


}
