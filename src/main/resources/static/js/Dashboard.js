document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    const toggle = document.getElementById("sidebarToggle");
    const overlay = document.getElementById("sidebarOverlay");
    const sidebar = document.getElementById("appSidebar");
    const media = window.matchMedia("(max-width: 900px)");
    const key = "complexlab-sidebar-collapsed";
    const mobile = () => media.matches;

    function storedCollapsed() {
        try { return localStorage.getItem(key) === "true"; }
        catch { return false; }
    }
    function storeCollapsed(value) {
        try { localStorage.setItem(key, String(value)); }
        catch { /* Storage may be disabled in private browsing. */ }
    }
    function isOpen() {
        return mobile() ? body.classList.contains("sidebar-open")
                        : !body.classList.contains("sidebar-collapsed");
    }
    function sync() {
        toggle?.setAttribute("aria-expanded", String(isOpen()));
        overlay?.setAttribute("aria-hidden", String(!mobile() || !isOpen()));
        if (sidebar) {
            sidebar.inert = mobile() && !isOpen();
        }
    }
    function closeMobile() {
        body.classList.remove("sidebar-open");
        sync();
    }
    function applyViewport() {
        body.classList.remove("sidebar-open");
        body.classList.toggle("sidebar-collapsed", !mobile() && storedCollapsed());
        sync();
    }

    toggle?.addEventListener("click", () => {
        if (mobile()) {
            body.classList.toggle("sidebar-open");
        } else {
            const collapsed = body.classList.toggle("sidebar-collapsed");
            storeCollapsed(collapsed);
        }
        sync();
    });
    overlay?.addEventListener("click", closeMobile);
    document.addEventListener("keydown", event => {
        if (event.key === "Escape" && mobile() && isOpen()) {
            closeMobile();
            toggle?.focus();
        }
    });
    sidebar?.querySelectorAll("a").forEach(link => {
        link.addEventListener("click", () => {
            if (mobile()) closeMobile();
        });
    });
    if (media.addEventListener) media.addEventListener("change", applyViewport);
    else media.addListener(applyViewport);
    applyViewport();
});
