package com.example.sb2.kit;

public class KaptchaConstants {
    public final static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    public final static String KAPTCHA_SESSION_DATE = "KAPTCHA_SESSION_DATE";

    public final static String KAPTCHA_SESSION_CONFIG_KEY = "kaptcha.session.key";

    public final static String KAPTCHA_SESSION_CONFIG_DATE = "kaptcha.session.date";
    // 图片边框，合法值：yes , no
    public final static String KAPTCHA_BORDER = "kaptcha.border";
    // 边框颜色，合法值： rgb (and optional alpha) 或者 white,black,blue.
    public final static String KAPTCHA_BORDER_COLOR = "kaptcha.border.color";
    // 边框厚度，合法值：>0
    public final static String KAPTCHA_BORDER_THICKNESS = "kaptcha.border.thickness";
    // 干扰颜色，合法值： r,g,b 或者 white,black,blue.
    public final static String KAPTCHA_NOISE_COLOR = "kaptcha.noise.color";
    // 干扰实现类
    public final static String KAPTCHA_NOISE_IMPL = "kaptcha.noise.impl";
    // 图片样式：
    // 水纹com.google.code.kaptcha.impl.WaterRipple
    // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
    // 阴影com.google.code.kaptcha.impl.ShadowGimpy
    public final static String KAPTCHA_OBSCURIFICATOR_IMPL = "kaptcha.obscurificator.impl";
    // 背景实现类
    public final static String KAPTCHA_PRODUCER_IMPL = "kaptcha.producer.impl";
    // 文本实现类
    public final static String KAPTCHA_TEXTPRODUCER_IMPL = "kaptcha.textproducer.impl";
    // 文本集合，验证码值从此集合中获取
    public final static String KAPTCHA_TEXTPRODUCER_CHAR_STRING = "kaptcha.textproducer.char.string";
    // 验证码长度
    public final static String KAPTCHA_TEXTPRODUCER_CHAR_LENGTH = "kaptcha.textproducer.char.length";
    // 字体
    public final static String KAPTCHA_TEXTPRODUCER_FONT_NAMES = "kaptcha.textproducer.font.names";
    // 字体颜色，合法值： r,g,b  或者 white,black,blue.
    public final static String KAPTCHA_TEXTPRODUCER_FONT_COLOR = "kaptcha.textproducer.font.color";
    // 字体大小
    public final static String KAPTCHA_TEXTPRODUCER_FONT_SIZE = "kaptcha.textproducer.font.size";
    // 文字间隔
    public final static String KAPTCHA_TEXTPRODUCER_CHAR_SPACE = "kaptcha.textproducer.char.space";
    // 文字渲染器
    public final static String KAPTCHA_WORDRENDERER_IMPL = "kaptcha.word.impl";
    // 背景实现类
    public final static String KAPTCHA_BACKGROUND_IMPL = "kaptcha.background.impl";
    // 背景颜色渐变，开始颜色
    public static final String KAPTCHA_BACKGROUND_CLR_FROM = "kaptcha.background.clear.from";
    // 背景颜色渐变，结束颜色
    public static final String KAPTCHA_BACKGROUND_CLR_TO = "kaptcha.background.clear.to";
    // 图片宽
    public static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";
    // 图片高
    public static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";
}
