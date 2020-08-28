package dbms;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms
 * @date 2020/8/27 下午 02:15
 */
public class Fetching implements AutoCloseable{

    private final Driver driver;

    public Fetching(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void fetchData(final String message) {
        try (Session session = driver.session()) {
            String newNode = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(newNode);
        }
    }

    public static void main(String[] args) throws Exception {
        try (Fetching fetching = new Fetching("bolt://localhost:7687",
                "neo4j", "andy0212")) {
            fetching.fetchData("hello, world");
        }
    }
}
