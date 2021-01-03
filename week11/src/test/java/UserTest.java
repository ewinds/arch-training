import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test
    public void testCheckPW() {
        User user = new User("tom", "secret");
        Assert.assertFalse(user.checkPW("jerry", "secret"));
        Assert.assertFalse(user.checkPW("tom", "plain"));
        Assert.assertTrue(user.checkPW("tom", "secret"));
    }
}
