/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.idea.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;
import org.apache.camel.idea.util.StringUtils;

/**
 * Service for holding preference for this plugin.
 */
public class CamelPreferenceService implements Disposable {

    public static final Icon DEFAULT_CAMEL_ICON = IconLoader.getIcon("/icons/camel.png");
    public static final Icon ALTERNATIVE_CAMEL_ICON = IconLoader.getIcon("/icons/camel2.png");

    private static final Logger LOG = Logger.getInstance(CamelPreferenceService.class);

    private volatile Icon currentCustomIcon;
    private volatile String currentCustomIconPath;

    private boolean realTimeEndpointValidation = true;
    private boolean realTimeSimpleValidation = true;
    private boolean highlightCustomOptions = true;
    private boolean downloadCatalog = true;
    private boolean scanThirdPartyComponents = true;
    private boolean scanThirdPartyLegacyComponents = true;
    private boolean showCamelIconInGutter = true;
    private String chosenCamelIcon = "Default Icon";
    private String customIconFilePath;

    public boolean isRealTimeEndpointValidation() {
        return realTimeEndpointValidation;
    }

    public void setRealTimeEndpointValidation(boolean realTimeEndpointValidation) {
        this.realTimeEndpointValidation = realTimeEndpointValidation;
    }

    public boolean isRealTimeSimpleValidation() {
        return realTimeSimpleValidation;
    }

    public void setRealTimeSimpleValidation(boolean realTimeSimpleValidation) {
        this.realTimeSimpleValidation = realTimeSimpleValidation;
    }

    public boolean isHighlightCustomOptions() {
        return highlightCustomOptions;
    }

    public void setHighlightCustomOptions(boolean highlightCustomOptions) {
        this.highlightCustomOptions = highlightCustomOptions;
    }

    public boolean isDownloadCatalog() {
        return downloadCatalog;
    }

    public void setDownloadCatalog(boolean downloadCatalog) {
        this.downloadCatalog = downloadCatalog;
    }

    public boolean isScanThirdPartyComponents() {
        return scanThirdPartyComponents;
    }

    public void setScanThirdPartyComponents(boolean scanThirdPartyComponents) {
        this.scanThirdPartyComponents = scanThirdPartyComponents;
    }

    public boolean isScanThirdPartyLegacyComponents() {
        return scanThirdPartyLegacyComponents;
    }

    public void setScanThirdPartyLegacyComponents(boolean scanThirdPartyLegacyComponents) {
        this.scanThirdPartyLegacyComponents = scanThirdPartyLegacyComponents;
    }

    public boolean isShowCamelIconInGutter() {
        return showCamelIconInGutter;
    }

    public void setShowCamelIconInGutter(boolean showCamelIconInGutter) {
        this.showCamelIconInGutter = showCamelIconInGutter;
    }

    public String getChosenCamelIcon() {
        return chosenCamelIcon;
    }

    public void setChosenCamelIcon(String chosenCamelIcon) {
        this.chosenCamelIcon = chosenCamelIcon;
    }

    public String getCustomIconFilePath() {
        return customIconFilePath;
    }

    public void setCustomIconFilePath(String customIconFilePath) {
        this.customIconFilePath = customIconFilePath;
    }

    public Icon getCamelIcon() {
        if (chosenCamelIcon.equals("Default Icon")) {
            return DEFAULT_CAMEL_ICON;
        } else if (chosenCamelIcon.equals("Alternative Icon")) {
            return ALTERNATIVE_CAMEL_ICON;
        }

        if (StringUtils.isNotEmpty(customIconFilePath)) {

            // use cached current icon
            if (customIconFilePath.equals(currentCustomIconPath)) {
                return currentCustomIcon;
            }

            Icon icon = IconLoader.findIcon(customIconFilePath);
            if (icon == null) {
                File file = new File(customIconFilePath);
                if (file.exists() && file.isFile()) {
                    try {
                        URL url = new URL("file:" + file.getAbsolutePath());
                        icon = IconLoader.findIcon(url, true);
                    } catch (MalformedURLException e) {
                        LOG.warn("Error loading custom icon", e);
                    }
                }
            }

            if (icon != null) {
                // cache current icon
                currentCustomIcon = icon;
                currentCustomIconPath = customIconFilePath;
                return currentCustomIcon;
            }
        }

        return DEFAULT_CAMEL_ICON;
    }

    @Override
    public void dispose() {
        // noop
    }
}
