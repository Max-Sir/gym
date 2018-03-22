package by.epam.gym.servlet;

public class Page {

    private String pageUrl;
    private boolean isRedirect;

    public Page(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
    }

    public Page() {
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
