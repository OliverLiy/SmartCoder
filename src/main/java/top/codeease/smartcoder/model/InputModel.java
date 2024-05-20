package top.codeease.smartcoder.model;


import lombok.Data;

import java.util.List;

/**
 * @author by: ly
 * @ClassName: InputModel
 * @Description: 通义大模型输入
 * @Date: 2024/5/20 下午9:53
 */
@Data
public class InputModel {
    private List<RoleContentModel> messages;
}
