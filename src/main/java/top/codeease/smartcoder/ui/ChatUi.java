package top.codeease.smartcoder.ui;

import top.codeease.smartcoder.executor.AiExecutor;
import top.codeease.smartcoder.storage.MessageHistoryStorage;

import javax.swing.*;

/**
 * @author by: ly
 * @ClassName: ChatUI
 * @Description: 聊天模块的UI
 * @Date: 2024/5/14 下午4:07
 */
public class ChatUi {
    private JTabbedPane tabbedPane1;
    private JTextField textField;
    private JButton button;
    private JEditorPane editorPane1;

    public ChatUi(){
        button.addActionListener(e -> {
            String text = textField.getText();
            AiExecutor aiExecutor = new AiExecutor();
            String result = aiExecutor.getChatHistoryByTongYi(text);
            // 构建html请求
            MessageHistoryStorage.putUserContent(text);
            MessageHistoryStorage.putAssistantContent(result);
            String html = MessageHistoryStorage.buildHtmlString();
            editorPane1.setText(html);
        });
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }
}
