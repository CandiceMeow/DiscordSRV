package github.scarsz.discordsrv;

import github.scarsz.discordsrv.objects.metrics.MCStats;
import org.junit.Assert;
import org.junit.Test;
import org.bukkit.plugin.Plugin;
import static org.mockito.Mockito.*;

public class testCreateGraph {
    private Plugin mockedPlugin = mock(Plugin.class);

    @Test
    public void test() {
        try {
            MCStats testClass = new MCStats(mockedPlugin);
            String result = testClass.createGraph("newGraphName").getName();
            Assert.assertEquals(result, "newGraphName");
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}