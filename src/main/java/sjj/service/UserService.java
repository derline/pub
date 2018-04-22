package sjj.service;

import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import sjj.bean.User;
import sjj.dao.UserDAO;
import sjj.dao.mapper.UserMapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserService extends BaseService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Dao dao;

    public User login() {
        int count = 10000;
        User user = null;
        StopWatch sw = new StopWatch();

        /**
         * example,*
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            user = dao.fetch(User.class, 1);
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));

        /**
         * jdbc select,不*
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            user = userDAO.selectEntity("user", User.class, "uid,username,password,uno,fullname,did", "uid,username", 1, "副总经理编辑");
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));

        /**
         * mybatis
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            user = userMapper.getUser(1);
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));

        /**
         * jdbc,query
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            user = userDAO.getUser();
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));

        /**
         * example,不*
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            Example example = new Example(User.class);
            example.selectProperties("uid,username,password,uno,fullname,did".split(","));
            example.createCriteria().andEqualTo("uid", 1);
            user = userMapper.selectOneByExample(example);
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));

        /**
         * example,*
         */
        user = null;
        sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid", 1);
            user = userMapper.selectOneByExample(example);
        }
        sw.stop();
        System.out.println(sw.getTotalTimeMillis());
        assert (user.getUsername().equals("副总经理编辑"));



        return user;
    }

}