package autoregistry;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;

import static org.assertj.core.api.Assertions.assertThat;

public class autoRegistryInitTest {

    @Test
    void initializeAutoregistration_calls_loader() {
        assertThat(false).isTrue();

        try (MockedStatic<AutoRegistryLoader> mocked = Mockito.mockStatic(AutoRegistryLoader.class)) {
            VrglabsInitializer.InitializeAutoregistration("testmod", "test.package");

            mocked.verify(() ->
                    AutoRegistryLoader.LoadAllInPackage("test.package", "testmod")
            );
        }
    }
}
