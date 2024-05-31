package top.codeease.smartcoder.ui;

import top.codeease.smartcoder.constant.CommonConstant;
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
            // 禁用按钮并更改文字
            button.setEnabled(false);
            button.setText(CommonConstant.BUTTON_PROCESS);

            // 获取输入文本
            String text = textField.getText();

            // 使用 SwingWorker 执行耗时任务
            SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    // 执行耗时操作
                    AiExecutor aiExecutor = new AiExecutor();
                    return aiExecutor.getChatHistoryByTongYi(text);
                }

                @Override
                protected void done() {
                    try {
                        // 获取耗时操作的结果
                        String result = get();

                        // 构建 HTML 请求
                        MessageHistoryStorage.putUserContent(text);
                        MessageHistoryStorage.putAssistantContent(result);
                        String html = MessageHistoryStorage.buildHtmlString();
                        editorPane1.setText(html);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        // 恢复按钮初始状态
                        button.setText(CommonConstant.BUTTON_INIT);
                        button.setEnabled(true);
                    }
                }
            };

            // 执行 SwingWorker
            worker.execute();
        });
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }
}
