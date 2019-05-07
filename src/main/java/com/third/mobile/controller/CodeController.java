package com.third.mobile.controller;

import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.integration.IMessageService;
import com.third.mobile.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/code")
public class CodeController {

    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IMessageService messageService;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void generateImageCode(HttpServletRequest request, HttpServletResponse response, String attr) {
        System.setProperty("java.awt.headless", "true");
        // 禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应是图片
        response.setContentType("image/jpeg");
        int width = 110;
        int height = 45;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 创建BufferedImage类的对象
        Graphics g = image.getGraphics(); // 创建Graphics类的对象
        Graphics2D g2d = (Graphics2D) g; // 通过Graphics类的对象创建一个Graphics2D类的对象
        Random random = new Random(); // 实例化一个Random对象
        Font mFont = new Font("Arial", Font.BOLD, 23); // 通过Font构造字体
        g.setColor(getRandColor(200, 250)); // 改变图形的当前颜色为随机生成的颜色
        g.fillRect(0, 0, width, height); // 绘制一个填色矩形

        // 画一条折线
        BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL); // 创建一个供画笔选择线条粗细的对象
        g2d.setStroke(bs); // 改变线条的粗细
        g.setColor(Color.DARK_GRAY); // 设置当前颜色为预定义颜色中的深灰色
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        for (int j = 0; j < 3; j++) {
            xPoints[j] = random.nextInt(width - 1);
            yPoints[j] = random.nextInt(height - 1);
        }
        g.drawPolyline(xPoints, yPoints, 3);
        // 生成并输出随机的验证文字
        g.setFont(mFont);
        String sRand = "";
        int itmp = 0;
        for (int i = 0; i < 4; i++) {
            if (random.nextInt(2) == 1) {
                itmp = random.nextInt(26) + 65; // 生成A~Z的字母
            } else {
                itmp = random.nextInt(10) + 48; // 生成0~9的数字
            }
            char ctmp = (char) itmp;
            sRand += String.valueOf(ctmp);
            Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));
            g.setColor(color);
            /**** 随机缩放文字并将文字旋转指定角度 **/
            // 将文字旋转指定角度
            Graphics2D g2d_word = (Graphics2D) g;
            AffineTransform trans = new AffineTransform();
            trans.rotate(random.nextInt(15) * 3.14 / 180, 5 * i + 5, 10);
            // 缩放文字
            float scaleSize = random.nextFloat() + 0.8f;
            if (scaleSize > 1.1f){
                scaleSize = 1f;
            }
            trans.scale(scaleSize, scaleSize);
            g2d_word.setTransform(trans);
            g.drawString(String.valueOf(ctmp), 20 * i + 20, 18);

        }
        stringRedisTemplate.opsForValue().set(Constants.IMAGE_CODE_PREFIX + sRand, sRand, 5, TimeUnit.MINUTES);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            logger.error("生成图形验证码错误");
            e.printStackTrace();
        }
    }

    public Color getRandColor(int s, int e) {
        Random random = new Random();
        if (s > 255){
            s = 255;
        }
        if (e > 255){
            e = 255;
        }
        int r = s + random.nextInt(e - s);
        int g = s + random.nextInt(e - s);
        int b = s + random.nextInt(e - s);
        return new Color(r, g, b);
    }

    @GetMapping(value = "/message")
    public UnifiedResult userVerification(String phone, String imageCode){

        String imageCodeRedisKey = Constants.IMAGE_CODE_PREFIX + imageCode.toUpperCase();
        if(!imageCode.toUpperCase().equals(stringRedisTemplate.opsForValue().get(imageCodeRedisKey))){
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,"图形验证码错误");
        }
        if (StringUtils.isEmpty(phone)) {
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
        }

        //生成激活码
        String code = createRandomNumber();
        String redisKey = Constants.MESSAGE_CODE_PREFIX + phone;
        if(messageService.sendCaptcha(code, phone, "1")) {
            logger.info("短信验证码发送成功：{}", code);
            stringRedisTemplate.opsForValue().set(redisKey, code, 10, TimeUnit.MINUTES);
            return UnifiedResultBuilder.defaultSuccessResult();
        }

        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    private static String createRandomNumber() {
        StringBuffer veriCode = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int temp = (int) (Math.random() * 9);
            veriCode.append(temp);
        }
        return veriCode.toString();
    }
}
