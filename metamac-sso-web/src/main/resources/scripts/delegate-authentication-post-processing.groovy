import org.apereo.cas.web.*
import org.apereo.cas.web.support.*
import org.springframework.webflow.execution.*

// https://apereo.github.io/cas/7.3.x/integration/Delegate-Authentication-PostProcessing.html#delegated-authentication-post-processing
def run(Object[] args) {
    def (requestContext,providers,logger) = args
    def provider = providers[0]
    logger.info("Checking provider ${provider.name}...")
    def response = WebUtils.getHttpServletResponseFromExternalWebflowContext(requestContext)
    logger.debug("Redirecting to ${provider.redirectUrl}")
    response.sendRedirect(provider.redirectUrl);
}