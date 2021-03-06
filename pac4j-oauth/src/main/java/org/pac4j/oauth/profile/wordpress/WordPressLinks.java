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
package org.pac4j.oauth.profile.wordpress;

import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.JsonObject;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class represents the links in WordPress.
 * 
 * @author Jerome Leleu
 * @since 1.1.0
 */
public final class WordPressLinks extends JsonObject {
    
    private static final long serialVersionUID = 650184033370922722L;
    
    private String self;
    
    private String help;
    
    private String site;
    
    @Override
    protected void buildFromJson(final JsonNode json) {
        this.self = (String) JsonHelper.convert(Converters.urlConverter, json, "self");
        this.help = (String) JsonHelper.convert(Converters.urlConverter, json, "help");
        this.site = (String) JsonHelper.convert(Converters.urlConverter, json, "site");
    }
    
    public String getSelf() {
        return this.self;
    }
    
    public String getHelp() {
        return this.help;
    }
    
    public String getSite() {
        return this.site;
    }
}
