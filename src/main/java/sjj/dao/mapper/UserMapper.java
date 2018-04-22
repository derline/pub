package sjj.dao.mapper;

import sjj.bean.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getUser(Integer uid);
}
