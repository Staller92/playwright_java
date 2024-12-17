package com.components;

import com.microsoft.playwright.Page;

public abstract class BaseComponent {

    protected Page page;

    public BaseComponent(final Page page) {
        this.page = page;
    }
}
