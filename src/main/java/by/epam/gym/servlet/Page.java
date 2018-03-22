package by.epam.gym.servlet;

/**
 * Class describes server page.
 *
 * @author Eugene Makarenko
 */
public class Page {

    private String pageUrl;
    private boolean isRedirect;

    /**
     * Instantiates a new Page.
     *
     * @param pageUrl the page's url.
     * @param isRedirect boolean value of variable isRedirect;
     */
    public Page(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
    }

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    /**
     * Gets page url.
     *
     * @return the page's url.
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Sets page url.
     *
     * @param pageUrl the page's url.
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * Get isRedirect value.
     *
     * @return boolean value of isRedirect variable.
     */
    public boolean isRedirect() {
        return isRedirect;
    }

    /**
     * Sets isRedirect value.
     *
     * @param redirect the boolean value.
     */
    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
