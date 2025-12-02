package pl.baranski.controllers;

import java.io.Serializable;

public class ViewBean implements Serializable {
    private String pageTitle = "Panel Zarządzania Kotami (View Scope)";

    public String getPageTitle() { return pageTitle; }
    public void setPageTitle(String pageTitle) { this.pageTitle = pageTitle; }
}
