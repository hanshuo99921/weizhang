/**
 * 
 */
package pkg_chexian;

import java.io.File;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @author Administrator
 *
 */
public class ZxingEAN13EncoderHandler {
    /**  
     * 编码  
     * @param contents  
     * @param width  
     * @param height  
     * @param imgPath  
     */  
    public void encode(String contents, int width, int height, String imgPath) {   
        int codeWidth = 3 + // start guard   
                (7 * 6) + // left bars   
                5 + // middle guard   
                (7 * 6) + // right bars   
                3; // end guard   
        codeWidth = Math.max(codeWidth, width);   
        try {   
           // BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.EAN_13, codeWidth, height, null);   
            BitMatrix bitMatrix2 = new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_128, codeWidth, height, null);
            MatrixToImageWriter   
                    .writeToFile(bitMatrix2, "png", new File(imgPath));   
             bitMatrix2.clear();
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    public void text_tm(String xlh,String filepath){
    	int width = 105, height = 50;   
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();   
        handler.encode(xlh, width, height, filepath);  
    }
    public static void main(String[] args) {   
        String imgPath = "C:/jboss6/server/default/deploy/chexian.war/half_tm/62601002_0.png ";   
        // 益达无糖口香糖的条形码   
        String contents = "3703991000309430";   
  
        int width = 105, height = 50;   
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();   
        handler.encode(contents, width, height, imgPath);   
  
        System.out.println("Michael ,you have finished zxing EAN13 encode.");   
    } 
}
