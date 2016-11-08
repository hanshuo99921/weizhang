package pkg_chexian.freecode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
/**
 * ͨ��google��zxing������ά��
 * @author tskk
 * @version 2015-6-26 13:30:20
 * ע���˴��룬���ܽ�����L�������logo��H����Ľ���
 * */
public final class DecodeImgZxing {
	private static Logger log = Logger.getLogger(DecodeImgZxing.class);   
	//��ά���ʽ����
	private static final EnumMap<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
	static{
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
	}
	/**
	 * ������ά�룬ʹ��google��zxing
	 * @param imgPath ��ά��·��
	 * @return content ��ά������
	 * */
	public  String decodeImg(File imgFile){
		String content = null;
		if(!imgFile.isFile()){
			System.out.println("������ļ�");
			return null;
		}
		try {
			BufferedImage image = ImageIO.read(imgFile);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			MultiFormatReader reader = new MultiFormatReader();
			Result result = reader.decode(binaryBitmap, hints);
			content = result.getText();
//			System.out.println("��ά������"+":"+result.toString()+"��"+result.getBarcodeFormat()+"��"+result.getText());
		} catch (NotFoundException e) {
			log.error("��ά�����NotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("��ά�����IOException");
			e.printStackTrace();
		}
		return content;
	}
	 public static void main(String[] args) {
		// File img = new File("f:"+File.separator+"3.jpg");  
		 //String content = DecodeImgZxing.decodeImg(img);  
	     //   System.out.println("1:"+content);  
	 }
	
}
