package com.mocai.umeng.share;

import com.umeng.socialize.UMShareListener;

/**
 * 分享的类型
 * @author Li Xiaopeng
 * @date 2018/12/21
 */
public interface IShareMediaType {

    /**********************分享的平台***************/
    ShareManager.ShareContentBuilder withPlatForm(SharePlatForm sharePlatForm);

    /****************分享的图片资源*****************/
    ShareManager.ShareContentBuilder withPictureRes(int pictureRes);
    ShareManager.ShareContentBuilder withNetPicture(String picUrl);

    /****************分享的文字资源*****************/
    ShareManager.ShareContentBuilder withTitle(String title);
    ShareManager.ShareContentBuilder withContent(String content);

    /****************分享的网页资源*****************/
    ShareManager.ShareContentBuilder withWeb(String url);

    /****************分享的音频资源*****************/
    ShareManager.ShareContentBuilder withAudio(String audioUrl,String audioTurnToUrl);

    /****************分享的视频资源*****************/
    ShareManager.ShareContentBuilder withVideo(String videoUrl);

    /****************分享微信小程序*****************/
    ShareManager.ShareContentBuilder withWxExe(String url,String path,String userName);

    /****************分享的回调*****************/
    ShareManager.ShareContentBuilder withCallback(UMShareListener listener);
}
