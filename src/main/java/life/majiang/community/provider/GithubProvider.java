package life.majiang.community.provider;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccesstTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class GithubProvider {

    public String getAccessToken(AccesstTokenDTO accesstTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            log.error("getAccessToken error,{}", accesstTokenDTO, e);


        }
        //a4baed0b3e1fd625aa30b16f207ade78d3fbf95d
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将String对象转化为User类
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            log.info(githubUser.getName());
            return githubUser;
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);

        }
        return null;
    }
}
