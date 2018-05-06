package com.sam.graduation.design.gdemailserver.utils.cut;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageCut {

	public static void cut(FileInputStream is, String subpath, ImageBean imageBean, String format) throws IOException {
		ImageInputStream iis = null;
		try {
			ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName(format).next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);

			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(imageBean.getX(), imageBean.getY(), imageBean.getW(), imageBean.getH());
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);

			ImageIO.write(bi, format, new File(subpath + imageBean.getName() + "." + format));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}
	}
}
