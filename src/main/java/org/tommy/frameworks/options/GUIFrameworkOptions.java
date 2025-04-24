package org.tommy.frameworks.options;

public class GUIFrameworkOptions {

    private boolean autoCancelClicks = true;

    public GUIFrameworkOptions setAutoCancelClicks(boolean value) {
        this.autoCancelClicks = value;
        return this;
    }

    public boolean isAutoCancelClicks() {
        return autoCancelClicks;
    }
}