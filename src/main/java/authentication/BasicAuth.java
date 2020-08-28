package authentication;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package authentication
 * @date 2020/8/28 下午 05:22
 */
public class BasicAuth implements AutoCloseable{
    private final Driver driver;

    public BasicAuth(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}