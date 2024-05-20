package top.codeease.smartcoder.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import top.codeease.smartcoder.config.OkHttpClientSingleton;
import top.codeease.smartcoder.constant.ModelConstant;
import top.codeease.smartcoder.model.*;
import top.codeease.smartcoder.storage.MessageHistoryStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static top.codeease.smartcoder.constant.ModelConstant.*;

/**
 * @author by: ly
 * @ClassName: AiExecutor
 * @Description: Ai模块执行器
 * @Date: 2024/5/15 上午9:52
 */
public class AiExecutor {

    private String generateUrl = "http://127.0.0.1:11434/api/generate";
    private String chatUrl = "http://127.0.0.1:11434/api/chat";
    private String ApiKey = "Bearer 换成自己阿里云百炼平台的key";
    /**
     * 通义大模型模型链接
     */
    private String tongYiUrl = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    /**
     * 基于通义大模型API获取返回结果
     * @param prompt
     * @return
     */
    public String getChatMessageByTongYi(String prompt){
        OkHttpClient client = OkHttpClientSingleton.getInstance();
        // 构建入参
        TongYiModel model = new TongYiModel();
        model.setModel(QWEN_MAX);
        InputModel inputModel = new InputModel();
        List<RoleContentModel> messages = new ArrayList<>();
        RoleContentModel roleContentModel = new RoleContentModel();
        roleContentModel.setRole(USER_ROLE);
        roleContentModel.setContent(prompt);
        messages.add(roleContentModel);
        inputModel.setMessages(messages);
        model.setInput(inputModel);
        // 发起请求
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(model));
        Request request = new Request.Builder()
                .header("Authorization",ApiKey)
                .url(tongYiUrl)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // 处理响应
            if (response.isSuccessful()) {
                // 请求成功
                ResponseBody responseBody = response.body();
                String string = responseBody.string();
                JSONObject jsonObject = JSON.parseObject(string);
                // 处理响应体
                JSONObject outputJson = jsonObject.getJSONObject(OUTPUT);
                return outputJson.getString(TEXT);
            } else {
                // 请求失败
                return "请求失败，响应码: " + response.code();
            }
        } catch (IOException e) {
            // 发生异常
            e.printStackTrace();
        }
        return "请求失败";
    }

    /**
     * 基于通义大模型API获取上下文对象
     * @param prompt
     * @return
     */
    public String getChatHistoryByTongYi(String prompt){
        OkHttpClient client = OkHttpClientSingleton.getInstance();
        // 构建入参
        TongYiModel model = new TongYiModel();
        model.setModel(QWEN_MAX);
        InputModel inputModel = new InputModel();
        // 构建上下文入参
        inputModel.setMessages(MessageHistoryStorage.buildRoleContentModel(prompt));
        model.setInput(inputModel);
        // 发起请求
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(model));
        Request request = new Request.Builder()
                .header("Authorization",ApiKey)
                .url(tongYiUrl)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // 处理响应
            if (response.isSuccessful()) {
                // 请求成功
                ResponseBody responseBody = response.body();
                String string = responseBody.string();
                JSONObject jsonObject = JSON.parseObject(string);
                // 处理响应体
                JSONObject outputJson = jsonObject.getJSONObject(OUTPUT);
                return outputJson.getString(TEXT);
            } else {
                // 请求失败
                return "请求失败，响应码: " + response.code();
            }
        } catch (IOException e) {
            // 发生异常
            e.printStackTrace();
        }
        return "请求失败";
    }


    /**
     * 简单的聊天获取结果
     * @param prompt
     * @return
     */
    public String getChatMessage(String prompt){
        OkHttpClient client = OkHttpClientSingleton.getInstance();
        ChatModel chatModel = new ChatModel();
        chatModel.setModel(ModelConstant.LLAMA);
        chatModel.setPrompt(prompt);
        chatModel.setStream(false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(chatModel));
        Request request = new Request.Builder()
                .url(generateUrl)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // 处理响应
            if (response.isSuccessful()) {
                // 请求成功
                ResponseBody responseBody = response.body();
                String string = responseBody.string();
                JSONObject jsonObject = JSON.parseObject(string);
                // 处理响应体
                return jsonObject.getString(RESPONSE);
            } else {
                // 请求失败
                return "请求失败，响应码: " + response.code();
            }
        } catch (IOException e) {
            // 发生异常
            e.printStackTrace();
        }
        return "请求失败";
    }

    /**
     * 带历史上下文的回答
     * @param prompt
     * @return
     */
    public String getRoleContentResult(String prompt){
        OkHttpClient client = OkHttpClientSingleton.getInstance();
        ChatRoleModel chatRoleModel = new ChatRoleModel();
        chatRoleModel.setModel(LLAMA);
        chatRoleModel.setStream(false);
        chatRoleModel.setMessages(MessageHistoryStorage.buildRoleContentModel(prompt));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(chatRoleModel));
        Request request = new Request.Builder()
                .url(chatUrl)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // 处理响应
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String string = responseBody.string();
                JSONObject jsonObject = JSON.parseObject(string);
                JSONObject messageJson = jsonObject.getJSONObject(MESSAGE);
                return messageJson.getString(CONTENT);
            } else {
                // 请求失败
                System.out.println("请求失败，响应码: " + response.code());
                return "请求失败，响应码: " + response.code();
            }
        } catch (IOException e) {
            // 发生异常
            e.printStackTrace();
        }
        return "请求失败";
    }
}
