package com.sam.graduation.design.gdemailserver.utils.cut;

import com.sam.graduation.design.gdemailserver.utils.UUIDUtil;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/3/9 14:51:32
 */
public class ImageCutInfo {

    public static ImageBean getImageCutInfo(int width, int height) {
        if (width > height) { // 采用横切算法
            String fileName = UUIDUtil.getUUIDWithoutLine();
            int cutX;
            int cutY = 0;
            int cutWidth = height;
            int cutHeight = height;
            int halfXOfImage = width / 2;
            cutX = halfXOfImage - cutWidth / 2;
            ImageBean imageBean = new ImageBean(fileName, cutX, cutY, cutWidth, cutHeight);
            return imageBean;
        } else if (width < height) { // 采用竖切算法
            String fileName = UUIDUtil.getUUIDWithoutLine();
            int cutX = 0;
            int cutY;
            int cutWidth = width;
            int cutHeight = width;
            int halfYOfImage = height / 2;
            cutY = halfYOfImage - cutHeight / 2;
            ImageBean imageBean = new ImageBean(fileName, cutX, cutY, cutWidth, cutHeight);
            return imageBean;
        } else { // 不采取措施，直接切割
            ImageBean imageBean = new ImageBean(UUIDUtil.getUUIDWithoutLine(), 0, 0, width, height);
            return imageBean;
        }
    }

}
