package org.anclav.doubledatasource;

import org.anclav.doubledatasource.config.TestContextConfiguration;
import org.assertj.core.api.JUnitJupiterSoftAssertions;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {
    DoubleDatasourceApplication.class,
    TestContextConfiguration.class
})
@ActiveProfiles("test")
@ContextConfiguration
public abstract class AbstractTest {

    @RegisterExtension
    protected final JUnitJupiterSoftAssertions softly = new JUnitJupiterSoftAssertions();
}
