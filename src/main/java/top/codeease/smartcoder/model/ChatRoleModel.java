package top.codeease.smartcoder.model;


import lombok.Data;

import java.util.List;

/**
 * @author by: ly
 * @ClassName: ChatRoleModel
 * @Description: 带有角色的入参模型
 * @Date: 2024/5/15 下午5:07
 */
@Data
public class ChatRoleModel {
    private String model;
    private List<RoleContentModel> messages;
    private Boolean stream;
}
