import com.example.config.RootConfig;
import com.example.entity.AuthUser;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
public class MainTest {

    @Resource
    UserMapper mapper;

    @Test
    @Transactional
    public void test() {
        AuthUser user = new AuthUser(0, "username", "password", "user");
        mapper.registerUser(user);
        int i = 1 / 0;
        mapper.addStudentInfo("student", user.getId(), "2020", "男");
    }
}
