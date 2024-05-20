package top.codeease.smartcoder.constant;

/**
 * @author by: ly
 * @ClassName: HtmlConstant
 * @Description: 定义Html相关常量
 * @Date: 2024/5/15 下午4:58
 */
public class HtmlConstant {
    public static String htmlTitle = "<html><body style='margin: 10px;'>";
    public static String htmlEnding = "</body></html>";
    public static String userHtmlFormat = "<div style='text-align: right;'>" +
            "<div style='display: inline-block; background-color: #DCF8C6; padding: 10px; border-radius: 10px;'>" +
            "<strong>You:</strong> %s" +
            "</div>" +
            "</div>";
    public static String assistantFormat = "<div style='text-align: left;'>" +
            "<div style='display: inline-block; background-color: #F0F0F0; padding: 10px; border-radius: 10px;'>" +
            "<strong>Assistant:</strong> %s" +
            "</div>" +
            "</div>" ;
}
