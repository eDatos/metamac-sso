//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apereo.cas.support.oauth.validator.authorization;

import lombok.Generated;
import org.apereo.cas.audit.AuditableExecution;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.oauth.OAuth20ResponseTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuth20IdTokenResponseTypeAuthorizationRequestValidator extends OAuth20TokenResponseTypeAuthorizationRequestValidator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth20IdTokenResponseTypeAuthorizationRequestValidator.class);

    public OAuth20IdTokenResponseTypeAuthorizationRequestValidator(final ServicesManager servicesManager, final ServiceFactory<WebApplicationService> webApplicationServiceServiceFactory, final AuditableExecution registeredServiceAccessStrategyEnforcer) {
        super(servicesManager, webApplicationServiceServiceFactory, registeredServiceAccessStrategyEnforcer);
    }

    public OAuth20ResponseTypes getResponseType() {
        return OAuth20ResponseTypes.IDTOKEN;
    }
}
