package top.codeease.smartcoder.storage;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import top.codeease.smartcoder.constant.HtmlConstant;
import top.codeease.smartcoder.constant.ModelConstant;
import top.codeease.smartcoder.model.RoleContentModel;
import top.codeease.smartcoder.util.MarkdownUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: MessageHistoryStorage
 * @Description: 将历史的记录存储在本地
 * @Date: 2024/5/15 下午4:50
 */
public class MessageHistoryStorage {
    /**
     * 存储用户的聊天记录
     */
    public static List<RoleContentModel> userContentList;
    /**
     * 存储AI的回答记录
     */
    public static List<RoleContentModel> assistantContentList;

    /**
     * 截断历史数量
     */
    public static final Integer LIMIT_NUMBER = 5;

    static {
        userContentList = new ArrayList<>();
        assistantContentList = new ArrayList<>();
        // 后期接入历史数据重新加载时可以在这里加载
    }

    /**
     * 初始化一个角色信息
     */
    public static void initRole(){

    }

    public static void putUserContent(String content){
        RoleContentModel roleContentModel = new RoleContentModel();
        roleContentModel.setRole(ModelConstant.USER_ROLE);
        roleContentModel.setContent(content);
        userContentList.add(roleContentModel);
    }

    public static void putAssistantContent(String content){
        RoleContentModel roleContentModel = new RoleContentModel();
        roleContentModel.setRole(ModelConstant.ASSISTANT_ROLE);
        roleContentModel.setContent(content);
        assistantContentList.add(roleContentModel);
    }

    /**
     * 构建展示的Html代码
     * @return
     */
    public static String buildHtmlString(){
        StringBuilder builder = new StringBuilder();
        builder.append(HtmlConstant.htmlTitle);

        for (int i = 0; i < assistantContentList.size(); i++) {
            builder.append(String.format(HtmlConstant.userHtmlFormat, MarkdownUtils.markdownToHtml(userContentList.get(i).getContent())));
            builder.append(String.format(HtmlConstant.assistantFormat, MarkdownUtils.markdownToHtml(assistantContentList.get(i).getContent())));
        }
        builder.append(HtmlConstant.htmlEnding);
        return builder.toString();
    }

    /**
     * 构建包含历史记录的请求集合
     * @param prompt
     * @return
     */
    public static List<RoleContentModel> buildRoleContentModel(String prompt){
        List<RoleContentModel> modelList = new ArrayList<>();
        if (assistantContentList.isEmpty()){
            RoleContentModel model = new RoleContentModel();
            model.setRole(ModelConstant.USER_ROLE);
            model.setContent(prompt);
            modelList.add(model);
        }else {
            for (int i = 0; i < assistantContentList.size(); i++) {
                modelList.add(userContentList.get(i));
                modelList.add(assistantContentList.get(i));
            }
            RoleContentModel model = new RoleContentModel();
            model.setRole(ModelConstant.USER_ROLE);
            model.setContent(prompt);
            modelList.add(model);
        }
        return modelList;
    }
}
