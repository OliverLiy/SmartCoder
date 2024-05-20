package top.codeease.smartcoder.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import top.codeease.smartcoder.ui.ChatUi;

/**
 * @author by: ly
 * @ClassName: ChatFactory
 * @Description:
 * @Date: 2024/5/14 下午4:41
 */
public class ChatFactory implements ToolWindowFactory {
    private ChatUi chatUi = new ChatUi();
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(chatUi.getTabbedPane1(), "", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);
    }
}
