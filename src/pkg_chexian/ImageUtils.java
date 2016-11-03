package pkg_chexian;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * @author Eric Xu
 * 
 */
public final class ImageUtils {
	public static void main(String[] args) throws IOException {
		// pressImage("G:\\imgtest\\sy.jpg", "G:\\imgtest\\test1.jpg", 0, 0,
		// 0.5f);
		//pressText("仅限中国邮政代办违章罚款", "f://62601002.jpg", "黑体", 36, 55, 0, 60);
		// resize("G:\\imgtest\\test1.jpg", 500, 500, true);
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度
	 * @throws Cls_exception 
	 */
	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, int fontSize, int x, int y) throws Cls_exception,IOException {
		pressText = "仅限中国邮政代办违章罚款";
		File img = null;
		Image src = null;
		BufferedImage image = null;
		Graphics2D g = null;
		try {
			img = new File(targetImg);
			if (img.exists() && img.isFile()) {
				if (img.length() > 0) {
					src = ImageIO.read(img);
					int width = src.getWidth(null);
					int height = src.getHeight(null);
					image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					g = image.createGraphics();
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.drawImage(src, 0, 0, width, height, null);
					g.rotate(Math.toRadians(-35),
							(double) image.getWidth() / 2,
							(double) image.getHeight() / 2);
					g.setColor(Color.red);
					g.setFont(new Font(fontName, fontStyle, fontSize));
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_ATOP, 0.3f));
					g.drawString(
							pressText,
							(width - (getLength(pressText) * fontSize)) / 2 + x,
							(height - fontSize) / 2 + y);
					// g.drawString(pressText, 5, (height - fontSize) / 2 + y);
					// g.drawString(pressText, -20, 120);
					g.dispose();
					ImageIO.write((BufferedImage) image, "jpg", img);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			if (img != null) {
				try {
					img = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}
			if (src != null) {
				try {
					src = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}			
			if (image != null) {
				try {
					image = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}
			if (g != null) {
				try {
					g = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}	
		}
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}