package life.majiang.community.controller;

import life.majiang.community.dto.AccesstTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.provider.GithubProvider;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        AccesstTokenDTO accesstTokenDTO = new AccesstTokenDTO();
        accesstTokenDTO.setClient_id(clientId);
        accesstTokenDTO.setClient_secret(clientSecret);
        accesstTokenDTO.setCode(code);
        accesstTokenDTO.setRedirect_uri(redirectUri);
        accesstTokenDTO.setState(state);
        String access_Token = githubProvider.getAccessToken(accesstTokenDTO);
        GithubUser githubUser = githubProvider.getUser(access_Token);
        if(githubUser!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtModify());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
             response.addCookie(new Cookie("token",token));
            //登入成功，写cooike 和 session
            return "redirect:/";
        }else{
            return "redirect:/";    //登入失败
        }
    }
}
