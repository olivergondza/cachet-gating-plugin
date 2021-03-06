package com.redhat.jenkins.plugins.cachet;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.PluginManager;
import hudson.PluginWrapper;

import java.util.logging.Logger;

import javax.annotation.Nonnull;

import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

/*
 * The MIT License
 *
 * Copyright (c) Red Hat, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

@Extension
public final class GlobalCachetConfiguration extends GlobalConfiguration {
    private static final Logger log = Logger.getLogger(GlobalCachetConfiguration.class.getName());

    private String cachetUrl;

    public GlobalCachetConfiguration() {
        load();
    }

    public String getCachetUrl() {
        return cachetUrl;
    }

    @DataBoundSetter
    public void setCachetUrl(String cachetUrl) {
        this.cachetUrl = cachetUrl;
    }

    @Override
    public String getDisplayName() {
        return Messages.PluginName();
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws hudson.model.Descriptor.FormException {
        req.bindJSON(this, json);
        save();
        return true;
    }
    public static @Nonnull GlobalCachetConfiguration get() {
        ExtensionList<GlobalConfiguration> all = GlobalConfiguration.all();
        GlobalCachetConfiguration c = all.get(GlobalCachetConfiguration.class);
        if (c == null) {
            GlobalConfiguration registered = all.getDynamic(GlobalCachetConfiguration.class.getCanonicalName());
            if (registered != null) {
                PluginManager pm = Jenkins.getInstance().pluginManager;
                PluginWrapper source = pm.whichPlugin(registered.getClass());
                throw new AssertionError("Version mismatch: GlobalCIConfiguration provided by other plugin: " + source);
            }
            throw new AssertionError("GlobalCIConfiguration is not registered: " + all);
        }
        return c;
    }
}
