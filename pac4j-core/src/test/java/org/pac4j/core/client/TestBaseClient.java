/*
  Copyright 2012 - 2013 Jerome Leleu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.core.client;

import junit.framework.TestCase;

import org.pac4j.core.context.MockWebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.TestsConstants;

/**
 * This class tests the {@link BaseClient} class.
 * 
 * @author Jerome Leleu
 * @since 1.4.0
 */
public final class TestBaseClient extends TestCase implements TestsConstants {
    
    public void testClone() {
        final BaseClient<Credentials, CommonProfile> oldClient = new MockBaseClient<Credentials>(TYPE);
        oldClient.setCallbackUrl(CALLBACK_URL);
        final BaseClient<Credentials, CommonProfile> newClient = oldClient.clone();
        assertEquals(oldClient.getName(), newClient.getName());
        assertEquals(oldClient.getCallbackUrl(), newClient.getCallbackUrl());
    }
    
    public void testDirectClient() throws RequiresHttpAction {
        final MockBaseClient<Credentials> client = new MockBaseClient<Credentials>(TYPE);
        client.setCallbackUrl(CALLBACK_URL);
        final MockWebContext context = MockWebContext.create();
        final String redirectionUrl = client.getRedirectionUrl(context);
        assertEquals(LOGIN_URL, redirectionUrl);
        final Credentials credentials = client.getCredentials(context);
        assertNull(credentials);
    }
    
    public void testIndirectClient() throws RequiresHttpAction {
        final MockBaseClient<Credentials> client = new MockBaseClient<Credentials>(TYPE, false);
        client.setCallbackUrl(CALLBACK_URL);
        final MockWebContext context = MockWebContext.create();
        final String redirectionUrl = client.getRedirectionUrl(context);
        assertEquals(CommonHelper.addParameter(CALLBACK_URL, BaseClient.NEEDS_CLIENT_REDIRECTION_PARAMETER, "true"),
                     redirectionUrl);
        context.addRequestParameter(BaseClient.NEEDS_CLIENT_REDIRECTION_PARAMETER, "true");
        try {
            client.getCredentials(context);
            fail("should throw RequiresHttpAction");
        } catch (final RequiresHttpAction e) {
            assertEquals(302, context.getResponseStatus());
            assertEquals(LOGIN_URL, context.getResponseHeaders().get("Location"));
            assertEquals("Needs client redirection", e.getMessage());
        }
    }
    
    public void testIndirectClientWithImmediate() throws RequiresHttpAction {
        final MockBaseClient<Credentials> client = new MockBaseClient<Credentials>(TYPE, false);
        client.setCallbackUrl(CALLBACK_URL);
        final MockWebContext context = MockWebContext.create();
        final String redirectionUrl = client.getRedirectionUrl(context, true);
        assertEquals(LOGIN_URL, redirectionUrl);
    }
    
    public void testNullCredentials() throws RequiresHttpAction {
        final MockBaseClient<Credentials> client = new MockBaseClient<Credentials>(TYPE, false);
        client.setCallbackUrl(CALLBACK_URL);
        assertNull(client.getUserProfile(null));
    }
}
