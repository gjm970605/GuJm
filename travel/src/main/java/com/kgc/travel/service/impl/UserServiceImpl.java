package com.kgc.travel.service.impl;

import com.kgc.travel.dao.UserDao;
import com.kgc.travel.dao.impl.UserDaoImpl;
import com.kgc.travel.domain.ResultInfo;
import com.kgc.travel.domain.User;
import com.kgc.travel.service.UserService;
import com.kgc.travel.util.MailUtils;
import com.kgc.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public ResultInfo register(User user) {
        User findUser = userDao.findByUsername(user.getUsername());

        ResultInfo resultInfo = new ResultInfo();
        if (findUser != null){
            //已经存在该用户名,不能注册
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("该用户名已注册,请更换用户名");
        }else {
            //可以注册 同时发送激活邮件
            //设置激活状态及激活唯一码，发送激活邮件
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            boolean add = userDao.addUser(user);
            if (add){
                //注册成功
                resultInfo.setFlag(true);

                //邮箱授权码yfgpkmpyikxcbead      or  hynkqfxjsqdrbcfd
                String text = "您好,这是课工场旅游网注册激活邮件,若您未注册,可忽略此邮件\r\n" +
                        "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点此激活您的账号</a>";
                MailUtils.sendMail(user.getEmail(),text,"课工场旅游网注册激活");

            }else{
                //注册失败
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败");
            }

        }
        return resultInfo;

    }

    @Override
    public boolean active(String toActiveCode) {
        //首先查询是否有此用户
        User findUser = userDao.findByCode(toActiveCode);

        if (findUser != null){
            //为用户进行激活
            return userDao.active(findUser);
        }
        //没有用户
        return false;
    }

    @Override
    public ResultInfo login(String username, String password) {
        User findUser = userDao.findByUsername(username);

        ResultInfo info = new ResultInfo();

        if (findUser != null){
            if (findUser.getPassword().equals(password)){
                //用户名密码正确
                if (findUser.getStatus().equals("Y")){
                    info.setFlag(true);
                    info.setData(findUser);
                }else {
                    //未激活
                    info.setFlag(false);
                    info.setErrorMsg("账号未激活");
                }
            }else {
                //密码错误
                info.setFlag(false);
                info.setErrorMsg("密码错误");
            }
        }else {
            //用户名错误
            info.setFlag(false);
            info.setErrorMsg("用户名错误");
        }

        return info;
    }
}
