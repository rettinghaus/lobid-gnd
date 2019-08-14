package apps;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modules.IndexComponent;
import play.Logger;

@RunWith(Parameterized.class)
public class IndexAppTest {

	private static IndexComponent index;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ "test/data/GND.jsonl" }, //
				{ "test/data/index" } });
	}

	private static String input;

	public IndexAppTest(String input) {
		IndexAppTest.input = input;
	}

	@BeforeClass
	public static void setUpEntityFacts() throws IOException {
		Index.indexEntityFacts();
	}

	@Before
	public void setUpIndex() throws IOException {
		String indexName = "test";
		Index.deleteIndex(indexName);
		index = Index.indexBaselineAndUpdates(input, input, indexName);
	}

	@Test
	public void testIndexExists() {
		long totalHits = index.query("*").getHits().getTotalHits();
		Logger.info("HITS for {}: {}", input, totalHits);
		Assert.assertTrue(totalHits > 0);
	}

}
