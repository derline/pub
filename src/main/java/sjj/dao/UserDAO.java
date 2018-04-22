package sjj.dao;

import org.springframework.stereotype.Repository;
import sjj.bean.User;

@Repository
public class UserDAO extends BaseDAO {

    public User getUser() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT uid, username, PASSWORD,uno, fullname,did  ");
        sb.append(" FROM USER WHERE uid =? ");
        return  queryForEntity(sb, User.class, 1);
    }
}
