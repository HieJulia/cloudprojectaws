package com.project.aws.service.s3;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtility {


    // charSet
    private final String charSet = "UTF-8";
    // qr code height
    private int qrCodeHeight = 500;
    // qr code width
    private int qrCodeWidth = 500;

    /**
     * May nghi la vay a
     *
     *
     * Choi chieu lai - no co tinh -
     *
     *
      * @param data
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public BufferedImage createQRCodeImage(String data) throws WriterException, IOException {
        Map<EncodeHintType, ErrorCorrectionLevel> map = new HashMap<>();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charSet), charSet),
                BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, map);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return bufferedImage;
    }
}



