package com.zoupanpan.www.login.auth;

import com.zoupanpan.www.login.dao.UserDao;
import com.zoupanpan.www.login.domain.User;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zoupanpan
 * @version 2020/3/22 15:09
 */
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Override
    protected  AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //char[] password = token.getPassword();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        } else {
            User user = userDao.findByName(username);
            if (user == null) {
                throw new UnknownAccountException("un know account");
            }
            if (!user.getPassword().equals(String.valueOf(token.getPassword()))) {
                throw new IncorrectCredentialsException("The password is error");
            }
            return new SimpleAuthenticationInfo(username,user.getPassword().toCharArray(),"loginRealm");
        }
    }
}
