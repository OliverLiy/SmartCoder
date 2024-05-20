package top.codeease.smartcoder.model;

import lombok.Data;

/**
 * @author by: ly
 * @ClassName: ChatModel
 * @Description: 聊天的模型对象
 * @Date: 2024/5/15 上午10:42
 */
@Data
public class ChatModel {
    private String model;
    private String prompt;
    private Boolean stream;
}
