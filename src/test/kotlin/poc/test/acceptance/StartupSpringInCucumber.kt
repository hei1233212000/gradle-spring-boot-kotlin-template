package poc.test.acceptance

import io.cucumber.java8.En
import poc.test.AbstractAcceptanceTest

/**
 * It needs to be a Glue to make cucumber could scan it out. That's why it implements the [En]
 */
class StartupSpringInCucumber : AbstractAcceptanceTest(), En
