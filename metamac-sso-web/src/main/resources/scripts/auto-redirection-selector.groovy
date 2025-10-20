import org.apereo.cas.web.*
import org.pac4j.core.context.*
import org.apereo.cas.pac4j.*
import org.apereo.cas.web.support.*
import java.util.stream.*
import java.util.*
import org.apereo.cas.configuration.model.support.delegation.*

/* Based on https://apereo.github.io/cas/7.3.x/integration/Delegate-Authentication-Redirection.html */
def run(Object[] args) {
    def (requestContext,service,registeredService,providers,applicationContext,logger) = args
    providers.forEach(provider -> {
        logger.info("Checking ${provider.name}...")
        if (provider.name.equals("Twitter")) {
            provider.autoRedirectType = DelegationAutoRedirectTypes.CLIENT
            return provider
        }
    })
    return null
}